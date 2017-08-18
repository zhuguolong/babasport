package cn.itcast.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.interfaces.SearchService;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.ProductQuery;

@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private HttpSolrServer solrServer;

	@Override
	public Pagination SearchProductPagination(String keyword, Integer pageNo) throws Exception {
		
		StringBuilder params = new StringBuilder();
		List<Product> productList = new ArrayList<>();
		long count = 0L;
		
		//通过mybatis逆向生成的这个对象, 来计算下pageNO, pageSize, startRows
		ProductQuery productQuery = new ProductQuery();
		//设置当前页数
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//设置每页显示条数
		productQuery.setPageSize(12);
		
		//创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		//开始封装参数条件
		//设置关键字查询
		if(keyword != null){
			solrQuery.setQuery("name_ik:" + keyword);
			params.append("&keyword=").append(keyword);
		}else {
			//solrQuery.setQuery("*:*");
		}
		//根据价格升序查询
		solrQuery.setSort("price",ORDER.asc);
		//分页
		//设置从第几条开始查询，相当于SQL语句中limit的第一个参数
		solrQuery.setStart(productQuery.getStartRow());
		//每页查询多少条，相当于SQL语句中limit的第二个参数
		solrQuery.setRows(productQuery.getPageSize());
		
		//查询并返回响应
		QueryResponse response = solrServer.query(solrQuery);
		//获取查询到的结果集
		SolrDocumentList results = response.getResults();
		if(results != null){
			//获取查询的数据总数
			count = results.getNumFound();
			//遍历结果集
			for (SolrDocument doc : results) {
				Product product = new Product();
				
				product.setId(Long.parseLong(String.valueOf(doc.get("id"))));
				product.setBrandId(Long.parseLong(String.valueOf(doc.get("brandId"))));
				product.setImgUrl(String.valueOf(doc.get("url")));
				if(String.valueOf(doc.get("price")) != null && !"".equals(String.valueOf(doc.get("price")))){
					product.setPrice(Float.parseFloat(String.valueOf(doc.get("price"))));
				}else{
					product.setPrice(0f);
				}
				product.setName(String.valueOf(doc.get("name_ik")));
				
				productList.add(product);
			}
		}
		
		//创建分页对象
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), (int)count, productList);
		//设置翻页时的跳转路径
		String url = "/product/list";
		//设置翻页所需要带的查询条件和翻页路径
		pagination.pageView(url, params.toString());
		return pagination;
	}

}
