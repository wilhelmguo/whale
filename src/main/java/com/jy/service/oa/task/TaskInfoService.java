package com.jy.service.oa.task;

import com.jy.common.mybatis.Page;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.service.base.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInfoService extends BaseService<TaskInfo> {
  public Page<TaskInfo> findMystartByPage(TaskInfo o, Page<TaskInfo> page);
  public Page<TaskInfo> findComplateByPage(TaskInfo o, Page<TaskInfo> page);
}
