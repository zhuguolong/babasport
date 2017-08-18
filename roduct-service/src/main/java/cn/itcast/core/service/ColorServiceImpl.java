package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.interfaces.ColorService;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.ColorQuery;
import cn.itcast.core.pojo.product.ColorQuery.Criteria;

@Service("colorServiceImpl")
@Transactional
public class ColorServiceImpl implements ColorService {
	
	@Autowired
	private ColorDao colorDao;

	@Override
	public List<Color> queryColorList() {
		ColorQuery colorQuery = new ColorQuery();
		Criteria criteria = colorQuery.createCriteria();
		criteria.andParentIdNotEqualTo(0L);
		return colorDao.selectByExample(colorQuery );
	}

}
