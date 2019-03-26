package com.mhdss.ggs.Spider;

import com.mhdss.ggs.constant.SpiderAddressType;
import com.mhdss.ggs.dto.SpiderResultDTO;
import com.mhdss.ggs.dto.XiaoHeiHeResponseDTO;
import com.mhdss.ggs.dto.XiaoHeiHeResultDTO;
import com.mhdss.ggs.task.ScannerResourceTask;
import com.mhdss.ggs.task.ScannerTarget;
import com.mhdss.ggs.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

    public List<SpiderResultDTO> getResult(String xiaoHeiHeUrl) {

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
            return null;
        }
        return result2Spider(responseData.getResult());

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

                String description = dto.getDescription();
                if (StringUtils.isNotBlank(description) && description.startsWith("小黑盒")) {
                    spider.setDescription("最新游戏资讯");
                } else {
                    spider.setDescription(description);
                }
                Long pulishTime = dto.getTimestamp();
                if (null == pulishTime) {
                    spider.setTimestamp(DateUtil.getCurrentTimeStamp());

                } else {
                    spider.setTimestamp(dto.getTimestamp());
                }

                List<String> imgs = dto.getImgs();
                if (CollectionUtils.isNotEmpty(imgs)) {
                    spider.setImg(imgs.get(0));
                }
                spider.setShareUrl(dto.getShare_url());
                spider.setTag(dto.getTag());

                spider.setTitle(dto.getTitle());
                spider.setSpiderType(SpiderAddressType.XIAOHEIHEZIZOUQI);

                spiders.add(spider);
            }

        }

        return spiders;
    }
}
