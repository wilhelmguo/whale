package com.jy.entity.oa.task;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("MsgInfo")
public class MsgInfo extends BaseEntity {
  private String id;

  private String name;

  private Integer type;

  private Date createtime;

  private String senderid;

  private String receiverid;

  private Integer status;

  private String content;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public String getSenderid() {
    return senderid;
  }

  public void setSenderid(String senderid) {
    this.senderid = senderid == null ? null : senderid.trim();
  }

  public String getReceiverid() {
    return receiverid;
  }

  public void setReceiverid(String receiverid) {
    this.receiverid = receiverid == null ? null : receiverid.trim();
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content == null ? null : content.trim();
  }
}