package com.jy.repository.oa.leave;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.oa.leave.Leave;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;
/**
 * 请假数据层
 */
@JYBatis
public interface LeaveDao extends BaseDao<Leave>{
	
	public Leave findLeaveByPId(@Param("pId")String pId);
	
	public void updateRejectReason(Leave leave);
	
	public void updateDescription(Leave leave);
}
