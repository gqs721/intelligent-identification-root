package com.java.common.utils;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import net.sf.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 微信公众号模板消息推送
 */
public class WeChatPushUtil {

    public static void main(String[] args){
        JSONObject json = new JSONObject();
        json.put("open_id", "ogt3q1b6N1p2KswvhcV8NsrmIh5c");
        json.put("warn_time", DateUtil.parseDateStr(new Date()));
        json.put("warn_position", "测试地点");
        json.put("warn_kind", "吸烟告警");
        json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
        json.put("url","www.baidu.com");
        weChatPush(json,"wxce40f437898d92b4","fa297202294c01081ecc356ef7096e6b","4hvHXU0t3nSBXJmdb5XbPnKm9z6QPoGdkTO0keHx9Xs");
    }

    /**
     * 模板消息推送
     */
    public static void weChatPush(JSONObject json, String appid, String appSecret, String templateId) {
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appid);  //appID
        wxStorage.setSecret(appSecret);//appsecretID
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        //数据
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", json.getString("content")),
                new WxMpTemplateData("keyword1", json.getString("warn_time"), "#173177"),
                new WxMpTemplateData("keyword2", json.getString("warn_position"), "#173177"),
                new WxMpTemplateData("keyword3", json.getString("warn_kind"), "#173177"),
                new WxMpTemplateData("remark", "请尽快处理")
        );


        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(json.getString("open_id"))//要推送的用户openid
                .data(data) //数据
                .templateId(templateId)//模版id
                .url(json.getString("url"))//点击模版消息要访问的网址
                .build();
        //发起推送
        try {
            String msg = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            System.out.println("推送成功：" + msg);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
