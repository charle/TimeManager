package com.jing.du.Main.Model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.sql.Time;

/**
 * Created by charle-chen on 15/6/29.
 */
public class DiaryItem extends DataSupport implements Serializable{
    private int id;
    private String beginTime;
    private String endTime;
    private Diary diary;
    private Category category;
    private Tag tag;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
