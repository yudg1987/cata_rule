package com.iflytek.rule.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 跨域配置 <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * 
 * @autho dgyu
 * @time 2021年11月5日 下午4:03:30 */
@Configuration
public class CorsConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				        // 放行哪些原始域
				        .allowedOrigins("*").allowedMethods(new String[] { "GET", "POST", "DELETE", "PUT", "PATCH" }).allowedHeaders("*")
				        .exposedHeaders("access-control-allow-headers", "access-control-allow-methods", "access-control-allow-origin", "access-control-max-age", "X-Frame-Options");
			}
		};
	}

}