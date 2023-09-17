package com.acanisue.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 0:20
 */
@Configuration
public class MybatisConfig {
		@Bean
		public MybatisPlusInterceptor mybatisPlusInterceptor() {
				MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
				PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
				paginationInnerInterceptor.setDbType(DbType.MYSQL);
				paginationInnerInterceptor.setOverflow(true);
				interceptor.addInnerInterceptor(paginationInnerInterceptor);
				return interceptor;
		}

		@Bean
		public IdentifierGenerator identifierGenerator(){
				return new DefaultIdentifierGenerator();
		}
}
