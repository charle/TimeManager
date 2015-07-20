package com.jing.du.Main.Model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by charle-chen on 15/7/12.
 */
public class Minder extends DataSupport implements Serializable{

    private int id;
    private Date createtime;
    private String title;
    private String content;
    private int minderType;
    private Date mindTime;
    private int mindOrNotMind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getMindTime() {
        return mindTime;
    }

    public void setMindTime(Date mindTime) {
        this.mindTime = mindTime;
    }

    public int getMindOrNotMind() {
        return mindOrNotMind;
    }

    public void setMindOrNotMind(int mindOrNotMind) {
        this.mindOrNotMind = mindOrNotMind;
    }
}
