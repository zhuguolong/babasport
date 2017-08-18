package cn.itcast.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.dao.product.BrandDao;
import cn.itcast.core.interfaces.BrandService;
import cn.itcast.core.pojo.product.Brand;
import cn.itcast.core.pojo.product.BrandQuery;

@Service("brandServiceImpl")
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;
	
	/**
	 * 查询品牌列表，带查询条件,带分页
	 */
	@Override
	public Pagination queryBrandList(String name, Integer isDisplay, Integer pageNo) {
		StringBuilder params = new StringBuilder();
		
		BrandQuery brandQuery = new BrandQuery();
		//cpn为当pageNo的值为null或者为负数时的处理方法
		brandQuery.setPageNo(Pagination.cpn(pageNo));	//当前页
		//每页显示5条记录
		brandQuery.setPageSize(5);
		
		if(name != null){
			brandQuery.setName(name);
			params.append("&name=").append(name);
		}
		if(isDisplay != null){
			brandQuery.setIsDisplay(isDisplay);
			params.append("&isDisplay").append(isDisplay);
		}else{
			brandQuery.setIsDisplay(1);
			params.append("&isDisplay").append(1);
		}
		
		//根据查询条件查询品牌列表
		List<Brand> brandList = brandDao.queryBrandList(brandQuery);
		//根据查询条件计算品牌记录总数
		Integer brandCount = brandDao.queryBrandCount(brandQuery);
		//方法要返回一个Pagination对象，所以就先创建一个Pagination对象。即构建分页对象，参数一：当前页，参数二：每页线束条数，参数三：查询记录总条数，参数四：查询的结果列表
		Pagination pagination = new Pagination(brandQuery.getPageNo(), brandQuery.getPageSize(), brandCount, brandList);
		
		//设置每次点击要跳转的页面number
		String url = "/brand/list.action";
		//设置每次跳转的路径和传入的参数
		pagination.pageView(url, params.toString());
		return pagination;
	}

	@Override
	public Brand queryBrandById(Long id) {
		return brandDao.queryBrandById(id);
	}

	/**
	 * 跟新品牌信息
	 */
	@Override
	public void updateBrandById(Brand brand) {
		brandDao.updateBrandById(brand);
	}

	@Override
	public void deleteBrandByIds(Long[] ids) {
		brandDao.deleteBrandByIds(ids);
	}

	@Override
	public List<Brand> findBrandList(String name, Integer isDisplay) {
		BrandQuery brandQuery = new BrandQuery();
		if(name != null){
			brandQuery.setName(name);
		}
		if(isDisplay != null){
			brandQuery.setIsDisplay(isDisplay);
		} else {
			brandQuery.setIsDisplay(1);
		}
		
		List<Brand> list = brandDao.findBrandList(brandQuery);
		return list;
	}

}
