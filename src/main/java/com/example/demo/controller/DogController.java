package com.example.demo.controller;

import com.example.demo.entity.Dog;
import com.example.demo.service.DogService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Dog)表控制层
 *
 * @author makejava
 * @since 2021-08-15 11:51:34
 */
@RestController
@RequestMapping("dog")
public class DogController {
    /**
     * 服务对象
     */
    @Resource
    private DogService dogService;

    /**
     * 通过主键查询单条数据
     *
     * @param dogId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "宠物查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "dogId",value="宠物编号",required = true,dataType = "String")})
    @GetMapping("selectOne")
    public Dog selectOne(@RequestParam("dogId") String dogId) {
        return this.dogService.queryById(dogId);
    }

}
