package com.acanisue.service;

import com.acanisue.domain.Camera;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 4:44
 */
public interface CameraService extends IService<Camera>{
		int getTheNextMaxId();
}
