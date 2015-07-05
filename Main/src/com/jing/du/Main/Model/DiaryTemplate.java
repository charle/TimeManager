package com.jing.du.Main.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charle-chen on 15/7/3.
 */
public class DiaryTemplate {
    private String create_time;
    private String weather;
    private String address;
    private List<DiaryItemTemplate> list = new ArrayList<DiaryItemTemplate>();

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DiaryItemTemplate> getList() {
        return list;
    }

    public void setList(List<DiaryItemTemplate> list) {
        this.list = list;
    }
}
