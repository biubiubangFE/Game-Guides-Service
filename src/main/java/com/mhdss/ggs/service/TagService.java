package com.mhdss.ggs.service;

import com.mhdss.ggs.dataobject.TagItemDO;

import java.util.List;

public interface TagService {

    List<Long> queryTagItem(String searchTag);

    List<Long> queryNewsId(List<Long> tagItemIds);
}
