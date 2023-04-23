package com.github.binarywang.demo.wx.channel.open.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public class OpenAuthorizationInfo implements Serializable {

    /** 授权方 appid */
    @JsonProperty("authorizer_appid")
    private String authorizerAppid;

    /** 接口调用令牌 */
    private String authorizerAccessToken;

    /** authorizer_access_token 的有效期，单位：秒 */
    private int expiresIn;

    /** 刷新令牌 */
    @JsonProperty("authorizer_refresh_token")
    private String authorizerRefreshToken;

    /** 授权给开发者的权限集列表 */
    @JsonProperty("func_info")
    private List<Integer> funcInfo;

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public List<Integer> getFuncInfo() {
        return funcInfo;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
    }

    public void setAuthorizerAccessToken(String authorizerAccessToken) {
        this.authorizerAccessToken = authorizerAccessToken;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken;
    }

    public void setFuncInfo(List<Integer> funcInfo) {
        this.funcInfo = funcInfo;
    }
}
