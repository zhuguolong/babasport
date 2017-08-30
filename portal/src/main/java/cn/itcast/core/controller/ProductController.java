package cn.itcast.core.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.interfaces.BrandService;
import cn.itcast.core.interfaces.CmsService;
import cn.itcast.core.interfaces.SearchService;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.Sku;

@Controller
public class ProductController {
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private CmsService cmsService;
	
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
	public String searchList(String keyword, Integer pageNo, Long brandId, String price, Model model) throws Exception {
		//获取品牌数据
		//List<Brand> brands = brandService.findBrandList(null, null);
		
		//从redis中获取品牌数据
		List<Brand> brands = brandService.queryBrandListFromRedis();
		
		//重solr中查询商品信息
		Pagination pagination = searchService.SearchProductPagination(keyword, pageNo, brandId, price);
		
		Map<String, String> map = new HashMap<>();
		//回显已经选择的过滤条件
		if(brandId != null){
			for (Brand brand : brands) {
				if(brandId == brand.getId()){
					map.put("品牌为：", brand.getName());
					break;
				}
			}
		}
		if(price != null){
			map.put("价格为：", price + "元");
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("brands", brands);
		model.addAttribute("keyword", keyword);
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		model.addAttribute("map", map);
		return "search";
	}
	
	/**
	 * 展示商品详情
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/detail")
	public String productDetail(Long id, Model model) throws Exception {
		//根据商品id获取商品对象
		Product product = cmsService.findProductById(id);
		//根据商品id获取商品颜色列表对象
		List<Sku> skuList = cmsService.findSkuListByProductId(id);
		
		//过滤颜色重复的数据(颜色id相同的颜色数据就认为是重复的数据)
		Set<Color> colors = new HashSet<>();
		if(skuList != null){
			for (Sku sku : skuList) {
				colors.add(sku.getColor());
			}
		}
		
		model.addAttribute("product", product);
		model.addAttribute("skuList", skuList);
		model.addAttribute("colors", colors);
		return "product";
	}
}
















































