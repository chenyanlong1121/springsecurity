package com.example.demo.service;

import com.example.demo.entity.Dog;

import java.util.List;

/**
 * (Dog)表服务接口
 *
 * @author makejava
 * @since 2021-08-15 11:51:34
 */
public interface DogService {

    /**
     * 通过ID查询单条数据
     *
     * @param dogId 主键
     * @return 实例对象
     */
    Dog queryById(String dogId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Dog> queryAllByLimit(int offset, int limit);

    /**
     * 通过Employee_id查询单条数据
     *
     * @param Employee_id 主键
     * @return 实例对象
     */
    List<Dog> queryByEmployee_id(String Employee_id);
    /**
     * 新增数据
     *
     * @param dog 实例对象
     * @return 实例对象
     */
    Dog insert(Dog dog);

    /**
     * 修改数据
     *
     * @param dog 实例对象
     * @return 实例对象
     */
    Dog update(Dog dog);

    /**
     * 通过主键删除数据
     *
     * @param dogId 主键
     * @return 是否成功
     */
    boolean deleteById(String dogId);

}
