package com.github.binarywang.demo.wx.channel.open.service.impl;

import com.github.binarywang.demo.wx.channel.open.service.WechatOpenService;
import com.github.binarywang.demo.wx.channel.open.vo.CategoryInfo;
import com.github.binarywang.demo.wx.channel.open.vo.OpenAuthorizationInfo;
import com.github.binarywang.demo.wx.channel.open.vo.AuthorizationSimpleInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo.MiniProgramInfo.Category;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Service
public class WechatOpenServiceImpl implements WechatOpenService {
    private static final Logger logger = LoggerFactory.getLogger(MyWxChannelServiceImpl.class);

    @Autowired
    private WxOpenService wxOpenService;

    @Override
    public String getPreAuthUrl(String url) {
        WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
        try {
            return wxOpenComponentService.getPreAuthUrl(url);
        } catch (WxErrorException e) {
            logger.error("gotoPreAuthUrl", e);
        }
        return null;
    }

    @Override
    public OpenAuthorizationInfo getQueryAuth(String authorizationCode) {
        OpenAuthorizationInfo info = null;
        try {
            WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
            WxOpenQueryAuthResult result = wxOpenComponentService.getQueryAuth(authorizationCode);
            if (result != null && result.getAuthorizationInfo() != null) {
                WxOpenAuthorizationInfo authorizationInfo = result.getAuthorizationInfo();
                info = convertOpenAuthorizationInfo(authorizationInfo);
            }
        } catch (WxErrorException e) {
            logger.error("获取调用凭据和授权信息失败, ", e);
        }
        return info;
    }

    private OpenAuthorizationInfo convertOpenAuthorizationInfo(WxOpenAuthorizationInfo authorizationInfo) {
        if (authorizationInfo == null) {
            return null;
        }
        OpenAuthorizationInfo info = new OpenAuthorizationInfo();
        info.setAuthorizerAppid(authorizationInfo.getAuthorizerAppid());
        info.setAuthorizerAccessToken(authorizationInfo.getAuthorizerAccessToken());
        info.setAuthorizerRefreshToken(authorizationInfo.getAuthorizerRefreshToken());
        info.setExpiresIn(authorizationInfo.getExpiresIn());
        info.setFuncInfo(authorizationInfo.getFuncInfo());
        return info;
    }

    @Override
    public AuthorizationSimpleInfo getAuthorizerInfo(String appId) {
        AuthorizationSimpleInfo result = null;
        if (StringUtils.isEmpty(appId)) {
            logger.error("getAuthorizerInfo appId为空");
            return null;
        }
        try {
            WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
            WxOpenAuthorizerInfoResult rs = wxOpenComponentService.getAuthorizerInfo(appId);
            if (rs != null) {
                result = new AuthorizationSimpleInfo();
                if (rs.getAuthorizationInfo() != null) {
                    WxOpenAuthorizationInfo info = rs.getAuthorizationInfo();
                    result.setAppId(info.getAuthorizerAppid());
                    result.setFuncInfo(info.getFuncInfo());
                }
                if (rs.getAuthorizerInfo() != null) {
                    WxOpenAuthorizerInfo info = rs.getAuthorizerInfo();
                    result.setNickName(info.getNickName());
                    result.setPrincipalName(info.getPrincipalName());
                    result.setUserName(info.getUserName());
                    if (info.getMiniProgramInfo() != null) {
                        result.setCategories(this.convertCategoryInfo(info.getMiniProgramInfo().getCategories()));
                    }
                }
                result.setMiniApp(rs.isMiniProgram());
            }
        } catch (WxErrorException e) {
            logger.error("getAuthorizerInfo", e);
        }
        return result;
    }

    private List<CategoryInfo> convertCategoryInfo(List<Category> categoryList) {
        if (categoryList == null) {
            return Collections.emptyList();
        }
        List<CategoryInfo> list = new ArrayList<>();
        for (Category category : categoryList) {
            list.add(new CategoryInfo(category.getFirst(), category.getSecond()));
        }
        return list;
    }
}
