package com.mhdss.ggs.task;

import com.mhdss.ggs.dto.XiaoHeiHeResponseDTO;
import com.mhdss.ggs.dto.XiaoHeiHeResultDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScannerSourceTask implements InitializingBean {

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    RestTemplate restTemplate;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ScannerSourceTask.class);

    private static final ParameterizedTypeReference<XiaoHeiHeResponseDTO<List<XiaoHeiHeResultDTO>>> PERMISSIONS_TYPE_REFERENCE =
            new ParameterizedTypeReference<XiaoHeiHeResponseDTO<List<XiaoHeiHeResultDTO>>>() {
            };

    @Override
    public void afterPropertiesSet() throws Exception {

        //初始化启动任务，扫描接口，获取资源列表
        ScannerSource scannerSource = new ScannerSource();

        scheduledExecutorService.scheduleWithFixedDelay(scannerSource, 10, 60, TimeUnit.SECONDS);

    }

    private class ScannerSource implements Runnable {

        @Override
        public void run() {

            //1. 解析接口页面,生成不同的新资源,1个解析

            String xiaoHeiHeUrl = ScannerTarget.xiaoHeiHeSourceUrl();

            ResponseEntity<XiaoHeiHeResponseDTO<List<XiaoHeiHeResultDTO>>> responseEntity = restTemplate.exchange(xiaoHeiHeUrl,
                    HttpMethod.GET, null, PERMISSIONS_TYPE_REFERENCE);

            if (null == responseEntity) {
                return;
            }

            XiaoHeiHeResponseDTO<List<XiaoHeiHeResultDTO>> responseData = responseEntity.getBody();

            //2.获取资源，新资源入库

            if (null == responseData || !responseData.getStatus().equals("ok")) {
                logger.error("拉取小黑河接口异常response:{}", responseData);
            }
            

            //3.分类解析，入库


        }
    }
}
