package com.jy.entity.system.cms;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("Ads")
public class Ads extends BaseEntity {
  private static final long serialVersionUID = 1L;
  private String id;

  private String title;

  private String publisher;

  private Date addtime;

  private Date uptime;

  private String pip;

  private Integer hits;
  private String cover;
  private Integer sequence;

  private String recommand;

  private String status;

  private String content;

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title == null ? null : title.trim();
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher == null ? null : publisher.trim();
  }

  public Date getAddtime() {
    return addtime;
  }

  public void setAddtime(Date addtime) {
    this.addtime = addtime;
  }

  public Date getUptime() {
    return uptime;
  }

  public void setUptime(Date uptime) {
    this.uptime = uptime;
  }

  public String getPip() {
    return pip;
  }

  public void setPip(String pip) {
    this.pip = pip == null ? null : pip.trim();
  }

  public Integer getHits() {
    return hits;
  }

  public void setHits(Integer hits) {
    this.hits = hits;
  }

  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public String getRecommand() {
    return recommand;
  }

  public void setRecommand(String recommand) {
    this.recommand = recommand == null ? null : recommand.trim();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content == null ? null : content.trim();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SysNews{");
    sb.append("id='").append(id).append('\'');
    sb.append(", title='").append(title).append('\'');
    sb.append(", publisher='").append(publisher).append('\'');
    sb.append(", addtime=").append(addtime);
    sb.append(", uptime=").append(uptime);
    sb.append(", pip='").append(pip).append('\'');
    sb.append(", hits=").append(hits);
    sb.append(", sequence=").append(sequence);
    sb.append(", recommand='").append(recommand).append('\'');
    sb.append(", status='").append(status).append('\'');
    sb.append(", content='").append(content).append('\'');
    sb.append('}');
    return sb.toString();
  }
}