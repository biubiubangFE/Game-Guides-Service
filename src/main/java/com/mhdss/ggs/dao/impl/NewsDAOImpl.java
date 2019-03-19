package com.mhdss.ggs.dao.impl;

import com.mhdss.ggs.dao.NewsDAO;
import com.mhdss.ggs.dataobject.NewsDO;
import com.mhdss.ggs.query.NewsQuery;
import org.springframework.stereotype.Repository;

@Repository
public class NewsDAOImpl extends BaseDAOImpl<NewsDO, NewsQuery> implements NewsDAO {

    @Override
    protected String getNameSpace() {
        return NewsDAO.class.getName();
    }
}