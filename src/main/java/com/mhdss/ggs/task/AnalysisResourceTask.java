package com.mhdss.ggs.task;

import com.mhdss.ggs.Spider.XiaoHeiHeAnalysis;
import com.mhdss.ggs.constant.GameType;
import com.mhdss.ggs.constant.NewsShowType;
import com.mhdss.ggs.constant.ParseStatus;
import com.mhdss.ggs.dataobject.NewsDO;
import com.mhdss.ggs.dataobject.ResourceDO;
import com.mhdss.ggs.service.NewsService;
import com.mhdss.ggs.service.ResourceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class AnalysisResourceTask implements InitializingBean {

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ResourceService resourceService;
    @Autowired
    NewsService newsService;

    private static final Logger logger = LoggerFactory.getLogger(AnalysisResourceTask.class);


    @Override
    public void afterPropertiesSet() throws Exception {

        //初始化启动任务，扫描接口，获取资源列表
        AnalyseSource analyseSource = new AnalyseSource();

        scheduledExecutorService.scheduleWithFixedDelay(analyseSource, 5, 240, TimeUnit.MINUTES);

    }

    private class AnalyseSource implements Runnable {

        @Override
        public void run() {

            //1.扫描Resource表
            List<ResourceDO> resourceDOList = resourceService.selectResource(ParseStatus.WAIT);

            if (CollectionUtils.isEmpty(resourceDOList)) {
                return;
            }
            XiaoHeiHeAnalysis xiaoHeiHeAnalysis = new XiaoHeiHeAnalysis();
            //2. 进行解析
            for (ResourceDO resourceDO : resourceDOList) {
                resourceService.updateParseStatus(resourceDO.getId(), ParseStatus.RUN);

                String article = xiaoHeiHeAnalysis.startAnalysis(resourceDO);
                if (StringUtils.isBlank(article)) {
                    resourceService.updateParseStatus(resourceDO.getId(), ParseStatus.ERROR);
                }
                NewsDO newsDO = new NewsDO();
                // 插入 news 表
                try {
                    newsDO.setResourceId(resourceDO.getId());
                    newsDO.setAuthor(resourceDO.getAuthor());
                    newsDO.setContent(article);
                    newsDO.setCopyFrom("XIAOHEIHE");
                    newsDO.setDescription(resourceDO.getDescription());
                    newsDO.setHitNum(0);
                    newsDO.setLikeNum(0);
                    newsDO.setPostNum(0);
                    newsDO.setTitle(resourceDO.getTitle());
                    newsDO.setPublishTime(resourceDO.getPublishTime());
                    newsDO.setThumpPath(resourceDO.getImg());
                    newsDO.setShowType(NewsShowType.CONTENT.getType());
                    newsDO.setGameType(GameType.getCode(resourceDO.getTag()).getType());
                    newsService.addNews(newsDO);
                } catch (Exception e) {
                    logger.error("news 插入数据库失败{},", newsDO);
                    resourceService.updateParseStatus(resourceDO.getId(), ParseStatus.ERROR);
                    continue;
                }
                resourceService.updateParseStatus(resourceDO.getId(), ParseStatus.SUCCESS);
            }


        }
    }
}
