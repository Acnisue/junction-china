package com.acanisue.mapper;

import com.acanisue.domain.Camera;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author llll
* @description 针对表【camera】的数据库操作Mapper
* @createDate 2023-09-16 00:48:52
* @Entity generator.domain.Camera
*/
@Mapper
public interface CameraMapper extends BaseMapper<Camera> {
		int getMaxId();
}
