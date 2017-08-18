package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.interfaces.SkuService;
import cn.itcast.core.pojo.product.Sku;

@Controller
@RequestMapping("/sku")
public class SkuController {
	
	@Autowired
	private SkuService skuService;

	@RequestMapping("/list")
	public String skuList(Long productId, Model model) throws Exception {
		List<Sku> skuList = skuService.querySkuByProductId(productId);
		model.addAttribute("skuList", skuList);
		return "sku/list";
	}
	
	@RequestMapping("/update")
	public void updateSku(Sku sku) throws Exception {
		skuService.updateSku(sku);
	}
}
