package com.example.demo.controller;

import com.example.demo.entity.DbPosition;
import com.example.demo.service.DbPositionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DbPosition)表控制层
 *
 * @author makejava
 * @since 2021-08-15 11:51:26
 */
@RestController
@RequestMapping("dbPosition")
public class DbPositionController {
    /**
     * 服务对象
     */
    @Resource
    private DbPositionService dbPositionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public DbPosition selectOne(String id) {
        return this.dbPositionService.queryById(id);
    }

}
