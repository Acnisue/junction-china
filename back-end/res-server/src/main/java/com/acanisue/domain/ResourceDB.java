package com.acanisue.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName resource
 */
@TableName(value ="resource")
@Data
@NoArgsConstructor
public class ResourceDB implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long resourceId;

    @JsonIgnore
    private String resourcePath;

    private Integer resourceFrom; // 来源

    private Integer resourceDel; //

    private Integer resourceIsProcessing; // 是否处理

    @JsonIgnore
    private String resourceProcessing;

    public ResourceDB(String resourcePath, Integer resourceFrom) {
        this.resourcePath = resourcePath;
        this.resourceFrom = resourceFrom;
    }

    public ResourceDB(String resourceProcessing) {
        this.resourceProcessing = resourceProcessing;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}