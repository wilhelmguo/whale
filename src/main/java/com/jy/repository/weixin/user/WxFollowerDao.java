package com.jy.repository.weixin.user;

import java.util.List;

import com.jy.entity.weixin.user.WxFollower;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;


/**
 * 微信关注者数据层
 */
@JYBatis
public interface WxFollowerDao  extends BaseDao<WxFollower>{
 
    public void clearFollower();
    
    public void insertFollowers(List<WxFollower> o);
}
