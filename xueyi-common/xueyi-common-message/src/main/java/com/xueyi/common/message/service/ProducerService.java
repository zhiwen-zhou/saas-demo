package com.xueyi.common.message.service;

import com.xueyi.common.message.domain.Message;
import org.apache.poi.ss.formula.functions.T;

/**
 * 消息队列-生产者 业务层
 *
 * @author Ethereal
 */
public interface ProducerService {
    /**
     * 发送消息
     *
     * @param content      消息体
     * @param exchangeName 交换机名称
     * @param routingKey   路由key
     */
    void sendMsg(Message content, String exchangeName, String routingKey);


    /**
     * 异步发送MQ消息
     *
     * @param topic 主题
     * @param data  消息体
     * @param key   msg_key
     */
    void asyncSend(String topic, Object data, String key);

    /**
     * 同步发送MQ消息
     *
     * @param topic 主题
     * @param data  消息体
     * @param key   msg_key
     */
    void syncSend(String topic, Object data, String key);

    /**
     * 发送事务消息
     * 配合RocketMQLocalTransactionListener使用
     */
    void transactionSend(String topic, Object data, String key);

}