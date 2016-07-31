package com.jy.controller.workflow.online.myTask;

import java.util.*;

import com.jy.common.utils.ActivitiUtils;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.workflow.ActivitiDeployUtil;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.service.oa.activiti.ActivitiDeployService;
import com.jy.service.oa.task.TaskInfoService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.Variable;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.oa.leave.Leave;
import com.jy.entity.workflow.online.TaskVo;
import com.jy.service.oa.leave.LeaveService;

/**
 * 我的任务
 */
@Controller
@RequestMapping(value = "/backstage/workflow/online/myTask/")
public class MyTaskController extends BaseController<Object> {

  private static final String SIGN_SECURITY_URL = "/backstage/workflow/online/myTask/signList";

  private static final String TODO_SECURITY_URL = "/backstage/workflow/online/myTask/todoList";

  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private IdentityService identityService;
  @Autowired
  private TaskService taskService;
  @Autowired
  private LeaveService leaveService;
  @Autowired
  private ActivitiDeployService activitiDeployService;
  @Autowired
  private TaskInfoService taskInfoService;
  @Autowired
  private RepositoryService repositoryService;

  /**
   * 签收任务列表
   */
  @RequestMapping(value = "signList")
  public String signList(org.springframework.ui.Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/workflow/online/myTask/signList";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value = "findSignByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findSignByPage(Page<TaskVo> page, String keyWord) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SIGN_SECURITY_URL))) {
      try {
        int pageNum = page.getPageNum() - 1;
        int pageSize = page.getPageSize();
        List<TaskVo> taskVos = new ArrayList<TaskVo>();
        String currentUserId = AccountShiroUtil.getCurrentUser().getAccountId();
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateUser(currentUserId);
        List<Task> tasks = taskQuery.listPage(pageNum, pageSize);
        for (Task t : tasks) {
          TaskVo taskVo = new TaskVo(t.getId(), t.getTaskDefinitionKey(), t.getName(), t.getProcessDefinitionId()
                  , t.getProcessInstanceId(), t.getPriority(), t.getCreateTime(), t.getDueDate()
                  , t.getDescription(), t.getOwner(), t.getAssignee());
          taskVos.add(taskVo);
        }
        long count = taskQuery.count();
        page.setTotalRecord((int) count);
        page.setResults(taskVos);
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("list", page);
        ar.setSucceed(p);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }

  /**
   * 待办任务列表
   */
  @RequestMapping(value = "todoList")
  public String todoList(org.springframework.ui.Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/workflow/online/myTask/todoList";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value = "findTodoByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findTodoByPage(Page<TaskVo> page, String keyWord) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, TODO_SECURITY_URL))) {
      try {
        int pageNum = page.getPageNum() - 1;
        int pageSize = page.getPageSize();
        List<TaskVo> taskVos = new ArrayList<TaskVo>();
        String currentUserId = AccountShiroUtil.getCurrentUser().getAccountId();
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(currentUserId);
        List<Task> tasks = taskQuery.listPage(pageNum, pageSize);
        for (Task t : tasks) {
          TaskVo taskVo = new TaskVo(t.getId(), t.getTaskDefinitionKey(), t.getName(), t.getProcessDefinitionId()
                  , t.getProcessInstanceId(), t.getPriority(), t.getCreateTime(), t.getDueDate()
                  , t.getDescription(), t.getOwner(), t.getAssignee());
          TaskInfo taskInfo = new TaskInfo();
          taskInfo.setTaskid(t.getId());
          List<TaskInfo> list = taskInfoService.find(taskInfo);
          if (list == null || list.size() == 0) {
            continue;
          }
          TaskInfo tInfo = list.get(0);
          taskVo.setPresentationSubject(tInfo.getPresentationsubject());
          taskVo.setProcessName(tInfo.getAttr1());
          taskVos.add(taskVo);

        }
        long count = taskQuery.count();
        page.setTotalRecord((int) count);
        page.setResults(taskVos);
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("list", page);
        ar.setSucceed(p);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }

  /**
   * 签收任务
   */
  @RequestMapping(value = "claim/{id}", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes claimTask(@PathVariable("id") String taskId) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SIGN_SECURITY_URL))) {
      try {
        String currentUserId = AccountShiroUtil.getCurrentUser().getAccountId();
        taskService.claim(taskId, currentUserId);
        ar.setSucceedMsg("签收成功");
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg("签收失败");
      }
    }
    return ar;
  }

  /**
   * 办理任务
   */
  @RequestMapping(value = "complete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes complete(@PathVariable("id") String taskId, Variable var) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, TODO_SECURITY_URL))) {
      try {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskid(taskId);
        List<TaskInfo> list = taskInfoService.find(taskInfo);
        Date now = new Date();
        if (list == null || list.size() == 0) {
          ar.setFailMsg("未找到相应任务");
          return ar;
        } else {
          TaskInfo ti = list.get(0);
          ti.setCompletetime(now);
          ti.setAttr2("同意");
          ti.setStatus(0);
          taskInfoService.update(ti);

          Map<String, Object> variables = var.getVariableMap();
          taskService.complete(taskId, variables);
          String pId = (String) variables.get("processInstanceId");
          ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                  .processInstanceId(pId)//使用流程实例ID查询
                  .singleResult();
          if (pi != null) {
            List<Task> tasksNext = taskService.createTaskQuery().processInstanceId(pId).list();
            for (Task task : tasksNext) {
              TaskInfo tInfo = new TaskInfo();
              tInfo.setId(get32UUID());
              tInfo.setBusinesskey(ti.getBusinesskey());
              tInfo.setCode(task.getTaskDefinitionKey());
              tInfo.setName(task.getName());
              tInfo.setStatus(1);
              tInfo.setPresentationsubject(ti.getPresentationsubject());
              tInfo.setCreatetime(now);
              tInfo.setCreator(ti.getCreator());
              TaskInfo tQuery = new TaskInfo();
              tQuery.setBusinesskey(ti.getBusinesskey());
              List<TaskInfo> ts = taskInfoService.find(tQuery);
              int approverNext = 0;
              if (ts != null) {
                approverNext = ts.size() - 1;
              }
              String assignee= (String) taskService.getVariable(task.getId(), "approver" + approverNext);
              tInfo.setAssignee(assignee);
              tInfo.setTaskid(task.getId());
              tInfo.setExecutionid(task.getExecutionId());
              tInfo.setProcessinstanceid(pi.getId());
              tInfo.setProcessdefinitionid(pi.getProcessDefinitionId());
              taskInfoService.insert(tInfo);
            }
          }
        }
        ar.setSucceedMsg("办理成功");
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg("办理失败");
      }
    }
    return ar;
  }


  @RequestMapping(value = "test", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes test(String pId) {
    AjaxRes ar = getAjaxRes();
    try {
      activitiDeployService.buildDeployment("leavetest", "请假流程测试",2);
    } catch (Exception e) {
      logger.error("部署出错", e);
    }

    return ar;
  }


  /**
   * 查询任务
   */
  @RequestMapping(value = "findTask", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findTask(String pId) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, TODO_SECURITY_URL))) {
      try {
        Leave leave = leaveService.findLeaveByPId(pId);
        ar.setSucceed(leave, Const.DATA_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }

  /**
   * 驳回任务
   */
  @RequestMapping(value = "reject/{id}", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes reject(@PathVariable("id") String taskId, String pId, String rejectReason, Variable var) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, TODO_SECURITY_URL))) {
      try {
        Map<String, Object> variables = var.getVariableMap();

        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskid(taskId);
        List<TaskInfo> list = taskInfoService.find(taskInfo);
        Date now = new Date();
        if (list == null || list.size() == 0) {
          ar.setFailMsg("未找到相应任务");
          return ar;
        } else {
          TaskInfo t = list.get(0);
          t.setDescription(rejectReason);
          t.setAttr2("拒绝");
          t.setStatus(0);
          t.setCompletetime(now);
          taskInfoService.update(t);
//          leaveService.updateRejectReason(pId, rejectReason);
          taskService.complete(taskId, variables);
          while (true) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                    .processInstanceId(pId)//使用流程实例ID查询
                    .singleResult();
            if (pi != null) {
              List<Task> tasksNext = taskService.createTaskQuery().processInstanceId(pId).list();
              for (Task task : tasksNext) {
                taskService.complete(task.getId(), variables);
              }
            } else {
              break;
            }
          }

        }
        ar.setSucceedMsg("驳回成功");
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg("驳回失败");
      }
    }
    return ar;
  }

  /**
   * 调整任务
   */
  @RequestMapping(value = "adjust/{taskId}", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes adjust(@PathVariable("taskId") String taskId, String pId, String description, Variable var) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, TODO_SECURITY_URL))) {
      try {
        Map<String, Object> variables = var.getVariableMap();
        leaveService.updateDescription(pId, description);
        taskService.complete(taskId, variables);
        ar.setSucceedMsg("调整成功");
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg("调整失败");
      }
    }
    return ar;
  }
}
