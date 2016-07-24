package com.jy.controller.attendance;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.attendance.WorkTime;
import com.jy.service.attendance.WorkRecordService;
import com.jy.service.attendance.WorkTimeService;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/*
 * 考勤记录分析
 */
@Controller
@RequestMapping("/backstage/WorkRecordAnalysis/")
public class WorkRecordAnalysisController extends BaseController<WorkRecord> {

  @Autowired
  public WorkRecordService service;

  @Autowired
  public WorkTimeService worktimeservice;

  /**
   * 考勤记录首页
   */
  @RequestMapping("index")
  public String index(Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/attendance/workrecord/analysis/list";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value = "findByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findByPage(Page<WorkRecord> page, WorkRecord o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/WorkRecordAnalysis/index"))) {
      try {
//        o.setEmployee();
        o.setCompany(getCompany());
        Page<WorkRecord> result = service.findByPage(o, page);
//        WorkTime w = new WorkTime();
//        w.setCompany(getCompany());
//        List<WorkTime> list = worktimeservice.find(w);
//        if (list == null || list.size() == 0) {
//          ar.setFailMsg("请先设置考勤时间规则!");
//          return ar;
//        }
//        WorkTime wt = list.get(0);
//        List<WorkRecord> workRecordList = new ArrayList<WorkRecord>();
//        if (result.getResults() != null && result.getResults().size() > 0){
//          for (WorkRecord workRecord : result.getResults()) {
//            String status = "";
//            if (StringUtils.isBlank(workRecord.getMorning()) &&
//                    StringUtils.isBlank(workRecord.getBeforenoon()) &&
//                    StringUtils.isBlank(workRecord.getAfternoon()) &&
//                    StringUtils.isBlank(workRecord.getNight())) {
//              status += "旷工";
//            } else {
//              if (StringUtils.isBlank(workRecord.getMorning())) {
//                status += "签到未打卡";
//              } else {
//                if (DateUtils.parseDate(workRecord.getMorning(), "HH:mm").after(DateUtils.parseDate(wt.getMorning(), "HH:mm"))) {
//                  status += "迟到";
//                }
//              }
//              if (StringUtils.isBlank(workRecord.getNight())) {
//                status += "签退未打卡";
//              } else {
//                if (DateUtils.parseDate(workRecord.getNight(), "HH:mm").before(DateUtils.parseDate(wt.getNight(), "HH:mm"))) {
//                  status += "早退";
//                }
//              }
//              if (StringUtils.isBlank(workRecord.getBeforenoon())) {
//                status += "上午签退未打卡";
//              }
//              if (StringUtils.isBlank(workRecord.getAfternoon())) {
//                status += "下午签到未打卡";
//              }
//            }
//            workRecord.setStatus(status);
//            workRecordList.add(workRecord);
//          }
//        }
//
//        result.setResults(workRecordList);
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
