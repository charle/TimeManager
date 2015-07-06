package com.jing.du.Main.Model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by charle-chen on 15/6/21.
 */
public class Tag  extends DataSupport implements Serializable{
    private int id;
    private Category category;
    private String name;
    private List<DiaryItem> diaryItems = new ArrayList<DiaryItem>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DiaryItem> getDiaryItems() {
        return diaryItems;
    }

    public void setDiaryItems(List<DiaryItem> diaryItems) {
        this.diaryItems = diaryItems;
    }
}
