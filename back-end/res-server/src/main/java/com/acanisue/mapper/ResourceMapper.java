package com.acanisue.mapper;

import com.acanisue.domain.ResourceDB;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author llll
* @description 针对表【resource】的数据库操作Mapper
* @createDate 2023-09-16 16:17:03
* @Entity generator.domain.Resource
*/
@Mapper
public interface ResourceMapper extends BaseMapper<ResourceDB> {

}
