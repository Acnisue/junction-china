package com.acanisue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.acanisue.mapper")
public class StatisticsDispatchApplication {

		@Bean
		public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
				return new RestTemplate(factory);
		}

		@Bean
		public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
				SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
				factory.setReadTimeout(25000);
				factory.setConnectTimeout(15000);
				// 设置代理
				//factory.setProxy(null);
				return factory;
		}


		public static void main(String[] args) {
				SpringApplication.run(StatisticsDispatchApplication.class, args);
		}

}
