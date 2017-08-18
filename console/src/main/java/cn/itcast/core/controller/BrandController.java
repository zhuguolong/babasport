package cn.itcast.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.interfaces.BrandService;
import cn.itcast.core.pojo.product.Brand;

@Controller
@RequestMapping("/brand")
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	
	/**
	 * 条件查询，分页显示品牌列表
	 * @param name
	 * @param isDisplay
	 * @param pageNo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String brandList(String name, Integer isDisplay,Integer pageNo , Model model) throws Exception {
		Pagination pagination = brandService.queryBrandList(name, isDisplay, pageNo);
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);
		return "brand/list";
	}
	
	/**
	 * 根据id查询品牌信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toEdit")
	public String editBrand(Long id, Model model) throws Exception {
		Brand brand = brandService.queryBrandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	
	/**
	 * 休干品牌信息
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public String updateBrand(Brand brand) throws Exception {
		brandService.updateBrandById(brand);
		return "redirect:list.action";
	}
	
	/**
	 * 批量删除品牌信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAll")
	public String deleteBrandByIds(Long[] ids) throws Exception {
		brandService.deleteBrandByIds(ids);
		return "forward:list.action";
	}
}
