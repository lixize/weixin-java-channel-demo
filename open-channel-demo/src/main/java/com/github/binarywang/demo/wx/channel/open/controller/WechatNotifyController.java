package com.github.binarywang.demo.wx.channel.open.controller;


import com.github.binarywang.demo.wx.channel.open.config.WxAutoTestConfig;
import com.github.binarywang.demo.wx.channel.open.service.ChannelMessageService;
import com.github.binarywang.demo.wx.channel.open.service.OpenMessageService;
import com.github.binarywang.demo.wx.channel.open.vo.DecodeMessage;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/wx/notify")
public class WechatNotifyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected OpenMessageService openMessageService;

    @Autowired
    protected ChannelMessageService channelMessageService;

    @Autowired
    private WxAutoTestConfig wxAutoTestConfig;

    /** 视频号小店的事件 */
    private Set<String> channelEvents;

    /**
     * 接收微信开放平台的消息
     *
     * @param requestBody  请求体
     * @param timestamp    时间戳
     * @param nonce        随机串
     * @param signature    签名
     * @param encType      加密类型
     * @param msgSignature 消息签名
     * @return Object
     */
    @RequestMapping("/receive_ticket")
    public Object receiveTicket(@RequestBody(required = false) String requestBody,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce, @RequestParam("signature") String signature,
            @RequestParam(name = "encrypt_type", required = false) String encType,
            @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        this.logger.info("\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);
        return openMessageService.ticketCallback(requestBody, timestamp, nonce, signature, encType, msgSignature);
    }

    /**
     * @param requestBody  请求体
     * @param appId        appId
     * @param signature    签名
     * @param timestamp    时间戳
     * @param nonce        随机串
     * @param openid       openid
     * @param encType      加密类型
     * @param msgSignature 消息签名
     * @return Object
     */
    @RequestMapping("{appId}/callback")
    public Object callback(@RequestBody(required = false) String requestBody,
            @PathVariable("appId") String appId,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("openid") String openid,
            @RequestParam("encrypt_type") String encType,
            @RequestParam("msg_signature") String msgSignature) {
        this.logger.info(
                "\n接收微信请求：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
        if (!StringUtils.equalsIgnoreCase("aes", encType)
                || !openMessageService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        // aes加密的消息
        DecodeMessage dm = openMessageService.decodeMessage(requestBody, timestamp, nonce, msgSignature);
        String plainText = dm.getText();
        if (wxAutoTestConfig.getMpAppIds() != null && wxAutoTestConfig.getMpAppIds().contains(appId)) {
            return openMessageService.appCallBlackForTest(plainText, appId, openid);
        }
        if (wxAutoTestConfig.getMaAppIds() != null && wxAutoTestConfig.getMaAppIds().contains(appId)) {
            return openMessageService.appCallBlackForTest(plainText, appId, openid);
        }

        String eventKey = dm.getEvent();
        // 视频号的消息交给视频号消息服务处理
        if (isChannelMessage(eventKey)) {
            return channelMessageService.processMessage(plainText, encType);
        }
        // 剩余的交给开放平台处理
        return openMessageService.processMessage(plainText, encType);
    }

    /**
     * 判断是否视频号小店的消息
     *
     * @param eventKey 事件Key
     * @return boolean
     */
    private boolean isChannelMessage(String eventKey) {
        if (StringUtils.isEmpty(eventKey)) {
            return false;
        }
        if (channelEvents == null) {
            channelEvents = channelMessageService.listSupportEvents();
        }
        if (channelEvents == null) {
            logger.error("视频号小店支持的事件为空");
            return false;
        }
        return channelEvents.contains(eventKey);
    }

}
