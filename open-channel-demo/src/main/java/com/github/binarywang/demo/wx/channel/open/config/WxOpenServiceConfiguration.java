package com.github.binarywang.demo.wx.channel.open.config;

import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisTemplateConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 微信开放平台相关服务自动注册
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Configuration
public class WxOpenServiceConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(WxOpenServiceConfiguration.class);

    @Bean
    @DependsOn(value = {"redisTemplate", "wxOpenProperties"})
    public WxOpenConfigStorage wxOpenConfigStorage(StringRedisTemplate redisTemplate, WxOpenProperties properties) {
        WxOpenInMemoryConfigStorage config = new WxOpenInRedisTemplateConfigStorage(redisTemplate, "wx:open:");

        String componentAppId = StringUtils.trimToNull(properties.getComponentAppId());
        String componentAppSecret = StringUtils.trimToNull(properties.getComponentSecret());
        String componentToken = StringUtils.trimToNull(properties.getComponentToken());
        String componentAesKey = StringUtils.trimToNull(properties.getComponentAesKey());
        if (StringUtils.isAnyBlank(componentAppId, componentAppSecret, componentToken, componentAesKey)) {
            logger.error("配置参数不完整, {}/{}/{}/{}", componentAppId, componentAppSecret, componentToken,
                    componentAesKey);
        }
        config.setWxOpenInfo(componentAppId, componentAppSecret, componentToken, componentAesKey);

        config.setRetrySleepMillis(1000);
        config.setMaxRetryTimes(5);

        return config;
    }

    @Bean
    public WxOpenService wxOpenService(WxOpenConfigStorage wxOpenConfigStorage) {
        WxOpenService wxOpenService = new WxOpenServiceImpl();
        wxOpenService.setWxOpenConfigStorage(wxOpenConfigStorage);
        return wxOpenService;
    }

    @Bean
    public WxOpenMessageRouter wxOpenMessageRouter(WxOpenService wxOpenService) {
        return new WxOpenMessageRouter(wxOpenService);
    }

    @Bean
    public WxOpenComponentService wxOpenComponentService(WxOpenService wxOpenService) {
        return wxOpenService.getWxOpenComponentService();
    }


}
