package com.acanisue.controller;

import com.acanisue.domain.IllegalVehicleStatistics;
import com.acanisue.domain.Results;
import com.acanisue.service.impl.IllegalVehicleStatisticsServiceImpl;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 8:38
 */
@RestController
@RequestMapping("/vehicle")
public class IllegalVehicleStatisticsController {
		@Resource
		private IllegalVehicleStatisticsServiceImpl vehicleStatisticsService;

		@PostMapping
		public Results add(@Validated IllegalVehicleStatistics illegal){
				return vehicleStatisticsService.save(illegal)?Results.setDealingWithDefault("操作成功").ok()
								 :Results.setDealingWithDefault("操作失败");
		}
		@PutMapping
		public Results update(@Validated IllegalVehicleStatistics illegal){
				Long id = illegal.getId();
				IllegalVehicleStatistics byId = vehicleStatisticsService.getById(id);
				if (ObjectUtils.isEmpty(byId)){
						return Results.setDealingWithDefault("不存在id");
				}
				return vehicleStatisticsService.updateById(illegal)?Results.setDealingWithDefault("操作成功").ok()
								 :Results.setDealingWithDefault("操作失败");
		}

		/**
		 * @param num
		 * @param size
		 * @param duration 以什么分类有 ：year, month , day ，hour
		 * @return {@link Results}
		 */

		@GetMapping
		public Results selectPage(@RequestParam(value = "num",defaultValue = "1") Integer num,
		                          @RequestParam(value = "size",defaultValue = "5") Integer size,
		                          @RequestParam(value = "duration")String duration){ //year, month , day ，hour

				return vehicleStatisticsService.selectTime(duration,num,size);
		}
}
