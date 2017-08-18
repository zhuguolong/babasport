package cn.itcast.core.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.interfaces.BbTestService;
import cn.itcast.core.pojo.BbTest;

@Controller
public class TestController {
	
	@Autowired
	private BbTestService bbTestService;
	
	@RequestMapping("/test")
	private void insertTest(){
		BbTest bbTest = new BbTest();
		bbTest.setName("李四");
		bbTest.setBirthday(new Date());
		
		bbTestService.insertTest(bbTest);
	}
}
