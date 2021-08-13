package com.delta.zf.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2021/7/1.
 */
@Controller
public class TestController {

//    @RequestMapping("/index")
//    public String index(){
//        System.out.println("index......");
//        return "blog/pages/test.html";
//    }

    @RequestMapping("/test")
    @ResponseBody
    public void test(HttpServletResponse response) throws Exception{
        Cookie cookieTest = new Cookie("testCookie","aaaaa");
        response.addCookie(cookieTest);
        response.sendRedirect("http://localhost:8081/index");
    }

    @RequestMapping("/sadmin/test")
    @ResponseBody
    public String test1(){
        System.out.println("sadmin test......");
        return "sadmin...testtest";
    }

    @RequestMapping("/admin/test")
    @ResponseBody
    public String test2(){
        System.out.println("admin test......");
        return "admin...testtest";
    }

    @RequestMapping("/usr/test")
    @ResponseBody
    public String test3(){
        System.out.println("usr test......");
        return "usr...testtest";
    }
}
