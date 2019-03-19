package com.mhdss.ggs.dao.impl;

import com.mhdss.ggs.dao.TagDAO;
import com.mhdss.ggs.dataobject.TagDO;
import com.mhdss.ggs.query.TagQuery;
import org.springframework.stereotype.Repository;

@Repository
public class TagDAOImpl extends BaseDAOImpl<TagDO, TagQuery> implements TagDAO {

    @Override
    protected String getNameSpace() {
        return TagDAO.class.getName();
    }
}