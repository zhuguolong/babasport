package cn.itcast.core.interfaces;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Product;

public interface ProductService {

	public Pagination findProductPagination(String name, Long brandId, Boolean isShow, Integer pageNo) throws Exception;
	
	public void addProduct(Product product) throws Exception;

	public void isShow(Long[] ids) throws Exception;
}
