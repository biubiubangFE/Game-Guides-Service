package com.mhdss.ggs.dao.impl;

import com.mhdss.ggs.dao.TagItemDAO;
import com.mhdss.ggs.dataobject.TagItemDO;
import com.mhdss.ggs.query.TagItemQuery;
import org.springframework.stereotype.Repository;

@Repository
public class TagItemDAOImpl extends BaseDAOImpl<TagItemDO, TagItemQuery> implements TagItemDAO {

    @Override
    protected String getNameSpace() {
        return TagItemDAO.class.getName();
    }
}