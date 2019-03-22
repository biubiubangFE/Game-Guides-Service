package com.mhdss.ggs.Spider;

import com.mhdss.ggs.constant.SpiderAddressType;
import com.mhdss.ggs.dataobject.ResourceDO;
import com.mhdss.ggs.service.NewsService;
import com.mhdss.ggs.service.ResourceService;

public class AnalyseResourceManage {

    private ResourceService resourceService;
    private NewsService newsService;

    public void startAnalyResource(ResourceDO resourceDO) {

        if (null == resourceDO) {
            return;
        }
        Byte fromType = resourceDO.getFromType();
        SpiderAddressType addressType = SpiderAddressType.getType(fromType);

        if (addressType == SpiderAddressType.XIAOHEIHEZIZOUQI) {
            XiaoHeiHeAnalysis xiaoHeiHeAnalysis = new XiaoHeiHeAnalysis(resourceService, newsService);
        }

    }

    public AnalyseResourceManage(ResourceService resourceService, NewsService newsService) {
        this.resourceService = resourceService;
        this.newsService = newsService;
    }

}
