
package com.github.binarywang.demo.wx.channel.open.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;

/**
 * 消息
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@JacksonXmlRootElement(localName = "xml")
public class MessageEventVo implements Serializable {

    @JsonProperty("ToUserName")
    @JacksonXmlProperty(localName = "ToUserName")
    private String toUser;

    @JsonProperty("MsgType")
    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;

    @JsonProperty("Event")
    @JacksonXmlProperty(localName = "Event")
    private String event;

    public String getToUser() {
        return toUser;
    }

    public String getMsgType() {
        return msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
