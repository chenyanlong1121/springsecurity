package com.example.demo.service.impl;

import com.example.demo.entity.DbDepartment;
import com.example.demo.dao.DbDepartmentDao;
import com.example.demo.service.DbDepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DbDepartment)表服务实现类
 *
 * @author makejava
 * @since 2021-08-15 12:28:44
 */
@Service("dbDepartmentService")
public class DbDepartmentServiceImpl implements DbDepartmentService {
    @Resource
    private DbDepartmentDao dbDepartmentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param departmentId 主键
     * @return 实例对象
     */
    @Override
    public DbDepartment queryById(String departmentId) {
        return this.dbDepartmentDao.queryById(departmentId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<DbDepartment> queryAllByLimit(int offset, int limit) {
        return this.dbDepartmentDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过superior_department_id查询单条数据
     *
     * @param superior_department_id
     * @return 实例对象
     */
    @Override
    public List<DbDepartment> queryAllBysup(String superior_department_id) {
        return this.dbDepartmentDao.queryAllBysup(superior_department_id);
    }

    /**
     * 新增数据
     *
     * @param dbDepartment 实例对象
     * @return 实例对象
     */
    @Override
    public DbDepartment insert(DbDepartment dbDepartment) {
        this.dbDepartmentDao.insert(dbDepartment);
        return dbDepartment;
    }

    /**
     * 修改数据
     *
     * @param dbDepartment 实例对象
     * @return 实例对象
     */
    @Override
    public DbDepartment update(DbDepartment dbDepartment) {
        this.dbDepartmentDao.update(dbDepartment);
        return this.queryById(dbDepartment.getDepartmentId());
    }

    /**
     * 通过主键删除数据
     *
     * @param departmentId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String departmentId) {
        return this.dbDepartmentDao.deleteById(departmentId) > 0;
    }
}
