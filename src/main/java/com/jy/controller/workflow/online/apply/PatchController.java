package com.jy.controller.workflow.online.apply;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.oa.overtime.Overtime;
import com.jy.entity.oa.patch.Patch;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.service.oa.activiti.ActivitiDeployService;
import com.jy.service.oa.overtime.OvertimeService;
import com.jy.service.oa.patch.PatchService;
import com.jy.service.oa.task.TaskInfoService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 补卡页面
 */
@Controller
@RequestMapping(value = "/backstage/workflow/online/patch/")
public class PatchController extends BaseController<Object> {

  private static final String SECURITY_URL = "/backstage/workflow/online/patch/index";

  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private TaskService taskService;
  @Autowired
  private TaskInfoService taskInfoService;
  @Autowired
  private IdentityService identityService;
  @Autowired
  private PatchService patchService;
  @Autowired
  private ActivitiDeployService activitiDeployService;


  /**
   * 补卡列表
   */
  @RequestMapping(value = "index")
  public String index(org.springframework.ui.Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/workflow/online/apply/patch";
    }
    return Const.NO_AUTHORIZED_URL;
  }


  /**
   * 启动流程
   */
  @RequestMapping(value = "start", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes startWorkflow(Patch o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
      try {
        String currentUserId = AccountShiroUtil.getCurrentUser().getAccountId();
        String[] approvers = o.getApprover().split(",");
        Map<String, Object> variables = new HashMap<String, Object>();
        for (int i = 0; i < approvers.length; i++) {
          variables.put("approver" + (i + 1), approvers[i]);
        }
        String workflowKey = "patch";

        identityService.setAuthenticatedUserId(currentUserId);
        Date now = new Date();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(workflowKey, variables, getCompany());
        String pId = processInstance.getId();
        String leaveID = get32UUID();
        o.setPid(pId);
        o.setAccountId(currentUserId);
        o.setCreatetime(now);
        o.setIsvalid(0);
        o.setName(AccountShiroUtil.getCurrentUser().getName());
        o.setId(leaveID);

        patchService.insert(o);
        Task task = taskService.createTaskQuery().processInstanceId(pId).singleResult();
        String processDefinitionName = ((ExecutionEntity) processInstance).getProcessInstance().getProcessDefinition().getName();
        String subkect = processDefinitionName + "-"
                + AccountShiroUtil.getCurrentUser().getName() + "-" + DateUtils.formatDate(now, "yyyy-MM-dd HH:mm");

        //开始流程
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId(get32UUID());
        taskInfo.setBusinesskey(leaveID);
        taskInfo.setCode("start");
        taskInfo.setName("发起申请");
        taskInfo.setStatus(0);
        taskInfo.setPresentationsubject(subkect);
        taskInfo.setAttr1(processDefinitionName);
        taskInfo.setCreatetime(DateUtils.addSeconds(now, -1));
        taskInfo.setCompletetime(DateUtils.addSeconds(now, -1));
        taskInfo.setCreator(currentUserId);
        taskInfo.setAssignee(currentUserId);
        taskInfo.setTaskid("0");
        taskInfo.setPkey(workflowKey);
        taskInfo.setExecutionid("0");
        taskInfo.setProcessinstanceid(processInstance.getId());
        taskInfo.setProcessdefinitionid(processInstance.getProcessDefinitionId());
        taskInfoService.insert(taskInfo);

        //第一级审批流程
        taskInfo.setId(get32UUID());
        taskInfo.setCode(processInstance.getActivityId());
        taskInfo.setName(task.getName());
        taskInfo.setStatus(1);
        taskInfo.setTaskid(task.getId());
        taskInfo.setCreatetime(now);
        taskInfo.setCompletetime(null);
        taskInfo.setAssignee(approvers[0]);
        taskInfoService.insert(taskInfo);
        ar.setSucceedMsg("发起补卡申请成功!");
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
