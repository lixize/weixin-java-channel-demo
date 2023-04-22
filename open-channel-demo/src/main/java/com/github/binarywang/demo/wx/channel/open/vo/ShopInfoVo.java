package com.github.binarywang.demo.wx.channel.open.vo;

import java.io.Serializable;

/**
 * 店铺信息
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public class ShopInfoVo implements Serializable {
    /** 店铺名称 */
    private String nickname;

    /** 店铺头像URL */
    private String headImgUrl;

    /** 店铺类型，目前为"企业"或"个体工商户" */
    private String subjectType;

    public ShopInfoVo() {
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }
}
