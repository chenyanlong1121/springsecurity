package com.example.demo.entity;

import java.io.Serializable;

/**
 * (DbEmployee)实体类
 *
 * @author makejava
 * @since 2021-08-16 11:43:17
 */
public class DbEmployee implements Serializable {
    private static final long serialVersionUID = 527142125556612677L;

    private Integer id;

    private String employeeId;

    private String sex;

    private String phone;

    private String email;

    private String employeeName;

    private Integer age;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
