package com.github.binarywang.demo.wx.channel.starter.service.impl;

import com.github.binarywang.demo.wx.channel.starter.vo.ShopInfoVo;
import com.github.binarywang.demo.wx.channel.starter.service.ChannelApiService;
import me.chanjar.weixin.channel.api.WxChannelService;
import me.chanjar.weixin.channel.bean.shop.ShopInfoResponse;
import me.chanjar.weixin.channel.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Service
public class ChannelApiServiceImpl implements ChannelApiService {
    private static final Logger logger = LoggerFactory.getLogger(ChannelApiServiceImpl.class);

    @Autowired
    @Lazy
    private WxChannelService wxChannelService;


    @Override
    public ShopInfoVo getShopInfo() {
        ShopInfoVo shopInfoVo = new ShopInfoVo();
        try {
            // 获取店铺信息
            ShopInfoResponse response = wxChannelService.getBasicService().getShopInfo();
            if (!response.isSuccess()) {
                logger.error("获取店铺信息失败, {}", JsonUtils.encode(response));
                return shopInfoVo;
            }
            // 将结果封装到vo中
            if (response.getInfo() != null) {
                shopInfoVo.setNickname(response.getInfo().getNickname());
                shopInfoVo.setHeadImgUrl(response.getInfo().getHeadImgUrl());
                shopInfoVo.setSubjectType(response.getInfo().getSubjectType());
            }
        } catch (Exception e) {
            logger.error("获取店铺信息失败", e);
        }
        return shopInfoVo;
    }
}
