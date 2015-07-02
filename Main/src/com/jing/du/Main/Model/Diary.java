package com.jing.du.Main.Model;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by charle-chen on 15/6/27.
 */
public class Diary extends DataSupport {
    private int id;
    private Date createTime;
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

    public List<DiaryItem> getDiaryItemArrayList() {
        return diaryItemArrayList;
    }

    public void setDiaryItemArrayList(List<DiaryItem> diaryItemArrayList) {
        this.diaryItemArrayList = diaryItemArrayList;
    }
}
