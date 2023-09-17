package com.acanisue.controller;

import com.acanisue.domain.Camera;
import com.acanisue.domain.Message;
import com.acanisue.domain.Results;
import com.acanisue.service.MessageService;
import com.acanisue.service.impl.CameraServiceImpl;
import com.acanisue.service.impl.MessageServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 10:17
 */
@RestController
@RequestMapping("/message")
public class MessageController {
		@Resource
		private MessageServiceImpl messageService;
		@Resource
		private CameraServiceImpl cameraService;

		@PostMapping
		public Results add(@Validated Message msg){
				Integer fromCamera = msg.getFromCamera();
				Camera byId = cameraService.getById(fromCamera);
				if (ObjectUtils.isEmpty(byId)){
						return Results.setDealingWithDefault("不存在该摄像头id");
				}
				return messageService.save(msg)? Results.setDealingWithDefault("操作成功").ok()
								 :Results.setDealingWithDefault("操作失败");
		}

		@GetMapping
		public Results selectPage(@RequestParam(value = "num",defaultValue = "1") Integer num,
		                          @RequestParam(value = "size",defaultValue = "5") Integer size,
		                          @RequestParam(value = "being_read",defaultValue ="0",required = false) Integer being_read,
		                          @RequestParam(value = "camera",defaultValue = "2",required = false) Integer camera){
				LambdaQueryWrapper<Message> wrapper = Wrappers.lambdaQuery();
				if (camera!=2) {
						Camera byId = cameraService.getById(camera);
						if (ObjectUtils.isEmpty(byId)){
								return Results.setDealingWithDefault("不存在摄像头 id");
						}
						wrapper.eq(Message::getFromCamera, camera);
				}
				if (being_read!=2) wrapper.eq(Message::getIsBeingRead,being_read);
				Page<Message> page = new Page<>(1, 5);
				messageService.page(page, wrapper);
				return Results.setDealingWithDefault(page).ok();
		}
		@PutMapping("/{id}")
		public Results updateMessage(@PathVariable String id,
		                             @RequestParam(value = "way",required = false) String way){
				Message byId = messageService.getById(id);
				if (ObjectUtils.isEmpty(byId)){
						return Results.setDealingWithDefault("不存在摄像头 id");
				}
				byId.setIsBeingRead(1);
				return messageService.updateById(byId)? Results.setDealingWithDefault("操作成功").ok()
								 :Results.setDealingWithDefault("操作失败");
		}
}
