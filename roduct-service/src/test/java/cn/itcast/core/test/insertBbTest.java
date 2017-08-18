package cn.itcast.core.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.interfaces.BbTestService;
import cn.itcast.core.pojo.BbTest;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class insertBbTest {
	
	@Autowired
	private BbTestService bbTestService;
	
	@Test
	public void testInsert(){
		
		BbTest bbTest = new BbTest();
		bbTest.setName("张莎");
		bbTest.setBirthday(new Date());
		
		bbTestService.insertTest(bbTest);
	}
}
