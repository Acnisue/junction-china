package com.acanisue.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 
 * @TableName message
 */
@TableName(value ="message")
@NoArgsConstructor
@Data
public class Message implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 发送消息来自 摄像头 id
     */
    @Min(message = "摄像头id无效",value = 1)
    private Integer fromCamera;

    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String context;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否被阅读
     */
    private Integer isBeingRead;


    public Message(Integer fromCamera, String context) {
        this.fromCamera = fromCamera;
        this.context = context;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}