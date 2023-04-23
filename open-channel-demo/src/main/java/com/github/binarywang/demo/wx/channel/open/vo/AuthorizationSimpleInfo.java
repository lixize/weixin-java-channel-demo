package com.github.binarywang.demo.wx.channel.open.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public class AuthorizationSimpleInfo implements Serializable {

    /** appid */
    private String appId;

    /** 原始id */
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 主体名称 */
    private String principalName;

    /** 授权给开发者的权限集列表 */
    private List<Integer> funcInfo;

    /** 小程序配置的类目信息 */
    private List<CategoryInfo> categories;

    /** 是否小程序 */
    private boolean miniApp;

    public String getAppId() {
        return appId;
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public List<Integer> getFuncInfo() {
        return funcInfo;
    }

    public List<CategoryInfo> getCategories() {
        return categories;
    }

    public boolean isMiniApp() {
        return miniApp;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public void setFuncInfo(List<Integer> funcInfo) {
        this.funcInfo = funcInfo;
    }

    public void setCategories(List<CategoryInfo> categories) {
        this.categories = categories;
    }

    public void setMiniApp(boolean miniApp) {
        this.miniApp = miniApp;
    }
}
