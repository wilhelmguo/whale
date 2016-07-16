package com.jy.repository.task.log;

import com.jy.entity.task.log.TaskLog;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;
/**
 * 动态任务数据层
 */
@JYBatis
public interface TaskLogDao extends BaseDao<TaskLog>{
	
}
