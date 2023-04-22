package com.github.binarywang.demo.wx.channel.normal.service.impl;

import me.chanjar.weixin.channel.api.impl.WxChannelServiceImpl;
import me.chanjar.weixin.channel.config.WxChannelConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 视频号小店服务实现
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Service("wxChannelService")
public class MyWxChannelServiceImpl extends WxChannelServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MyWxChannelServiceImpl.class);


    public MyWxChannelServiceImpl(WxChannelConfig wxChannelConfig) {
        // 初始化配置，不要直接this.config = wxChannelConfig
        this.setConfig(wxChannelConfig);
        logger.info("MyWxChannelServiceImpl loading...");

    }
}
