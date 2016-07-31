package com.jy.repository.oa.task;

import com.jy.common.mybatis.Page;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务数据层
 */
@JYBatis
public interface TaskInfoDao extends BaseDao<TaskInfo> {
  /**
   * 获得对象列表
   *
   * @param o    对象
   * @param page 分页对象
   * @return List
   */
  public List<TaskInfo> findComplateByPage(@Param("param") TaskInfo o, Page<TaskInfo> page);

  /**
   * 获得我发起的对象列表
   *
   * @param o    对象
   * @param page 分页对象
   * @return List
   */
  public List<TaskInfo> findMystartByPage(@Param("param") TaskInfo o, Page<TaskInfo> page);
}
