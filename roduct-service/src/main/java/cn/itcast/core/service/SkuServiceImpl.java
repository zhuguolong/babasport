package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.interfaces.SkuService;
import cn.itcast.core.pojo.product.Color;
import cn.itcast.core.pojo.product.Sku;
import cn.itcast.core.pojo.product.SkuQuery;
import cn.itcast.core.pojo.product.SkuQuery.Criteria;

@Service("skuServiceImpl")
@Transactional
public class SkuServiceImpl implements SkuService {
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private ColorDao colorDao;

	@Override
	public List<Sku> querySkuByProductId(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		Criteria criteria = skuQuery.createCriteria();
		criteria.andProductIdEqualTo(productId);
		List<Sku> skuList = skuDao.selectByExample(skuQuery);
		//因为sku表中的color_id(外键)列为数字，所以还要到bbs_color表中根据(id)外键字段获取颜色表中的name值
		if(skuList != null){
			for (Sku sku : skuList) {
				Long colorId = sku.getColorId();
				Color color = colorDao.selectByPrimaryKey(colorId);
				sku.setColor(color);
			}
		}
		return skuList;
	}

	@Override
	public void updateSku(Sku sku) throws Exception {
		skuDao.updateByPrimaryKeySelective(sku);
	}

}
