package com.example.demo.controller;

import com.example.demo.entity.Center;
import com.example.demo.service.CenterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Center)表控制层
 *
 * @author makejava
 * @since 2021-09-08 13:28:09
 */
@RestController
@RequestMapping("center")
public class CenterController {
    /**
     * 服务对象
     */
    @Resource
    private CenterService centerService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Center selectOne(Integer id) {
        return this.centerService.queryById(id);
    }

}
