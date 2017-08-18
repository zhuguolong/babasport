package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.interfaces.BrandService;
import cn.itcast.core.interfaces.ColorService;
import cn.itcast.core.interfaces.ProductService;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Product;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ColorService colorService;
	
	@RequestMapping("/list")
	public String productList(String name, Long brandId, Boolean isShow, Integer pageNo, Model model) throws Exception {
		//实现输入条件
		List<Brand> brandList = brandService.findBrandList(null, null);
		//当点击查询按钮时实现查询
		Pagination pagination = productService.findProductPagination(name, brandId, isShow, pageNo);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("brandList", brandList);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
		return "product/list";
	}
	
	/**
	 * 跳转到添加商品页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAdd")
	public String toAdd(Model model) throws Exception {
		//获取所有品牌
		List<Brand> brandList = brandService.findBrandList(null, null);
		//获取所有颜色
		List<Color> colorList = colorService.queryColorList();
		
		model.addAttribute("brandList", brandList);
		model.addAttribute("colorList", colorList);
		return "product/add";
	}
	
	@RequestMapping("/add")
	public String addProduct(Product product) throws Exception {
		productService.addProduct(product);
		return "redirect:list.action";
	}
	
	@RequestMapping("/isShow")
	public String isShow(Long[] ids) throws Exception {
		productService.isShow(ids);
		return "redirect:list.action";
	}
}
