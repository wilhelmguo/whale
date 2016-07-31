package com.jy.entity.oa.overtime;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("Overtime")
public class Overtime extends BaseEntity {
  private static final long serialVersionUID = 1L;
  private String id;

  private String accountId;

  private String name;

  private String copyto;

  private String approver;

  private Date starttime;

  private Date endtime;

  private String duration;

  private String reason;

  private String pid;

  private Date createtime;

  private Date updatetime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId == null ? null : accountId.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public String getCopyto() {
    return copyto;
  }

  public void setCopyto(String copyto) {
    this.copyto = copyto == null ? null : copyto.trim();
  }

  public String getApprover() {
    return approver;
  }

  public void setApprover(String approver) {
    this.approver = approver == null ? null : approver.trim();
  }

  public Date getStarttime() {
    return starttime;
  }

  public void setStarttime(Date starttime) {
    this.starttime = starttime;
  }

  public Date getEndtime() {
    return endtime;
  }

  public void setEndtime(Date endtime) {
    this.endtime = endtime;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration == null ? null : duration.trim();
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason == null ? null : reason.trim();
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid == null ? null : pid.trim();
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public Date getUpdatetime() {
    return updatetime;
  }

  public void setUpdatetime(Date updatetime) {
    this.updatetime = updatetime;
  }
}