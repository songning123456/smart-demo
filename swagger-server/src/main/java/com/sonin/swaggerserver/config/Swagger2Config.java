package com.sonin.swaggerserver.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author sonin
 * @date 2021/8/19 16:23
 * http://localhost:6005/swagger-server/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createWebRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("web")
                .apiInfo(apiInfoWeb())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sonin.swaggerserver"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfoWeb() {
        return new ApiInfoBuilder()
                // 大标题
                .title("web端API接口文档")
                // 版本号
                .version("1.0")
                // 描述
                .description("restful风格接口")
                .build();
    }
}
