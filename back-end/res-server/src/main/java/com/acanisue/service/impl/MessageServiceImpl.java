package com.acanisue.service.impl;

import com.acanisue.domain.Message;
import com.acanisue.mapper.MessageMapper;
import com.acanisue.service.MessageService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 编写者：Acanisue
 * 日期；2023/9/16 4:50
 */
@Service
public class MessageServiceImpl extends ServiceImpl< MessageMapper,Message> implements MessageService {
}
