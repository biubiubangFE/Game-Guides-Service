package com.mhdss.ggs.task;

import com.mhdss.ggs.utils.DateUtil;

import java.util.UUID;

public class ScannerTarget {


    public static String xiaoHeiHeSourceUrl() {

       return "https://api.xiaoheihe.cn/maxnews/app/list?lang=zh-cn&os_type=iOS&os_version=12.1.4&_time="+DateUtil.getCurrentTimeStamp()+"&version=1.2.58&device_id=7FCA3040-E2E8-4E04-ADBB-5579ED504F07&heybox_id=15959927&hkey="+UUID.randomUUID()+"&limit=20&offset=0";
    }
}
