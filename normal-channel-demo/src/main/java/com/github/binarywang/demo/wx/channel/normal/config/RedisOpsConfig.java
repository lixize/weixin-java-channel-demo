package com.github.binarywang.demo.wx.channel.normal.config;

import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Configuration
public class RedisOpsConfig {

    /**
     * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
     */
    @Bean
    @DependsOn("stringRedisTemplate")
    public WxRedisOps wxRedisOps(StringRedisTemplate stringRedisTemplate) {
        return new RedisTemplateWxRedisOps(stringRedisTemplate);
    }

}
