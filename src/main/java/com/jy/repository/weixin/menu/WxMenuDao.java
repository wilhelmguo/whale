package com.jy.repository.weixin.menu;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.weixin.menu.WxMenu;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;

/**
 * 微信菜单数据层
 */
@JYBatis
public interface WxMenuDao  extends BaseDao<WxMenu>{
	/**
	 * 统计菜单数目
	 * @param o 对象      
	 * @return long
	 */
	public int menuCount(WxMenu o);
	
	public WxMenu getWxMenuByKeyId(@Param("keyId")String keyId);
}
