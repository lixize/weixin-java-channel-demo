package com.github.binarywang.demo.wx.channel.starter.service;

import java.util.Set;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public interface ChannelMessageService {

    /**
     * 获取消息格式
     *
     * @return 消息格式 JSON / XML
     */
    String getMsgDataFormat();

    /**
     * 验证消息的确来自微信服务器. 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319&token=&lang=zh_CN
     *
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param signature 微信加密签名
     * @return 是否成功
     */
    boolean checkSignature(String timestamp, String nonce, String signature);

    /**
     * 处理回调消息
     *
     * @param requestBody  消息体
     * @param msgSignature 签名串
     * @param encryptType  加密方式
     * @param signature    微信加密签名
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @return String
     */
    String processMessage(String requestBody, String msgSignature, String encryptType, String signature,
            String timestamp, String nonce);

    /**
     * 处理回调消息
     *
     * @param requestBody 消息体]
     * @return json 是否为json格式
     * @param encryptType 加密方式
     * @return String
     */
    String processMessage(String requestBody, boolean json, String encryptType);

    /**
     * 获取所有支持的消息
     *
     * @return Set
     */
    Set<String> listSupportEvents();
}
