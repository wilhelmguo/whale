package com.jy.controller.workflow.online.apply;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.oa.overtime.Overtime;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.service.oa.activiti.ActivitiDeployService;
import com.jy.service.oa.overtime.OvertimeService;
import com.jy.service.oa.task.TaskInfoService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加班页面
 */
@Controller
@RequestMapping(value = "/backstage/workflow/online/overtime/")
public class OvertimeController extends BaseController<Object> {

  private static final String SECURITY_URL = "/backstage/workflow/online/overtime/index";

  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private TaskService taskService;
  @Autowired
  private TaskInfoService taskInfoService;
  @Autowired
  private IdentityService identityService;
  @Autowired
  private OvertimeService overtimeService;
  @Autowired
  private ActivitiDeployService activitiDeployService;


  /**
   * 加班列表
   */
  @RequestMapping("indexList")
  public String indexList(Model model) {
    if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/attendance/overtime/list";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value="findByPage", method=RequestMethod.POST)
  @ResponseBody
  public AjaxRes findByPage(Page<Overtime> page, Overtime o){
    AjaxRes ar=getAjaxRes();
    if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/backstage/sysDict/index"))){
      try {
        o.setIsapprove(1);
        Page<Overtime> result=overtimeService.findByPage(o,page);
        Map<String, Object> p=new HashMap<String, Object>();
        p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
        p.put("list",result);
        ar.setSucceed(p);
      } catch (Exception e) {
        logger.error(e.toString(),e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value="find", method=RequestMethod.POST)
  @ResponseBody
  public AjaxRes find(Overtime o){
    AjaxRes ar=getAjaxRes();
    if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
      try {
        List<Overtime> list=overtimeService.find(o);
        Overtime obj=list.get(0);
        ar.setSucceed(obj);
      } catch (Exception e) {
        logger.error(e.toString(),e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }
  
  /**
   *
   */
  @RequestMapping(value = "index")
  public String index(org.springframework.ui.Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/workflow/online/apply/overtime";
    }
    return Const.NO_AUTHORIZED_URL;
  }


  /**
   * 启动流程
   */
  @RequestMapping(value = "start", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes startWorkflow(Overtime o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
      try {
        String currentUserId = AccountShiroUtil.getCurrentUser().getAccountId();
        String[] approvers = o.getApprover().split(",");
        Map<String, Object> variables = new HashMap<String, Object>();
        for (int i = 0; i < approvers.length; i++) {
          variables.put("approver" + i, approvers[i]);
        }
        String key = "overtime" + approvers.length;
        activitiDeployService.buildDeployment(key, "加班流程", approvers.length);

        identityService.setAuthenticatedUserId(currentUserId);
        Date now = new Date();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, variables);
        String pId = processInstance.getId();
        String leaveID = get32UUID();
        o.setPid(pId);
        o.setAccountId(currentUserId);
        o.setCreatetime(now);
        o.setName(AccountShiroUtil.getCurrentUser().getName());
        o.setId(leaveID);
        o.setOrg(AccountShiroUtil.getCurrentUser().getDepartment());

        overtimeService.insert(o);
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(pId).list();
        for (Task task : tasks) {
          taskService.complete(task.getId(), variables);
          TaskInfo taskInfo = new TaskInfo();
          taskInfo.setId(get32UUID());
          taskInfo.setBusinesskey(leaveID);
          taskInfo.setCode(processInstance.getActivityId());
          String processDefinitionName = ((ExecutionEntity) processInstance).getProcessInstance().getProcessDefinition().getName();
          taskInfo.setName(task.getName());
          taskInfo.setStatus(0);
          String subkect = processDefinitionName + "-"
                  + AccountShiroUtil.getCurrentUser().getName() + "-" + DateUtils.formatDate(now, "yyyy-MM-dd HH:mm");
          taskInfo.setPresentationsubject(subkect);
          taskInfo.setAttr1(processDefinitionName);
          taskInfo.setCreatetime(now);
          taskInfo.setCompletetime(now);
          taskInfo.setCreator(currentUserId);
          taskInfo.setAssignee(currentUserId);
          taskInfo.setTaskid(task.getId());
          taskInfo.setExecutionid(task.getExecutionId());
          taskInfo.setProcessinstanceid(processInstance.getId());
          taskInfo.setProcessdefinitionid(processInstance.getProcessDefinitionId());
          taskInfoService.insert(taskInfo);
        }
        List<Task> tasksNext = taskService.createTaskQuery().processInstanceId(pId).list();
        for (Task task : tasksNext) {
          TaskInfo taskInfo = new TaskInfo();
          taskInfo.setId(get32UUID());
          taskInfo.setBusinesskey(leaveID);
          taskInfo.setCode(task.getTaskDefinitionKey());
          taskInfo.setName(task.getName());
          taskInfo.setStatus(1);
          String processDefinitionName = ((ExecutionEntity) processInstance).getProcessInstance().getProcessDefinition().getName();
          taskInfo.setAttr1(processDefinitionName);
          String subkect = processDefinitionName + "-"
                  + AccountShiroUtil.getCurrentUser().getName() + "-" + DateUtils.formatDate(now, "yyyy-MM-dd HH:mm");
          taskInfo.setPresentationsubject(subkect);
          taskInfo.setCreatetime(now);
          taskInfo.setCreator(currentUserId);
          taskInfo.setAssignee(approvers[0]);
          taskInfo.setTaskid(task.getId());
          taskInfo.setExecutionid(task.getExecutionId());
          taskInfo.setProcessinstanceid(processInstance.getId());
          taskInfo.setProcessdefinitionid(processInstance.getProcessDefinitionId());
          taskInfoService.insert(taskInfo);
        }
        ar.setSucceedMsg("发起加班申请成功!");
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg("启动流程失败");
      } finally {
        identityService.setAuthenticatedUserId(null);
      }
    }
    return ar;
  }

}
