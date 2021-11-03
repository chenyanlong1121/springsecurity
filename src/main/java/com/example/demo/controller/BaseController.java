package com.example.demo.controller;

import com.example.demo.entity.BaseObject;
import com.example.demo.entity.DbEmployee;
import com.example.demo.entity.Dog;
import com.example.demo.service.BaseService;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Api(tags = "信息管理平台")
@RestController
@RequestMapping("/BaseController")
public class BaseController {

    @Resource
    private BaseService baseService;

    @ApiOperation(value = "登出")
    @GetMapping("loginout")
    public void loginout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        session.invalidate();
        String path=request.getContextPath();
        response.sendRedirect(path+"/login.jsp");
    }

    @ApiOperation(value = "管理层登录")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value="员工编号",required = true,dataType = "String")})
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public List<BaseObject> login(@RequestParam("employeeId")String employeeId){
        return  this.baseService.login(employeeId);
    }
    @ApiOperation(value = "普通员工登录")
    @PreAuthorize("hasAnyRole('USER')")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value="员工编号",required = true,dataType = "String")})
    @RequestMapping(value = "loginuser",method = RequestMethod.GET)
    public List<BaseObject> loginuser(@RequestParam("employeeId")String employeeId){
        return  this.baseService.loginuser(employeeId);
    }
    @ApiOperation(value = "干部登录")
    @PreAuthorize("hasAnyRole('USERLV2')")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value="员工编号",required = true,dataType = "String")})
    @RequestMapping(value = "loginuserlv2",method = RequestMethod.GET)
    public List<BaseObject> loginuserlv2(@RequestParam("employeeId")String employeeId){
        return  this.baseService.loginuserlv2(employeeId);
    }

    @ApiOperation(value = "上级查询下级员工")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value="员工编号",required = true,dataType = "String")})
    @GetMapping("/SuperiorQuerySubordinateEmployees")
    public List<DbEmployee> SuperiorQuerySubordinateEmployees(@RequestParam("employeeId") String id) {
        return this.baseService.SuperiorQuerySubordinateEmployees(id);
    }


    @ApiOperation(value = "上级领导查看下级部门的员工")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value="员工编号",required = true,dataType = "String")})
    //上级领导查看下级部门的员工
    @GetMapping("/SuperiorQuerySubordinateDepartmentEmployees")
    public List<DbEmployee> SuperiorQuerySubordinateDepartmentEmployees(@RequestParam("employeeId") String id){
        return this.baseService.SuperiorQuerySubordinateDepartmentEmployees(id);
    }


    @ApiOperation(value = "上级查询下级的饲养犬")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value="员工编号",required = true,dataType = "String")})
    @GetMapping("/SuperiorQuerySubordinateEmployeesDog")
    //上级领导查看下级的饲养犬
    public List<Dog> SuperiorQuerySubordinateEmployeesDog(@RequestParam("employeeId")String id){
        return this.baseService.SuperiorQuerySubordinateEmployeesDog(id);
    }


    @ApiOperation(value = "上级部门查询下级部门员工的饲养犬")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId",value="员工编号",required = true,dataType = "String")})
    @GetMapping("/SuperiorQuerySubordinateDepartmentEmployeesDog")
    //上级部门领导查看下级部门员工的饲养犬
    public List<Dog> SuperiorQuerySubordinateDepartmentEmployeesDog(@RequestParam("employeeId")String id){
        return this.baseService.SuperiorQuerySubordinateDepartmentEmployeesDog(id);
    }



    @ApiOperation(value = "test")
    @GetMapping("/test")
    public ResponseEntity<InputStreamResource> test() {
        byte[] bytes=null;
        try{
            bytes=new byte[1024];
        }catch (Exception e){
            e.printStackTrace();
        }
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
        Arrays.fill(bytes, (byte) 9);
        InputStreamResource stream=new InputStreamResource(byteArrayInputStream);
        IOUtils.closeQuietly(byteArrayInputStream);
        return from("test","txt",stream);

    }
    public static ResponseEntity<InputStreamResource> from(String fileName, String suffix,InputStreamResource bytes) {
        if (bytes == null ) {
            throw new RuntimeException("文件生成失败");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        String encodedFileName = null;
        try {
            encodedFileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s.%s\"; filename*=utf-8''%s.%s", encodedFileName, suffix, encodedFileName, suffix));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Language", "UTF-8");
        return ResponseEntity.ok().headers(headers).contentLength(1024*1024*512).contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytes);
    }

}
