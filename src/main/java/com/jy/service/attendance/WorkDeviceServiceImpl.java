package com.jy.service.attendance;

import com.jy.entity.attendance.WorkDevice;
import com.jy.repository.attendance.WorkDeviceDao;
import com.jy.service.base.BaseServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gsw on 16-7-11
 * @version 1.0
 */
@Service("WorkDeviceService")
public class WorkDeviceServiceImpl extends BaseServiceImp<WorkDevice> implements WorkDeviceService {
  @Autowired
  private WorkDeviceDao workDeviceDao;
}
