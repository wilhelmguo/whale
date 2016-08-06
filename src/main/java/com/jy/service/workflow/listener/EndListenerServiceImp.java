package com.jy.service.workflow.listener;

import com.jy.common.utils.SpringWebContextUtil;
import com.jy.common.utils.base.Const;
import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.oa.claim.Claim;
import com.jy.entity.oa.leave.Leave;
import com.jy.entity.oa.overtime.Overtime;
import com.jy.entity.oa.patch.Patch;
import com.jy.entity.oa.userdef.Userdef;
import com.jy.service.attendance.WorkRecordService;
import com.jy.service.oa.claim.ClaimService;
import com.jy.service.oa.leave.LeaveService;
import com.jy.service.oa.overtime.OvertimeService;
import com.jy.service.oa.patch.PatchService;
import com.jy.service.oa.userdef.UserdefService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.service.invoker.SpringBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gsw on 16-8-5
 * @version 1.0
 */
@Service("EndListenerServiceImp")
public class EndListenerServiceImp implements ExecutionListener, EndListenerService {

    @Autowired
    private LeaveService leaveService;
    @Autowired
    private ClaimService claimService;
    @Autowired
    private OvertimeService overtimeService;
    @Autowired
    private PatchService patchService;
    @Autowired
    private UserdefService userdefService;
    @Autowired
    private WorkRecordService workRecordService;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        leaveService = SpringWebContextUtil.getApplicationContext().getBean(LeaveService.class);
        claimService = SpringWebContextUtil.getApplicationContext().getBean(ClaimService.class);
        overtimeService = SpringWebContextUtil.getApplicationContext().getBean(OvertimeService.class);
        patchService = SpringWebContextUtil.getApplicationContext().getBean(PatchService.class);
        userdefService = SpringWebContextUtil.getApplicationContext().getBean(UserdefService.class);
        workRecordService = SpringWebContextUtil.getApplicationContext().getBean(WorkRecordService.class);


        String eventName = delegateExecution.getEventName();
//start
        ExecutionEntity ee = (ExecutionEntity) delegateExecution;
        String pId = delegateExecution.getProcessInstanceId();
        String name = ee.getProcessDefinition().getName();
        Boolean approval = (Boolean) delegateExecution.getVariable("approval");
        Integer isapproval = approval ? 1 : 0;
        if ("end".equals(eventName)) {
            if (Const.LEAVE_NAME.equals(name)) {
                Leave leave = leaveService.findLeaveByPId(pId);
                if (leave != null) {
                    leave.setIsapprove(isapproval);
                    leave.setIsValid(isapproval);
                    leaveService.update(leave);
                }
            } else if (Const.CLAIM_NAME.equals(name)) {
                Claim claim = new Claim();
                claim.setPid(pId);
                List<Claim> list = claimService.find(claim);
                if (list != null && list.size() != 0) {
                    Claim c = list.get(0);
                    c.setIsapprove(isapproval);
                    c.setIsValid(isapproval);
                    claimService.update(c);
                }
            } else if (Const.OVERTIME_NAME.equals(name)) {
                Overtime obj = new Overtime();
                obj.setPid(pId);
                List<Overtime> list = overtimeService.find(obj);
                if (list != null && list.size() != 0) {
                    Overtime c = list.get(0);
                    c.setIsapprove(isapproval);
                    c.setIsValid(isapproval);
                    overtimeService.update(c);
                }
            } else if (Const.PATCH_NAME.equals(name)) {
                Patch obj = new Patch();
                obj.setPid(pId);
                List<Patch> list = patchService.find(obj);
                if (list != null && list.size() != 0) {
                    Patch c = list.get(0);
                    c.setIsapprove(isapproval);
                    c.setIsValid(isapproval);
                    patchService.update(c);
                    if (isapproval == 1) {
                        WorkRecord wr = new WorkRecord();
                        wr.setEmployee(c.getAccountId());
                        wr.setDate(c.getDate());
                        List<WorkRecord> listWr = workRecordService.find(wr);
                        if (listWr != null && listWr.size() != 0) {
                            WorkRecord w1 = listWr.get(0);
                            if (StringUtils.isNotBlank(c.getMorning())) {
                                w1.setMorning(c.getMorning());
                            }
                            if (StringUtils.isNotBlank(c.getBeforenoon())) {
                                w1.setMorning(c.getBeforenoon());
                            }
                            if (StringUtils.isNotBlank(c.getAfternoon())) {
                                w1.setMorning(c.getAfternoon());
                            }
                            if (StringUtils.isNotBlank(c.getNight())) {
                                w1.setMorning(c.getNight());
                            }
                            String status = StringUtils.isBlank(w1.getStatus()) ? "补卡" : w1.getStatus() + ",补卡";
                            w1.setStatus(status);
                            workRecordService.update(w1);
                        } else {
                            WorkRecord wr2 = new WorkRecord();
                            wr2.setEmployee(c.getAccountId());
                            wr2.setDate(c.getDate());
                            wr2.setType("0");
                            wr2.setEmployeeName(c.getName());
                            wr2.setMorning(c.getMorning());
                            wr2.setBeforenoon(c.getBeforenoon());
                            wr2.setAfternoon(c.getAfternoon());
                            wr2.setNight(c.getNight());
                            wr2.setStatus("补卡");

                            workRecordService.insert(wr2);
                        }
                    }
                }
            } else {
                Userdef obj = new Userdef();
                obj.setPid(pId);
                List<Userdef> list = userdefService.find(obj);
                if (list != null && list.size() != 0) {
                    Userdef c = list.get(0);
                    c.setIsapprove(isapproval);
                    c.setIsValid(isapproval);
                    userdefService.update(c);
                }
            }
        }
    }


}
