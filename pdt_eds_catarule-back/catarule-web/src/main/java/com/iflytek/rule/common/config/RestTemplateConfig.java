package com.iflytek.rule.common.config;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {
	@Value("${restReadTimeout}")
	private int restReadTimeout;
	@Value("${restConnectTimeout}")
	private int restConnectTimeout;

	/*@Bean
	@LoadBalanced
	@ConditionalOnProperty(prefix = "eds",name = "ribbon.restTemplate.enabled",havingValue ="true" )
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);
		// 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver
		// 的编码集为"ISO-8859-1"）
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
		while (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
		}
		messageConverters.add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}*/

	@Bean
	@ConditionalOnProperty(prefix = "eds",name = "ribbon.restTemplate.enabled",havingValue ="false" )
	public RestTemplate normalRestTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);
		// 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver
		// 的编码集为"ISO-8859-1"）
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
		while (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
		}
		messageConverters.add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(restReadTimeout*1000);// ms
		factory.setConnectTimeout(restConnectTimeout*1000);// ms
		return factory;
	}

}
