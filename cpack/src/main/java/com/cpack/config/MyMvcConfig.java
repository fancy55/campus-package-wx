package com.cpack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

//扩展springMvc:   viewResolve视图解析器、 dispatcherServlet、 formatter
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Bean
    public MyViewResolver myViewResolver(){
        return new MyViewResolver();
    }


    //视图跳转
    //这个页面跳转没有任何业务逻辑过程，只是单纯的路由过程 ( 点击一个按钮跳转到一个页面 )
    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry){
        //浏览器发送/aaa，就会跳转到success页面
        viewControllerRegistry.addViewController("/login").setViewName("NotLogin");

        viewControllerRegistry.addViewController("/err").setViewName("Error");
    }


    public static class MyViewResolver implements ViewResolver{

        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 设置允许跨域的路径
//        registry.addMapping("/**")
//                // 设置允许跨域请求的域名
//                .allowedOrigins("*")
//                // 是否允许证书 不再默认开启
//                .allowCredentials(true)
//                // 设置允许的方法
//                .allowedMethods("*")
//                // 跨域允许时间
//                .maxAge(3600);
//    }
}
