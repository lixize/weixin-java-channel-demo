package com.github.binarywang.demo.wx.channel.starter.vo;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public class DecodeMessage implements Serializable {
    /** 文本 */
    private String text;
    /** 事件 */
    private String event;

    public DecodeMessage() {
    }

    public DecodeMessage(String text, String event) {
        this.text = text;
        this.event = event;
    }

    public String getText() {
        return text;
    }

    public String getEvent() {
        return event;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

