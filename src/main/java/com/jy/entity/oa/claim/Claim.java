package com.jy.entity.oa.claim;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("Claim")
public class Claim extends BaseEntity {
  private static final long serialVersionUID = 1L;
  private String id;

  private Integer amount;

  private String accountId;

  private String name;

  private String copyto;

  private String approver;

  private String type;

  private String detail;

  private String pid;

  private Date createtime;

  private Date updatetime;

  private String attach;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type == null ? null : type.trim();
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail == null ? null : detail.trim();
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

  public String getAttach() {
    return attach;
  }

  public void setAttach(String attach) {
    this.attach = attach == null ? null : attach.trim();
  }
}