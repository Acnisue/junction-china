package com.acanisue.mapper;

import com.acanisue.domain.IllegalVehicleStatistics;
import com.acanisue.domain.KeyValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author llll
* @description 针对表【illegal_vehicle_statistics】的数据库操作Mapper
* @createDate 2023-09-16 00:48:52
* @Entity generator.domain.IllegalVehicleStatistics
*/
@Mapper
public interface IllegalVehicleStatisticsMapper extends BaseMapper<IllegalVehicleStatistics> {

		List<KeyValue>  selectTimeM();

		List<KeyValue> selectTimeY();

		List<KeyValue> selectTimeD(@Param("s") String s);

		List<KeyValue> selectTimeH(@Param("s") String s);
}
