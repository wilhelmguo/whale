package com.jy.common.utils.workflow;

import com.jy.common.utils.base.Const;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @ClassName: ActivitiDeployServiceImp
 * @Description: TODO(activiti 工作流程图自动生成)
 * @date 2016年4月20日 上午8:32:56
 */
public class ActivitiDeployUtil {

  public static void main(String[] args) {
    try {
      buildDeployment("leavetest", "请假流程测试", 2);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Deployment buildDeployment(String key, String name, int size) throws IOException {
    BpmnModel model = new BpmnModel();
    Process process = new Process();
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
    Deployment deployment = getProcessEngine().getRepositoryService().createDeployment().addBpmnModel(name + ".bpmn20.xml", model).name(name + "_deployment").deploy();

    return deployment;
  }

  /*任务节点*/
  protected static UserTask createUserTask(String id, String name) {
    UserTask userTask = new UserTask();
    userTask.setName(name);
    userTask.setId(id);
    return userTask;
  }

  /*任务节点*/
  protected static UserTask createUserTask(String id, String name, String candidateUser) {
    List<String> candidateUsers = new ArrayList<String>();
    candidateUsers.add(candidateUser);
    UserTask userTask = new UserTask();
    userTask.setName(name);
    userTask.setId(id);
    userTask.setCandidateUsers(candidateUsers);
    return userTask;
  }

  protected static ProcessEngine getProcessEngine() {
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
  protected static SequenceFlow createSequenceFlow(String from, String to) {
    SequenceFlow flow = new SequenceFlow();
    flow.setSourceRef(from);
    flow.setTargetRef(to);

    return flow;
  }

  /*排他网关*/
  protected static ExclusiveGateway createExclusiveGateway(String id) {
    ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
    exclusiveGateway.setId(id);
    return exclusiveGateway;
  }

  /*开始节点*/
  protected static StartEvent createStartEvent() {
    StartEvent startEvent = new StartEvent();
    startEvent.setId("start");
    startEvent.setName("开始");
    return startEvent;
  }

  /*结束节点*/
  protected static EndEvent createEndEvent() {
    EndEvent endEvent = new EndEvent();
    endEvent.setId("end");
    endEvent.setName("结束");
    return endEvent;
  }

}  