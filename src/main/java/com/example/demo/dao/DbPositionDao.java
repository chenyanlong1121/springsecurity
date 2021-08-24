package com.example.demo.dao;

import com.example.demo.entity.DbPosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DbPosition)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-16 11:08:49
 */
public interface DbPositionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    DbPosition queryById(String employeeId);


    List<DbPosition> queryByDepartment_id(String department_id);
    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DbPosition> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dbPosition 实例对象
     * @return 对象列表
     */
    List<DbPosition> queryAll(DbPosition dbPosition);

    /**
     * 新增数据
     *
     * @param dbPosition 实例对象
     * @return 影响行数
     */
    int insert(DbPosition dbPosition);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DbPosition> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DbPosition> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DbPosition> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DbPosition> entities);

    /**
     * 修改数据
     *
     * @param dbPosition 实例对象
     * @return 影响行数
     */
    int update(DbPosition dbPosition);

    /**
     * 通过主键删除数据
     *
     * @param employeeId 主键
     * @return 影响行数
     */
    int deleteById(String employeeId);

}

