package com.github.binarywang.demo.wx.channel.open.service;

import java.util.Set;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public interface ChannelMessageService {
    /**
     * 处理回调消息
     *
     * @param requestBody 消息体]
     * @param encryptType 加密方式
     * @return String
     */
    String processMessage(String requestBody, String encryptType);

    /**
     * 获取所有支持的消息
     *
     * @return Set
     */
    Set<String> listSupportEvents();
}
