package com.xueyi.gateway.feign;

import com.xueyi.common.core.constant.ServiceConstants;
import com.xueyi.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServiceConstants.AUTH_SERVICE)
public interface AuthFeignManager {

    @GetMapping("/verifyToken")
    public R<String> verifyToken(@RequestParam("tag") String tag, @RequestParam("token") String token);
}
