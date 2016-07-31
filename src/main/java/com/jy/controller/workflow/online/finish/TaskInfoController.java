package com.jy.controller.workflow.online.finish;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.service.oa.task.TaskInfoService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
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
 * 任务历史
 */
@Controller
@RequestMapping("/backstage/workflow/online/taskInfo/")
public class TaskInfoController extends BaseController<TaskInfo> {

  @Autowired
  public TaskInfoService service;
  @Autowired
  public RuntimeService runtimeService;

  /**
   * 我发起的任务首页
   */
  @RequestMapping("myStart")
  public String myStart(Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/workflow/online/mystart/list";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  /**
   * 已办任务首页
   */
  @RequestMapping("index")
  public String index(Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/workflow/online/finish/list";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value = "findMystartByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findMystartByPage(Page<TaskInfo> page, TaskInfo o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/workflow/online/taskInfo/index"))) {
      try {
        o.setCreator(AccountShiroUtil.getCurrentUser().getAccountId());
        Page<TaskInfo> result = service.findMystartByPage(o, page);
        List<TaskInfo> list = result.getResults();
        List<TaskInfo> resultlist = new ArrayList<TaskInfo>();
        if (list != null) {
          for (TaskInfo t : list) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessinstanceid()).singleResult();
            if (pi == null){
              t.setAttr3("已结束");
            }else {
              t.setAttr3("运行中");
            }
            resultlist.add(t);
          }
        }
        result.setResults(resultlist);
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

  @RequestMapping(value = "findComplateByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findComplateByPage(Page<TaskInfo> page, TaskInfo o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/workflow/online/taskInfo/index"))) {
      try {
        o.setAssignee(AccountShiroUtil.getCurrentUser().getAccountId());
        Page<TaskInfo> result = service.findComplateByPage(o, page);
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


  @RequestMapping(value = "find", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes find(TaskInfo o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
      try {
        List<TaskInfo> list = service.find(o);
        TaskInfo obj = list.get(0);
        ar.setSucceed(obj);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }

}
