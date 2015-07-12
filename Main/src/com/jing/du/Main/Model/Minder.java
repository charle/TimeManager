package com.jing.du.Main.Model;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by charle-chen on 15/7/12.
 */
public class Minder extends DataSupport {
    private Date createtime;
    private String title;
    private String content;
    private int minderType;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date creattime) {
        this.createtime = creattime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMinderType() {
        return minderType;
    }

    public void setMinderType(int minderType) {
        this.minderType = minderType;
    }
}
