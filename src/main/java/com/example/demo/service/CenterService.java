package com.example.demo.service;

import com.example.demo.entity.Center;

import java.util.List;

/**
 * (Center)表服务接口
 *
 * @author makejava
 * @since 2021-09-08 13:28:08
 */
public interface CenterService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Center queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Center> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param center 实例对象
     * @return 实例对象
     */
    Center insert(Center center);

    /**
     * 修改数据
     *
     * @param center 实例对象
     * @return 实例对象
     */
    Center update(Center center);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
