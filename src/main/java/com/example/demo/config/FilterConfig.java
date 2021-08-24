package com.example.demo.config;


import com.example.demo.entity.DbEmployee;
import com.example.demo.service.DbEmployeeService;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;

@Order(1)
@WebFilter(filterName="Filter", urlPatterns="/*")
public class FilterConfig  implements Filter {
    @Resource
    DbEmployeeService dbEmployeeService;
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest
            , ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        DbEmployee dbEmployee=dbEmployeeService.queryById(servletRequest.getAttribute("employeeId").toString());
//        if(dbEmployee!=null){
//            filterChain.doFilter(servletRequest,servletResponse);
//        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
