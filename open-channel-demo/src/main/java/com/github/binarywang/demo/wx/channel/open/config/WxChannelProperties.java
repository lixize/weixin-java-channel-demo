package com.github.binarywang.demo.wx.channel.open.config;


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

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }
}
