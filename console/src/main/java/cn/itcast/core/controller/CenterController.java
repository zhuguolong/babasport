package cn.itcast.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/console")
public class CenterController {
	
	/**
	 * 跳转到首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public String Index() throws Exception {
		return "index";
	}
	
	/**
	 * 首页上部
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/top")
	public String Top() throws Exception {
		return "top";
	}
	
	/**
	 * 首页主体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/main")
	public String Main() throws Exception {
		return "main";
	}
	
	/**
	 * 首页主体的左边
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/left")
	public String Left() throws Exception {
		return "left";
	}
	
	/**
	 * 首页主体的右边
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/right")
	public String Right() throws Exception {
		return "right";
	}
	
	/**
	 * 点击top页面上的'商品'按钮跳转到商品列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frame/product_main")
	public String productMain() throws Exception {
		return "frame/product_main";
	}
	
	/**
	 * 跳转到商品左侧页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frame/product_left")
	public String productLeft() throws Exception {
		return "frame/product_left";
	}
}
