package com.jy.controller.attendance;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.attendance.WorkDevice;
import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.oa.leave.Leave;
import com.jy.entity.system.account.Account;
import com.jy.service.attendance.WorkRecordService;
import org.activiti.engine.runtime.ProcessInstance;
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

  @RequestMapping(value = "insertOrUpdate", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes signMorning(String type) {
    AjaxRes ar = getAjaxRes();
    try {
      Date now = new Date();
      Account curentuser = AccountShiroUtil.getCurrentUser();
      WorkRecord o = new WorkRecord();
      o.setCompany(getCompany());
      o.setDate(DateUtils.getDateStart(now));
      o.setEmployee(curentuser.getLoginName());
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
        service.insert(o);
      } else {
        WorkRecord w = list.get(0);
        if ("1".equals(type)) {
          w.setMorning(DateUtils.formatDate(now, "HH:mm"));
        }
        if ("2".equals(type)) {
          w.setBeforenoon(DateUtils.formatDate(now, "HH:mm"));
        }
        if ("3".equals(type)) {
          w.setAfternoon(DateUtils.formatDate(now, "HH:mm"));
        }
        if ("4".equals(type)) {
          w.setNight(DateUtils.formatDate(now, "HH:mm"));
        }
        service.update(w);
      }
      ar.setSucceedMsg(Const.SAVE_SUCCEED);
    } catch (Exception e) {
      logger.error(e.toString(), e);
      ar.setFailMsg(Const.SAVE_FAIL);
    }
    return ar;
  }


}
