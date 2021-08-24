package com.example.demo.entity;

import java.io.Serializable;

/**
 * (DbPosition)实体类
 *
 * @author makejava
 * @since 2021-08-16 11:08:44
 */
public class DbPosition implements Serializable {
    private static final long serialVersionUID = -32623080892011512L;

    private String employeeId;

    private String position;

    private String departmentId;

    public DbPosition(String employeeId, String position, String department_id) {
         this.employeeId=employeeId;
        this.position=position;
        this.departmentId=department_id;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

}
