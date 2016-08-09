package com.jy.service.attendance;

import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.system.dict.SysDict;
import com.jy.service.base.BaseService;

import java.util.List;

public interface WorkRecordService extends BaseService<WorkRecord>{
    /**
     * 获得对象列表
     * @param o 对象
     * @return List
     */
    public List<WorkRecord> findByDate(WorkRecord o);
}
