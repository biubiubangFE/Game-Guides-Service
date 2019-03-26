package com.mhdss.ggs.service.impl;

import com.mhdss.ggs.constant.BaseStatus;
import com.mhdss.ggs.dao.NewsDAO;
import com.mhdss.ggs.dataobject.NewsDO;
import com.mhdss.ggs.service.NewsService;
import com.mhdss.ggs.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDAO newsDAO;

    @Override
    public void addNews(NewsDO newsDO) {

        newsDO.setCreateTime(DateUtil.getCurrentTimeStamp());
        newsDO.setStatus(BaseStatus.NORMAL.getStatus());
        newsDAO.insert(newsDO);
    }
}
