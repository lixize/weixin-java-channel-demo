package com.github.binarywang.demo.wx.channel.normal.controller;


import com.github.binarywang.demo.wx.channel.normal.common.Result;
import com.github.binarywang.demo.wx.channel.normal.service.ChannelApiService;
import com.github.binarywang.demo.wx.channel.normal.vo.ShopInfoVo;
import me.chanjar.weixin.channel.util.XmlUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@RestController
@RequestMapping("/channel")
public class ChannelDemoController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ChannelDemoController.class);

    @Autowired
    private ChannelApiService channelApiService;

    /**
     * 获取店铺信息
     *
     * @return Result
     */
    @GetMapping("/shopInfo")
    public Result<ShopInfoVo> getShopInfo() {
        XmlUtils.decode("<xml></xml>", ShopInfoVo.class);
        return Result.success(channelApiService.getShopInfo());
    }
}
