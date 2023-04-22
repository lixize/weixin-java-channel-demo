package com.github.binarywang.demo.wx.channel.open.service.impl;

import com.github.binarywang.demo.wx.channel.open.config.WxChannelProperties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import me.chanjar.weixin.channel.config.WxChannelConfig;
import me.chanjar.weixin.channel.config.impl.WxChannelDefaultConfigImpl;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.WxOpenService;
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
public class MyWxChannelConfigImpl extends WxChannelDefaultConfigImpl implements WxChannelConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyWxChannelConfigImpl.class);

    private WxOpenService wxOpenService;

    private WxOpenConfigStorage wxOpenConfigStorage;

    public MyWxChannelConfigImpl(WxOpenService wxOpenService, WxChannelProperties properties,
            WxOpenConfigStorage wxOpenConfigStorage) {
        this.setAppid(StringUtils.trimToNull(properties.getAppid()));
        this.setRetrySleepMillis(1000);
        this.setMaxRetryTimes(5);
        this.wxOpenService = wxOpenService;
        this.wxOpenConfigStorage = wxOpenConfigStorage;

        logger.info("MyWxChannelConfigImpl loading...");
    }

    @Override
    public String getAccessToken() {
        WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
        String accessToken = null;
        try {
            accessToken = wxOpenComponentService.getAuthorizerAccessToken(this.getAppid(), false);
        } catch (WxErrorException e) {
            logger.error("获取小店accessToken失败", e);
        }
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Lock getAccessTokenLock() {
        return new ReentrantLock();
    }

    @Override
    public boolean isAccessTokenExpired() {
        return wxOpenConfigStorage.isAuthorizerAccessTokenExpired(this.getAppid());
    }

    @Override
    public synchronized void updateAccessToken(WxAccessToken accessToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void expireAccessToken() {
        wxOpenConfigStorage.expireAuthorizerAccessToken(this.getAppid());
    }
}
