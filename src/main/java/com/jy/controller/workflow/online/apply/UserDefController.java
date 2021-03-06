package com.jy.controller.workflow.online.apply;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.entity.oa.userdef.Userdef;
import com.jy.service.oa.activiti.ActivitiDeployService;
import com.jy.service.oa.claim.ClaimService;
import com.jy.service.oa.task.TaskInfoService;
import com.jy.service.oa.userdef.UserdefService;
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
 * 自定义页面
 */
@Controller
@RequestMapping(value = "/backstage/workflow/online/userdef/")
public class UserDefController extends BaseController<Object> {

    private static final String SECURITY_URL = "/backstage/workflow/online/userdef/index";

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskInfoService taskInfoService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private UserdefService userdefService;
    @Autowired
    private ActivitiDeployService activitiDeployService;


    /**
     * 申请列表
     */
    @RequestMapping(value = "index")
    public String index(org.springframework.ui.Model model) {
        if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/workflow/online/apply/userdef";
        }
        return Const.NO_AUTHORIZED_URL;
    }


    /**
     * 启动流程
     */
    @RequestMapping(value = "start", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes startWorkflow(Userdef o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                String currentUserId = AccountShiroUtil.getCurrentUser().getAccountId();
                String[] approvers = o.getApprover().split(",");
                Map<String, Object> variables = new HashMap<String, Object>();
                for (int i = 0; i < approvers.length; i++) {
                    variables.put("approver" + i, approvers[i]);
                }
                String key = o.getWkey();
//                activitiDeployService.buildDeployment(key, o.getWname(), approvers.length);

                identityService.setAuthenticatedUserId(currentUserId);
                Date now = new Date();
                ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(key, variables,getCompany());
                String pId = processInstance.getId();
                String leaveID = get32UUID();
                o.setPid(pId);
                o.setAccountId(currentUserId);
                o.setCreatetime(now);
                o.setName(AccountShiroUtil.getCurrentUser().getName());
                o.setId(leaveID);

                userdefService.insert(o);
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
                ar.setSucceedMsg("发起审批申请成功!");
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
