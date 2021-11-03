package com.example.demo.service.impl;

import com.example.demo.entity.Center;
import com.example.demo.dao.CenterDao;
import com.example.demo.service.CenterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Center)表服务实现类
 *
 * @author makejava
 * @since 2021-09-08 13:28:08
 */
@Service("centerService")
public class CenterServiceImpl implements CenterService {
    @Resource
    private CenterDao centerDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Center queryById(Integer id) {
        return this.centerDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Center> queryAllByLimit(int offset, int limit) {
        return this.centerDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param center 实例对象
     * @return 实例对象
     */
    @Override
    public Center insert(Center center) {
        this.centerDao.insert(center);
        return center;
    }

    /**
     * 修改数据
     *
     * @param center 实例对象
     * @return 实例对象
     */
    @Override
    public Center update(Center center) {
        this.centerDao.update(center);
        return this.queryById(center.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.centerDao.deleteById(id) > 0;
    }
}
