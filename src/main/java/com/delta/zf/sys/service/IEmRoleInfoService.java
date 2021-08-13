package com.delta.zf.sys.service;

import com.delta.zf.sys.bean.EmRoleInfo;

import java.util.List;

/**
 * Created by Administrator on 2021/7/17.
 */
public interface IEmRoleInfoService {

    List<EmRoleInfo> getRolesByUid(Integer uid);

    List<EmRoleInfo> getRolesAll();
}
