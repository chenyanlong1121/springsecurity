package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Model)实体类
 *
 * @author makejava
 * @since 2021-09-08 13:28:54
 */
public class Model implements Serializable {
    private static final long serialVersionUID = -62859770746357158L;

    private Integer id;

    private String modelName;

    private String centerId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

}
