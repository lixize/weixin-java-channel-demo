package com.github.binarywang.demo.wx.channel.open.service;

import com.github.binarywang.demo.wx.channel.open.vo.DecodeMessage;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public interface OpenMessageService {

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
     * ticket回调
     *
     * @param requestBody    请求体
     * @param timestamp      时间戳
     * @param nonce          随机串
     * @param signature      前面
     * @param encType        加密类型
     * @param msgSignature   消息签名
     * @return String
     */
    String ticketCallback(String requestBody, String timestamp, String nonce, String signature,
            String encType, String msgSignature);

    /**
     * 解密消息
     *
     * @param requestBody  消息体
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @param msgSignature 签名串
     * @return 解密后的消息体
     */
    DecodeMessage decodeMessage(String requestBody, String timestamp, String nonce, String msgSignature);

    /**
     * 处理回调消息
     *
     * @param requestBody    请求体
     * @param encType        加密类型
     * @return String
     */
    String processMessage(String requestBody,String encType);

    /**
     * 加密消息
     *
     * @param target 消息体
     * @return 加密后的消息体
     */
    String encodeMessage(String target);

    /**
     * 过审测试 模拟
     *
     * @param requestBody    请求体
     * @param appId          appId
     * @param openid         openId
     * @return String
     */
    String appCallBlackForTest(String requestBody, String appId, String openid);

}
