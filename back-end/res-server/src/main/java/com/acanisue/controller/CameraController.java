package com.acanisue.controller;

import cn.hutool.json.JSONObject;
import com.acanisue.domain.Camera;
import com.acanisue.domain.ResourceDB;
import com.acanisue.domain.Results;
import com.acanisue.service.CameraService;
import com.acanisue.service.CommonService;
import com.acanisue.service.impl.CameraServiceImpl;
import com.acanisue.service.impl.CommonServiceImpl;
import com.acanisue.service.impl.ResourceServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.acanisue.util.Constant.JAR_PATH;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 0:23
 */
@RestController
@RequestMapping("/camera")
public class CameraController {
		@Resource
		private CameraServiceImpl cameraService;
		@Resource
		private RestTemplate restTemplate;

		@Resource
		private CommonService commonService;

		@Resource
		private ResourceServiceImpl resourceService;

		/**
		 *
		 * 查看注册的摄像头
		 * @param num
		 * @param size
		 * @return {@link Results}
		 */

		@GetMapping
		public Results selectPage(@RequestParam(value = "num",defaultValue = "1") Integer num,
		                          @RequestParam(value = "size",defaultValue = "5") Integer size){
				Page<Camera> page = cameraService.page(new Page<>(num, size));
				return Results.setDealingWithDefault(page).ok();
		}

		/**
		 * 摄像头注册
		 * @param camera
		 * @return {@link Results}
		 */

		@PostMapping
		public Results addCamera(@Validated Camera camera){
				camera.setId(cameraService.getTheNextMaxId());
				LambdaQueryWrapper<Camera> wrapper = Wrappers.lambdaQuery();
				wrapper.eq(Camera::getLocations,camera.getLocations());
				List<Camera> list = cameraService.list(wrapper);
				if (!ObjectUtils.isEmpty(list)){
						return Results.setDealingWithDefault("已存在该地点的摄像头");
				}
				camera.setCreateTime(new Date());
				return cameraService.save(camera)?Results.setDealingWithDefault("操作成功").ok()
								 :Results.setDealingWithDefault("操作失败");
		}
		@PutMapping
		public Results updateCamera(@Validated Camera camera){
				Integer id = camera.getId();
				Camera byId = cameraService.getById(id);
				if (ObjectUtils.isEmpty(byId)) {
						return Results.setDealingWithDefault("不存在id");
				}
				return cameraService.updateById(camera)?Results.setDealingWithDefault("操作成功").ok()
								 :Results.setDealingWithDefault("操作失败");
		}

		@GetMapping("/image")
		public Results getFiles(HttpServletResponse response,
		                       @RequestParam(value = "num",defaultValue = "1") Integer num,
		                       @RequestParam(value = "size",defaultValue = "5") Integer size,
		                       @RequestParam(value = "camera_id",required = false)Integer integer){

				LambdaQueryWrapper<ResourceDB> wrapper = Wrappers.lambdaQuery();
				wrapper.eq(ResourceDB::getResourceFrom, integer);
				wrapper.eq(ResourceDB::getResourceDel, 0);
				Page<ResourceDB> page = new Page<>(num, size);
				resourceService.page(page,wrapper);
				return Results.setDealingWithDefault(page).ok();
		}

		@GetMapping("/image/view")
		public Results getFile(HttpServletResponse response,
		                       @RequestParam(value = "res_id")Long id) throws IOException {


				ResourceDB byId = resourceService.getById(id);
				if (ObjectUtils.isEmpty(byId)){
						return Results.setDealingWithDefault("id不存或者资源不存在 ");
				}
				if (byId.getResourceDel()==1){
						return Results.setDealingWithDefault("已被删除");
				}
				File file=new File(JAR_PATH+byId.getResourcePath());
				String s = postForObject(file);
				JSONObject entries = new JSONObject(s);
				Integer integer = entries.get("code", Integer.class);
				Results results = Results.setDealingWithDefault(entries.get("data"))
								 .setCode(integer)
								 .setMsg(entries.get("msg", String.class));
				return 200==integer?results.ok():results;
		}
		@PostMapping("/image/view")
		public Results getFile(HttpServletResponse response,
		                       MultipartFile file) throws IOException {
					File	fileTemp=file.getResource().getFile();
				String s = postForObject(fileTemp);
				JSONObject entries = new JSONObject(s);
				Integer integer = entries.get("code", Integer.class);
				Results results = Results.setDealingWithDefault(entries.get("data"))
								 .setCode(integer)
								 .setMsg(entries.get("msg", String.class));
				return 200==integer?results.ok():results;
		}

		public String postForObject(File file){
				FileSystemResource fileSystemResource = new FileSystemResource(file);

				// 请求头设置,multipart/form-data格式的数据
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);

				//提交参数设置
				MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
				param.add("file", fileSystemResource);

				// 组装请求体
				HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(param, headers);

				//发起请求
				return restTemplate.postForObject("http://172.16.129.18:5000/ai/vision/result", request, String.class);
		}
}
