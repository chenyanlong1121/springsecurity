package com.example.demo.service.impl;

import com.example.demo.entity.DbPosition;
import com.example.demo.dao.DbPositionDao;
import com.example.demo.service.DbPositionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DbPosition)表服务实现类
 *
 * @author makejava
 * @since 2021-08-15 11:51:26
 */
@Service("dbPositionService")
public class DbPositionServiceImpl implements DbPositionService {
    @Resource
    private DbPositionDao dbPositionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param employeeId 主键
     * @return 实例对象
     */
    @Override
    public DbPosition queryById(String employeeId) {
        return this.dbPositionDao.queryById(employeeId);
    }

    @Override
    public List<DbPosition> queryByDepartment_id(String department_id) {
        return this.dbPositionDao.queryByDepartment_id(department_id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<DbPosition> queryAllByLimit(int offset, int limit) {
        return this.dbPositionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param dbPosition 实例对象
     * @return 实例对象
     */
    @Override
    public DbPosition insert(DbPosition dbPosition) {
        this.dbPositionDao.insert(dbPosition);
        return dbPosition;
    }

    /**
     * 修改数据
     *
     * @param dbPosition 实例对象
     * @return 实例对象
     */
    @Override
    public DbPosition update(DbPosition dbPosition) {
        this.dbPositionDao.update(dbPosition);
        return this.queryById(dbPosition.getEmployeeId());
    }

    /**
     * 通过主键删除数据
     *
     * @param employeeId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String employeeId) {
        return this.dbPositionDao.deleteById(employeeId) > 0;
    }
}
