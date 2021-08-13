package com.delta.zf.sys.service;

import com.delta.zf.sys.bean.EmMenuInfo;

import java.util.List;

/**
 * Created by Administrator on 2021/7/22.
 */
public interface IEmMenuInfoService {

    List<EmMenuInfo> getMenuByRole(String roleCode);

    List<EmMenuInfo> getMenuAll();

    List<EmMenuInfo> getMenuById(Integer id);

    Integer updateMenuInfo(EmMenuInfo emMenuInfo,Integer flag);

    Integer addNewMenu(EmMenuInfo emMenuInfo);
}
