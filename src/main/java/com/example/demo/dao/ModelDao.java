package com.example.demo.dao;

import com.example.demo.entity.Model;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Model)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-08 13:28:54
 */
public interface ModelDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Model queryById(Integer id);
    /**
     * 通过model_name查询单条数据
     *
     * @param model_name 主键
     * @return 实例对象
     */
    Model queryBymodel_name(String model_name);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Model> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param model 实例对象
     * @return 对象列表
     */
    List<Model> queryAll(Model model);

    /**
     * 新增数据
     *
     * @param model 实例对象
     * @return 影响行数
     */
    int insert(Model model);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Model> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Model> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Model> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Model> entities);

    /**
     * 修改数据
     *
     * @param model 实例对象
     * @return 影响行数
     */
    int update(Model model);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

