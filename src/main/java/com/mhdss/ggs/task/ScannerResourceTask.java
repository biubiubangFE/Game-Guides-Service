package com.mhdss.ggs.task;

import com.mhdss.ggs.Spider.XiaoHeiHeSpider;
import com.mhdss.ggs.dto.SpiderResultDTO;
import com.mhdss.ggs.service.ResourceService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScannerResourceTask implements InitializingBean {

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ResourceService resourceService;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ScannerResourceTask.class);


    @Override
    public void afterPropertiesSet() throws Exception {

        //初始化启动任务，扫描接口，获取资源列表
        ScannerSource scannerSource = new ScannerSource();

        scheduledExecutorService.scheduleWithFixedDelay(scannerSource, 10, 60, TimeUnit.SECONDS);

    }

    private class ScannerSource implements Runnable {

        @Override
        public void run() {

            //1.得到扫描路径及
            XiaoHeiHeSpider xiaoHeiHeSpider = new XiaoHeiHeSpider(restTemplate);
            List<SpiderResultDTO> spiderResultDTOS = xiaoHeiHeSpider.getResult();

            //3.分类解析，入库

            for (SpiderResultDTO spiderResultDTO : spiderResultDTOS) {

                resourceService.addResource(spiderResultDTO);

            }

        }
    }
}
