package com.jing.du.common.file;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by charle-chen on 15/7/11.
 */
public class OutputDb {

    /**
     * 功能：替换
     *
     * @param inputTpl  输入模板
     * @param inputData 输入参数
     */
    public StringBuffer replace(String inputTpl, HashMap<String, String> inputData,boolean changeRow) {
        StringBuffer sb = new StringBuffer();
        try {
            Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
            Matcher matcher = pattern.matcher(inputTpl);
            while (matcher.find()) {
                String name = matcher.group(1);// 键名
                String value = (String) inputData.get(name);// 键值
                if (value == null) {
                    value = "";
                } else {
                    value = value.replaceAll("\\$", "\\\\\\$");
                }
                matcher.appendReplacement(sb, value);
            }

            // 最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
            matcher.appendTail(sb);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(changeRow){
            return sb.append(System.getProperty("line.separator"));
        }
        return sb;

    }

    /**
     * 功能：写入手机文件
     *
     * @param fileName   写入文件名
     * @param stringData 写入数据
     * @param 是否追加
     */
    public static void writeDocument(String fileName, String stringData,boolean append) {
        try {
            FileOutputStream outStream = new FileOutputStream("/mnt/sdcard/" + fileName, append);
            OutputStreamWriter writer = new OutputStreamWriter(outStream, "utf8");
            writer.write(stringData);
            writer.flush();
            writer.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
