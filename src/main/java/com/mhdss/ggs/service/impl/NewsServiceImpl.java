package com.mhdss.ggs.service.impl;

import com.mhdss.ggs.constant.BaseStatus;
import com.mhdss.ggs.convert.NewsConvert;
import com.mhdss.ggs.dao.NewsDAO;
import com.mhdss.ggs.dataobject.NewsDO;
import com.mhdss.ggs.query.NewsQuery;
import com.mhdss.ggs.query.PagedList;
import com.mhdss.ggs.service.NewsService;
import com.mhdss.ggs.service.TagService;
import com.mhdss.ggs.utils.DateUtil;
import com.mhdss.ggs.vo.NewsVO;
import com.mhdss.ggs.vo.PageResVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDAO newsDAO;
    @Autowired
    private TagService tagService;

    @Override
    public void addNews(NewsDO newsDO) {

        newsDO.setCreateTime(DateUtil.getCurrentTimeStamp());
        newsDO.setStatus(BaseStatus.NORMAL.getStatus());
        newsDAO.insert(newsDO);
    }

    @Override
    public NewsVO queryById(Long newsId) {

        NewsDO newsDO = newsDAO.selectById(newsId);

        return NewsConvert.do2VO(newsDO);
    }

    @Override
    public PageResVO<NewsVO> paginationNews(Byte gameType, String searchTag, String keyword, Byte sortType, int pageNo, int pageSize) {
        // 排序 sortType 1,时间倒叙，2,阅读量，3,点赞量
        // gameType 1.自走棋2.吃鸡3.主机游戏
        PagedList<NewsDO> pagedList = null;
        if (StringUtils.isNotBlank(searchTag)) {

            List<Long> tagItemIds = tagService.queryTagItem(searchTag);

            if (CollectionUtils.isNotEmpty(tagItemIds)) {
                List<Long> newsIds = tagService.queryNewsId(tagItemIds);
                if (CollectionUtils.isNotEmpty(newsIds)) {

                    NewsQuery query = new NewsQuery();
                    query.setNewsIds(newsIds);
                    query.setGameType(gameType);
                    query.setSortType(sortType);
                    query.setKeyword(keyword);
                    query.setPagination(pageNo, pageSize);
                    pagedList = newsDAO.selectPage(query);

                }
            }

        } else {
            NewsQuery query = new NewsQuery();
            query.setGameType(gameType);
            query.setSortType(sortType);
            query.setPagination(pageNo, pageSize);
            pagedList = newsDAO.selectPage(query);

        }
        PageResVO<NewsVO> pageResVO = new PageResVO<>();
        if (null == pagedList) {
            pageResVO.setPageCount(0);
            pageResVO.setResultCount(0);
            pageResVO.setResultList(new ArrayList<>());
        } else {
            pageResVO.setPageCount(pagedList.getTotalPage());
            pageResVO.setResultCount(pagedList.getTotal());
            pageResVO.setResultList(NewsConvert.dos2VOs(pagedList.getSource()));
        }

        return pageResVO;
    }

}
