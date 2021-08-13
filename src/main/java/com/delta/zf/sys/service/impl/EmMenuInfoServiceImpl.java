package com.delta.zf.sys.service.impl;

import com.delta.zf.index.ConstSqlProvider;
import com.delta.zf.sys.bean.EmMenuInfo;
import com.delta.zf.sys.dao.EmMenuInfoDao;
import com.delta.zf.sys.service.IEmMenuInfoService;
import com.delta.zf.tools.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021/7/22.
 */
@Service
public class EmMenuInfoServiceImpl implements IEmMenuInfoService{

    @Autowired
    EmMenuInfoDao emMenuInfoDao;

    @Override
    public List<EmMenuInfo> getMenuByRole(String roleCode) {
        List<EmMenuInfo> emMenuInfos = emMenuInfoDao.getMenuByRole(roleCode);
        System.out.println(emMenuInfos);
        return emMenuInfos;
    }

    public List<EmMenuInfo> getMenuAll(){
        return emMenuInfoDao.getMenuAll();
    }

    @Override
    public List<EmMenuInfo> getMenuById(Integer id) {
        return emMenuInfoDao.getMenuById(id);
    }

    @Override
    public Integer updateMenuInfo(EmMenuInfo emMenuInfo, Integer flag) {
        emMenuInfo.setMenuStatus(((flag==1)?"1":"0"));
        return emMenuInfoDao.updateMenuInfo(emMenuInfo);
    }

    @Override
    public Integer addNewMenu(EmMenuInfo emMenuInfo) {
        return emMenuInfoDao.addNewMenu(emMenuInfo);
    }

    public void updateMenuAuth(String menuAuthInfo){
        char oper = menuAuthInfo.charAt(0);
        String roleId = menuAuthInfo.substring(1,7);
        String menuCode = menuAuthInfo.substring(7);
        String sql;
        if(oper=='1'){
            sql = "delete from em_role_menu where em_role_id='"+roleId+"' and em_menu_code = '"+menuCode+"'";
        }else {
            sql = "insert into em_role_menu(em_role_id,em_menu_code) values('"+roleId+"','"+menuCode+"')";
        }
        try {
            System.out.println(sql);
            DataUtils.execute(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Map<String,Object>> getMenuAuth(String roleCode){
        List<Map<String,Object>> res = new ArrayList<>();
        String sql = ConstSqlProvider.MENU_AUTH.replace("#role_code#",roleCode);
        System.out.println(sql);
        try {
            res = DataUtils.queryList(sql);
            Object roleId = res.get(0).get("em_role_id");
            Object roleName = res.get(0).get("em_role_name");
            for (Map<String,Object> map:res) {
                map.put("em_role_id",roleId);
                map.put("em_role_name",roleName);
            }
            System.out.println(res);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
