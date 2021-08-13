package com.delta.zf.config;

//import com.delta.zf.sys.service.IEmBaseInfoService;
import com.delta.zf.sys.service.impl.EmBaseInfoServiceImpl;
import com.delta.zf.tools.BaseTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Administrator on 2021/7/1.
 */
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{

//    @Autowired
//    IEmBaseInfoService emBaseInfoService;
    @Autowired
    EmBaseInfoServiceImpl emBaseInfoService;

    @Autowired
    MyFilter myFilter;

    @Autowired
    MyAccessDecisionManager myAccessDecisionManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(emBaseInfoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_sadmin > ROLE_admin \n ROLE_admin > ROLE_usr";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public AntPathMatcher getAntPahtMatcher(){
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/usr/**").permitAll()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        o.setSecurityMetadataSource(myFilter);
                        return o;
                    }
                })
//                .antMatchers("/sadmin/**")
//                .hasRole("sadmin")
//                .antMatchers("/admin/**")
//                .hasRole("admin")
//                .antMatchers("/usr/**")
//                .hasRole("usr")
//                .anyRequest()
//                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage(BaseTools.redict("login"))
                //.successForwardUrl("/index")
                .permitAll()
                .and()
                .csrf()
                .disable();
    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/blog/css/*.css",
                "/blog/image/**",
                "/blog/js/*.js",
                "/blog/*.html",
                "/blog/pages/*.html"
        );
//        web.ignoring().antMatchers("/blog/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\blog\\image\\";
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+path);
    }
}
