package com.xueyi.gateway.service.impl;

import com.xueyi.common.core.utils.ip.IpUtils;
import com.xueyi.gateway.service.UnifiedLogService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class UnifiedLogServiceImpl implements UnifiedLogService {

    @Override
    public void recordLog(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = getIpByRequest(request);
        String agent = headers.getFirst("user-agent");
        boolean mobileDevice = IpUtils.isMobileDevice(agent);
        String path = request.getURI().getPath();
        String type = request.getMethod().name();
        switch (type) {
            case "GET":
                MultiValueMap<String, String> queryParams = request.getQueryParams();
                System.out.println(queryParams.toString());//{name=[hello], job=[world]}
                break;
            case "POST":
//                break;
            default:

                break;
        }
    }

    private String getIpByRequest(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }
}
