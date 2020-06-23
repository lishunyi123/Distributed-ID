package com.lishunyi.service.model;

import lombok.Data;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/6/19 10:33
 **/
@Data
public class SnowflakeDecodeVO {

    private String timestamp;

    private String workerId;

    private String sequenceId;
}
