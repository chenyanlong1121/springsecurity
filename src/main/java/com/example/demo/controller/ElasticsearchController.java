package com.example.demo.controller;

import com.example.demo.service.ElasticsearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.List;


@Api(tags = "Elasticsearch管理")
@RestController
@RequestMapping("/index")
public class ElasticsearchController {
    @Resource
    private ElasticsearchService service;

    @ApiOperation(value = "创建索引")
    @ApiImplicitParams({@ApiImplicitParam(name = "idxName",value="索引名称",required = true,dataType = "String")})
    @RequestMapping(value = "createIndex",method = RequestMethod.POST)
    public void createIndex(@RequestParam String idxName)  {
        service.createIndex(idxName);
    }

    @ApiOperation(value = "创建文档")
    @ApiImplicitParams({@ApiImplicitParam(name = "idxName",value="索引名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "path",value = "文档路径",required = true,dataType = "String")})
    @RequestMapping(value = "createDoc",method = RequestMethod.POST)
    public void createDoc(@RequestParam String idxName,@RequestParam String path)  {
        service.createDoc(idxName,path);
    }

    @ApiOperation(value = "批量新建文档")
    @ApiImplicitParams({@ApiImplicitParam(name = "idxName",value="索引名称",required = true,dataType = "String")})
    @RequestMapping(value = "createDocs",method = RequestMethod.POST)
    public void createDocs(@RequestParam String idxName, @RequestParam List<String> paths){
        service.createDocs(idxName,paths);
    }
    @ApiOperation(value = "多字段查询文档")
    @ApiImplicitParams({@ApiImplicitParam(name = "idxName",value="索引名称",required = true,dataType = "String")})
    @RequestMapping(value = "searchDoc",method = RequestMethod.POST)
    public void searchDocs(@RequestParam String idxName,@RequestParam List<String> list){
        service.searchDocs(idxName,list);
    }
    @ApiOperation(value = "多字段查询文档")
    @ApiImplicitParams({@ApiImplicitParam(name = "idxName",value="索引名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "str",value = "查询字段",required = true,dataType = "String")})
    @RequestMapping(value = "searchDocss",method = RequestMethod.POST)
    public void searchDocss(@RequestParam String idxName,@RequestParam String str){
        service.searchDocss(idxName, str);
    }

    @ApiOperation(value = "多字段查询文档+时间")
    @ApiImplicitParams({@ApiImplicitParam(name = "idxName",value="索引名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "str",value = "查询字段",required = true,dataType = "String"),
             @ApiImplicitParam(name = "dateFormat",value = "时间点",required = true,dataType = "String")})
    @RequestMapping(value = "searchDocsss",method = RequestMethod.POST)
    void searchDocsss(@RequestParam String idxName, String str, @RequestParam String dateFormat){
        service.searchDocsss(idxName, str, dateFormat);
    }

    @ApiOperation(value = "查询全部")
    @ApiImplicitParams({@ApiImplicitParam(name = "idxName",value="索引名称",required = true,dataType = "String")})
    @RequestMapping(value = "searchall",method = RequestMethod.POST)
    void searchall(@RequestParam String idxName){
        service.searchall(idxName);
    }

    @ApiOperation(value = "删除文档")
    @ApiImplicitParams({@ApiImplicitParam(name = "index",value="索引名称",required = true,dataType = "String"),
    @ApiImplicitParam(name = "type",value = "文档类型",required = true,dataType = "String"),
    @ApiImplicitParam(name = "id",value = "id",required = true,dataType ="String")})
    @RequestMapping(value = "deleteDoc",method = RequestMethod.POST)
    void deleteDoc(String index,String type,String id){
        service.deleteDoc(index, type, id);
    }

    @ApiOperation(value = "模糊查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "name",value="姓名",required = true,dataType = "String")})
    @RequestMapping(value = "searchFuzziness",method = RequestMethod.POST)
    void searchFuzziness(@RequestParam String name){
        service.searchFuzziness(name);
    }



    @ApiOperation(value = "聚合查询年龄")
    @RequestMapping(value = "searchTermsbyAge",method = RequestMethod.POST)
    void searchTermbyAge(){
        service.searchTermbyAge();
    }
}
