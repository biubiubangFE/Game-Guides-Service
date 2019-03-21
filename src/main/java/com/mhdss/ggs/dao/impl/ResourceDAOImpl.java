package com.mhdss.ggs.dao.impl;

import com.mhdss.ggs.dao.ResourceDAO;
import com.mhdss.ggs.dataobject.ResourceDO;
import com.mhdss.ggs.query.ResourceQuery;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceDAOImpl extends BaseDAOImpl<ResourceDO, ResourceQuery> implements ResourceDAO {

    @Override
    protected String getNameSpace() {
        return ResourceDAO.class.getName();
    }
}