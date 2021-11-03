package com.example.demo.dao;

import com.example.demo.entity.Mission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Mission)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-08 13:28:33
 */
public interface MissionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Mission queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Mission> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);
    /**
     * 查询每周数据
     *
     * @param date1 查询起始时间
     * @param date2  查询结束时间
     * @return 对象列表
     */
    List<Mission> queryAllBydate(@Param("date1") String date1, @Param("date2") String date2);
    /**
     * 查询所有
     *
     * @return 对象列表
     */

    List<Mission> queryAllMissions();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param mission 实例对象
     * @return 对象列表
     */
    List<Mission> queryAll(Mission mission);

    /**
     * 新增数据
     *
     * @param mission 实例对象
     * @return 影响行数
     */
    int insert(Mission mission);



    /**
     * 修改数据
     *
     * @param mission 实例对象
     * @return 影响行数
     */
    int updateOrinsert(Mission mission);



    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Mission> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Mission> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Mission> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Mission> entities);

    /**
     * 修改数据
     *
     * @param mission 实例对象
     * @return 影响行数
     */
    int update(Mission mission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

