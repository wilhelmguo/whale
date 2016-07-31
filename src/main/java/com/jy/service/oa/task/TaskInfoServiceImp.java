package com.jy.service.oa.task;

import com.jy.common.mybatis.Page;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.repository.oa.task.TaskInfoDao;
import com.jy.service.base.BaseServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TaskInfoService")
public class TaskInfoServiceImp extends BaseServiceImp<TaskInfo> implements TaskInfoService {
  @Autowired
  private TaskInfoDao taskInfoDao;

  @Override
  public Page<TaskInfo> findComplateByPage(TaskInfo o, Page<TaskInfo> page) {
    page.setResults(taskInfoDao.findComplateByPage(o, page));
    return page;
  }
  @Override
  public Page<TaskInfo> findMystartByPage(TaskInfo o, Page<TaskInfo> page){
    page.setResults(taskInfoDao.findMystartByPage(o, page));
    return page;
  }
}
