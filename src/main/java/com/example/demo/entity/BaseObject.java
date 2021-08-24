package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class BaseObject {
    private DbEmployee dbEmployee;
    List<Dog> dogList=new ArrayList<>();

    public BaseObject(DbEmployee dbEmployee, List<Dog> queryByEmployee_id) {
        this.dogList=queryByEmployee_id;
        this.dbEmployee=dbEmployee;
    }

    public DbEmployee getDbEmployee() {
        return dbEmployee;
    }

    public void setDbEmployee(DbEmployee dbEmployee) {
        this.dbEmployee = dbEmployee;
    }

    public List<Dog> getDogList() {
        return dogList;
    }

    public void setDogList(List<Dog> dogList) {
        this.dogList = dogList;
    }
}
