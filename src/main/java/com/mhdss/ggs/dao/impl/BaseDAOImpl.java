package com.mhdss.ggs.dao.impl;

import com.google.common.base.Preconditions;
import com.mhdss.ggs.dao.BaseDAO;
import com.mhdss.ggs.dataobject.BaseDO;
import com.mhdss.ggs.query.AbstractQuery;
import com.mhdss.ggs.query.PagedList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

public abstract class BaseDAOImpl<T extends BaseDO, QUERY extends AbstractQuery> implements BaseDAO<T, QUERY> {
    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

    protected abstract String getNameSpace();

    @Override
    public int insert(T t) {
        checkNotNull(t);
        
        return sqlSessionTemplate.insert(getStatement(STATEMENT_INSERT), t);
    }

    @Override
    public int insertUnique(T t) {
        try {
            return insert(t);
        } catch (DuplicateKeyException e) {
            return 0;
        }
    }

    @Override
    public int insertUpdateOnDuplicate(T t) {
        checkNotNull(t);
        
        return sqlSessionTemplate.update(getStatement(STATEMENT_INSERT_UPDATE_ON_DUPLICATE), t);
    }

    @Override
    public int updateById(T t) {
        checkNotNull(t);
        
        return sqlSessionTemplate.update(getStatement(STATEMENT_UPDATE_BY_ID), t);
    }

    @Override
    public int updateByIds(T t, Collection<Long> ids) {
        checkNotNull(t);
        checkIds(ids);
        
        Map<String, Object> parameter = new HashMap<>(2);
        parameter.put("target", t);
        parameter.put("ids", ids);
        
        return sqlSessionTemplate.update(getStatement(STATEMENT_UPDATE_BY_IDS), parameter);
    }

    @Override
    public int updateByQuery(T t, QUERY query) {
        List<Long> ids = selectIdsByQuery(query);
        
        if (null == ids || ids.size() == 0) {
            return 0;
        }
        
        return updateByIds(t, ids);
    }

    @Override
    public int deleteById(Long id) {
        checkNotNull(id);
        
        return sqlSessionTemplate.delete(getStatement(STATEMENT_DELETE_BY_ID), id);
    }

    @Override
    public int deleteByIds(Collection<Long> ids) {
        checkIds(ids);
        
        return sqlSessionTemplate.delete(getStatement(STATEMENT_DELETE_BY_IDS), ids);
    }

    @Override
    public int deleteByQuery(QUERY query) {
        checkQuery(query);
        
        List<Long> ids = selectIdsByQuery(query);
        
        if (null == ids || ids.size() == 0) {
            return 0;
        }
        
        return deleteByIds(ids);
    }

    @Override
    public T selectById(Long id) {
        checkNotNull(id);
        
        return sqlSessionTemplate.selectOne(getStatement(STATEMENT_SELECT_BY_ID), id);
    }

    @Override
    public List<Long> selectIdsByQuery(QUERY query) {
        checkQuery(query);
        
        return sqlSessionTemplate.selectList(getStatement(STATEMENT_SELECT_IDS_BY_QUERY), query);
    }

    @Override
    public List<T> selectByIds(Collection<Long> ids) {
        checkIds(ids);
        
        return sqlSessionTemplate.selectList(getStatement(STATEMENT_SELECT_BY_IDS), ids);
    }

    @Override
    public int selectCount(QUERY query) {
        checkQuery(query);
        
        return sqlSessionTemplate.selectOne(getStatement(STATEMENT_SELECT_COUNT), query);
    }

    @Override
    public T selectByQuery(QUERY query) {
        List<T> resultSet = selectList(query);
        
        if (resultSet == null || resultSet.size() == 0) {
            return null;
        }
        
        if (resultSet.size() > 1) {
            throw new TooManyResultsException(query.toString());
        }
        
        return resultSet.get(0);
    }

    @Override
    public List<T> selectList(QUERY query) {
        checkQuery(query);
        
        return sqlSessionTemplate.selectList(getStatement(STATEMENT_SELECT_LIST), query);
    }

    @Override
    public PagedList<T> selectPage(QUERY query) {
        checkQuery(query);
        checkArgument(query.getRange() > 0, "must setPagination.");
        
        int total = selectCount(query);
        
        PagedList<T> pagedList = new PagedList<>();
        
        if (total == 0) {
            pagedList.setTotal(0);
            pagedList.setTotalPage(0);
            pagedList.setPage(query.getPage());
            pagedList.setPageSize(query.getPageSize());
            pagedList.setSource(null);
            
            return pagedList;
        }
        
        List<T> source = selectList(query);
        pagedList.setTotal(total);
        pagedList.setTotalPage(computingTotalPage(total, query.getPageSize()));
        pagedList.setPage(query.getPage());
        pagedList.setPageSize(query.getPageSize());
        pagedList.setSource(source);
        
        return pagedList;
    }

    private void checkNotNull(Object object) {
        Preconditions.checkNotNull(object);
    }

    private void checkQuery(QUERY query) {
        checkNotNull(query);
        query.checkValidity();
    }

    private void checkIds(Collection<Long> ids) {
        checkArgument(ids != null && ids.size() > 0, "ids can't not null or empty.");
    }

    private void checkArgument(boolean argument, String message) {
        Preconditions.checkArgument(argument, message);
    }

    private String getStatement(String ref) {
        return getNameSpace().concat(".").concat(ref);
    }

    private int computingTotalPage(int total, int pageSize) {
        if (total == 0) {
            return 0;
        }
        
        int totalPage = total / pageSize;
        
        if (total == (totalPage * pageSize)) {
            return totalPage;
        } else {
            return totalPage + 1;
        }
    }
}