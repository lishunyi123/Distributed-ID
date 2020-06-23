package com.lishunyi.service.controller;


import com.sankuai.inf.leaf.service.SegmentService;
import com.sankuai.inf.leaf.service.SnowflakeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/5/12 10:19
 **/
@RestController
@RequestMapping("v1")
public class IdController {

    /**
     * 号段模式.
     */
    @Resource
    private SegmentService segmentService;

    /**
     * 雪花算法模式.
     */
    @Resource
    private SnowflakeService snowflakeService;

    /**
     * 根据key获取号段模式的id
     *
     * @param key 业务key
     * @return id
     */
    @GetMapping("segment/get/{key}")
    public String getSegmentId(@PathVariable("key") String key) {
        return String.valueOf(segmentService.getId(key).getId());
    }

    /**
     * 根据key获取雪花模式的id
     *
     * @param key 业务key
     * @return id
     */
    @GetMapping("snowflake/get/{key}")
    public String getSnowflakeId(@PathVariable("key") String key) {
        return String.valueOf(snowflakeService.getId(key).getId());
    }
}
