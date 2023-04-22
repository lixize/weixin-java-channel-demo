package com.github.binarywang.demo.wx.channel.normal.service.impl;

import com.github.binarywang.demo.wx.channel.normal.config.WxChannelProperties;
import me.chanjar.weixin.channel.config.WxChannelConfig;
import me.chanjar.weixin.channel.config.impl.WxChannelRedisConfigImpl;
import me.chanjar.weixin.common.redis.WxRedisOps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 视频号小店服务配置实现
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Service("wxChannelConfig")
public class MyWxChannelConfigImpl extends WxChannelRedisConfigImpl implements WxChannelConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyWxChannelConfigImpl.class);

    private final WxChannelProperties properties;

    public MyWxChannelConfigImpl(WxRedisOps wxRedisOps, WxChannelProperties properties) {
        super(wxRedisOps, "wx:channel:");
        logger.info("MyWxChannelConfigImpl loading...");
        this.properties = properties;
        this.config(properties);
    }

    protected void config(WxChannelProperties properties) {
        this.setAppid(StringUtils.trimToNull(properties.getAppid()));
        this.setSecret(StringUtils.trimToNull(properties.getSecret()));
        this.setToken(StringUtils.trimToNull(properties.getToken()));
        this.setAesKey(StringUtils.trimToNull(properties.getAesKey()));
        this.setMsgDataFormat(StringUtils.trimToNull(properties.getMsgDataFormat()));

        this.setRetrySleepMillis(1000);
        this.setMaxRetryTimes(5);
    }


}
