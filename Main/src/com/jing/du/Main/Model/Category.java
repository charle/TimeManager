package com.jing.du.Main.Model;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charle-chen on 15/6/25.
 */
public class Category extends DataSupport {
    private int id;
    private String name;
    private List<DiaryItem> diaryItems = new ArrayList<DiaryItem>();
    private List<Tag> tagList = new ArrayList<Tag>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<DiaryItem> getDiaryItems() {
        return diaryItems;
    }

    public void setDiaryItems(List<DiaryItem> diaryItems) {
        this.diaryItems = diaryItems;
    }
}
