package com.delta.zf.index;

import com.delta.zf.sys.bean.EmBaseInfo;
import com.delta.zf.tools.BaseTools;
import com.delta.zf.tools.DataUtils;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.PrintWriter;
import java.net.CookieManager;
import java.net.CookieStore;
import java.util.*;

/**
 * Created by Administrator on 2021/7/18.
 */
@Controller
@CrossOrigin(origins = "http://localhost:8081")
public class IndexController {

    @RequestMapping(value = "/index")
    @ResponseBody
    public void index(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        EmBaseInfo emBaseInfo = BaseTools.getUserInfo(session,EmBaseInfo.class);
        System.out.println("打印EmBaseInfo："+emBaseInfo);
        String emkey = request.getParameter("emkey");
        System.out.println("emkey parameter:"+emkey);
        //获取当前项目路径
        String path = System.getProperty("user.dir");
        System.out.println("path:"+path);
        String newFilePath = path+"\\emkey.bin";
        File file = new File(newFilePath);
        if(BaseTools.isNull(emkey) || !BaseTools.getDEUUID(emkey).equals(BaseTools.getUUIDKEY(session))){
            emkey = BaseTools.getENUUID();
            session.removeAttribute(BaseTools.getUUIDKEY(session));
            session.setAttribute(BaseTools.getDEUUID(emkey),emBaseInfo);
        }
        if(emBaseInfo!=null){
            System.out.println("zoujinlaile");
            Cookie cookie = new Cookie("EMKEY",BaseTools.getUUIDKEY(session));
            cookie.setMaxAge(600);
            System.out.println(BaseTools.writeInfoToTxt(file,emBaseInfo));
        }else{
            emBaseInfo = (EmBaseInfo) BaseTools.getObjectByTxt(file);
            System.out.println("未登陆获取的对象："+emBaseInfo);
        }
        try{
            //静态资源不会被拦截
            response.sendRedirect(BaseTools.redict("index")+"?emkey="+emkey);
            //静态资源会被拦截
            //request.getRequestDispatcher(BaseTools.redict("index")+"?emid="+emBaseInfo.getEmId()+"em").forward(request,response);
        }catch (Exception e){
            System.out.println("重定向异常");
            e.printStackTrace();
        }
    }

    @RequestMapping("/blog/usr/homePie")
    @ResponseBody
    public List<Map<String,Object>> homePie(HttpSession session){
        EmBaseInfo emBaseInfo = BaseTools.getUserInfo(session,EmBaseInfo.class);
        String sql = ConstSqlProvider.HOME_PIE.replace("#uname#",emBaseInfo.getUsername());
        List<Map<String,Object>> res = null;
        try {
            System.out.println(sql);
            res = DataUtils.queryList(sql);
            Map<String,Object> map0 = res.get(0);
            map0.put("sliced",true);
            map0.put("selected",true);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/blog/usr/home3D")
    @ResponseBody
    public List<Integer> home3D(HttpSession session){
        EmBaseInfo emBaseInfo = BaseTools.getUserInfo(session,EmBaseInfo.class);
        String sql = ConstSqlProvider.HOME_3D.replace("#uname#",emBaseInfo.getUsername());
        List<Integer> res = null;
        try {
            List<Map<String,Object>> resList = DataUtils.queryList(sql);
            res = new ArrayList<>();
            for (Map<String,Object> map:resList) {
                res.add(Integer.parseInt(String.valueOf(map.get("y"))));
            }
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/session")
    @ResponseBody
    public Map getSessionInfo(HttpSession session){
        Map<String,Object> map = new HashMap();
        map.put("session_id",session.getId());
        Enumeration<String> attributeNames = session.getAttributeNames();
        List<String> list = new ArrayList<>();
        while (attributeNames.hasMoreElements()){
            list.add(attributeNames.nextElement());
        }
        map.put("session_names",list.toString());
        System.out.println(map);
        return map;
    }
}
