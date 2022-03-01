package com.xueyi.common.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xueyi.common.message.domain.Message;
import com.xueyi.common.message.service.ProducerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息队列-生产者 业务层处理
 *
 * @author Ethereal
 */
@Component
public class ProducerServiceImpl implements ProducerService {

    protected static Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendMsg(Message content, String exchangeName, String routingKey) {
        org.springframework.amqp.core.Message message = MessageBuilder.withBody(JSONObject.toJSONString(content).getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON).setCorrelationId(content.getId()).build();
        if (StringUtils.isNotBlank(content.getExpiration())) {
            message = MessageBuilder.fromMessage(message).setExpiration(content.getExpiration()).build();
        }
        CorrelationData data = new CorrelationData(content.getId());
        //存储到redis
        redisTemplate.opsForValue().set(data.getId(), JSONObject.toJSONString(content));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, data);
    }

    @Override
    public void asyncSend(String topic, Object data, String key) {
        rocketMQTemplate.asyncSend(topic, org.springframework.messaging.support.MessageBuilder.withPayload(data).setHeader("KEYS", key).build(), new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        logger.info(JSONObject.toJSONString(sendResult));
                        redisTemplate.opsForValue().set(sendResult.getMsgId(), JSONObject.toJSONString(data));
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
        );
    }

    @Override
    public void syncSend(String topic, Object data, String key) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, org.springframework.messaging.support.MessageBuilder.withPayload(data).setHeader("KEYS", key).build());
        redisTemplate.opsForValue().set(sendResult.getMsgId(), JSONObject.toJSONString(data));
    }

    @Override
    public void transactionSend(String topic, Object data, String key) {
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(topic, org.springframework.messaging.support.MessageBuilder.withPayload(data).setHeader("KEYS", key).build(), null);
        redisTemplate.opsForValue().set(sendResult.getMsgId(), JSONObject.toJSONString(data));
    }
}