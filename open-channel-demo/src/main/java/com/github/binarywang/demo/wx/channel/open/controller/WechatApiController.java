package com.github.binarywang.demo.wx.channel.open.controller;


import com.github.binarywang.demo.wx.channel.open.common.Result;
import com.github.binarywang.demo.wx.channel.open.service.WechatOpenService;
import com.github.binarywang.demo.wx.channel.open.vo.AuthorizationSimpleInfo;
import com.github.binarywang.demo.wx.channel.open.vo.OpenAuthorizationInfo;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Controller
@RequestMapping("/api")
public class WechatApiController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WechatOpenService wechatOpenService;

    @GetMapping("/auth/goto_auth_url_show")
    @ResponseBody
    public String gotoPreAuthUrlShow() {
        return "<a href='goto_auth_url'>go</a>";
    }

    @GetMapping("/auth/goto_auth_url")
    public void gotoPreAuthUrl(HttpServletRequest request, HttpServletResponse response) {
        String host = request.getHeader("host");
        String url = "http://" + host + "/api/auth/jump";
        try {
            url = wechatOpenService.getPreAuthUrl(url);
            // 添加来源，解决302跳转来源丢失的问题
            response.addHeader("Referer", "http://" + host);
            response.sendRedirect(url);
        } catch (IOException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/auth/jump")
    @ResponseBody
    public Result<OpenAuthorizationInfo> jump(@RequestParam("auth_code") String authorizationCode) {
        OpenAuthorizationInfo queryAuthResult = wechatOpenService.getQueryAuth(authorizationCode);
        logger.info("getQueryAuth", queryAuthResult);
        return Result.success(queryAuthResult);
    }

    @GetMapping("/get_authorizer_info")
    @ResponseBody
    public Result<AuthorizationSimpleInfo> getAuthorizerInfo(@RequestParam String appId) {
        return Result.success(wechatOpenService.getAuthorizerInfo(appId));
    }
}