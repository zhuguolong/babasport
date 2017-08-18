package cn.itcast.core.interfaces;

import java.util.List;

import cn.itcast.core.pojo.product.Sku;

public interface SkuService {
	
	public List<Sku> querySkuByProductId(Long productId);
	
	public void updateSku(Sku sku) throws Exception;
}
