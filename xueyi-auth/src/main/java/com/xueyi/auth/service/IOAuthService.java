package com.xueyi.auth.service;

public interface IOAuthService {

    /**
     * 第三方系统调用生成 第三方token
     *
     * @param appKey
     * @param appSecret
     * @return
     */
    String generateToken(String appKey, String appSecret);

    /**
     * 供第三方应用申请，用于授权非登录用户操作
     *
     * @param tag
     * @return
     */
    String addThirdPartyUser(String tag);

    /**
     * 用于管理员操作审核第三方申请，通过后方可调用
     *
     * @param id
     * @return
     */
    String verifyThirdPartyUser(String id);


    /**
     * 验证token是否正确
     *
     * @param tag
     * @param token
     * @return
     */
    boolean verifyToken(String tag, String token);

}
