package com.delta.zf.sys.controller;

import com.delta.zf.sys.bean.EmBaseInfo;
import com.delta.zf.sys.bean.EmRoleInfo;
import com.delta.zf.sys.service.IEmRoleInfoService;
import com.delta.zf.sys.service.impl.EmRoleInfoServiceImpl;
import com.delta.zf.tools.BaseTools;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021/7/21.
 */
@Controller
@Scope("prototype")
@RequestMapping("/blog")
public class EmRoleInfoController {

    @Autowired
    EmRoleInfoServiceImpl emRoleInfoService;

    @RequestMapping(value = "/usr/getRolesAll")
    @ResponseBody
    public List<EmRoleInfo> getRolesAll(){
        return emRoleInfoService.getRolesAll();
    }

    @RequestMapping(value = "/usr/getRolesByEmKey")
    @ResponseBody
    public List<Map<String,Object>> getRolesByEmKey(@Param(value = "emkey") String emkey, HttpSession session){
        String key = BaseTools.getDEUUID(emkey);
        return BaseTools.getUserInfo(session,EmBaseInfo.class).getEmRoleInfoList();
    }
}
