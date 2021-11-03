package com.example.demo.service;

import com.example.demo.entity.Model;

import java.util.List;

/**
 * (Model)表服务接口
 *
 * @author makejava
 * @since 2021-09-08 13:28:54
 */
public interface ModelService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Model queryById(Integer id);


    Model queryBymodel_name(String model_name);
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Model> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param model 实例对象
     * @return 实例对象
     */
    Model insert(Model model);

    /**
     * 修改数据
     *
     * @param model 实例对象
     * @return 实例对象
     */
    Model update(Model model);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
