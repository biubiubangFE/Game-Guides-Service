package com.mhdss.ggs.controller;

import com.mhdss.ggs.service.NewsService;
import com.mhdss.ggs.utils.ResponseData;
import com.mhdss.ggs.vo.NewsVO;
import com.mhdss.ggs.vo.PageResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public ResponseData<?> pageList(@RequestParam(value = "gameType",required = false) Byte gameType,
                                    @RequestParam(value = "searchTag",required = false) String searchTag,
                                    @RequestParam(value = "sortType", defaultValue = "1") Byte sortType,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {


        PageResVO<NewsVO> pageResVO = newsService.paginationNews(gameType, searchTag, sortType, pageNo, pageSize);

        return ResponseData.success(pageResVO);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseData<?> get(@RequestParam(value = "newsId") Long newsId) {


        NewsVO newsVO = newsService.queryById(newsId);

        return ResponseData.success(newsVO);
    }
}
