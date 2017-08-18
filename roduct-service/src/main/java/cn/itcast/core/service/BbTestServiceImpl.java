package cn.itcast.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.dao.BbTestDao;
import cn.itcast.core.interfaces.BbTestService;
import cn.itcast.core.pojo.BbTest;

@Service("bbTestServiceImpl")
@Transactional
public class BbTestServiceImpl implements BbTestService {

	@Autowired
	private BbTestDao bbTestDao;
	
	@Override
	public void insertTest(BbTest bbTest) {
		bbTestDao.insertTest(bbTest);
	}

}
