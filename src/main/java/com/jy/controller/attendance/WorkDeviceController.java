package com.jy.controller.attendance;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.attendance.WorkDevice;
import com.jy.service.attendance.WorkDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/*
 * 考勤设备
 */
@Controller
@RequestMapping("/backstage/workDevice/")
public class WorkDeviceController extends BaseController<WorkDevice> {

  @Autowired
  public WorkDeviceService service;

  /**
   * 考勤设备首页
   */
  @RequestMapping("index")
  public String index(Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/attendance/workdevice/list";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value = "findByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findByPage(Page<WorkDevice> page, WorkDevice o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/workDevice/index"))) {
      try {
        o.setCompany(getCompany());
        Page<WorkDevice> result = service.findByPage(o, page);
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
  public AjaxRes add(WorkDevice o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
      try {
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
  public AjaxRes find(WorkDevice o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
      try {
        o.setCompany(getCompany());
        List<WorkDevice> list = service.find(o);
        WorkDevice obj = list.get(0);
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
  public AjaxRes update(WorkDevice o) {
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
  public AjaxRes del(WorkDevice o) {
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
        List<WorkDevice> list = new ArrayList<WorkDevice>();
        for (String s : chk) {
          WorkDevice sd = new WorkDevice();
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
