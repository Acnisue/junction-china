package com.acanisue.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 
 * @TableName illegal_vehicle_statistics
 */
@TableName(value ="illegal_vehicle_statistics")
@Data
@NoArgsConstructor
public class IllegalVehicleStatistics implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 车类型
     */
    @NotBlank(message = "车类型不能为空")
    private String vehicleType;

    /**
     * 违规类型
     */
    @NotBlank(message = "违规类型 不能为空")
    private String violationType;

    /**
     * 摄像头id
     */
    @Min(value = 1,message = "是有效自增id")
    private Integer cameraId;

    /**
     * 时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    private Date createTime;


    public IllegalVehicleStatistics(String vehicleType, String violationType, Integer cameraId, Date createTime) {
        this.vehicleType = vehicleType;
        this.violationType = violationType;
        this.cameraId = cameraId;
        this.createTime = createTime;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}