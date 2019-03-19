package com.mhdss.ggs.dao;

import com.mhdss.ggs.dataobject.BaseDO;
import com.mhdss.ggs.query.AbstractQuery;
import java.util.Collection;
import java.util.List;

public interface MapperDAO<T extends BaseDO, QUERY extends AbstractQuery> {
    String STATEMENT_INSERT = "insert";
    String STATEMENT_INSERT_UPDATE_ON_DUPLICATE = "insertUpdateOnDuplicate";
    String STATEMENT_UPDATE_BY_ID = "updateById";
    String STATEMENT_UPDATE_BY_IDS = "updateByIds";
    String STATEMENT_DELETE_BY_ID = "deleteById";
    String STATEMENT_DELETE_BY_IDS = "deleteByIds";
    String STATEMENT_SELECT_BY_ID = "selectById";
    String STATEMENT_SELECT_IDS_BY_QUERY = "selectIdsByQuery";
    String STATEMENT_SELECT_BY_IDS = "selectByIds";
    String STATEMENT_SELECT_COUNT = "selectCount";
    String STATEMENT_SELECT_LIST = "selectList";

    int insert(T t);

    int insertUpdateOnDuplicate(T t);

    int updateById(T t);

    int updateByIds(T t, Collection<Long> ids);

    int deleteById(Long id);

    int deleteByIds(Collection<Long> ids);

    int deleteByQuery(QUERY query);

    T selectById(Long id);

    List<Long> selectIdsByQuery(QUERY query);

    List<T> selectByIds(Collection<Long> ids);

    int selectCount(QUERY query);

    List<T> selectList(QUERY query);
}