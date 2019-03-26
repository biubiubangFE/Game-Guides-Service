package com.mhdss.ggs.service.impl;

import com.mhdss.ggs.dao.TagItemDAO;
import com.mhdss.ggs.dao.TagItemNewsRelatedDAO;
import com.mhdss.ggs.dataobject.TagItemDO;
import com.mhdss.ggs.dataobject.TagItemNewsRelatedDO;
import com.mhdss.ggs.query.TagItemNewsRelatedQuery;
import com.mhdss.ggs.query.TagItemQuery;
import com.mhdss.ggs.service.TagService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagItemDAO tagItemDAO;
    @Autowired
    private TagItemNewsRelatedDAO tagItemNewsRelatedDAO;

    @Override
    public List<Long> queryTagItem(String searchTag) {

        TagItemQuery tagItemQuery = new TagItemQuery();
        tagItemQuery.setSearchTag(searchTag);
        List<TagItemDO> tagItems = tagItemDAO.selectList(tagItemQuery);
        if (CollectionUtils.isEmpty(tagItems)) {
            return new ArrayList<>();
        }
        return tagItems.stream().map(TagItemDO::getId).collect(Collectors.toList());
    }

    @Override
    public List<Long> queryNewsId(List<Long> tagItemIds) {

        TagItemNewsRelatedQuery query = new TagItemNewsRelatedQuery();
        query.setTagItemIds(tagItemIds);
        List<TagItemNewsRelatedDO> tagItemNewsRelatedDOS = tagItemNewsRelatedDAO.selectList(query);
        if (CollectionUtils.isEmpty(tagItemNewsRelatedDOS)) {
            return new ArrayList<>();
        }
        return tagItemNewsRelatedDOS.stream().map(TagItemNewsRelatedDO::getNewsId).collect(Collectors.toList());
    }
}
