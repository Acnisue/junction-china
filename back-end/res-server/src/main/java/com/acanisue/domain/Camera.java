package com.acanisue.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @TableName camera
 */
@TableName(value ="camera")
@Data
public class Camera implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.INPUT)
    private Integer id;

    /**
     * 地址
     */
    @NotBlank(message = "地址不能为空")
    private String locations;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}