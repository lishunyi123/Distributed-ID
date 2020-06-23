package com.lishunyi.service.model;

import lombok.Data;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/6/18 16:57
 **/
@Data
public class SegmentBufferView {

    /**
     * 业务键名称.
     */
    private String key;

    /**
     * 当前使用的segment的下一个值.
     */
    private Long value0;

    /**
     * 当前使用的segment的步长.
     */
    private Integer step0;

    /**
     * 当前使用的segment的最大值.
     */
    private Long max0;

    /**
     * 下一个segment的下一个值.
     */
    private Long value1;

    /**
     * 下一个segment的步长.
     */
    private Integer step1;

    /**
     * 下一个segment的最大值.
     */
    private Long max1;

    /**
     * 当前的使用的segment的index.
     */
    private Integer pos;

    /**
     * 下一个segment是否处于可切换状态.
     */
    private Boolean nextReady;

    /**
     * 是否初始化完成.
     */
    private Boolean initOk;
}
