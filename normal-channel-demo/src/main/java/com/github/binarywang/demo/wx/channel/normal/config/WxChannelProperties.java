package com.github.binarywang.demo.wx.channel.normal.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 属性配置类
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Configuration
@ConfigurationProperties(prefix = WxChannelProperties.PREFIX)
public class WxChannelProperties {
  public static final String PREFIX = "wx.channel";

  /** 设置视频号小店的appid */
  private String appid;

  /** 设置视频号小店的Secret */
  private String secret;

  /** 设置视频号小店消息服务器配置的token */
  private String token;

  /** 设置视频号小店消息服务器配置的EncodingAESKey */
  private String aesKey;

  /** 消息格式，XML或者JSON */
  private String msgDataFormat = "JSON";

  /** 是否使用稳定的access_token */
  private boolean useStableAccessToken = false;

  public WxChannelProperties() {
  }

  public String getAppid() {
    return appid;
  }

  public String getSecret() {
    return secret;
  }

  public String getToken() {
    return token;
  }

  public String getAesKey() {
    return aesKey;
  }

  public String getMsgDataFormat() {
    return msgDataFormat;
  }

  public boolean isUseStableAccessToken() {
    return useStableAccessToken;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setAesKey(String aesKey) {
    this.aesKey = aesKey;
  }

  public void setMsgDataFormat(String msgDataFormat) {
    this.msgDataFormat = msgDataFormat;
  }

  public void setUseStableAccessToken(boolean useStableAccessToken) {
    this.useStableAccessToken = useStableAccessToken;
  }
}
