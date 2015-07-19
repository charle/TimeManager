package com.jing.du.Main.Model;

import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/7/18.
 */
public class User extends DataSupport {
    private int id;
    private String sign;
    private String nickName;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
