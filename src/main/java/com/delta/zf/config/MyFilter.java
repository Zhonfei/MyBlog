package com.delta.zf.config;

import com.delta.zf.sys.bean.EmRoleInfo;
import com.delta.zf.sys.service.IEmRoleInfoService;
import com.delta.zf.tools.BaseTools;
import com.delta.zf.tools.DataUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021/7/21.
 */
@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource{

    @Autowired
    AntPathMatcher pathMatcher;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println("请求路径："+requestUrl);
       // List<EmRoleInfo> emRoleInfoList = emRoleInfoService.getRolesAll();
        List<Map<String,Object>> emRoleInfoList = null;
        try {
            emRoleInfoList = DataUtils.queryList("select * from em_role_info");
            System.out.println(emRoleInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int len = emRoleInfoList.size();
        String[] roles = new String[1];
        for (int i = 0; i < len; i++) {
            Map map = emRoleInfoList.get(i);
            System.out.println("map:"+map);
            System.out.println(map.get("role_pattern"));
            System.out.println(pathMatcher.match(String.valueOf(map.get("role_pattern")), requestUrl));
            if (pathMatcher.match(String.valueOf(map.get("role_pattern")), requestUrl)) {
                roles[0] = String.valueOf(map.get("role_name"));
                System.out.println("roles[0]:"+roles[0]);
                return SecurityConfig.createList(roles);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


}
