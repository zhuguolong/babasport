package cn.itcast.core.service;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.interfaces.ProductService;
import cn.itcast.core.pojo.product.Product;
import cn.itcast.core.pojo.product.ProductQuery;
import cn.itcast.core.pojo.product.ProductQuery.Criteria;
import cn.itcast.core.pojo.product.Sku;
import redis.clients.jedis.Jedis;

@Service("productServiceImpl")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private Jedis jedis;
	
	@Autowired
	private HttpSolrServer solrServer;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public Pagination findProductPagination(String name, Long brandId, Boolean isShow, Integer pageNo) throws Exception {
		//设置拼接查询条件的字符串
		StringBuilder params = new StringBuilder();
		
		ProductQuery productQuery = new ProductQuery();
		//设置当前页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//设置每页查新几条记录
		productQuery.setPageSize(10);
		//创建查询条件对象
		Criteria criteria = productQuery.createCriteria();
		if(name != null){
			criteria.andNameLike("%" + name + "%");
			params.append("&name=").append(name);
		}
		if(brandId != null){
			criteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(name);
		}
		if(isShow != null){
			criteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		}else{
			criteria.andIsShowEqualTo(false);
			params.append("&isShow=0");
		}
		
		//根据条件查询数据列表
		List<Product> productList = productDao.selectByExample(productQuery);
		//根据条件插叙拿出商品总数
		int count = productDao.countByExample(productQuery);
		
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), count, productList);
		
		//设置翻页跳转路径
		String url = "/product/list.action";
		pagination.pageView(url, params.toString());
		return pagination;
	}

	@Override
	public void addProduct(Product product) throws Exception {
		//使用jedis自动生成商品唯一编号
		Long pid = jedis.incr("pid");
		product.setId(pid);
		
		//初始化上架时间
		product.setCreateTime(new Date());
		//设置下架
		product.setIsShow(false);
		//设置没有删除状态
		product.setIsDel(false);
		
		//1.保存商品数据
		productDao.insertSelective(product);
		
		//2.初始化库存数据
		String[] colors = product.getColors().split(",");
		//遍历商品中的颜色
		for(String colorId : colors){
			//遍历商品中的尺码
			for(String size : product.getSizes().split(",")){
				//同款商品的不同颜色不同尺码，都是每一个库存数据
				Sku sku = new Sku();
				sku.setColorId(Long.parseLong(colorId));
				sku.setCreateTime(new Date());
				sku.setDeliveFee(10F);
				sku.setMarketPrice(0F);
				sku.setPrice(0F);
				sku.setProductId(product.getId());
				sku.setSize(size);
				sku.setStock(0);
				sku.setUpperLimit(200);
				//保存库存数据
				skuDao.insertSelective(sku);
			}
		}
	}

	/**
	 * 上架
	 */
	@Override
	public void isShow(Long[] ids) throws Exception {
		if(ids != null){
			for (final Long id : ids) {
				//1.根据商品ID，改变商品上架状态
				Product product = new Product();
				product.setId(id);
				product.setIsShow(true);
				productDao.updateByPrimaryKeySelective(product);
				
				//2.将商品id发送到消息中间件中
				jmsTemplate.send(new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						//创建一个文本消息对象，用来发送消息对象，即商品id
						return session.createTextMessage(String.valueOf(id));
					}
				});
			}
		}
	}
}














































