package com.github.binarywang.demo.wx.channel.starter.controller;

import com.github.binarywang.demo.wx.channel.starter.service.ChannelMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信回调消息 控制器
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@RestController
@RequestMapping("/wx/notify/{appid}")
public class WechatNotifyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ChannelMessageService channelMessageService;

    /**
     * 接收微信服务器的认证消息
     *
     * @param appid     视频号appId(注意这是路径的参数)
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return echostr
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@PathVariable String appid,
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {
        logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
                signature, timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (channelMessageService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }

    /**
     * 接收微信服务器的消息
     *
     * @param appid        视频号appId(注意这是路径的参数)
     * @param requestBody  消息体
     * @param msgSignature 签名串
     * @param encryptType  加密方式
     * @param signature    微信加密签名
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @return String
     */
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable String appid,
            @RequestBody String requestBody,
            @RequestParam(name = "msg_signature", required = false) String msgSignature,
            @RequestParam(name = "encrypt_type", required = false) String encryptType,
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce) {
        logger.info("\n接收微信请求：[msg_signature=[{}], encrypt_type=[{}], signature=[{}]," +
                        " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                msgSignature, encryptType, signature, timestamp, nonce, requestBody);
        return channelMessageService.processMessage(requestBody, msgSignature, encryptType, signature,
                timestamp, nonce);
    }
}
