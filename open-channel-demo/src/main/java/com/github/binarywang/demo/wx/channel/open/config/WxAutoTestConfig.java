package com.github.binarywang.demo.wx.channel.open.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Configuration
@ConfigurationProperties(prefix = "wx.test")
public class WxAutoTestConfig {

    private List<String> mpAppIds = new ArrayList<>();

    private List<String> maAppIds = new ArrayList<>();

    public List<String> getMpAppIds() {
        return mpAppIds;
    }

    public List<String> getMaAppIds() {
        return maAppIds;
    }

    public void setMpAppIds(List<String> mpAppIds) {
        this.mpAppIds = mpAppIds;
    }

    public void setMaAppIds(List<String> maAppIds) {
        this.maAppIds = maAppIds;
    }
}
