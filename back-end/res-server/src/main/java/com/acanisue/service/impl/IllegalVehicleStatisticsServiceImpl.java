package com.acanisue.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.acanisue.domain.IllegalVehicleStatistics;
import com.acanisue.domain.KeyValue;
import com.acanisue.domain.Results;
import com.acanisue.mapper.IllegalVehicleStatisticsMapper;
import com.acanisue.service.IllegalVehicleStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 4:46
 */
@Service
public class IllegalVehicleStatisticsServiceImpl extends ServiceImpl<IllegalVehicleStatisticsMapper, IllegalVehicleStatistics> implements IllegalVehicleStatisticsService {
	@Resource
	private IllegalVehicleStatisticsMapper mapper;



		@Override
		public Results selectTime(String duration,Integer num,Integer size) {
				List<KeyValue> keyValues=null;
				switch (duration){
						case "year":
								keyValues = mapper.selectTimeY();
								break;
						case "month":
								keyValues =mapper.selectTimeM();
								break;
						case "day":
								String s= getAcquiredNearlyDays(8, "yyyy-MM-dd","day");
								keyValues =mapper.selectTimeD(s);
								break;
						case "hour":
								String acquiredNearlyHors = getAcquiredNearlyDays(8, "yyyy-MM-dd HH","hour");
								keyValues = mapper.selectTimeH(acquiredNearlyHors);
								break;
				}
				return Results.setDealingWithDefault(keyValues).ok();
		}



		public String getAcquiredNearlyDays(int days,String format,String type){
				List<String> strings=new ArrayList<>();

						if ("day".equals(type)){
								for (int i = 0; i < days; i++) {
										String dateTime = DateUtil.offsetDay(new Date(), -i).toString(format);
										strings.add("'" + dateTime + "'");
								}
						}
						else if ("hour".equals(type)){
								for (int i = 0; i < days; i++) {
										String dateTime = DateUtil.offsetHour(new Date(), -i).toString(format);
										strings.add("'" + dateTime + "'");
								}
						}

				String join = String.join(",", strings);
				return "("+join+")";
		}
}
