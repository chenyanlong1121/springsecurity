package com.example.demo.service.impl;

import com.example.demo.entity.DbEmployee;
import com.example.demo.dao.DbEmployeeDao;
import com.example.demo.service.DbEmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DbEmployee)表服务实现类
 *
 * @author makejava
 * @since 2021-08-15 11:51:17
 */
@Service("dbEmployeeService")
public class DbEmployeeServiceImpl implements DbEmployeeService {
    @Resource
    private DbEmployeeDao dbEmployeeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    @Override
    public DbEmployee queryById(String employeeId) {
        return this.dbEmployeeDao.queryById(employeeId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<DbEmployee> queryAllByLimit(int offset, int limit) {
        return this.dbEmployeeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param dbEmployee 实例对象
     * @return 实例对象
     */
    @Override
    public DbEmployee insert(DbEmployee dbEmployee) {
        this.dbEmployeeDao.insert(dbEmployee);
        return dbEmployee;
    }

    /**
     * 修改数据
     *
     * @param dbEmployee 实例对象
     * @return 实例对象
     */
    @Override
    public DbEmployee update(DbEmployee dbEmployee) {
        this.dbEmployeeDao.update(dbEmployee);
        return this.queryById(dbEmployee.getEmployeeId());
    }

    /**
     * 通过主键删除数据
     *
     * @param employeeId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String employeeId) {
        return this.dbEmployeeDao.deleteById(employeeId) > 0;
    }
}
