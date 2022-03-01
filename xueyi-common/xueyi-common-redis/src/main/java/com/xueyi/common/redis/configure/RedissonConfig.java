package com.xueyi.common.redis.configure;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${redisson.config.address}")
    private String address;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String[] split = address.split(",");
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(split);

        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}