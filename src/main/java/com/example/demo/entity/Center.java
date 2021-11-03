package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Center)实体类
 *
 * @author makejava
 * @since 2021-09-08 13:28:08
 */
public class Center implements Serializable {
    private static final long serialVersionUID = 461075740378318637L;

    private Integer id;

    private String centerName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

}
