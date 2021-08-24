package com.example.demo.controller;

import com.example.demo.entity.DbEmployee;
import com.example.demo.entity.DbPosition;
import com.example.demo.entity.Dog;
import com.example.demo.service.DbEmployeeService;
import com.example.demo.service.DbPositionService;
import com.example.demo.service.DogService;
import com.example.demo.service.impl.DbEmployeeServiceImpl;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@Api(tags = "员工信息管理")
/**
 * (DbEmployee)表控制层
 *
 * @author makejava
 * @since 2021-08-15 11:51:17
 */
@RestController
@RequestMapping("dbEmployee")
public class DbEmployeeController {
    /**
     * 服务对象
     */
    @Resource
    private DbEmployeeServiceImpl dbEmployeeService;
    @Resource
    private DbPositionService dbPositionService;
    @Resource
    private DogService dogService;
    /**
     * 通过主键查询单条数据
     *
     * @param employeeId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "员工查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value = "员工编号",required = true,dataType = "String")})
    @GetMapping("selectOne")
    public DbEmployee selectOne(@RequestParam("employeeId") String employeeId) {
        return this.dbEmployeeService.queryById(employeeId);
    }


    /**
     * 新增数据
     *
     * @param dbEmployee 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "员工添加")
    @ApiImplicitParams({@ApiImplicitParam(name = "age",value="年龄",required = true,dataType = "int"),
            @ApiImplicitParam(name = "departmentId",value = "部门编号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "email",value ="邮箱",required = true,dataType = "String"),
            @ApiImplicitParam(name = "employeeId",value = "员工编号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "employeeName",value="员工姓名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "phone",value="员工电话",required = true,dataType = "String"),
            @ApiImplicitParam(name = "sex",value="性别",required = true,dataType = "String"),
            @ApiImplicitParam(name = "position",value="职位",required = true,dataType = "String"),
            @ApiImplicitParam(name = "id",value="id",required = false,dataType = "Long")
    })
     @RequestMapping(value = "insert",method = RequestMethod.GET)
    public DbEmployee insert(@Param("dbemployee") DbEmployee dbEmployee
            ,@RequestParam("position")String position
            ,@RequestParam("departmentId")String department_id){
         dbPositionService.insert(new DbPosition(dbEmployee.getEmployeeId(),position,department_id));
        return this.dbEmployeeService.insert(dbEmployee);
    }


    /**
     * 通过主键删除数据
     *
     * @param employeeId 主键
     * @return 是否成功
     */
    @ApiOperation(value = "员工删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value = "员工编号",required = true,dataType = "String")})
    @RequestMapping(value = "deleteEmployee",method = RequestMethod.GET)
    public boolean deleteById(@RequestParam("employeeId") String employeeId){

        return dbEmployeeService.deleteById(employeeId)&&dbPositionService.deleteById(employeeId);
    }


    @ApiOperation(value = "员工更新")
    @ApiImplicitParams({@ApiImplicitParam(name = "age",value="年龄",required = true,dataType = "int"),
            @ApiImplicitParam(name = "departmentId",value = "部门编号",required = true,dataType = "string"),
            @ApiImplicitParam(name = "email",value ="邮箱",required = true,dataType = "string"),
            @ApiImplicitParam(name = "employeeId",value = "员工编号",required = true,dataType = "string"),
            @ApiImplicitParam(name = "employeeName",value="员工姓名",required = true,dataType = "string"),
            @ApiImplicitParam(name = "phone",value="员工电话",required = true,dataType = "string"),
            @ApiImplicitParam(name = "sex",value="性别",required = true,dataType = "string"),
            @ApiImplicitParam(name = "position",value="职位",required = true,dataType = "string"),
            @ApiImplicitParam(name = "id",value="id",required = false,dataType = "Long"),
            @ApiImplicitParam(name = "dogId",value = "宠物犬编号",required = false,allowMultiple = true)
    })
    @RequestMapping(value = "/updateEmployee",method = RequestMethod.GET)
    public DbEmployee updateEmployee(@Param("employee") DbEmployee employee
            ,@RequestParam("position")String position
            ,@RequestParam("departmentId")String department_id
    , @RequestParam("dogId")String dogId){
        dbEmployeeService.update(employee);
        dbPositionService.update(new DbPosition(employee.getEmployeeId(),position,department_id));
        dogService.update(new Dog(employee.getEmployeeId(),dogId));
        return dbEmployeeService.update(employee);
    }

}
