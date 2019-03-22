package com.mhdss.ggs.service;

import com.mhdss.ggs.constant.ParseStatus;
import com.mhdss.ggs.dataobject.ResourceDO;
import com.mhdss.ggs.dto.SpiderResultDTO;

import java.util.List;

public interface ResourceService {

    void addResource(SpiderResultDTO resultDTO);

    List<ResourceDO> selectResource(ParseStatus parseStatus);
}
