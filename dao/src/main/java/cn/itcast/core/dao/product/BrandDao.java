package cn.itcast.core.dao.product;

import java.util.List;

import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.BrandQuery;

public interface BrandDao {
	
	public List<Brand> findBrandList(BrandQuery brandQuery);
	
	/**
	 * 带查询条件，带分页
	 * @param brandQuery
	 * @return
	 */
	public List<Brand> queryBrandList(BrandQuery brandQuery);
	
	/**
	 * 查询品牌记录总数
	 * @param brandQuery
	 * @return
	 */
	public Integer queryBrandCount(BrandQuery brandQuery);
	
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
	
	/**
	 * 批量删除品牌信息
	 * @param ids
	 */
	public void deleteBrandByIds(Long[] ids);
}
