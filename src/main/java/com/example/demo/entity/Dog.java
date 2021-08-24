package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Dog)实体类
 *
 * @author makejava
 * @since 2021-08-15 11:51:34
 */
public class Dog  {

    private Long id;

    private String dogId;

    private String employeeId;

    public Dog(String employeeId, String dogId) {
        this.dogId=dogId;
        this.employeeId=employeeId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDogId() {
        return dogId;
    }

    public void setDogId(String dogId) {
        this.dogId = dogId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}
