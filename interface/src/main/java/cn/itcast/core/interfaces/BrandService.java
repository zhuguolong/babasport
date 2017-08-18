package cn.itcast.core.interfaces;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.pojo.product.Brand;

public interface BrandService {
	public List<Brand> findBrandList(String name, Integer isDisplay);
	
	/**
	 * 带查询条件，带分页
	 * @param name
	 * @param isDisplay
	 * @return
	 */
	public Pagination queryBrandList(String name, Integer isDisplay, Integer pageNo);
	
	/**
	 * 根据品牌id查询品牌信息
	 * @param id
	 * @return
	 */
	public Brand queryBrandById(Long id);

	/**
	 * 跟新品牌信息
	 * @param brand
	 */
	public void updateBrandById(Brand brand);
	
	public void deleteBrandByIds(Long[] ids);
}
