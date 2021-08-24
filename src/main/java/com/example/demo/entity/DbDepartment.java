package com.example.demo.entity;

import java.io.Serializable;

/**
 * (DbDepartment)实体类
 *
 * @author makejava
 * @since 2021-08-15 11:51:00
 */
public class DbDepartment  {

    private Long id;

    private String departmentId;

    private String departmentName;

    private String phone;

    private String superiorDepartmentId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSuperiorDepartmentId() {
        return superiorDepartmentId;
    }

    public void setSuperiorDepartmentId(String superiorDepartmentId) {
        this.superiorDepartmentId = superiorDepartmentId;
    }

}
