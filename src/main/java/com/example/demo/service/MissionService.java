package com.example.demo.service;

import com.example.demo.entity.Mission;

import java.util.List;

/**
 * (Mission)表服务接口
 *
 * @author makejava
 * @since 2021-09-08 13:28:33
 */
public interface MissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Mission queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Mission> queryAllByLimit(int offset, int limit);

    /**
     * 查询多条数据
     * @return 对象列表
     */
    List<Mission> queryAllByDate(String date1,String date2);
    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<Mission> queryAllMissions();

    /**
     * 新增数据
     *
     * @param mission 实例对象
     * @return 实例对象
     */
    Mission insert(Mission mission);

    /**
     * 修改数据
     *
     * @param mission 实例对象
     * @return 实例对象
     */
    Mission update(Mission mission);

    /**
     * 修改数据
     *
     * @param mission 实例对象
     * @return 实例对象
     */
    Mission updateOrinsert(Mission mission);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
