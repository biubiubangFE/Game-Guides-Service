package com.mhdss.ggs.dao.impl;

import com.mhdss.ggs.dao.TagItemNewsRelatedDAO;
import com.mhdss.ggs.dataobject.TagItemNewsRelatedDO;
import com.mhdss.ggs.query.TagItemNewsRelatedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class TagItemNewsRelatedDAOImpl extends BaseDAOImpl<TagItemNewsRelatedDO, TagItemNewsRelatedQuery> implements TagItemNewsRelatedDAO {

    @Override
    protected String getNameSpace() {
        return TagItemNewsRelatedDAO.class.getName();
    }
}