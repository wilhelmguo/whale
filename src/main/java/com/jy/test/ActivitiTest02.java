package com.jy.test;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *
 */

/**
 * @author
 * @ClassName: ActivitiDeployServiceImp
 * @Description: TODO(activiti 工作流程图自动生成)
 * @date 2016年4月20日 上午8:32:56
 */
public class ActivitiTest02 {

    public static void main(String[] args) {
        try {
            test01();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test01() throws IOException {
        ProcessEngine processEngine = getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        TaskService taskService = processEngine.getTaskService();
        File file = new File("/home/gsw/git/whale/src/main/resources/bpmn/leave.bpmn");
        InputStream in = new FileInputStream(file);
        Deployment deployment = repositoryService.createDeployment().addInputStream("leave.bpmn", in).name("请假流程").deploy();
        System.out.println(deployment);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approver0", "1");
        variables.put("approver1", "2");
        variables.put("day", "3");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        InputStream processDiagram = processEngine.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());
        FileUtils.copyInputStreamToFile(processDiagram, new File("deployments/leave.png"));
        InputStream processBpmn = processEngine.getRepositoryService().getResourceAsStream(deployment.getId(), "leave.bpmn");
        FileUtils.copyInputStreamToFile(processBpmn, new File("deployments/leave.bpmn"));
        DeploymentEntity de=(DeploymentEntity)deployment;
        Model m=repositoryService.createModelQuery().modelKey("leave").singleResult();
        ProcessDefinition p= repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        BpmnModel model= repositoryService.getBpmnModel(p.getId());
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for(FlowElement e : flowElements) {
                if ("org.activiti.bpmn.model.UserTask".equals(e.getClass().getName())){
                    System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());
                }

            }
        }
        // 3. Deploy the process to the engine

//    // 4. Start a process instance
//    ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(PROCESSID);
//
//    // 5. Check if task is available
//    List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
//    Assert.assertEquals(1, tasks.size());
//    Task t=tasks.get(0);
//    Map<String, Object> variables = new HashMap<String, Object>();
//    variables.put("approver0","2");
//    processEngine.getTaskService().complete(t.getId(),variables);
//
//
//    // 6. Save process diagram to a file
//    InputStream processDiagram = processEngine.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());
//    FileUtils.copyInputStreamToFile(processDiagram, new File("deployments/"+PROCESSID+".png"));
//
//    // 7. Save resulting BPMN xml to a file
//    InputStream processBpmn = processEngine.getRepositoryService().getResourceAsStream(deployment.getId(), PROCESSID+".bpmn");
//    FileUtils.copyInputStreamToFile(processBpmn,new File("deployments/"+PROCESSID+".bpmn"));
//
//    System.out.println(".........end...");
    }

    protected static ProcessEngine getProcessEngine() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
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


}