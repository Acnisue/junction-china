package com.acanisue.service;

import com.acanisue.domain.ResourceDB;
import com.acanisue.domain.Results;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 16:02
 */
public interface CommonService {

		Results fileUp( MultipartFile files, String type, Integer id) throws IOException;

		void optTypeFile(String type, Long id, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException;

		void selectFile(ResourceDB resourceDB, HttpServletResponse response) throws JsonProcessingException;


		Results updateFile(Long id, Integer integer, String type, MultipartFile files) throws IOException;

}
