package com.jy.controller.moblie.workflow;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.oa.task.TaskInfo;
import com.jy.entity.workflow.online.TaskVo;
import com.jy.service.oa.task.TaskInfoService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsw on 16-8-8
 * @version 1.0
 */
@Controller
@RequestMapping("/moblie/api/v1/workflow")
public class MTaskController extends BaseController<Object> {
    private static final String TODO_SECURITY_URL = "/backstage/workflow/online/myTask/todoList";
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskInfoService taskInfoService;
    @Autowired
    public RuntimeService runtimeService;

    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes findTodoByPage() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, TODO_SECURITY_URL))) {
            try {
                int pageNum = 1;
                int pageSize = 50;
                List<TaskVo> taskVos = new ArrayList<TaskVo>();
                String currentUserId = AccountShiroUtil.getCurrentUser().getAccountId();
                // 根据当前人的ID查询
                TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(currentUserId).orderByTaskCreateTime().desc();
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
                ar.setSucceed(taskVos);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    //已经审批
    @RequestMapping(value = "/finishes", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes findComplateByPage() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/workflow/online/taskInfo/index"))) {
            try {
                Page<TaskInfo> page = new Page<TaskInfo>();
                page.setPageNum(1);
                page.setPageSize(50);
                TaskInfo o = new TaskInfo();
                o.setAssignee(AccountShiroUtil.getCurrentUser().getAccountId());
                Page<TaskInfo> result = taskInfoService.findComplateByPage(o, page);
                ar.setSucceed(result.getResults());
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "mystarts", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes findMystartByPage() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/workflow/online/taskInfo/index"))) {
            try {
                Page<TaskInfo> page = new Page<TaskInfo>();
                page.setPageNum(1);
                page.setPageSize(50);
                TaskInfo o = new TaskInfo();
                o.setCreator(AccountShiroUtil.getCurrentUser().getAccountId());
                Page<TaskInfo> result = taskInfoService.findMystartByPage(o, page);
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
                ar.setSucceed(resultlist);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }
}
