package com.jy.entity.oa.task;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("TaskInfo")
public class TaskInfo extends BaseEntity {
  private String id;

  private String businesskey;

  private String code;

  private String name;

  private String description;

  private Integer status;

  private String presentationsubject;

  private Date createtime;

  private Date completetime;

  private Date expirationtime;

  private String creator;

  private String assignee;

  private String taskid;

  private String executionid;

  private String processinstanceid;

  private String processdefinitionid;

  private String attr1;

  private String attr2;

  private String attr3;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public String getBusinesskey() {
    return businesskey;
  }

  public void setBusinesskey(String businesskey) {
    this.businesskey = businesskey == null ? null : businesskey.trim();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code == null ? null : code.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description == null ? null : description.trim();
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getPresentationsubject() {
    return presentationsubject;
  }

  public void setPresentationsubject(String presentationsubject) {
    this.presentationsubject = presentationsubject == null ? null : presentationsubject.trim();
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public Date getCompletetime() {
    return completetime;
  }

  public void setCompletetime(Date completetime) {
    this.completetime = completetime;
  }

  public Date getExpirationtime() {
    return expirationtime;
  }

  public void setExpirationtime(Date expirationtime) {
    this.expirationtime = expirationtime;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator == null ? null : creator.trim();
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee == null ? null : assignee.trim();
  }

  public String getTaskid() {
    return taskid;
  }

  public void setTaskid(String taskid) {
    this.taskid = taskid == null ? null : taskid.trim();
  }

  public String getExecutionid() {
    return executionid;
  }

  public void setExecutionid(String executionid) {
    this.executionid = executionid == null ? null : executionid.trim();
  }

  public String getProcessinstanceid() {
    return processinstanceid;
  }

  public void setProcessinstanceid(String processinstanceid) {
    this.processinstanceid = processinstanceid == null ? null : processinstanceid.trim();
  }

  public String getProcessdefinitionid() {
    return processdefinitionid;
  }

  public void setProcessdefinitionid(String processdefinitionid) {
    this.processdefinitionid = processdefinitionid == null ? null : processdefinitionid.trim();
  }

  public String getAttr1() {
    return attr1;
  }

  public void setAttr1(String attr1) {
    this.attr1 = attr1 == null ? null : attr1.trim();
  }

  public String getAttr2() {
    return attr2;
  }

  public void setAttr2(String attr2) {
    this.attr2 = attr2 == null ? null : attr2.trim();
  }

  public String getAttr3() {
    return attr3;
  }

  public void setAttr3(String attr3) {
    this.attr3 = attr3 == null ? null : attr3.trim();
  }
}