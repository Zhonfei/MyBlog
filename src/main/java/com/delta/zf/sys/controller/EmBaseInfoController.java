package com.delta.zf.sys.controller;

import com.delta.zf.sys.bean.EmBaseInfo;
import com.delta.zf.tools.BaseTools;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by Administrator on 2021/7/18.
 */
@RestController
@RequestMapping("/blog")
public class EmBaseInfoController {

    @RequestMapping(value = "/usr/getUserInfo")
    public EmBaseInfo getUserInfo(HttpSession session){
        //EmBaseInfo emBaseInfo = BaseTools.getUserInfo(session,EmBaseInfo.class);
        EmBaseInfo emBaseInfo = (EmBaseInfo) session.getAttribute(BaseTools.getUUIDKEY(session));
        Enumeration<String> attributeNames = session.getAttributeNames();
        System.out.println("session content");
        while (attributeNames.hasMoreElements()){
            System.out.println(attributeNames.nextElement());
        }
        return emBaseInfo;
    }
}
