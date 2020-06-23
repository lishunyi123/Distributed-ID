package com.lishunyi.service.controller;

import com.lishunyi.service.model.SegmentBufferData;
import com.lishunyi.service.model.SegmentBufferView;
import com.lishunyi.service.model.SnowflakeDecodeVO;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.model.LeafAlloc;
import com.sankuai.inf.leaf.segment.model.SegmentBuffer;
import com.sankuai.inf.leaf.service.SegmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ID监控控制器
 *
 * @author 李顺仪
 * @version 1.0
 * @since 2020/6/18 14:42
 **/
@RestController
@Slf4j
public class IdMonitorController {

    /**
     * 号段模式.
     */
    @Resource
    private SegmentService segmentService;

    /**
     * 当前号段缓存数据
     *
     * @return 缓存数据列表
     */
    @GetMapping("cache")
    public List<SegmentBufferData> getCache() {
        SegmentIDGenImpl segmentIDGen = segmentService.getIdGen();
        if (segmentIDGen == null) {
            log.error("You should config leaf.segment.enable=true first");
        }
        List<SegmentBufferData> list = new ArrayList<>(16);
        ;
        SegmentBufferData segmentBufferData = null;
        assert segmentIDGen != null;
        Map<String, SegmentBuffer> cache = segmentIDGen.getCache();
        for (Map.Entry<String, SegmentBuffer> entry : cache.entrySet()) {
            SegmentBufferView sv = new SegmentBufferView();
            SegmentBuffer buffer = entry.getValue();
            sv.setInitOk(buffer.isInitOk());
            sv.setKey(buffer.getKey());
            sv.setPos(buffer.getCurrentPos());
            sv.setNextReady(buffer.isNextReady());
            sv.setMax0(buffer.getSegments()[0].getMax());
            sv.setValue0(buffer.getSegments()[0].getValue().get());
            sv.setStep0(buffer.getSegments()[0].getStep());
            sv.setMax1(buffer.getSegments()[1].getMax());
            sv.setValue1(buffer.getSegments()[1].getValue().get());
            sv.setStep1(buffer.getSegments()[1].getStep());
            segmentBufferData = new SegmentBufferData(entry.getKey(), sv);
            list.add(segmentBufferData);
        }
        return list;
    }

    /**
     * 获取所有key列表
     *
     * @return key列表
     */
    @GetMapping("list")
    public List<LeafAlloc> list() {
        SegmentIDGenImpl segmentIDGen = segmentService.getIdGen();
        if (segmentIDGen == null) {
            log.error("You should config leaf.segment.enable=true first");
        }
        assert segmentIDGen != null;
        return segmentIDGen.getAllLeafAllocs();
    }

    /**
     * 反解析雪花ID
     * <p>
     * {
     * "timestamp": "1567733700834(2019-09-06 09:35:00.834)",
     * "sequenceId": "3448",
     * "workerId": "39"
     * }
     * </p>
     *
     * @param snowflakeIdStr 雪花ID
     * @return 反解析对象
     */
    @GetMapping("decode-snowflake-id")
    public SnowflakeDecodeVO decodeSnowflakeId(@RequestParam("snowflakeId") String snowflakeIdStr) {
        SnowflakeDecodeVO snowflakeDecodeVO = new SnowflakeDecodeVO();
        try {
            long snowflakeId = Long.parseLong(snowflakeIdStr);

            long originTimestamp = (snowflakeId >> 22) + 1288834974657L;
            LocalDateTime localDateTime = Instant.ofEpochMilli(originTimestamp).atZone(ZoneId.of("UTC+8")).toLocalDateTime();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            snowflakeDecodeVO.setTimestamp(String.valueOf(originTimestamp) + "(" + dtf.format(localDateTime) + ")");

            long workerId = (snowflakeId >> 12) ^ (snowflakeId >> 22 << 10);
            snowflakeDecodeVO.setWorkerId(String.valueOf(workerId));

            long sequence = snowflakeId ^ (snowflakeId >> 12 << 12);
            snowflakeDecodeVO.setSequenceId(String.valueOf(sequence));
        } catch (NumberFormatException e) {
            log.error("snowflake Id反解析发生异常!");
        }
        return snowflakeDecodeVO;
    }
}
