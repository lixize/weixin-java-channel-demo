package com.github.binarywang.demo.wx.channel.open.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决引入jackson-dataformat-xml导致的xml转换问题
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Configuration
public class WebInterceptorAdapter implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> aConverters) {
        aConverters.removeIf (aConverter -> (aConverter instanceof MappingJackson2XmlHttpMessageConverter));
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

}

