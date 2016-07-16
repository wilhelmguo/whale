package com.jy.entity.attendance;

import com.jy.entity.base.BaseEntity;
import org.apache.ibatis.type.Alias;

@Alias("WorkDevice")
public class WorkDevice extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String id;

    private String type;

    private String name;

    private String belongto;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBelongto() {
        return belongto;
    }

    public void setBelongto(String belongto) {
        this.belongto = belongto == null ? null : belongto.trim();
    }
}