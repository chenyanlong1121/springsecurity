package com.example.demo;


import com.example.demo.entity.SelfUserEntity;
import com.example.demo.entity.SysRole;
import com.example.demo.entity.SysUser;
import com.example.demo.service.BaseService;
import com.example.demo.service.SysRoleService;
import com.example.demo.service.SysUserService;
import com.example.demo.service.impl.SelfUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 自定义登录验证
 * @Author Sans
 * @CreateTime 2019/10/1 19:11
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private RedisTemplate redisTemplate;

    SelfUserEntity userInfo;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        if(redisTemplate.hasKey("chenyanlong::125")){
            //缓存查询用户是否存在
            System.out.println("缓存查询用户是否存在");
            userInfo=loadUserByname();
        }else{
            // 数据库查询用户是否存在
            System.out.println("数据库查询用户是否存在");
            userInfo = selfUserDetailsService.loadUserByUsername(userName);
        }
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码本来应该使用BCryptPasswordEncoder进行加密的，但是我拒绝
        if (!password.equals(userInfo.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<SysRole> sysRoleEntityList =baseService.selectSysRoleByUserId(userInfo.getUserId());
        for (SysRole sysRoleEntity: sysRoleEntityList){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleEntity.getRoleName()));
        }
        userInfo.setAuthorities(authorities);
        // 进行登录
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }
    public SelfUserEntity loadUserByname(){

        SysUser sysUserEntity=(SysUser) redisTemplate.boundValueOps("chenyanlong::125").get();
        if (sysUserEntity!=null){
            // 组装参数
            SelfUserEntity selfUserEntity = new SelfUserEntity();
            BeanUtils.copyProperties(sysUserEntity,selfUserEntity);
            selfUserEntity.setUserId(Long.valueOf(sysUserEntity.getUserId()));
            return selfUserEntity;
        }
        return null;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}