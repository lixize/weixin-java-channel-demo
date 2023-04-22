package com.github.binarywang.demo.wx.channel.starter.service.impl;

import com.github.binarywang.demo.wx.channel.starter.service.ChannelMessageService;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import me.chanjar.weixin.channel.api.WxChannelService;
import me.chanjar.weixin.channel.api.impl.BaseWxChannelMessageServiceImpl;
import me.chanjar.weixin.channel.config.WxChannelConfig;
import me.chanjar.weixin.channel.message.WxChannelMessage;
import me.chanjar.weixin.channel.message.WxChannelMessageRouter;
import me.chanjar.weixin.channel.message.WxChannelMessageRouterRule;
import me.chanjar.weixin.channel.util.JsonUtils;
import me.chanjar.weixin.channel.util.WxChCryptUtils;
import me.chanjar.weixin.channel.util.XmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Service
public class ChannelMessageServiceImpl extends BaseWxChannelMessageServiceImpl implements ChannelMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ChannelMessageServiceImpl.class);

    @Resource
    private WxChannelService wxChannelService;

    @Resource
    private WxChannelConfig wxChannelConfig;

    public ChannelMessageServiceImpl(WxChannelMessageRouter router) {
        super(router);
    }

    @Override
    public String getMsgDataFormat() {
        return wxChannelConfig.getMsgDataFormat();
    }

    @Override
    public boolean checkSignature(String timestamp, String nonce, String signature) {
        return wxChannelService.checkSignature(timestamp, nonce, signature);
    }

    @Override
    public String processMessage(String requestBody, String msgSignature, String encryptType, String signature,
            String timestamp, String nonce) {
        final boolean isJson = "JSON".equalsIgnoreCase(this.getMsgDataFormat());
        if (StringUtils.isBlank(encryptType)) {
            return this.processMessage(requestBody, isJson, null);
        } else if (encryptType.equalsIgnoreCase("aes")) {
            WxChannelMessage message = isJson ? JsonUtils.decode(requestBody, WxChannelMessage.class) :
                    XmlUtils.decode(requestBody, WxChannelMessage.class);
            String encryptContent = message.getEncrypt();
            // aes加密的消息
            String plainText = this.decodeMessage(encryptContent, timestamp, nonce, msgSignature);
            return this.processMessage(plainText, isJson, encryptType);
        }
        // 这个一般不会出现，除非配置错了
        throw new RuntimeException("不可识别的加密类型：" + encryptType);
    }

    @Override
    public String processMessage(String requestBody, boolean isJson, String encryptType) {
        Object result = null;
        WxChannelMessage message = isJson ? JsonUtils.decode(requestBody, WxChannelMessage.class) :
                XmlUtils.decode(requestBody, WxChannelMessage.class);
        String appId = wxChannelConfig.getAppid();
        result = this.route(message, appId, encryptType, wxChannelService);
        if (result instanceof String) {
            return (String) result;
        } else if (result != null) {
            return this.encodeMessage(isJson ? JsonUtils.encode(result) : XmlUtils.encode(result));
        } else {
            return "success";
        }
    }

    @Override
    public Set<String> listSupportEvents() {
        Set<String> eventSet = new HashSet<>();
        for (WxChannelMessageRouterRule<? extends WxChannelMessage> rule : this.router.getRules()) {
            if (rule.getEvent() != null) {
                eventSet.add(rule.getEvent());
            }
        }
        return eventSet;
    }

    /**
     * 解密消息
     *
     * @param requestBody  消息体
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @param msgSignature 签名串
     * @return 解密后的消息体
     */
    protected String decodeMessage(String requestBody, String timestamp, String nonce, String msgSignature) {
        String plainText = null;
        try {
            WxChCryptUtils cryptUtil = new WxChCryptUtils(wxChannelConfig);
            plainText = cryptUtil.decryptContent(msgSignature, timestamp, nonce, requestBody);
            logger.info("\n\n{}\n", plainText);
        } catch (Throwable e) {
            logger.error("解密异常", e);
            String aesKey = wxChannelConfig.getAesKey();
            String token = wxChannelConfig.getToken();
            String appId = wxChannelConfig.getAppid();
            logger.info("requestBody:{}, timestamp:{}, nonce:{}, msgSignature:{}, token:{}, aesKey:{}, appid:{}",
                    requestBody, timestamp, nonce, msgSignature, aesKey, token, appId);
        }
        return plainText;
    }

    /**
     * 加密消息
     *
     * @param target 消息体
     * @return 加密后的消息体
     */
    protected String encodeMessage(String target) {
        String result = null;
        try {
            WxChCryptUtils cryptUtil = new WxChCryptUtils(wxChannelConfig);
            result = cryptUtil.encrypt(target);
        } catch (Throwable e) {
            logger.error("加密异常", e);
        }
        return result;
    }
}
