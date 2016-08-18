package com.jy.controller.attendance;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.attendance.WorkRule;
import com.jy.entity.attendance.WorkTime;
import com.jy.service.attendance.WorkDeviceService;
import com.jy.service.attendance.WorkRecordService;
import com.jy.service.attendance.WorkRuleService;
import com.jy.service.attendance.WorkTimeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/*
 * 考勤记录
 */
@Controller
@RequestMapping("/backstage/workRecord/")
public class WorkRecordController extends BaseController<WorkRecord> {

    @Autowired
    public WorkRecordService service;
    @Autowired
    public WorkTimeService workTimeService;

    /**
     * 考勤记录首页
     */
    @RequestMapping("index")
    public String index(Model model) {
        if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/attendance/workrecord/list";
        }
        return Const.NO_AUTHORIZED_URL;
    }

    @RequestMapping(value = "findMyRecordByPage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes findMyRecordByPage(Page<WorkRecord> page, WorkRecord o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/workRecord/index"))) {
            try {
                o.setEmployee(AccountShiroUtil.getCurrentUser().getAccountId());
                o.setCompany(getCompany());
                Page<WorkRecord> result = service.findByPage(o, page);
                Map<String, Object> p = new HashMap<String, Object>();
                result.setResults(isNormal(result.getResults()));
                p.put("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
                p.put("list", result);
                ar.setSucceed(p);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    public List<WorkRecord> isNormal(List<WorkRecord> wrs) {
        List<WorkRecord> result = new ArrayList<WorkRecord>();
        if (CollectionUtils.isEmpty(wrs)) {
            return result;
        }
        WorkTime workRule = new WorkTime();
        workRule.setCompany(getCompany());
        List<WorkTime> workRules = workTimeService.find(workRule);
        if (CollectionUtils.isNotEmpty(workRules)) {
            WorkTime wt = workRules.get(0);
            for (WorkRecord wr : wrs) {
                wr.setNormal(true);
                if (StringUtils.isBlank(wr.getMorning()) || StringUtils.isBlank(wr.getNight())) {
                    wr.setNormal(false);
                } else if (StringUtils.isBlank(wt.getMorning()) || StringUtils.isBlank(wt.getNight())) {
                    wr.setNormal(true);
                } else {
                    if (DateUtils.paseDates(wr.getMorning(), "HH:mm").after(DateUtils.paseDates(wt.getMorning(), "HH:mm")) ||
                            DateUtils.paseDates(wr.getNight(), "HH:mm").before(DateUtils.paseDates(wt.getNight(), "HH:mm"))) {
                        wr.setNormal(false);

                    }
                }

                result.add(wr);
            }
        }
        return result;
    }

    @RequestMapping(value = "findByPage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes findByPage(Page<WorkRecord> page, WorkRecord o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/workRecord/index"))) {
            try {
//        o.setEmployee();
                o.setCompany(getCompany());
                Page<WorkRecord> result = service.findByPage(o, page);
                Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
                p.put("list", result);
                ar.setSucceed(p);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes add(WorkRecord o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
            try {
                o.setId(get32UUID());
                o.setCompany(getCompany());
//				o.setCreateTime(new Date());	
                service.insert(o);
                ar.setSucceedMsg(Const.SAVE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.SAVE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "find", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes find(WorkRecord o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                o.setCompany(getCompany());
                o.setEmployee(AccountShiroUtil.getCurrentUser().getAccountId());
                o.setDate(DateUtils.getDateStart(new Date()));
                List<WorkRecord> list = service.find(o);
                if (list == null || list.size() == 0) {
                    ar.setSucceed(new WorkRecord());
                    return ar;
                }
                WorkRecord obj = list.get(0);
                ar.setSucceed(obj);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes update(WorkRecord o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
//				o.setUpdateTime(new Date());
                service.update(o);
                ar.setSucceedMsg(Const.UPDATE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.UPDATE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes del(WorkRecord o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                service.delete(o);
                ar.setSucceedMsg(Const.DEL_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DEL_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "delBatch", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes delBatch(String chks) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
            try {
                String[] chk = chks.split(",");
                List<WorkRecord> list = new ArrayList<WorkRecord>();
                for (String s : chk) {
                    WorkRecord sd = new WorkRecord();
                    sd.setId(s);
                    list.add(sd);
                }
                service.deleteBatch(list);
                ar.setSucceedMsg(Const.DEL_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DEL_FAIL);
            }
        }
        return ar;
    }
}
