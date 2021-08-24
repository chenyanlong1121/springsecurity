package com.example.demo.service;

import com.example.demo.entity.BaseObject;
import com.example.demo.entity.DbEmployee;
import com.example.demo.entity.Dog;
import com.example.demo.entity.SysRole;

import java.util.List;

public interface BaseService {
    //上级领导查看本部门下级
    List<DbEmployee> SuperiorQuerySubordinateEmployees(String id);
    //上级领导查看下级部门的员工
    List<DbEmployee> SuperiorQuerySubordinateDepartmentEmployees(String id);
    //上级领导查看下级的饲养犬
    List<Dog> SuperiorQuerySubordinateEmployeesDog(String id);
    //上级部门领导查看下级部门员工的饲养犬
    public List<Dog> SuperiorQuerySubordinateDepartmentEmployeesDog(String id);

    List<BaseObject> login(String employeeId);

    List<BaseObject> loginuser(String employeeId);

    List<BaseObject> loginuserlv2(String employeeId);
    List<SysRole> selectSysRoleByUserId(Long userId);
}
