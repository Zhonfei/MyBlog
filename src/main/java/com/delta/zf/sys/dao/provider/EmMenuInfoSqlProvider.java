package com.delta.zf.sys.dao.provider;

import com.delta.zf.sys.bean.EmMenuInfo;
import com.delta.zf.tools.BaseTools;

/**
 * Created by Administrator on 2021/7/22.
 */
public class EmMenuInfoSqlProvider {

    public String updateMenuInfo(EmMenuInfo emMenuInfo){
        String sql = "update em_menu_info set menu_status = '"+emMenuInfo.getMenuStatus()+"'";
        if(!BaseTools.isNull(emMenuInfo.getMenuCode())){
            sql += " , menu_code = '"+emMenuInfo.getMenuCode()+"'";
        }
        if(!BaseTools.isNull(emMenuInfo.getMenuName())){
            sql += " , menu_name = '"+emMenuInfo.getMenuName()+"'";
        }
        if(!BaseTools.isNull(emMenuInfo.getMenuUrl())){
            sql += " , menu_url = '"+emMenuInfo.getMenuUrl()+"'";
        }
        if(!BaseTools.isNull(emMenuInfo.getMenuIco())){
            sql += " , menu_ico = '"+emMenuInfo.getMenuIco()+"'";
        }
        sql += " where id="+emMenuInfo.getId();
        System.out.println(sql);
        return sql;
    }

    public String getMenuByRole(String roleCode){
        String sql = "select m.*" +
                " from em_role_menu rm " +
                " inner join em_menu_info m on rm.em_menu_code = m.menu_code" +
                " where rm.em_role_id = '"+roleCode+"'" +
                " and m.menu_status = '1'" +
                " and LENGTH(m.menu_code) = " +
                " (select max(LENGTH(menu_code)) from em_menu_info) order by m.menu_code";
        System.out.println(sql);
        return sql;
    }
}
