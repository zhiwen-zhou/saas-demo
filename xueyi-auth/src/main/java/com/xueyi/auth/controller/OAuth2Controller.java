package com.xueyi.auth.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.collect.Maps;
import com.xueyi.auth.service.IOAuthService;
import com.xueyi.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OAuth2Controller {

    @Autowired
    private IOAuthService oAuthService;

    /**
     * 提供第三方授权系统
     *
     * @param appKey
     * @param appSecret
     * @return
     */
    @SentinelResource
    @GetMapping("/generate")
    public R<Map<String, String>> generateToken(@RequestParam("appKey") String appKey, @RequestParam("appSecret") String appSecret) {
        String s = oAuthService.generateToken(appKey, appSecret);
        Map<String, String> resultMap = Maps.newHashMap();
        resultMap.put("token", s);
        resultMap.put("expires", "7200");
        return R.ok(resultMap);
    }

    /**
     * 校验token是否成功
     *
     * @param tag
     * @param token
     * @return
     */
    @GetMapping("/verifyToken")
    public R<String> verifyToken(@RequestParam("tag") String tag, @RequestParam("token") String token) {
        return oAuthService.verifyToken(tag, token) ? R.ok() : R.fail("校验失败");
    }

    /**
     * 用于第三方系统申请授权使用
     *
     * @param param
     * @return
     */
    @PostMapping("apply")
    public R<String> applyThirdParty(@RequestBody Map<String, String> param) {
        String tag = oAuthService.addThirdPartyUser(param.get("tag"));
        return R.ok(tag);
    }

}
