package com.mhdss.ggs.constant;

import org.apache.http.client.HttpClient;

public class SeretMessage {

    public static String appId = "wx2c17c18abe040c48";

    public static String appSecret = "36acee527c3a75c3c219c848df8ab93c";

    //商户号
    public static String mchId = "1522485571";
    //子商户号
    public static String subMchId = "123123";

    public static String apiKey = "d86edd7de6bd4d429fccfd1bc1c5cd26";


    public static String merchantUrl = "";

    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";

    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();
}
