package com.example.demo.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport{
        @Bean
        public Docket createRestApi(){
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    //为当前包下controller生成API文档
//                .apis(RequestHandlerSelectors.basePackage("com.troila"))
                    //为有@Api注解的Controller生成API文档
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                    //为有@ApiOperation注解的方法生成API文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    //为任何接口生成API文档
//                .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();
            //添加登录认证
                /*.securitySchemes(securitySchemes())
                .securityContexts(securityContexts());*/
        }

        private ApiInfo apiInfo() {
            Contact contact = new Contact("chenyanlong", "", "1950344188@qq.com");
            return new ApiInfoBuilder()
                    .title("SwaggerUI")
                    .description("接口文档")
                    .contact(contact)
                    .version("1.0")
                    .build();
        }

        /**
         * 配置swagger2的静态资源路径
         * @param registry
         */
        @Override
        protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            // 解决静态资源无法访问
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/static/");
            // 解决swagger无法访问
            registry.addResourceHandler("/swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            // 解决swagger的js文件无法访问
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
