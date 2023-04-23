package com.github.binarywang.demo.wx.channel.open.service.impl;


import com.github.binarywang.demo.wx.channel.open.service.ChannelMessageService;
import com.github.binarywang.demo.wx.channel.open.service.OpenMessageService;
import java.util.HashSet;
import java.util.Set;
import me.chanjar.weixin.channel.api.WxChannelService;
import me.chanjar.weixin.channel.api.impl.BaseWxChannelMessageServiceImpl;
import me.chanjar.weixin.channel.config.WxChannelConfig;
import me.chanjar.weixin.channel.message.WxChannelMessage;
import me.chanjar.weixin.channel.message.WxChannelMessageRouter;
import me.chanjar.weixin.channel.message.WxChannelMessageRouterRule;
import me.chanjar.weixin.channel.util.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Service
public class ChannelMessageServiceImpl extends BaseWxChannelMessageServiceImpl implements ChannelMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ChannelMessageServiceImpl.class);

    @Autowired
    private WxChannelService wxChannelService;

    @Autowired
    private WxChannelConfig wxChannelConfig;

    @Autowired
    private OpenMessageService openMessageService;

    public ChannelMessageServiceImpl(WxChannelMessageRouter router) {
        super(router);
    }

    @Override
    public String processMessage(String requestBody, String encryptType) {
        Object result = null;
        WxChannelMessage message = XmlUtils.decode(requestBody, WxChannelMessage.class);
        String appId = wxChannelConfig.getAppid();
        result = this.route(message, requestBody, appId, wxChannelService);
        if (result instanceof String) {
            return (String) result;
        } else if (result != null) {
            return openMessageService.encodeMessage(XmlUtils.encode(result));
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
}
