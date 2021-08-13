package com.delta.zf.sys.service.impl;

import com.delta.zf.sys.bean.EmRoleInfo;
import com.delta.zf.sys.dao.EmRoleInfoDao;
import com.delta.zf.sys.service.IEmRoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2021/7/17.
 */
@Service
@Scope("prototype")
public class EmRoleInfoServiceImpl implements IEmRoleInfoService{

    @Autowired
    EmRoleInfoDao emRoleInfoDao;

    @Override
    public List<EmRoleInfo> getRolesByUid(Integer uid) {
        List<EmRoleInfo> emRoleInfoList = emRoleInfoDao.getRolesByUid(uid);
        System.out.println(emRoleInfoList);
        return emRoleInfoList;
    }

    @Override
    public List<EmRoleInfo> getRolesAll() {
        List<EmRoleInfo> emRoleInfoList = emRoleInfoDao.getRolesAll();
        System.out.println(emRoleInfoList);
        return emRoleInfoList;
    }
}
