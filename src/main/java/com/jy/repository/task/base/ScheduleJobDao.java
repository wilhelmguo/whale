package com.jy.repository.task.base;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.task.base.ScheduleJob;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;
/**
 * 动态任务数据层
 */
@JYBatis
public interface ScheduleJobDao extends BaseDao<ScheduleJob>{
	/**
	 * 根据Id获取任务
	 */
	public ScheduleJob getScheduleJobById(@Param("scheduleJobId")String scheduleJobId);
	
}
