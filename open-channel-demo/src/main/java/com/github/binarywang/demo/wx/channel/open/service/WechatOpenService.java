package com.github.binarywang.demo.wx.channel.open.service;

import com.github.binarywang.demo.wx.channel.open.vo.OpenAuthorizationInfo;
import com.github.binarywang.demo.wx.channel.open.vo.AuthorizationSimpleInfo;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public interface WechatOpenService {

    /**
     * 获取用户授权页URL
     *
     * @param url url
     * @return url
     */
    String getPreAuthUrl(String url);

    /**
     * 使用授权码换取公众号或小程序的接口调用凭据和授权信息
     */
    OpenAuthorizationInfo getQueryAuth(String authorizationCode);

    /**
     * 获取授权方的帐号基本信息
     *
     * @param authorizerAppid the authorizer appid
     * @return the authorizer info
     */
    AuthorizationSimpleInfo getAuthorizerInfo(String authorizerAppid);
}
