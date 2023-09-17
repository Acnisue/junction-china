package com.acanisue.mapper;

import com.acanisue.domain.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author llll
* @description 针对表【message】的数据库操作Mapper
* @createDate 2023-09-16 00:48:52
* @Entity generator.domain.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
