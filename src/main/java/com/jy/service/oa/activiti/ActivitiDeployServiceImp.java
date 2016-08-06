package com.jy.service.oa.activiti;

import com.jy.common.utils.base.Const;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.nutz.dao.util.cri.Static;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName: ActivitiDeployServiceImp
 * @Description: TODO(activiti 工作流程图自动生成)
 * @date 2016年6月20日 上午8:32:56
 */
@Service("ActivitiDeployServiceImp")
public class ActivitiDeployServiceImp implements ActivitiDeployService {
  @Autowired
  private RepositoryService repositoryService;



  /**
   * @param key  流程key
   * @param name 流程名称
   * @param size 审批级数
   * @return
   * @throws IOException
   */
  @Override
  public Deployment buildDeployment(String key, String name, int size) throws IOException {
//    ProcessEngine processEngine = getProcessEngine();433476
    // 1. Build up the model from scratch
    BpmnModel model = new BpmnModel();
    Process process = new Process();

    List<ActivitiListener> executionListeners=new ArrayList<ActivitiListener>();
    ActivitiListener end=new ActivitiListener();
    end.setEvent("end");
    end.setImplementationType("class");
    end.setImplementation("com.jy.service.workflow.listener.EndListenerServiceImp");
    executionListeners.add(end);
//    ActivitiListener start=new ActivitiListener();
//    start.setEvent("start");
//    start.setImplementationType("class");
//    start.setImplementation("com.jy.common.workflow.listener.EndListener");
//    executionListeners.add(start);
    process.setExecutionListeners(executionListeners);

    model.addProcess(process);
    process.setId(key);
    process.setName(name);

    process.addFlowElement(createStartEvent());
    process.addFlowElement(createUserTask("request", "发起请求"));
    for (int i = 0; i < size; i++) {
      String userTaskName = "approve" + i;
      String approverName = "${approver" + i + "}";
      process.addFlowElement(createUserTask(userTaskName, Const.SPMC.get(i), approverName));
    }
    process.addFlowElement(createEndEvent());
    process.addFlowElement(createSequenceFlow("start", "request"));
    process.addFlowElement(createSequenceFlow("request", "approve0"));
    for (int i = 1; i < size; i++) {
      process.addFlowElement(createSequenceFlow("approve" + (i - 1), "approve" + i));
    }
    process.addFlowElement(createSequenceFlow("approve" + (size-1), "end"));

//    process.addFlowElement(createSequenceFlow("start", "request"));
//    process.addFlowElement(createSequenceFlow("request", "approve0"));
//    process.addFlowElement(createSequenceFlow("approve0", "end"));


    // 2. Generate graphical information
    new BpmnAutoLayout(model).execute();

    // 3. Deploy the process to the engine
    Deployment deployment = repositoryService.createDeployment().addBpmnModel(name + ".bpmn20.xml", model).name(name + "_deployment").deploy();

    return deployment;
  }

  /*任务节点*/
  protected UserTask createUserTask(String id, String name) {
    UserTask userTask = new UserTask();
    userTask.setName(name);
    userTask.setId(id);
    return userTask;
  }

  /*任务节点*/
  protected UserTask createUserTask(String id, String name, String candidateUser) {
    List<String> candidateUsers = new ArrayList<String>();
    candidateUsers.add(candidateUser);
    UserTask userTask = new UserTask();
    userTask.setName(name);
    userTask.setId(id);
    userTask.setCandidateUsers(candidateUsers);
    return userTask;
  }

  protected ProcessEngine getProcessEngine() {
    ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
            .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/oa?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true")
            .setJdbcDriver("com.mysql.jdbc.Driver")
            .setJdbcUsername("root")
            .setJdbcPassword("root")
            .setDatabaseSchemaUpdate("true")
            .setJobExecutorActivate(false)
            .buildProcessEngine();
    return processEngine;

  }

  /*连线*/
  protected SequenceFlow createSequenceFlow(String from, String to) {
    SequenceFlow flow = new SequenceFlow();
    flow.setSourceRef(from);
    flow.setTargetRef(to);

    return flow;
  }

  /*排他网关*/
  protected ExclusiveGateway createExclusiveGateway(String id) {
    ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
    exclusiveGateway.setId(id);
    return exclusiveGateway;
  }

  /*开始节点*/
  protected StartEvent createStartEvent() {
    StartEvent startEvent = new StartEvent();
    startEvent.setId("start");
    startEvent.setName("开始");
    return startEvent;
  }

  /*结束节点*/
  protected EndEvent createEndEvent() {
    EndEvent endEvent = new EndEvent();
    endEvent.setId("end");
    endEvent.setName("结束");
    return endEvent;
  }

}  