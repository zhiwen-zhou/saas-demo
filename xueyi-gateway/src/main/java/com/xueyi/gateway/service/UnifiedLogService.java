package com.xueyi.gateway.service;

import org.springframework.http.server.reactive.ServerHttpRequest;

public interface UnifiedLogService {

    void recordLog(ServerHttpRequest serverHttpRequest);
}
