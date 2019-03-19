package com.mhdss.ggs.dao;

import com.mhdss.ggs.dataobject.BaseDO;
import com.mhdss.ggs.query.AbstractQuery;
import com.mhdss.ggs.query.PagedList;
import java.util.List;

public interface BaseDAO<T extends BaseDO, QUERY extends AbstractQuery> extends MapperDAO<T, QUERY> {
    int insertUnique(T t);

    int updateByQuery(T t, QUERY query);

    T selectByQuery(QUERY query);

    PagedList<T> selectPage(QUERY query);
}