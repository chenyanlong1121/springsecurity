package com.example.demo.service.impl;

import com.example.demo.entity.Dog;
import com.example.demo.dao.DogDao;
import com.example.demo.service.DogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Dog)表服务实现类
 *
 * @author makejava
 * @since 2021-08-15 11:51:34
 */
@Service("dogService")
public class DogServiceImpl implements DogService {
    @Resource
    private DogDao dogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param dogId 主键
     * @return 实例对象
     */
    @Override
    public Dog queryById(String dogId) {
        return this.dogDao.queryById(dogId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Dog> queryAllByLimit(int offset, int limit) {
        return this.dogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过查询Employee_id数据
     *
     * @param Employee_id 主键
     * @return 实例对象
     */
    @Override
    public List<Dog> queryByEmployee_id(String Employee_id) {
        return this.dogDao.queryByEmployee_id(Employee_id);
    }

    /**
     * 新增数据
     *
     * @param dog 实例对象
     * @return 实例对象
     */
    @Override
    public Dog insert(Dog dog) {
        this.dogDao.insert(dog);
        return dog;
    }

    /**
     * 修改数据
     *
     * @param dog 实例对象
     * @return 实例对象
     */
    @Override
    public Dog update(Dog dog) {
        this.dogDao.update(dog);
        return this.queryById(dog.getDogId());
    }

    /**
     * 通过主键删除数据
     *
     * @param dogId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String dogId) {
        return this.dogDao.deleteById(dogId) > 0;
    }
}
