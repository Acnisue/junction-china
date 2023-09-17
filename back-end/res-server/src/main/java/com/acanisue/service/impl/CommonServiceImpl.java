package com.acanisue.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.acanisue.domain.ResourceDB;
import com.acanisue.domain.Results;
import com.acanisue.service.CommonService;
import com.acanisue.util.Pag;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.acanisue.util.Constant.FILEPATH;
import static com.acanisue.util.Constant.JAR_PATH;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 16:07
 */
@Service
public class CommonServiceImpl implements CommonService {

		private ObjectMapper objectMapper = new ObjectMapper();

		@Resource
		private ResourceServiceImpl resourceService;

		@Override
		public Results fileUp(MultipartFile file, String type, Integer id) throws IOException {
				Results results = checkFile(file);
				if (results!=null)return results;
				//获取路径
				String relativePath = selectFilePath(type);
				if (relativePath == null) {
						return Results.setDealingWithDefault("请选择正确的类型");
				}
				//第一个相对路径
				String s = relativePath(file, relativePath);
				ResourceDB resourceDB = new ResourceDB(s, id);
				//第一个绝对路径
				s = JAR_PATH + s;
				File fileTemp = new File(s);
				file.transferTo(fileTemp);
				resourceService.save(resourceDB);
				return Results.setDealingWithDefault("成功！").setData(resourceDB).ok();
		}


		@Override
		public void optTypeFile(String type, Long id, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException {
				ResourceDB resourceDB = resourceService.getById(id);
				if (isNullIf(ObjectUtils.isEmpty(resourceDB), "不存在资源id", response)) return;
				if (isNullIf(resourceDB.getResourceDel() == 1, "资源已被删除", response)) return;
				String path = JAR_PATH + resourceDB.getResourcePath();
				File file = new File(path);
				if (isNullIf(FileUtil.isEmpty(file), "不存在资源文件", response)) return;
				String userAgent = request.getHeader("User_Agent");
				Pag.downloadFile(userAgent, response, file);
		}

		public String selectFilePath(String type) {
				switch (type) {
						case "image":
								return FILEPATH[0];
						case "video":
								return FILEPATH[2];
						case "temp":
								return FILEPATH[1];
						default:
								return null;
				}
		}

		public Results checkFile(MultipartFile file){
				if (ObjectUtils.isEmpty(file)) {
						return Results.setDealingWithDefault("文件为空");
				}
				if (file.getSize() == 0) {
						return Results.setDealingWithDefault("上传的是没有任何数据");
				}
				return null;
		}

		/**
		 * @param file 根据MultipartFile里面的文件名
		 * @param relativePath 存储的相对地址
		 * @return {@link String} 返回的时相对地址与加工的文件名组成的对路径
		 */

		public String relativePath(MultipartFile file,String relativePath){
				String originalFilename = UUID.randomUUID().toString().replace("_", "") + "$" + file.getOriginalFilename();
				if (FileNameUtil.containsInvalid(originalFilename)) {
						originalFilename = FileNameUtil.cleanInvalid(originalFilename);
				}
				//第一个相对路径
				return relativePath + File.separator + originalFilename;
		}

		/**
		 * @param resourceDB 根据存储的必要信息资源类
		 * @param response
		 * @throws JsonProcessingException
		 */

		@Override
		public void selectFile(ResourceDB resourceDB, HttpServletResponse response) throws JsonProcessingException {
				String resourcePath = resourceDB.getResourcePath();
				String realPath = JAR_PATH + resourcePath;
				File file = new File(realPath);

				if (FileUtil.isEmpty(file)) {
						Pag.renderString(response, new ObjectMapper().writeValueAsString
										 (Results.setDealingWithDefault("文件已被删除")));
						return;
				}
				String suffix = FileUtil.getType(file);
				Pag.renderFile(response, file, suffix);
		}

		@Override
		public Results updateFile(Long id, Integer integer, String type, MultipartFile file) throws IOException {
				Results results = checkFile(file);
				if (results!=null)return results;
				//获取路径
				String relativePath = selectFilePath(type);
				if (relativePath == null) return Results.setDealingWithDefault("请选择正确的类型");

				LambdaQueryChainWrapper<ResourceDB> wrapper = resourceService.lambdaQuery();
				wrapper.eq(ResourceDB::getResourceId, id);
				wrapper.eq(ResourceDB::getResourceIsProcessing, 0);
				ResourceDB byId = resourceService.getOne(wrapper);
				if (ObjectUtils.isEmpty(byId)) return Results.setDealingWithDefault("不存在或者已处理的数据 id");
				//第一个相对路径
				String s = relativePath(file, relativePath);
				byId.setResourceProcessing(s);
				byId.setResourceIsProcessing(1);
				boolean b = resourceService.updateById(byId);
				if (b){
						//第一个绝对路径
						s = JAR_PATH + s;
						File fileTemp = new File(s);
						file.transferTo(fileTemp);
				}
				return b?Results.setDealingWithDefault("操作成功").ok():Results.setDealingWithDefault("操作失败");
		}


		public boolean isNullIf(boolean b, String isTrueMsg, HttpServletResponse response) throws JsonProcessingException {
				if (b) {
						Pag.renderString(response, objectMapper
										 .writeValueAsString(Results.setDealingWithDefault(isTrueMsg)));
						return true;
				}
				return false;
		}
}
