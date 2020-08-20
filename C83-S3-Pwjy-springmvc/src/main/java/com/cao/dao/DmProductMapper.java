package com.cao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yc.damai.bean.DmCategory;
import com.yc.damai.bean.DmProduct;

public interface DmProductMapper {

	@Select("select * from dm_product")
	@Results(id="rmdp",value = {
			@Result(column="id",property="id",id=true),
			@Result(column="market_price",property="marketPrice"),
			@Result(column="shop_price",property="shopPrice"),
			@Result(column="is_hot",property="isHot")})
	List<DmProduct> selectAll();
	
	@Select("select * from dm_product where id=#{id}")
	@ResultMap("rmdp")
	DmProduct selectById(int id);
	
	@Select("select * from dm_product where is_hot=1")
	List<DmProduct> selectForHot();
	
	List<DmProduct> selectByObj(DmProduct dp);
	
	/**
	 * 根据枚举的分类id进行查询
	 * @Param cids 存放分类id的数组0-N
	 * 
	 * mybatis不能识别参数的名称
	 *  		@Param 用于定义参数的名称
	 */
	List<DmProduct> selectByCids(@Param("cids") int[ ] cids);
	
	int update(DmProduct dp);
	
}
