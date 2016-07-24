package com.jy.entity.attendance;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Alias("WorkRecord")
public class WorkRecord extends BaseEntity {
  private static final long serialVersionUID = 1L;
  private String id;

  private String type;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date starttime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endtime;

  private String employee;

  private String employeeName;

  private String location;

  private String picture;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private String week;

  //考勤状态
  private String status;

  private String morning;
  private String department;

  private String beforenoon;

  private String afternoon;

  private String night;

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type == null ? null : type.trim();
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getEmployee() {
    return employee;
  }

  public void setEmployee(String employee) {
    this.employee = employee == null ? null : employee.trim();
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location == null ? null : location.trim();
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture == null ? null : picture.trim();
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(String week) {
    this.week = week == null ? null : week.trim();
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
}