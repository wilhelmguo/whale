package com.jy.repository.weixin.event;

import java.util.List;

import com.jy.entity.weixin.event.WxEventClick;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;
/**
 * 微信点击事件数据层
 */
@JYBatis
public interface WxEventClickDao  extends BaseDao<WxEventClick>{

	/**
	* 批量插入微信点击事件
	* @param o 微信点击事件集合
	*/
	public void insertItems(List<WxEventClick> o);
	
}
