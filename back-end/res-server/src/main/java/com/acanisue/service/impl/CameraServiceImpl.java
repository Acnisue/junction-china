package com.acanisue.service.impl;

import com.acanisue.domain.Camera;
import com.acanisue.mapper.CameraMapper;
import com.acanisue.service.CameraService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 4:45
 */
@Service
public class CameraServiceImpl extends ServiceImpl<CameraMapper, Camera> implements CameraService {

		@Resource
		private CameraMapper cameraMapper;

		@Override
		public int getTheNextMaxId() {
				int maxId = cameraMapper.getMaxId();
				return maxId+1;
		}
}
