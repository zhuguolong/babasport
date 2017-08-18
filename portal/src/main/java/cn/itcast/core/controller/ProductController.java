package cn.itcast.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.interfaces.SearchService;

@Controller
public class ProductController {
	
	@Autowired
	private SearchService searchService;

	/**
	 * 前台首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/")
	public String Index() throws Exception {
		return "index";
	}
	
	@RequestMapping("/product/list")
	public String searchList(String keyword, Integer pageNo, Model model) throws Exception {
		Pagination pagination = searchService.SearchProductPagination(keyword, pageNo);
		model.addAttribute("pagination", pagination);
		return "search";
	}
}