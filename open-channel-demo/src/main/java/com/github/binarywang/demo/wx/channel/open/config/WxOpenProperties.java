package com.github.binarywang.demo.wx.channel.open.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Configuration
@ConfigurationProperties(prefix = WxOpenProperties.PREFIX)
public class WxOpenProperties {
    public static final String PREFIX = "wx.open";

    /** 设置微信三方平台的appid */
    private String componentAppId;

    /** 设置微信三方平台的app secret */
    private String componentSecret;

    /** 设置微信三方平台的token */
    private String componentToken;

    /** 设置微信三方平台的EncodingAESKey */
    private String componentAesKey;

    public String getComponentAppId() {
        return componentAppId;
    }

    public String getComponentSecret() {
        return componentSecret;
    }

    public String getComponentToken() {
        return componentToken;
    }

    public String getComponentAesKey() {
        return componentAesKey;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    public void setComponentSecret(String componentSecret) {
        this.componentSecret = componentSecret;
    }

    public void setComponentToken(String componentToken) {
        this.componentToken = componentToken;
    }

    public void setComponentAesKey(String componentAesKey) {
        this.componentAesKey = componentAesKey;
    }
}
