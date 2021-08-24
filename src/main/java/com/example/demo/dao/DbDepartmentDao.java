package com.example.demo.dao;

import com.example.demo.entity.DbDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DbDepartment)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-15 11:51:00
 */
public interface DbDepartmentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param departmentId 主键
     * @return 实例对象
     */
    DbDepartment queryById(String departmentId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DbDepartment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dbDepartment 实例对象
     * @return 对象列表
     */
    List<DbDepartment> queryAll(DbDepartment dbDepartment);



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
     * @return 影响行数
     */
    int insert(DbDepartment dbDepartment);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DbDepartment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DbDepartment> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DbDepartment> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DbDepartment> entities);

    /**
     * 修改数据
     *
     * @param dbDepartment 实例对象
     * @return 影响行数
     */
    int update(DbDepartment dbDepartment);

    /**
     * 通过主键删除数据
     *
     * @param departmentId 主键
     * @return 影响行数
     */
    int deleteById(String departmentId);

}

