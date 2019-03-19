package com.mhdss.ggs.dao.impl;

import com.mhdss.ggs.dao.CommentDAO;
import com.mhdss.ggs.dataobject.CommentDO;
import com.mhdss.ggs.query.CommentQuery;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAOImpl extends BaseDAOImpl<CommentDO, CommentQuery> implements CommentDAO {

    @Override
    protected String getNameSpace() {
        return CommentDAO.class.getName();
    }
}