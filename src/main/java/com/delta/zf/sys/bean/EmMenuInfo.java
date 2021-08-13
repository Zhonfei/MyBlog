package com.delta.zf.sys.bean;

/**
 * Created by Administrator on 2021/7/22.
 */
public class EmMenuInfo {

    private Integer id;

    private String menuCode;

    private String menuPcode;

    private String menuName;

    private String menuUrl;

    //private String menuPattern;

    private String menuIco;

    private String menuStatus;

//    private String rolesFlag;
//
//    public String getRolesFlag() {
//        return rolesFlag;
//    }
//
//    public void setRolesFlag(String rolesFlag) {
//        this.rolesFlag = rolesFlag;
//    }

    @Override
    public String toString() {
        return "EmMenuInfo{" +
                "id=" + id +
                ", menuCode='" + menuCode + '\'' +
                ", menuPcode='" + menuPcode + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
//                ", menuPattern='" + menuPattern + '\'' +
                ", menuIco='" + menuIco + '\'' +
                ", menuStatus='" + menuStatus + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuPcode() {
        return menuPcode;
    }

    public void setMenuPcode(String menuPcode) {
        this.menuPcode = menuPcode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIco() {
        return menuIco;
    }

    public void setMenuIco(String menuIco) {
        this.menuIco = menuIco;
    }

    //    public String getMenuPattern() {
//        return menuPattern;
//    }
//
//    public void setMenuPattern(String menuPattern) {
//        this.menuPattern = menuPattern;
//    }

    public String getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(String menuStatus) {
        this.menuStatus = menuStatus;
    }
}
