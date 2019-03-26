package com.mhdss.ggs.task;

import com.mhdss.ggs.Spider.XiaoHeiHeSpider;
import com.mhdss.ggs.constant.GameType;
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

        scheduledExecutorService.scheduleWithFixedDelay(scannerSource, 1, 60, TimeUnit.MINUTES);

    }

    private class ScannerSource implements Runnable {

        @Override
        public void run() {

            for (GameType gameType : GameType.values()) {
                //小黑河类型扫描
                scannerGameSource(gameType);
            }

        }

        private void scannerGameSource(GameType gameType) {

            XiaoHeiHeSpider xiaoHeiHeSpider = new XiaoHeiHeSpider(restTemplate);
            String xiaoHeiHeUrl = ScannerTarget.xiaoHeiHeSourceUrl() + " &tag=" + gameType.getCode();
            List<SpiderResultDTO> spiderResultDTOS = xiaoHeiHeSpider.getResult(xiaoHeiHeUrl);

            //3.分类解析，入库

            for (SpiderResultDTO spiderResultDTO : spiderResultDTOS) {
                logger.debug("插入 resource url =  {}", spiderResultDTO.getShareUrl());
                try {
                    resourceService.addResource(spiderResultDTO);
                } catch (Exception e) {
                    logger.error("资源插入数据库失败,spiderResultDTO = {}", spiderResultDTO);
                }

            }
        }
    }
}
