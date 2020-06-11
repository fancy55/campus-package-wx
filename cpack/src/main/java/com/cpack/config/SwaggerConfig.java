package com.cpack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.DocumentationType;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.cpack.controller";//指定要扫描的包
    public static final String SWAGGER_SCAN_OTHER_PACKAGE = "com.cpack.mcontroller";//指定要扫描的包
    public static final String SWAGGER_SCAN_P_PACKAGE = "com.cpack.pcontroller";//指定要扫描的包
    public static final String VERSION = "2.0.0";



    //配置swagger的docket的bean实例
    @Bean
    public Docket createApi(){
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev","test");
//        boolean flag = environment.acceptProfiles();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户端")
                .apiInfo(apiInfo())
//                .enable(flag)
                .select()// 选择那些路径和api会生成document
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))// 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())// 过滤路径
                .build();
    }


    @Bean
    public Docket createApi1(){
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev","test");
//        boolean flag = environment.acceptProfiles();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理员端")
                .apiInfo(apiInfo())
                .select()// 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_OTHER_PACKAGE))
                .paths(PathSelectors.any())// 过滤路径
                .build();
    }

    @Bean
    public Docket createApi2(){
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev","test");
//        boolean flag = environment.acceptProfiles();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("站点端")
                .apiInfo(apiInfo())
                .select()// 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_P_PACKAGE))
                .paths(PathSelectors.any())// 过滤路径
                .build();
    }

    //配置swagger信息apiInfo
    private ApiInfo apiInfo() {
        Contact contact = new Contact("覃乐怡","https://blog.csdn.net/qq_42677329","836955157@qq.com");

        return new ApiInfoBuilder()
                .title("快递校园通api文档")
                .description("good good study,day day up!!!")
                .termsOfServiceUrl("")
                .version(VERSION)
                .contact(contact)
                .build();
    }
}
