package com.jy.repository.attendance;

import com.jy.entity.attendance.WorkRecord;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;

import java.util.List;


/**
 * 用户数据层
 */
@JYBatis
public interface WorkRecordDao extends BaseDao<WorkRecord> {
    /**
     * 获得对象列表
     * @param o 对象
     * @return List
     */
    public List<WorkRecord> findByDate(WorkRecord o);
}
