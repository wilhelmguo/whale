package com.jy.controller.attendance;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.attendance.WorkDevice;
import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.attendance.WorkTime;
import com.jy.entity.oa.leave.Leave;
import com.jy.entity.system.account.Account;
import com.jy.service.attendance.WorkDeviceService;
import com.jy.service.attendance.WorkRecordService;
import com.jy.service.attendance.WorkTimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 打卡页面
 */
@Controller
@RequestMapping(value = "/backstage/sign/")
public class SignController extends BaseController<WorkRecord> {

    private static final String SECURITY_URL = "/backstage/sign/index";

    @Autowired
    public WorkRecordService service;

    @Autowired
    public WorkTimeService worktimeservice;
    @Autowired
    public WorkDeviceService workDeviceService;

    /**
     * 打卡
     */
    @RequestMapping(value = "index")
    public String index(org.springframework.ui.Model model) {
        if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/attendance/sign/list";
        }
        return Const.NO_AUTHORIZED_URL;
    }

    /**
     * 外勤打卡
     */
    @RequestMapping(value = "outsideindex")
    public String outsideindex(org.springframework.ui.Model model) {
        if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/attendance/sign/outside/list";
        }
        return Const.NO_AUTHORIZED_URL;
    }

    @RequestMapping(value = "insertOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes signMorning(String type) {
        AjaxRes ar = getAjaxRes();
        try {
            WorkTime w = new WorkTime();
            w.setCompany(getCompany());
            List<WorkTime> workTimes = worktimeservice.find(w);
            if (workTimes == null || workTimes.size() == 0) {
                ar.setFailMsg("请先设置考勤时间规则!");
                return ar;
            }
            if (!isValiadIP()) {
                ar.setFailMsg("改ip不合法,请检查设备或联系管理员!");
                return ar;
            }
            Date now = new Date();
            Account curentuser = AccountShiroUtil.getCurrentUser();
            WorkRecord o = new WorkRecord();
            o.setCompany(getCompany());
            o.setDate(DateUtils.getDateStart(now));
            o.setEmployee(curentuser.getAccountId());
            o.setDepartment(curentuser.getDepartment());
            List<WorkRecord> list = service.find(o);
            if (list == null || list.size() == 0) {
                o.setId(get32UUID());
                o.setEmployeeName(curentuser.getName());
                o.setType("0");
                o.setWeek(DateUtils.getWeekOfDate(now));
                if ("1".equals(type)) {
                    o.setMorning(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("2".equals(type)) {
                    o.setBeforenoon(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("3".equals(type)) {
                    o.setAfternoon(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("4".equals(type)) {
                    o.setNight(DateUtils.formatDate(now, "HH:mm"));
                }
                o.setStatus(getSignStatus(o, workTimes.get(0)));
                service.insert(o);
            } else {
                WorkRecord wr = list.get(0);
                if ("1".equals(type)) {
                    wr.setMorning(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("2".equals(type)) {
                    wr.setBeforenoon(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("3".equals(type)) {
                    wr.setAfternoon(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("4".equals(type)) {
                    wr.setNight(DateUtils.formatDate(now, "HH:mm"));
                }
                wr.setStatus(getSignStatus(wr, workTimes.get(0)));
                service.update(wr);
            }
            ar.setSucceedMsg(Const.SAVE_SUCCEED);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            ar.setFailMsg(Const.SAVE_FAIL);
        }
        return ar;
    }

    private boolean isValiadIP() {
        WorkDevice wd = new WorkDevice();
        wd.setCompany(getCompany());
        List<WorkDevice> wlist = workDeviceService.find(wd);
        if (CollectionUtils.isNotEmpty(wlist)) {
            for (WorkDevice onew : wlist) {
                if (onew.getName().equals(getRequest().getRemoteAddr())) {
                    return true;
                }
            }
        }else {
            return true;
        }
        return false;
    }

    private String getSignStatus(WorkRecord workRecord, WorkTime wt) {
        String status = workRecord.getStatus() == null ? "" : workRecord.getStatus();
        if (StringUtils.isBlank(workRecord.getMorning()) &&
                StringUtils.isBlank(workRecord.getBeforenoon()) &&
                StringUtils.isBlank(workRecord.getAfternoon()) &&
                StringUtils.isBlank(workRecord.getNight())) {
            if (!status.contains("旷工")) {
                status = status + ",旷工";
            }

        } else {
            if (StringUtils.isBlank(workRecord.getMorning())) {
                if (!status.contains("缺卡")) {
                    status += ",缺卡";
                }
            } else {
                if (DateUtils.paseDates(workRecord.getMorning(), "HH:mm").after(DateUtils.paseDates(wt.getMorning(), "HH:mm"))) {
                    if (!status.contains("迟到")) {
                        status += ",迟到";
                    }
                }
            }
            if (StringUtils.isBlank(workRecord.getNight())) {
                if (!status.contains("缺卡")) {
                    status += ",缺卡";
                }
            } else {
                if (DateUtils.paseDates(workRecord.getNight(), "HH:mm").before(DateUtils.paseDates(wt.getNight(), "HH:mm"))) {
                    if (!status.contains("早退")) {
                        status += ",早退";
                    }
                }
            }
            if (StringUtils.isBlank(workRecord.getBeforenoon()) && StringUtils.isNotBlank(wt.getBeforenoon())) {
                if (!status.contains("缺卡")) {
                    status += ",缺卡";
                }
            }
            if (StringUtils.isBlank(workRecord.getAfternoon()) && StringUtils.isNotBlank(wt.getAfternoon())) {
                if (!status.contains("缺卡")) {
                    status += ",缺卡";
                }
            }
        }
        if (status.startsWith(",")) {
            status = status.replaceFirst(",", "");
        }
        return status;
    }

}
