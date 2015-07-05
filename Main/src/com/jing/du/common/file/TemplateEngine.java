package com.jing.du.common.file;

import android.content.Context;
import org.apache.http.util.EncodingUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by charle-chen on 15/7/2.
 */
public class TemplateEngine {

    private static String confPath;   // 配置文件，包括完整绝对路径
    private static String enter;
    private Context context;

    // 构造函数，初始化数据
    public TemplateEngine(String confPath, Context mContext) {
        TemplateEngine.confPath = confPath;
        TemplateEngine.enter = System.getProperty("line.separator");
        this.context = mContext;
    }

    /**
     * 替换模板变量
     *
     * @param template
     * @param data
     * @return
     */
    public static String replaceArgs(String template, List<HashMap<String, Object>> list) {
        // sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
        StringBuffer sb = new StringBuffer();
        try {
            for (HashMap<String, Object> data : list) {
                //匹配${}
                Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
                Matcher matcher = pattern.matcher(template);
                while (matcher.find()) {
                    String name = matcher.group(1);// 键名
                    String value = (String) data.get(name);// 键值
                    if (value == null) {
                        value = "";
                    } else {
                        value = value.replaceAll("\\$", "\\\\\\$");
                    }
                    matcher.appendReplacement(sb, value);
                }

                // 最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
                matcher.appendTail(sb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString() + enter;   //加一个空行（结束行）
    }

    /**
     * 读取配置文件
     *
     * @param confPath
     */
    public static String readConf(String confPath) {
        StringBuffer sb = new StringBuffer();
        try {
            FileReader fr = new FileReader(confPath);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line + enter);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 写入到配置文件
     *
     * @param confPath
     * @param stringData
     * @param isAppend   是否追加写入
     */
    public static void writeConf(String fileName, String stringData, boolean isAppend) {
        try {
            FileOutputStream outStream = new FileOutputStream("/mnt/sdcard/" + fileName, true);
            OutputStreamWriter writer = new OutputStreamWriter(outStream, "utf8");
            writer.write(stringData);
            writer.flush();
            writer.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getConfPath() {
        return confPath;
    }

    public static void setConfPath(String confPath) {
        TemplateEngine.confPath = confPath;
    }

    public static String getEnter() {
        return enter;
    }

    public static void setEnter(String enter) {
        TemplateEngine.enter = enter;
    }

    public String readTemplate(String tplPath) {
        String res = "";
        try {
            //得到资源中的asset数据流
            InputStream in = context.getResources().getAssets().open(tplPath);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据中括号内的ID查询对象
     *
     * @param id
     * @return
     */
    public String getObject(String id) {
        StringBuffer sb = new StringBuffer();
        try {
            FileReader fr = new FileReader(confPath);
            LineNumberReader nr = new LineNumberReader(fr);
            String line = "";
            int startLineNumber = -1;
            while ((line = nr.readLine()) != null) {
                // 匹配到开头
                if (line.indexOf("[" + id + "]") >= 0) {
                    startLineNumber = nr.getLineNumber();
                }
                if (startLineNumber != -1 && nr.getLineNumber() >= startLineNumber) {
                    sb.append(line + enter);
                    // 匹配到结束，以换行符结束
                    if (line.trim().equals("")) {
                        break;
                    }
                }
            }
            nr.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 删除对象（IO没有对文本直接删除的方法，先读出所有内容，过滤删除内容，重新写回文件。）
     *
     * @param id
     * @return
     */
    public void deleteObject(String id) {
        // 读取配置文件
        String data = readConf(confPath);
        // 过滤删除内容
        data = data.replace(getObject(id), "");
        // 重新写回文件
        writeConf(confPath, data, false);
    }

}
