package com.example.demo.dao;

import com.example.demo.entity.Dog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Dog)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-15 11:51:34
 */
public interface DogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param dogId 主键
     * @return 实例对象
     */
    Dog queryById(String dogId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Dog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过Employee_id查询单条数据
     *
     * @param Employee_id 主键
     * @return 实例对象
     */
    List<Dog> queryByEmployee_id(String Employee_id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param dog 实例对象
     * @return 对象列表
     */
    List<Dog> queryAll(Dog dog);

    /**
     * 新增数据
     *
     * @param dog 实例对象
     * @return 影响行数
     */
    int insert(Dog dog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Dog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Dog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Dog> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Dog> entities);

    /**
     * 修改数据
     *
     * @param dog 实例对象
     * @return 影响行数
     */
    int update(Dog dog);

    /**
     * 通过主键删除数据
     *
     * @param dogId 主键
     * @return 影响行数
     */
    int deleteById(String dogId);

}

