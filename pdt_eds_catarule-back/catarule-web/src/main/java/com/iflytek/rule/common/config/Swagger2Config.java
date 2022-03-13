/*
 *
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved.
 *
 * FileName：Swagger2Config.java
 *
 * Description：
 *
 * History：
 * Version   Author      Date            Operation
 * 1.0	  lli   2017年8月2日下午5:00:19	       Create
 */
package com.iflytek.rule.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * swagger配置
 * <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * @autho dgyu
 * @time 2021年11月1日 下午3:41:57
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.iflytek.rule.controller"))
                .paths(PathSelectors.any()).build();
//                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("归目规则管理系统")
                .description("归目规则管理后台接口说明一览").version("1.0").build();
    }

}
