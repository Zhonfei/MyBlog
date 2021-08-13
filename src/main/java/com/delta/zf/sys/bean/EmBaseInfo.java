package com.delta.zf.sys.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021/7/17.
 */
public class EmBaseInfo implements UserDetails,Serializable{

    private Integer emId;

    private String emName;

    private String emPwd;

    private String realName;

    private String realId;

    private String realPhone;

    private String emState;

    private boolean emAccLock;

    private boolean emAccExpired;

    private boolean emAccEnabled;

    private boolean emCreExpired;

    private List<Map<String,Object>> emRoleInfoList;

    @Override
    public String toString() {
        return "EmBaseInfo{" +
                "emId=" + emId +
                ", emName='" + emName + '\'' +
                ", emPwd='" + emPwd + '\'' +
                ", realName='" + realName + '\'' +
                ", realId='" + realId + '\'' +
                ", realPhone='" + realPhone + '\'' +
                ", emState='" + emState + '\'' +
                ", emAccLock=" + emAccLock +
                ", emAccExpired=" + emAccExpired +
                ", emAccEnabled=" + emAccEnabled +
                ", emCreExpired=" + emCreExpired +
                ", emRoleInfoList=" + emRoleInfoList +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Map roleMap:emRoleInfoList){
            authorities.add(new SimpleGrantedAuthority(String.valueOf(roleMap.get("role_name"))));
        }
        System.out.println("authorities");
        System.out.println(authorities);
        return authorities;
    }

    public List<Map<String,Object>> getEmRoleInfoList() {
        return emRoleInfoList;
    }

    public void setEmRoleInfoList(List<Map<String,Object>> emRoleInfoList) {
        this.emRoleInfoList = emRoleInfoList;
    }

    public void setEmId(Integer emId) {
        this.emId = emId;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public void setEmPwd(String emPwd) {
        this.emPwd = emPwd;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setRealId(String realId) {
        this.realId = realId;
    }

    public void setRealPhone(String realPhone) {
        this.realPhone = realPhone;
    }

    public void setEmState(String emState) {
        this.emState = emState;
    }

    public void setEmAccLock(boolean emAccLock) {
        this.emAccLock = emAccLock;
    }

    public void setEmAccExpired(boolean emAccExpired) {
        this.emAccExpired = emAccExpired;
    }

    public void setEmAccEnabled(boolean emAccEnabled) {
        this.emAccEnabled = emAccEnabled;
    }

    public void setEmCreExpired(boolean emCreExpired) {
        this.emCreExpired = emCreExpired;
    }

    public Integer getEmId() {
        return emId;
    }

    public String getRealName() {
        return realName;
    }

    public String getRealId() {
        return realId;
    }

    public String getRealPhone() {
        return realPhone;
    }

    public String getEmState() {
        return emState;
    }

    @Override
    public String getPassword() {
        return this.emPwd;
    }

    @Override
    public String getUsername() {
        return this.emName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return emAccExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return emAccLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return emCreExpired;
    }

    @Override
    public boolean isEnabled() {
        return emAccEnabled;
    }
}
