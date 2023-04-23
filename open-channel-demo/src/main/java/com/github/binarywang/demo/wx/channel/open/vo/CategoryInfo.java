package com.github.binarywang.demo.wx.channel.open.vo;

import java.io.Serializable;

/**
 * 类目信息
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public class CategoryInfo implements Serializable {

    private String first;
    private String second;

    public CategoryInfo() {
    }

    public CategoryInfo(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
