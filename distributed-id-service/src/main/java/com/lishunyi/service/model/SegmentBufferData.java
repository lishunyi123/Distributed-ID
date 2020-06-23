package com.lishunyi.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/6/18 16:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentBufferData {

    private String key;

    private SegmentBufferView view;
}
