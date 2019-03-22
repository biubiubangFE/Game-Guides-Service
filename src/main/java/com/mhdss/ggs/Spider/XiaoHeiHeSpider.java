package com.mhdss.ggs.Spider;

import com.mhdss.ggs.constant.SpiderAddressType;
import com.mhdss.ggs.dto.SpiderResultDTO;
import com.mhdss.ggs.dto.XiaoHeiHeResponseDTO;
import com.mhdss.ggs.dto.XiaoHeiHeResultDTO;
import com.mhdss.ggs.task.ScannerResourceTask;
import com.mhdss.ggs.task.ScannerTarget;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class XiaoHeiHeSpider {

    private RestTemplate restTemplate;

    private static final ParameterizedTypeReference<XiaoHeiHeResponseDTO<List<XiaoHeiHeResultDTO>>> PERMISSIONS_TYPE_REFERENCE =
            new ParameterizedTypeReference<XiaoHeiHeResponseDTO<List<XiaoHeiHeResultDTO>>>() {
            };

    private static final Logger logger = LoggerFactory.getLogger(ScannerResourceTask.class);

    public List<SpiderResultDTO> getResult() {

        String xiaoHeiHeUrl = ScannerTarget.xiaoHeiHeSourceUrl();

        ResponseEntity<XiaoHeiHeResponseDTO<List<XiaoHeiHeResultDTO>>> responseEntity = restTemplate.exchange(xiaoHeiHeUrl,
                HttpMethod.GET, null, PERMISSIONS_TYPE_REFERENCE);

        if (null == responseEntity) {
            logger.error("拉取小黑河接口异常ResponseEntity = null");
            return null;
        }

        XiaoHeiHeResponseDTO<List<XiaoHeiHeResultDTO>> responseData = responseEntity.getBody();

        //2.获取资源，新资源入库

        if (null == responseData || !responseData.getStatus().equals("ok")) {
            logger.error("拉取小黑河接口异常response:{}", responseData);
        }
        return null;


    }

    public XiaoHeiHeSpider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private List<SpiderResultDTO> result2Spider(List<XiaoHeiHeResultDTO> resultDTOS) {

        List<SpiderResultDTO> spiders = new ArrayList<>();
        if (CollectionUtils.isEmpty(resultDTOS)) {
            return spiders;
        }

        for (XiaoHeiHeResultDTO dto : resultDTOS) {
            byte contentType = dto.getContent_type();
            if (contentType == 2) {

                SpiderResultDTO spider = new SpiderResultDTO();
                spider.setAuthor(dto.getAuthor());
                spider.setDescription(dto.getDescription());
                List<String> imgs = dto.getImgs();
                if (CollectionUtils.isNotEmpty(imgs)) {
                    spider.setImg(imgs.get(0));
                }
                spider.setShareUrl(dto.getShare_url());
                spider.setTag(dto.getTag());
                spider.setTimestamp(dto.getTimestamp());
                spider.setTitle(dto.getTitle());
                spider.setSpiderType(SpiderAddressType.XIAOHEIHEZIZOUQI);

                spiders.add(spider);
            }

        }

        return spiders;
    }
}
