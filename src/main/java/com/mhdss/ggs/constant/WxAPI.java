package com.mhdss.ggs.constant;

public class WxAPI {

    public static String wxLoginCheck = "https://api.weixin.qq.com/sns/jscode2session";

    public static String getAccessToken = "https://api.weixin.qq.com/cgi-bin/token";

    public static String msgSecCheck = "https://api.weixin.qq.com/wxa/msg_sec_check";

    public static String imgSecCheck = "https://api.weixin.qq.com/wxa/img_sec_check";

    public static String unifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 企业向微信用户个人付款
     */
    public static String mchToUserPay = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public static String getWxacodeunLimit = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";
}
