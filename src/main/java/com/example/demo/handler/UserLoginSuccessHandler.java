package com.example.demo.handler;

import com.example.demo.Utils.ResultUtil;

import com.example.demo.entity.BaseObject;
import com.example.demo.entity.DbEmployee;
import com.example.demo.entity.DbPosition;
import com.example.demo.entity.SelfUserEntity;
import com.example.demo.service.BaseService;
import com.example.demo.service.DbEmployeeService;
import com.example.demo.service.DbPositionService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 登录成功处理类
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    DbPositionService dbPositionService;

    /**
     * 登录成功返回结果
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 组装JWT
        SelfUserEntity selfUserEntity =  (SelfUserEntity) authentication.getPrincipal();
//        String token = JWTTokenUtil.createAccessToken(selfUserEntity);
//        token = JWTConfig.tokenPrefix + token;
        // 封装返回参数
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "登录成功");
//       resultData.put("token",token);
        ResultUtil.responseJson(response,resultData);
//     response.sendRedirect("http://localhost:8080/BaseController/login?employeeId="+selfUserEntity.getUsername());

    }
}
