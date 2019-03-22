package com.mhdss.ggs.Spider;

import com.mhdss.ggs.dataobject.ResourceDO;
import com.mhdss.ggs.service.NewsService;
import com.mhdss.ggs.service.ResourceService;

public class XiaoHeiHeAnalysis {

    private ResourceService resourceService;
    private NewsService newsService;

    public void startAnalysis(ResourceDO resourceDO) {

        //1.更新状态

    }


    public XiaoHeiHeAnalysis(ResourceService resourceService, NewsService newsService) {
        this.resourceService = resourceService;
        this.newsService = newsService;
    }
}
