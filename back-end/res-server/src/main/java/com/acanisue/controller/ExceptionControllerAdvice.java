package com.acanisue.controller;

import cn.hutool.core.collection.CollUtil;
import com.acanisue.domain.Results;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

/**
 * 编写者：Acanisue
 * 日期；2023/3/23 13:07
 */
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {


		@ExceptionHandler(JsonProcessingException.class)
		public Results doInvalidDefinitionException(JsonProcessingException e) {
				//if (log.isDebugEnabled()) log.warn(e.getMessage(),e);
				return Results.setDealingWithDefault(" 原因：解析失败");
		}
		@ExceptionHandler(InvalidDefinitionException.class)
		public Results doInvalidDefinitionException(InvalidDefinitionException e) {
				//if (log.isDebugEnabled()) log.warn(e.getMessage(),e);
				return Results.setDealingWithDefault("没有任何的东西");
		}

		@ExceptionHandler(SQLException.class)
		public Results doSQLException(SQLException e) {
				//if (log.isDebugEnabled()) log.error(e.getMessage(),e);
				return Results.setDealingWithDefault("sql参数出错").setCode(205);
		}
		@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
		public Results doSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
				//if (log.isDebugEnabled()) log.error(e.getMessage(),e);
				return Results.setDealingWithDefault("sql插入约束异常").setCode(400);
		}

		@ExceptionHandler(IOException.class)
		public Results doIOException(IOException e) {
				//if (log.isDebugEnabled()) log.warn(e.getMessage(),e);
				return Results.setDealingWithDefault("文件上传中，但文件存储时出现问题");
		}
		@ExceptionHandler(MultipartException.class)
		public Results doMultipartException(MultipartException e) {
				//if (log.isDebugEnabled()) log.warn(e.getMessage(),e);
				return Results.setDealingWithDefault("文件大于服务器规定大小");
		}

		@ExceptionHandler(BeanCreationException.class)
		public Results doBeanCreationException(BeanCreationException e) {
			//	if (log.isDebugEnabled()) log.warn(e.getMessage(),e);
				return Results.setDealingWithDefault("服务器出现问题,请等一下");
		}
		@ExceptionHandler(MethodArgumentTypeMismatchException.class)
		public Results doMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
				return Results.setDealingWithDefault("url参数类型不匹配").setCode(404);
		}
		@ExceptionHandler(MissingServletRequestParameterException.class)
		public Results doMissingServletRequestParameterException(MissingServletRequestParameterException e) {
				return Results.setDealingWithDefault("请求参数残缺不完整").setCode(400);
		}
		@ExceptionHandler(SQLSyntaxErrorException.class)
		public Results doSQLSyntaxErrorException(SQLSyntaxErrorException e) {
				//if (log.isDebugEnabled()) log.info(e.getMessage(),e);
				return Results.setDealingWithDefault("服务器错误").setCode(500);
		}
		@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
		public Results doHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
				//if (log.isDebugEnabled()) log.info(e.getMessage(),e);
				return Results.setDealingWithDefault("请求方法或者url不存在").setCode(404);
		}
		@ExceptionHandler(BindException.class)
		public Results doBindException(BindException e) throws JsonProcessingException {
				BindingResult bindingResult = e.getBindingResult();
				//Map<String, String> map = new HashMap<>();
				List<String> list = CollUtil.newArrayList();
				for (FieldError fieldError : bindingResult.getFieldErrors()) {
						// fieldError.getField(),
						list.add(fieldError.getDefaultMessage());
				}
				String s = String.join(",", list);
				return Results.setDealingWithDefault(s).setPrompt(false)
								 .setCode(Results.CODE_FIND);
		}
		@ExceptionHandler(NullPointerException.class)
		public Results doNullException(NullPointerException run) {
				String localizedMessage = run.getLocalizedMessage();
				if (log.isDebugEnabled()) log.info(localizedMessage,run);
				return Results.setDealingWithDefault("原因：值存在为空");
		}

		@ExceptionHandler(RuntimeException.class)
		public Results doRuntimeException(RuntimeException e) {
				String message = e.getMessage();
				if (e.getMessage().contains("com.mysql.cj.jdbc.exceptions.CommunicationsException")){
						return Results.setDealingWithDefault("网路通信故障(数据库链接故障)").setCode(500);
				}
				return Results.setDealingWithDefault(message);
		}

		@ExceptionHandler(Exception.class)
		public Results doException(Exception e) {
				String message = e.getMessage();
				 log.error(message,e);
				return Results.setDealingWithDefault("错误");
		}


}
