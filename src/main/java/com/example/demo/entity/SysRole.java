package com.example.demo.entity;

import java.io.Serializable;

/**
 * (SysRole)实体类
 *
 * @author makejava
 * @since 2021-08-19 20:14:07
 */
public class SysRole implements Serializable {
    private static final long serialVersionUID = 459076911536545233L;

    private String roleName;

    private String roleId;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
