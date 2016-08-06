package com.jy.entity.oa.base;

import com.jy.entity.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author gsw on 16-8-6
 * @version 1.0
 */
public class OaBase extends BaseEntity {
    private Integer isapprove;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date stime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date etime;

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Integer getIsapprove() {
        return isapprove;
    }

    public void setIsapprove(Integer isapprove) {
        this.isapprove = isapprove;
    }
}
