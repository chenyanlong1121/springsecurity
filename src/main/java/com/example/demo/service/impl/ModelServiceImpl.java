package com.example.demo.service.impl;

import com.example.demo.entity.Model;
import com.example.demo.dao.ModelDao;
import com.example.demo.service.ModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Model)表服务实现类
 *
 * @author makejava
 * @since 2021-09-08 13:28:54
 */
@Service("modelService")
public class ModelServiceImpl implements ModelService {
    @Resource
    private ModelDao modelDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Model queryById(Integer id) {
        return this.modelDao.queryById(id);
    }

    @Override
    public Model queryBymodel_name(String model_name) {
        return this.modelDao.queryBymodel_name(model_name);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Model> queryAllByLimit(int offset, int limit) {
        return this.modelDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param model 实例对象
     * @return 实例对象
     */
    @Override
    public Model insert(Model model) {
        this.modelDao.insert(model);
        return model;
    }

    /**
     * 修改数据
     *
     * @param model 实例对象
     * @return 实例对象
     */
    @Override
    public Model update(Model model) {
        this.modelDao.update(model);
        return this.queryById(model.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.modelDao.deleteById(id) > 0;
    }
}
