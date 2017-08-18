package cn.itcast.core.pojo.product;

import java.io.Serializable;

public class BrandQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3044001923623731235L;
	
	//品牌ID  bigint
	private Long id;
	//品牌名称
	private String name;
	//描述
	private String description;
	//图片URL
	private String imgUrl;
	//排序  越大越靠前   
	private Integer sort;
	//是否可用   0 不可用 1 可用
	private Integer isDisplay;//is_display
	
	//添加属性
	//当前页
	private Integer pageNo = 1;
	private Integer pageSize = 10;
	//开始行
	private Integer startRow;
	
	public void setPageNo(Integer pageNo) {
		//计算开始行
		this.startRow = (pageNo - 1)*pageSize;
		this.pageNo = pageNo;
	}
    
    public void setPageSize(Integer pageSize) {
		//计算开始行
		this.startRow = (pageNo - 1)*pageSize;
		this.pageSize = pageSize;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}
    
}
