package cn.itcast.core.interfaces;

import cn.itcast.common.page.Pagination;

public interface SearchService {
	
	public Pagination SearchProductPagination(String keyword, Integer pageNo) throws Exception;
}
