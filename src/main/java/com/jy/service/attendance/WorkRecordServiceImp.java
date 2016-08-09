package com.jy.service.attendance;

import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.system.dict.SysDict;
import com.jy.repository.attendance.WorkRecordDao;
import com.jy.service.base.BaseServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("WorkRecordService")
public class WorkRecordServiceImp extends BaseServiceImp<WorkRecord> implements WorkRecordService {
    @Autowired
    private WorkRecordDao workRecordDao;

    public List<WorkRecord> findByDate(WorkRecord o) {
        return workRecordDao.findByDate(o);
    }
}
