package com.example.demo.service;

import com.example.demo.entity.DbPosition;

import java.util.List;

/**
 * (DbPosition)表服务接口
 *
 * @author makejava
 * @since 2021-08-15 11:51:26
 */
public interface DbPositionService {

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    DbPosition queryById(String employeeId);


    /**
     * 通过department_id查询单条数据
     *
     * @param department_id 主键
     * @return 实例对象
     */
    List<DbPosition> queryByDepartment_id(String department_id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DbPosition> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param dbPosition 实例对象
     * @return 实例对象
     */
    DbPosition insert(DbPosition dbPosition);

    /**
     * 修改数据
     *
     * @param dbPosition 实例对象
     * @return 实例对象
     */
    DbPosition update(DbPosition dbPosition);

    /**
     * 通过主键删除数据
     *
     * @param employeeId 主键
     * @return 是否成功
     */
    boolean deleteById(String employeeId);

}
