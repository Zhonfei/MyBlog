package com.delta.zf.sys.controller;

import com.delta.zf.sys.bean.EmBaseInfo;
import com.delta.zf.sys.bean.EmMenuInfo;
import com.delta.zf.sys.service.IEmMenuInfoService;
import com.delta.zf.sys.service.impl.EmMenuInfoServiceImpl;
import com.delta.zf.tools.BaseTools;
import com.delta.zf.tools.DataUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021/7/22.
 */
@RestController
@RequestMapping("/blog")
public class EmMenuInfoController {
    @Autowired
    EmMenuInfoServiceImpl emMenuInfoService;

    @RequestMapping("/usr/getMenuByRole")
    public List<EmMenuInfo> getMenuByRole(@Param(value = "roleCode") String roleCode){
        return emMenuInfoService.getMenuByRole(roleCode);
    }

    @RequestMapping("/admin/getMenuAuth")
    public List<Map<String,Object>> getMenuAuth(@Param(value = "roleCode") String roleCode){
        return emMenuInfoService.getMenuAuth(roleCode);
    }

    @PostMapping("/admin/updateMenuInfo")
    public void updateMenuInfo(@Param(value = "emMenuInfo") EmMenuInfo emMenuInfo, @Param(value = "emkey") String emkey, @Param(value = "flag") Integer flag, HttpServletResponse response){
        System.out.println("updateMenuInfo....");
        System.out.println(emMenuInfo);
        System.out.println(emkey);
        System.out.println(flag);
        if(emMenuInfoService.updateMenuInfo(emMenuInfo,flag)>0){
            try {
                System.out.println("tiaozhuan....");
                response.sendRedirect(BaseTools.redict("index")+"?emkey="+emkey);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/admin/addNewMenu")
    public Integer addNewMenu(MultipartFile uploadNewMenuFile,
                           EmMenuInfo emMenuInfo,String rolesFlag,HttpServletRequest request) throws Exception{
        System.out.println("rolesFlag:"+rolesFlag);
        //System.out.println(request.getParameter("rolesFlag"));
        String[] roles = rolesFlag.split(",");
        StringBuffer sb = new StringBuffer("");
        for (int i=0;i<roles.length;i++){
            sb.append("('"+roles[i]+"','"+emMenuInfo.getMenuCode()+"'),");
        }
        sb.deleteCharAt(sb.length()-1);
        String insertSql = "insert into em_role_menu(em_role_id,em_menu_code) values";
        insertSql+=sb;
        System.out.println("insertSql:"+insertSql);
        DataUtils.execute(insertSql);
        String menuIco = BaseTools.uploadFile(uploadNewMenuFile,emMenuInfo.getMenuUrl());
        emMenuInfo.setMenuIco(menuIco);
        System.out.println(uploadNewMenuFile);
        System.out.println(emMenuInfo);
        int flag = emMenuInfoService.addNewMenu(emMenuInfo);
        return flag;
    }

    @RequestMapping("/admin/getMenuById")
    public List<EmMenuInfo> getMenuById(Integer id){
        return emMenuInfoService.getMenuById(id);
    }

    @RequestMapping("/admin/getMenuAll")
    public List<EmMenuInfo> getMenuAll(){
        return emMenuInfoService.getMenuAll();
    }

    @PostMapping("/admin/updateMenuAuth")
    public void updateMenuAuth(@Param(value = "menuAuthInfo") String menuAuthInfo,@Param(value = "emkey") String emkey,HttpServletResponse response){
        System.out.println(menuAuthInfo);
        System.out.println(emkey);
        emMenuInfoService.updateMenuAuth(menuAuthInfo);
        try {
            response.sendRedirect(BaseTools.redict("index")+"?emkey="+emkey+"#authority");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
