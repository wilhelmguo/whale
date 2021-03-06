package com.jy.controller.attendance;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.attendance.WorkTime;
import com.jy.entity.attendance.WorkTime;
import com.jy.service.attendance.WorkRuleService;
import com.jy.service.attendance.WorkTimeService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 考勤时间
 */
@Controller
@RequestMapping("/backstage/workTime/")
public class WorkTimeController extends BaseController<WorkTime> {

  @Autowired
  public WorkTimeService service;

  /**
   * 考勤时间首页
   */
  @RequestMapping("index")
  public String index(Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/attendance/worktime/list";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value = "findByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findByPage(Page<WorkTime> page, WorkTime o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/workTime/index"))) {
      try {
        o.setCompany(getCompany());
        Page<WorkTime> result = service.findByPage(o, page);
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
  public AjaxRes add(WorkTime o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
      try {
        WorkTime w=new WorkTime();
        w.setCompany(getCompany());
        int count = service.count(w);
        if (count > 0) {
          ar.setFailMsg("考勤时间规则只能设置一条!");
          return ar;
        }
        o.setCompany(getCompany());
        o.setId(get32UUID());
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
  public AjaxRes find(WorkTime o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
      try {
        o.setCompany(getCompany());
        List<WorkTime> list = service.find(o);
        if (list==null || list.size()==0){
          ar.setSucceed(new WorkTime());
          return ar;
        }
        WorkTime obj = list.get(0);
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
  public AjaxRes update(WorkTime o) {
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
  public AjaxRes del(WorkTime o) {
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
        List<WorkTime> list = new ArrayList<WorkTime>();
        for (String s : chk) {
          WorkTime sd = new WorkTime();
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
