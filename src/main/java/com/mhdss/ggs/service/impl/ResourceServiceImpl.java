package com.mhdss.ggs.service.impl;

import com.mhdss.ggs.constant.BaseStatus;
import com.mhdss.ggs.constant.ParseStatus;
import com.mhdss.ggs.dao.ResourceDAO;
import com.mhdss.ggs.dataobject.ResourceDO;
import com.mhdss.ggs.dto.SpiderResultDTO;
import com.mhdss.ggs.query.ResourceQuery;
import com.mhdss.ggs.service.ResourceService;
import com.mhdss.ggs.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {


    @Autowired
    private ResourceDAO resourceDAO;

    @Override
    public void addResource(SpiderResultDTO resultDTO) {

        ResourceDO resourceDO = new ResourceDO();
        resourceDO.setAuthor(resultDTO.getAuthor());
        resourceDO.setDescription(resultDTO.getDescription());
        resourceDO.setFromType(resultDTO.getSpiderType().getType());
        resourceDO.setImg(resultDTO.getImg());
        resourceDO.setTag(resultDTO.getTag());
        resourceDO.setTitle(resultDTO.getTitle());
        resourceDO.setPublishTime(resultDTO.getTimestamp());
        resourceDO.setShareUrl(resultDTO.getShareUrl());
        resourceDO.setParseStatus(ParseStatus.WAIT.getStatus());
        resourceDO.setStatus(BaseStatus.NORMAL.getStatus());
        resourceDO.setCreateTime(DateUtil.getCurrentTimeStamp());
        resourceDO.setUpdateTime(DateUtil.getCurrentTimeStamp());

        resourceDAO.insertUpdateOnDuplicate(resourceDO);
    }

    @Override
    public List<ResourceDO> selectResource(ParseStatus parseStatus) {

        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.setParseStatus(parseStatus.getStatus());

        return resourceDAO.selectList(resourceQuery);
    }

    @Override
    public void updateParseStatus(Long resourceId, ParseStatus parseStatus) {
        ResourceDO resourceDO = new ResourceDO();
        resourceDO.setId(resourceId);
        resourceDO.setParseStatus(parseStatus.getStatus());
        resourceDO.setUpdateTime(DateUtil.getCurrentTimeStamp());
        resourceDAO.updateById(resourceDO);
    }
}
