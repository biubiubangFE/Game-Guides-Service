package com.mhdss.ggs.dao.impl;

import com.mhdss.ggs.dao.WxUserDAO;
import com.mhdss.ggs.dataobject.WxUserDO;
import com.mhdss.ggs.query.WxUserQuery;
import org.springframework.stereotype.Repository;

@Repository
public class WxUserDAOImpl extends BaseDAOImpl<WxUserDO, WxUserQuery> implements WxUserDAO {

    @Override
    protected String getNameSpace() {
        return WxUserDAO.class.getName();
    }
}