package com.jy.entity.oa.patch;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Alias("Patch")
public class Patch extends BaseEntity {
  private static final long serialVersionUID = 1L;
  private String id;

  private String accountId;

  private String name;

  private String copyto;

  private String approver;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private String morning;

  private String beforenoon;

  private String afternoon;

  private String night;

  private String pid;

  private Integer isvalid;

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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getMorning() {
    return morning;
  }

  public void setMorning(String morning) {
    this.morning = morning == null ? null : morning.trim();
  }

  public String getBeforenoon() {
    return beforenoon;
  }

  public void setBeforenoon(String beforenoon) {
    this.beforenoon = beforenoon == null ? null : beforenoon.trim();
  }

  public String getAfternoon() {
    return afternoon;
  }

  public void setAfternoon(String afternoon) {
    this.afternoon = afternoon == null ? null : afternoon.trim();
  }

  public String getNight() {
    return night;
  }

  public void setNight(String night) {
    this.night = night == null ? null : night.trim();
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid == null ? null : pid.trim();
  }

  public Integer getIsvalid() {
    return isvalid;
  }

  public void setIsvalid(Integer isvalid) {
    this.isvalid = isvalid;
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