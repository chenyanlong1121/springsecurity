package com.example.demo.controller;

import com.example.demo.entity.DbDepartment;
import com.example.demo.service.DbDepartmentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DbDepartment)表控制层
 *
 * @author makejava
 * @since 2021-08-15 11:51:01
 */
@RestController
@RequestMapping("dbDepartment")
public class DbDepartmentController {
    /**
     * 服务对象
     */
    @Resource
    private DbDepartmentService dbDepartmentService;

    /**
     * 通过主键查询单条数据
     *
     * @param departmentId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "部门查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "departmentId",value = "部门编号",required = true,dataType = "String")})
    @GetMapping("selectOne")
    public DbDepartment selectOne(@RequestParam("departmentId") String departmentId) {
        return this.dbDepartmentService.queryById(departmentId);
    }

}
