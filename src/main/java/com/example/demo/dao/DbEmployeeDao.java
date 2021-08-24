package com.example.demo.dao;

import com.example.demo.entity.DbEmployee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DbEmployee)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-16 11:43:19
 */
public interface DbEmployeeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    DbEmployee queryById(String employeeId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DbEmployee> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dbEmployee 实例对象
     * @return 对象列表
     */
    List<DbEmployee> queryAll(DbEmployee dbEmployee);

    /**
     * 新增数据
     *
     * @param dbEmployee 实例对象
     * @return 影响行数
     */
    int insert(DbEmployee dbEmployee);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DbEmployee> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DbEmployee> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DbEmployee> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DbEmployee> entities);

    /**
     * 修改数据
     *
     * @param dbEmployee 实例对象
     * @return 影响行数
     */
    int update(DbEmployee dbEmployee);

    /**
     * 通过主键删除数据
     *
     * @param employeeid 主键
     * @return 影响行数
     */
    int deleteById(String employeeid);

}

