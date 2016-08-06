package com.jy.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

/**
 *
 */

/**
 * @ClassName: ActivitiDeployServiceImp
 * @Description: TODO(activiti 工作流程图自动生成)
 * @author
 * @date 2016年4月20日 上午8:32:56 
 *
 */
public class ActivitiTest01 {

  public static void main(String[] args) {
    try {
      test01();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void test01() throws IOException {
    ProcessEngine processEngine=getProcessEngine();

    // 1. Build up the model from scratch
    BpmnModel model = new BpmnModel();
    Process process=new Process();


    List<ActivitiListener> executionListeners=new ArrayList<ActivitiListener>();
    ActivitiListener a=new ActivitiListener();
    a.setEvent("end");
    a.setImplementationType("class");
    a.setImplementation("com.jy.common.workflow.listener.EndListener");
    executionListeners.add(a);
    process.setExecutionListeners(executionListeners);

    model.addProcess(process);
    final String PROCESSID ="process01";
    final String PROCESSNAME ="测试01";
    process.setId(PROCESSID);
    process.setName(PROCESSNAME);

    process.addFlowElement(createStartEvent());
    process.addFlowElement(createUserTask("request", "发起请求"));
    process.addFlowElement(createUserTask("approve", "领导审批", "${approver0}"));

    process.addFlowElement(createEndEvent());

    process.addFlowElement(createSequenceFlow("start", "request"));
    process.addFlowElement(createSequenceFlow("request", "approve"));
    process.addFlowElement(createSequenceFlow("approve", "end"));


    // 2. Generate graphical information
    new BpmnAutoLayout(model).execute();

    // 3. Deploy the process to the engine
    Deployment deployment = processEngine.getRepositoryService().createDeployment().addBpmnModel(PROCESSID+".bpmn", model).name(PROCESSID+"_deployment").deploy();

    // 4. Start a process instance
    ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(PROCESSID);

    // 5. Check if task is available
    List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
    Assert.assertEquals(1, tasks.size());
    Task t=tasks.get(0);
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("approver0","2");
    processEngine.getTaskService().complete(t.getId(),variables);


    // 6. Save process diagram to a file
    InputStream processDiagram = processEngine.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());
    FileUtils.copyInputStreamToFile(processDiagram, new File("deployments/"+PROCESSID+".png"));

    // 7. Save resulting BPMN xml to a file
    InputStream processBpmn = processEngine.getRepositoryService().getResourceAsStream(deployment.getId(), PROCESSID+".bpmn");
    FileUtils.copyInputStreamToFile(processBpmn,new File("deployments/"+PROCESSID+".bpmn"));

    System.out.println(".........end...");
  }

  protected static ProcessEngine getProcessEngine(){
    ProcessEngine processEngine=ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
            .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/jyroot")
            .setJdbcDriver("com.mysql.jdbc.Driver")
            .setJdbcUsername("root")
            .setJdbcPassword("root")
            .setDatabaseSchemaUpdate("true")
            .setJobExecutorActivate(false)
            .buildProcessEngine();
    return processEngine;

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
    List<String> candidateUsers=new ArrayList<String>();
    candidateUsers.add(candidateUser);
    UserTask userTask = new UserTask();
    userTask.setName(name);
    userTask.setId(id);
    userTask.setCandidateUsers(candidateUsers);
    return userTask;
  }


  /*连线*/
  protected static SequenceFlow createSequenceFlow(String from, String to) {
    SequenceFlow flow = new SequenceFlow();
    flow.setSourceRef(from);
    flow.setTargetRef(to);

    return flow;
  }
  /*连线*/
  protected static SequenceFlow createSequenceFlow(String from, String to,String name,String conditionExpression) {
    SequenceFlow flow = new SequenceFlow();
    flow.setSourceRef(from);
    flow.setTargetRef(to);
    flow.setName(name);
    if(StringUtils.isNotEmpty(conditionExpression)){
      flow.setConditionExpression(conditionExpression);
    }
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
    List<ActivitiListener> executionListeners=new ArrayList<ActivitiListener>();
    ActivitiListener a=new ActivitiListener();
    a.setEvent("end");
    a.setImplementationType("class");
    a.setImplementation("com.jy.common.workflow.listener.EndListener");
    executionListeners.add(a);
    endEvent.setExecutionListeners(executionListeners);
    return endEvent;
  }

}  