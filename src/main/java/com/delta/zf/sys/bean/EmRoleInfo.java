package com.delta.zf.sys.bean;

/**
 * Created by Administrator on 2021/7/17.
 */
public class EmRoleInfo {

    private Integer roleCode;

    private String roleName;

    private String roleNameZH;

    private String rolePattern;

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNameZH() {
        return roleNameZH;
    }

    public void setRoleNameZH(String roleNameZH) {
        this.roleNameZH = roleNameZH;
    }

    public String getRolePattern() {
        return rolePattern;
    }

    public void setRolePattern(String rolePattern) {
        this.rolePattern = rolePattern;
    }

    @Override
    public String toString() {
        return "EmRoleInfo{" +
                "roleCode=" + roleCode +
                ", roleName='" + roleName + '\'' +
                ", roleNameZH='" + roleNameZH + '\'' +
                ", rolePattern='" + rolePattern + '\'' +
                '}';
    }
}
