package com.example.demo.service;

import com.example.demo.entity.DbDepartment;

import java.util.List;

/**
 * (DbDepartment)表服务接口
 *
 * @author makejava
 * @since 2021-08-15 11:51:01
 */
public interface DbDepartmentService {

    /**
     * 通过ID查询单条数据
     *
     * @param departmentId 主键
     * @return 实例对象
     */
    DbDepartment queryById(String departmentId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DbDepartment> queryAllByLimit(int offset, int limit);

    /**
     * 通过superior_department_id查询单条数据
     *
     * @param superior_department_id
     * @return 实例对象
     */
    List<DbDepartment> queryAllBysup(String superior_department_id);


    /**
     * 新增数据
     *
     * @param dbDepartment 实例对象
     * @return 实例对象
     */
    DbDepartment insert(DbDepartment dbDepartment);

    /**
     * 修改数据
     *
     * @param dbDepartment 实例对象
     * @return 实例对象
     */
    DbDepartment update(DbDepartment dbDepartment);

    /**
     * 通过主键删除数据
     *
     * @param departmentId 主键
     * @return 是否成功
     */
    boolean deleteById(String departmentId);

}
