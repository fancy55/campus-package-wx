package com.cpack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * 授权
     * @param http
     * @throws Exception
     */
    //链式编程
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人访问，功能页只有有权限的人才能使用
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/ye/**").hasRole("vip");

        //没有权限的人会跳到登录页面
//        http.formLogin();//  /login
        http.formLogin().loginPage("/index");  //定制登录页


        http.csrf().disable();//关闭
        //注销 /logout   get明文传输
//        http.logout().deleteCookies("remove").invalidateHttpSession(true);
        http.logout().logoutSuccessUrl("/");//注销后跳到首页


        //开启 "记住我功能" cookie 默认保存2周
        http.rememberMe().rememberMeParameter("remember");
    }

    /**
     * 认证
     * springboot 2.1.x
     * @return
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("qly").password(new BCryptPasswordEncoder().encode("201739160412")).roles("vip")
                .and()
                .withUser("xxy").password(new BCryptPasswordEncoder().encode("201739160305")).roles("vip");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(users.username("user").password("password").roles("USER"))
                .withUser(users.username("admin").password("password").roles("USER","ADMIN"));
    }
}
