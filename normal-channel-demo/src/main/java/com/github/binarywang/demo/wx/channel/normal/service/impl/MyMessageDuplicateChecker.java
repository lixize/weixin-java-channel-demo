package com.github.binarywang.demo.wx.channel.normal.service.impl;

import me.chanjar.weixin.common.api.WxMessageDuplicateChecker;

/**
 * 自定义消息重复检查器，用于调试
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public class MyMessageDuplicateChecker implements WxMessageDuplicateChecker {

    @Override
    public boolean isDuplicate(String messageId) {
        return false;
    }
}
