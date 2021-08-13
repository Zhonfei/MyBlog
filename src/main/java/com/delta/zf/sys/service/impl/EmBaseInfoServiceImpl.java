package com.delta.zf.sys.service.impl;

import com.delta.zf.sys.bean.EmBaseInfo;
import com.delta.zf.sys.bean.EmRoleInfo;
import com.delta.zf.sys.dao.EmBaseInfoDao;
//import com.delta.zf.sys.service.IEmBaseInfoService;
import com.delta.zf.tools.BaseTools;
import com.delta.zf.tools.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021/7/17.
 */
@Service
public class EmBaseInfoServiceImpl implements UserDetailsService{

    @Autowired
    EmBaseInfoDao emBaseInfoDao;

    @Override
    public EmBaseInfo loadUserByUsername(String s) throws UsernameNotFoundException {
        EmBaseInfo emBaseInfo = emBaseInfoDao.loadUserByUsername(s);
        if(emBaseInfo==null){
            throw new UsernameNotFoundException("找不到用户！");
        }
        String sql = "select r.* from em_role_info r\n" +
                "inner join em_user_role ur on r.role_code = ur.role_id and ur.states = '1'\n" +
                " where ur.user_id = "+emBaseInfo.getEmId()+"";
        System.out.println(sql);
        try {
            List<Map<String,Object>> mapList = DataUtils.queryList(sql);
            System.out.println("mapList");
            System.out.println(mapList);
            emBaseInfo.setEmRoleInfoList(mapList);
            System.out.println(emBaseInfo);
        }catch (Exception e){
            System.out.println("角色获取失败！");
            e.printStackTrace();
        }
        return emBaseInfo;
    }
}
