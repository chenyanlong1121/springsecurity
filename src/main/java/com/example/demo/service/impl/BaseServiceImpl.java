package com.example.demo.service.impl;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("baseService")
public class BaseServiceImpl implements BaseService {
    @Resource
    private DbPositionDao dbPositionDao;
    @Resource
    private DbDepartmentDao dbDepartmentDao;
    @Autowired
    private DbEmployeeServiceImpl dbEmployeeService;
    @Resource
    private DogDao dogDao;
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Resource
    private SysRoleDao sysRoleDao;

    List<Dog> dogList=new ArrayList<>();
    @Autowired
    private BaseServiceImpl baseService;

    //上级领导查看本部门下级
    public List<DbEmployee> SuperiorQuerySubordinateEmployees(String id){
        List<DbEmployee> employeeList=new ArrayList<>();
        DbPosition dbPosition=dbPositionDao.queryById(id);
        if(dbPosition.getPosition().equals("lv2")){
            String departmentId=dbPositionDao.queryById(id).getDepartmentId();
            List<DbPosition> positionList=dbPositionDao.queryByDepartment_id(departmentId);
            for(DbPosition dbPosition1:positionList){
                DbEmployee dbEmployee=dbEmployeeService.queryById(dbPosition1.getEmployeeId());
                employeeList.add(dbEmployee);
            }
            return employeeList;
        }else {
            System.out.println("你的权限不够");
        }
        return null;
    }
    //上级领导查看下级部门的员工
    public List<DbEmployee> SuperiorQuerySubordinateDepartmentEmployees(String id){
        List<DbEmployee> employeeList=new ArrayList<>();
        DbPosition dbPosition=dbPositionDao.queryById(id);
        List<DbDepartment> departmentList=dbDepartmentDao.queryAllBysup(dbPositionDao.queryById(id).getDepartmentId());
        if(dbPosition.getPosition().equals("lv2")&&departmentList!=null){
            for(DbDepartment dbDepartment:departmentList){
                List<DbPosition> positionList=dbPositionDao.queryByDepartment_id(dbDepartment.getDepartmentId());
                for(DbPosition dbPosition1:positionList){
                    DbEmployee dbEmployee=dbEmployeeService.queryById(dbPosition1.getEmployeeId());
                    employeeList.add(dbEmployee);
                }
            }
            return employeeList;
        }else {
            System.out.println("检查你的权限或者部门");
        }
        return null;
    }
    //上级领导查看下级的饲养犬
    public List<Dog> SuperiorQuerySubordinateEmployeesDog(String id){

        List<DbEmployee> dbEmployeeList=baseService.SuperiorQuerySubordinateEmployees(id);
        for(DbEmployee employee:dbEmployeeList){
            List<Dog> dogLists=dogDao.queryByEmployee_id(employee.getEmployeeId());
            for(Dog dog:dogLists){
                dogList.add(dog);
            }
        }
        return dogList;
    }
    //上级部门领导查看下级部门员工的饲养犬
    public List<Dog> SuperiorQuerySubordinateDepartmentEmployeesDog(String id){
        List<DbEmployee> dbEmployeeList=baseService.SuperiorQuerySubordinateDepartmentEmployees(id);
        for(DbEmployee employee:dbEmployeeList){
            List<Dog> dogLists=dogDao.queryByEmployee_id(employee.getEmployeeId());
            for(Dog dog:dogLists){
                dogList.add(dog);
            }
        }
        return dogList;
    }

    @Override
    public List<BaseObject> login(String id) {
        List<BaseObject> objects=new ArrayList<>();
//        DbPosition dbPosition=dbPositionDao.queryById(id);
//        if(dbPosition.getDepartmentId().equals("2")){
//            if(dbPosition.getPosition().equals("lv2")) {
                List<DbEmployee> dbEmployeeList = baseService.SuperiorQuerySubordinateDepartmentEmployees(id);
                dbEmployeeList.addAll(baseService.SuperiorQuerySubordinateEmployees(id));
                for (int i = 0; i < dbEmployeeList.size() - 1; i++) {
                    objects.add(new BaseObject(dbEmployeeList.get(i), dogDao.queryByEmployee_id(dbEmployeeList.get(i).getEmployeeId())));
                }
                return objects;
//            }
//        }
//            }else {
//                 objects.add(new BaseObject(dbEmployeeService.queryById(id),new ArrayList<>()));
//            }
//        }else if(dbPosition.getPosition().equals("lv2")){
//            List<DbEmployee> dbEmployeeList=baseService.SuperiorQuerySubordinateEmployees(id);
//            for(int i=0;i<dbEmployeeList.size();i++){
//                objects.add(new BaseObject(dbEmployeeList.get(i),dogDao.queryByEmployee_id(dbEmployeeList.get(i).getEmployeeId())));
//
//            }
//            return  objects;
//        }else {
//            objects.add(new BaseObject(dbEmployeeService.queryById(id),new ArrayList<>()));
//        }
//        return objects;
    }
    public List<BaseObject> loginuser(String id) {
        List<BaseObject> objects=new ArrayList<>();
        objects.add(new BaseObject(dbEmployeeService.queryById(id),new ArrayList<>()));
        return  objects;
    }
    public List<BaseObject> loginuserlv2(String id) {
        List<BaseObject> objects=new ArrayList<>();
        List<DbEmployee> dbEmployeeList=baseService.SuperiorQuerySubordinateEmployees(id);
            for(int i=0;i<dbEmployeeList.size();i++){
                objects.add(new BaseObject(dbEmployeeList.get(i),dogDao.queryByEmployee_id(dbEmployeeList.get(i).getEmployeeId())));

            }
            return  objects;
    }
    public List<SysRole> selectSysRoleByUserId(Long userId){
       String roleId=sysUserRoleDao.queryById(Math.toIntExact(userId)).getRoleId();
       List<SysRole> sysRoles=new ArrayList<>();
       sysRoles.add(sysRoleDao.queryByid(roleId));
       String st=sysRoles.get(0).getRoleName();
       return sysRoles;
    }
}
