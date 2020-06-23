package com.lishunyi.service.service;


import com.lishunyi.distributed.id.IdRequest;
import com.lishunyi.distributed.id.IdResponse;
import com.lishunyi.distributed.id.IdServiceGrpc;
import com.sankuai.inf.leaf.service.SegmentService;
import com.sankuai.inf.leaf.service.SnowflakeService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.annotation.Resource;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/6/20 13:13
 **/
@GrpcService
public class IdServiceImpl extends IdServiceGrpc.IdServiceImplBase {

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
     * <pre>
     * 获取号段模式ID
     * </pre>
     *
     * @param request          请求
     * @param responseObserver 返回
     */
    @Override
    public void getSegmentId(IdRequest request, StreamObserver<IdResponse> responseObserver) {
        IdResponse response = IdResponse.newBuilder()
                .setId(String.valueOf(segmentService.getId(request.getKey()).getId()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
