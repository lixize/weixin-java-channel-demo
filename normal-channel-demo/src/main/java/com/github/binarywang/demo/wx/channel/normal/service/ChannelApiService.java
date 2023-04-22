package com.github.binarywang.demo.wx.channel.normal.service;


import com.github.binarywang.demo.wx.channel.normal.vo.ShopInfoVo;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public interface ChannelApiService {

    /**
     * 获取店铺信息
     *
     * @return 店铺信息
     */
    ShopInfoVo getShopInfo();
}
