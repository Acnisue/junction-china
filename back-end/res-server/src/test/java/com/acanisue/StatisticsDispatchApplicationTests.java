package com.acanisue;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.acanisue.domain.Camera;
import com.acanisue.domain.IllegalVehicleStatistics;
import com.acanisue.domain.KeyValue;
import com.acanisue.mapper.IllegalVehicleStatisticsMapper;
import com.acanisue.service.impl.CameraServiceImpl;
import com.acanisue.service.impl.IllegalVehicleStatisticsServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@SpringBootTest
class StatisticsDispatchApplicationTests {

		@Resource
		private CameraServiceImpl cameraService;
		@Resource
		private IllegalVehicleStatisticsServiceImpl vehicleStatisticsService;

		@Resource
		private IllegalVehicleStatisticsMapper mapper;
		@Test
		void contextLoads() {

//				Page<Camera> page = cameraService.page(new Page<>(1, 5));
				int theNextMaxId = cameraService.getTheNextMaxId();
				System.out.println(theNextMaxId);
		}

		@Test
		void test1(){
//				IllegalVehicleStatistics illegal =new IllegalVehicleStatistics("三轮车","违载",1515,new Date());
//
//				vehicleStatisticsService.save(illegal);


//				DateTime dateTime = DateUtil.offsetDay(new Date(), -7);
//				List<KeyValue> keyValues =mapper.selectTimeD(dateTime);


//				System.out.println(keyValues);

		}

		@Resource
		private RestTemplate restTemplate;

		@Test
		public void test3(){
				FileSystemResource fileSystemResource = new FileSystemResource(new File("E:\\java_project\\statisticsDispatch\\static\\image\\img_1.png"));

				// 请求头设置,multipart/form-data格式的数据
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);

				//提交参数设置
				MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
				param.add("file", fileSystemResource);

				// 组装请求体
				HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(param, headers);

				//发起请求
				String forObject = restTemplate.postForObject("http://172.16.129.18:5000/ai/vision/result",request, String.class);

				System.out.println(forObject);

		}
}
