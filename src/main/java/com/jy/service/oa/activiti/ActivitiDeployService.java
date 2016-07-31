package com.jy.service.oa.activiti;

import org.activiti.engine.repository.Deployment;

import java.io.IOException;

/**
 * @author
 * @ClassName: ActivitiDeployServiceImp
 * @Description: TODO(activiti 工作流程图自动生成)
 * @date 2016年6月20日 上午8:32:56
 */
public interface ActivitiDeployService {


  public Deployment buildDeployment(String key, String name, int size) throws IOException;

}  