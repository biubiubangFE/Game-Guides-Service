package com.mhdss.ggs.convert;

import com.mhdss.ggs.dataobject.NewsDO;
import com.mhdss.ggs.vo.NewsVO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsConvert {

    public static NewsVO do2VO(NewsDO DO) {
        if (null == DO) {
            return null;
        }
        NewsVO newsVO = new NewsVO();
        newsVO.setAuthor(DO.getAuthor());
        newsVO.setContent(DO.getContent());
        newsVO.setDescription(DO.getDescription());
        newsVO.setGameType(DO.getGameType());
        newsVO.setHitNum(DO.getHitNum());
        newsVO.setHtmlPath(DO.getHtmlPath());
        newsVO.setLikeNum(DO.getLikeNum());
        newsVO.setNewsId(DO.getId());
        newsVO.setPublishTime(DO.getPublishTime());
        newsVO.setThumpPath(DO.getThumpPath());
        newsVO.setShowType(DO.getShowType());
        newsVO.setTitle(DO.getTitle());
        newsVO.setPostNum(DO.getPostNum());
        return newsVO;
    }

    public static List<NewsVO> dos2VOs(List<NewsDO> newsDOS) {

        if (CollectionUtils.isEmpty(newsDOS)) {
            return new ArrayList<>();
        }
        List<NewsVO> newsVOS = new ArrayList<>();
        for (NewsDO DO : newsDOS) {
            newsVOS.add(do2VO(DO));
        }
        return newsVOS;
    }
}
