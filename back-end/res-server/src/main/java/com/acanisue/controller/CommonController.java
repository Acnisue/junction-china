package com.acanisue.controller;

import com.acanisue.domain.Camera;
import com.acanisue.domain.ResourceDB;
import com.acanisue.domain.Results;
import com.acanisue.service.CommonService;
import com.acanisue.service.ResourceService;
import com.acanisue.service.impl.CameraServiceImpl;
import com.acanisue.service.impl.ResourceServiceImpl;
import com.acanisue.util.Pag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 15:29
 */
@RestController
public class CommonController {
		@Resource
		private CommonService commonService;
		@Resource
		private CameraServiceImpl cameraService;
		@Resource
		private ResourceServiceImpl resourceService;

		@PostMapping("/file/up")
		public Results fileUp(
						 @RequestParam(value = "way",defaultValue = "new",required = false)String way,
						 @RequestParam("type") String type,
						 @RequestParam("camera_id") Integer id,
						 @RequestParam(value = "res_id",required = false)Long resId,
						 HttpServletRequest request,
		                      MultipartFile file) throws IOException {
				Camera byId = cameraService.getById(id);
				if (ObjectUtils.isEmpty(byId)){
						return Results.setDealingWithDefault("不存在摄像头id");
				}
				if (way.equals("new")){
						return commonService.fileUp(file, type,id);
				}else if (way.equals("old")){
						return commonService.updateFile(resId,id,type,file);
				}
				return Results.setDealingWithDefault("way未知类型");
		}

		/**
		 * 下载文件
		 *
		 * @param type 类型    res: 资源
		 * @param id   id    对应类型id: teamId,contestantId,resourceId
		 */
		@GetMapping("/file/download/{id}")
		public void downloadFile(@RequestParam(name="type",defaultValue = "res",required = false) String type
						 , @PathVariable Long id, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException {
				commonService.optTypeFile(type, id,response,request);
		}


		/**
		 * @param id
		 * @param response 返回上传的文件， 以文件
		 * @throws JsonProcessingException
		 */

		@GetMapping("/file")
		public void selectFile(@RequestParam("re_id") Long id, HttpServletResponse response) throws JsonProcessingException {
				ObjectMapper objectMapper = new ObjectMapper();
				if (ObjectUtils.isEmpty(id)){
						Pag.renderString(response, objectMapper.writeValueAsString
										 (Results.setDealingWithDefault("id不能为空")));
						return ;
				}
				ResourceDB resourceDB = resourceService.getById(id);
				if (ObjectUtils.isEmpty(resourceDB)){
						Pag.renderString(response, objectMapper.writeValueAsString
										 (Results.setDealingWithDefault("资源不存在").setCode(410)));
						return;
				}

				if (resourceDB.getResourceDel()==1){
						Pag.renderString(response, objectMapper.writeValueAsString
										 (Results.setDealingWithDefault("文件已被删除")));
						return ;
				}
				commonService.selectFile(resourceDB,response);
		}




}
