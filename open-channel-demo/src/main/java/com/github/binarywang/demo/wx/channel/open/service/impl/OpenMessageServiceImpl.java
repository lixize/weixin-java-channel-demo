package com.github.binarywang.demo.wx.channel.open.service.impl;

import com.github.binarywang.demo.wx.channel.open.service.OpenMessageService;
import com.github.binarywang.demo.wx.channel.open.vo.DecodeMessage;
import com.github.binarywang.demo.wx.channel.open.vo.MessageEventVo;
import me.chanjar.weixin.channel.util.XmlUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import me.chanjar.weixin.open.util.WxOpenCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Service
public class OpenMessageServiceImpl implements OpenMessageService {

    private static final Logger logger = LoggerFactory.getLogger(OpenMessageServiceImpl.class);

    @Autowired
    private WxOpenService wxOpenService;

    @Autowired
    private WxOpenConfigStorage wxOpenConfigStorage;

    @Override
    public boolean checkSignature(String timestamp, String nonce, String signature) {
        return wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature);
    }

    @Override
    public String ticketCallback(String requestBody, String timestamp, String nonce, String signature, String encType,
            String msgSignature) {
        if (!StringUtils.equalsIgnoreCase("aes", encType)
                || !wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        if (StringUtils.isNotBlank(requestBody)) {
            // aes加密的消息
            WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody,
                    wxOpenConfigStorage, timestamp, nonce, msgSignature);
            logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            try {
                String out = this.route(inMessage);
                logger.debug("\n组装回复信息：{}", out);
            } catch (WxErrorException e) {
                logger.error("receive_ticket", e);
            }
        } else {
            logger.error("requestBody空的，有点离谱");
        }
        return "success";
    }

    /**
     * 消息路由
     *
     * @param wxMessage 消息体
     * @return string
     */
    protected String route(WxOpenXmlMessage wxMessage) throws WxErrorException {
        WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
        return componentService.route(wxMessage);
    }

    @Override
    public DecodeMessage decodeMessage(String requestBody, String timestamp, String nonce, String msgSignature) {
        String plainText = null;
        try {
            WxOpenCryptUtil cryptUtil = new WxOpenCryptUtil(wxOpenConfigStorage);
            plainText = cryptUtil.decryptXml(msgSignature, timestamp, nonce, requestBody);
            logger.info("\n\n{}\n", plainText);
        } catch (Throwable e) {
            logger.error("解密异常", e);
            String aesKey = wxOpenConfigStorage.getComponentAesKey();
            String token = wxOpenConfigStorage.getComponentToken();
            String appId = wxOpenConfigStorage.getComponentAppId();
            logger.info("requestBody:{}, timestamp:{}, nonce:{}, msgSignature:{}, token:{}, aesKey:{}, appid:{}",
                    requestBody, timestamp, nonce, msgSignature, aesKey, token, appId);
        }

        return new DecodeMessage(plainText, this.getEventKey(plainText));
    }

    @Override
    public String processMessage(String requestBody, String encType) {
        WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
        WxOpenXmlMessage message = WxOpenXmlMessage.fromXml(requestBody);
        try {
            return wxOpenComponentService.route(message);
        } catch (WxErrorException e) {
            logger.error("处理消息异常", e);
        }
        return "success";
    }

    protected String getEventKey(String plainText) {
        MessageEventVo message = XmlUtils.decode(plainText, MessageEventVo.class);
        if (message != null) {
            return message.getEvent();
        }
        return null;
    }

    @Override
    public String encodeMessage(String target) {
        String result = null;
        try {
            WxOpenCryptUtil cryptUtil = new WxOpenCryptUtil(wxOpenConfigStorage);
            result = cryptUtil.encrypt(target);
        } catch (Throwable e) {
            logger.error("加密异常", e);
        }
        return result;
    }

    @Override
    public String appCallBlackForTest(String requestBody, String appId, String openid) {
        String out = "";
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
        try {
            WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
            if (StringUtils.equals(inMessage.getMsgType(), "text")) {
                if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
                    out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
                            WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                                    .fromUser(inMessage.getToUser())
                                    .toUser(inMessage.getFromUser())
                                    .build(), wxOpenConfigStorage
                    );
                } else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {
                    String authorizationCode = inMessage.getContent().replace("QUERY_AUTH_CODE:", "");
                    this.getQueryAuth(authorizationCode);
                    String msg = authorizationCode + "_from_api";
                    WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(inMessage.getFromUser())
                            .build();
                    wxOpenComponentService.getWxMpServiceByAppid(appId).getKefuService()
                            .sendKefuMessage(kefuMessage);
                }
            } else if (StringUtils.equals(inMessage.getMsgType(), "event")) {
                WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback")
                        .toUser(inMessage.getFromUser()).build();
                wxOpenComponentService.getWxMpServiceByAppid(appId).getKefuService()
                        .sendKefuMessage(kefuMessage);
            }
            return out;
        } catch (WxErrorException e) {
            logger.error("callback", e);
        }
        return out;
    }

    public void getQueryAuth(String authorizationCode) {
        try {
            WxOpenQueryAuthResult result = wxOpenService.getWxOpenComponentService().getQueryAuth(authorizationCode);
        } catch (WxErrorException e) {
            logger.error("获取调用凭据和授权信息失败, ", e);
        }
    }
}
