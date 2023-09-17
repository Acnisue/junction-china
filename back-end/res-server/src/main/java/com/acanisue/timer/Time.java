package com.acanisue.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 编写者：Acanisue
 * 日期；2023/9/17 0:01
 */
@Component
public class Time {

		@Resource
		private RestTemplate restTemplate;

		@Scheduled
		public void minutesForThree(){

		}
}
