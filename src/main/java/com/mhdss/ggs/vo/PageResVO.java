package com.mhdss.ggs.vo;

import com.google.common.collect.Lists;

import java.util.List;

public class PageResVO<T> extends BaseVO {

	private int pageCount;// 总页数
	private int resultCount;// 总结果数
	private List<T> resultList;// 查询结果

	public static PageResVO<?> emptyPage() {
		PageResVO<?> resVO = new PageResVO<>();
		resVO.setPageCount(0);
		resVO.setResultCount(0);
		resVO.setResultList(Lists.newArrayListWithCapacity(0));
		return resVO;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
