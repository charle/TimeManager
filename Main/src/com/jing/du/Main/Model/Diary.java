package com.jing.du.Main.Model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by charle-chen on 15/6/27.
 */
public class Diary extends DataSupport implements Serializable {
    private int id;
    private Date createTime;
    private int weatherType;
    private String address;
    private List<DiaryItem> diaryItemArrayList=new ArrayList<DiaryItem>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(int weatherType) {
        this.weatherType = weatherType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DiaryItem> getDiaryItemArrayList() {
        return diaryItemArrayList;
    }

    public void setDiaryItemArrayList(List<DiaryItem> diaryItemArrayList) {
        this.diaryItemArrayList = diaryItemArrayList;
    }
}
