package com.jy.entity.attendance;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

@Alias("WorkTime")
public class WorkTime extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String id;

    private String morning;

    private String beforenoon;

    private String afternoon;

    private String night;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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