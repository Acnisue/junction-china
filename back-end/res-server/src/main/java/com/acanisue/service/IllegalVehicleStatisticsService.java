package com.acanisue.service;

import com.acanisue.domain.IllegalVehicleStatistics;

import com.acanisue.domain.Results;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 4:44
 */
public interface IllegalVehicleStatisticsService{
		Results selectTime(String duration,Integer num,Integer size);
}
