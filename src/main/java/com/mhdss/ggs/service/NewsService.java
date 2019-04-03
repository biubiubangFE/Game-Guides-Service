package com.mhdss.ggs.service;

import com.mhdss.ggs.dataobject.NewsDO;
import com.mhdss.ggs.vo.NewsVO;
import com.mhdss.ggs.vo.PageResVO;

public interface NewsService {

    void addNews(NewsDO newsDO);

    NewsVO queryById(Long newsId);

    PageResVO<NewsVO> paginationNews(Byte gameType, String searchTag, String keyword, Byte sortType, int pageNo, int pageSize);
}
