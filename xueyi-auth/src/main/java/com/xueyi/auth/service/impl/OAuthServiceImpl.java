package com.xueyi.auth.service.impl;

import com.xueyi.auth.service.IOAuthService;
import com.xueyi.common.core.utils.IdUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OAuthServiceImpl implements IOAuthService {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OAuthServiceImpl.class);

    @Override
    public String generateToken(String appKey, String appSecret) {
        logger.info("appKey:{}=====appSecret:{}", appKey, appSecret);
        //校验是否满足token生成条件
        boolean flag = false;
        if (flag) {
            return IdUtils.fastSimpleUUID();
        }
        return "";
    }

    @Override
    public String addThirdPartyUser(String tag) {
        logger.info("tag:{}", tag);
        String appKey = IdUtils.fastUUID();
        String appSercet = IdUtils.fastUUID();
        Integer status = 0;//未审核状态
        //insert操作
        return "0";
    }

    @Override
    public String verifyThirdPartyUser(String id) {
        Long appId = Long.parseLong(id);
        //由管理员级别用户去审核后第三方系统方可使用
        return null;
    }

    @Override
    public boolean verifyToken(String tag, String token) {
        return false;
    }

}
