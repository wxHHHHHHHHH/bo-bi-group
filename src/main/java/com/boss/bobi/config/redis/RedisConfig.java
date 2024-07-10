package com.boss.bobi.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


    // 锁前缀
    private static final String SCHEMA_PREFIX = "redis://";

    // 超时时间
    private final long lockWatchTimeOut = 3000;

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //参照StringRedisTemplate内部实现指定序列化器
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        return redisTemplate;
    }

    /**
     * 创建 RedissonClient，注入IOC容器
     */
    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        RedisProperties.Cluster redisPropertiesCluster = redisProperties.getCluster();
        if (redisPropertiesCluster != null) {
            //集群redis
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            for (String cluster : redisPropertiesCluster.getNodes()) {
                clusterServersConfig.addNodeAddress(SCHEMA_PREFIX + cluster);
            }
            if (StringUtils.hasText(redisProperties.getPassword())) {
                clusterServersConfig.setPassword(redisProperties.getPassword());
            }
            clusterServersConfig.setTimeout((int) redisProperties.getTimeout().toMillis());
            clusterServersConfig.setPingConnectionInterval(30000);
        } else if (StringUtils.hasText(redisProperties.getHost())) {
            //单点redis
            SingleServerConfig singleServerConfig = config.useSingleServer().
                    setAddress(SCHEMA_PREFIX + redisProperties.getHost() + ":" + redisProperties.getPort());
            if (StringUtils.hasText(redisProperties.getPassword())) {
                singleServerConfig.setPassword(redisProperties.getPassword());
            }
            singleServerConfig.setTimeout((int) redisProperties.getTimeout().toMillis());
            singleServerConfig.setPingConnectionInterval(30000);
            singleServerConfig.setDatabase(redisProperties.getDatabase());
        } else if (sentinel != null) {
            //哨兵模式
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(sentinel.getMaster());
            for (String node : sentinel.getNodes()) {
                sentinelServersConfig.addSentinelAddress(SCHEMA_PREFIX + node);
            }
            if (StringUtils.hasText(redisProperties.getPassword())) {
                sentinelServersConfig.setPassword(redisProperties.getPassword());
            }
            sentinelServersConfig.setTimeout((int) redisProperties.getTimeout().toMillis());
            sentinelServersConfig.setPingConnectionInterval(30000);
            sentinelServersConfig.setDatabase(redisProperties.getDatabase());
        }
        config.setLockWatchdogTimeout(lockWatchTimeOut);
        return Redisson.create(config);
    }

    private RedisSerializer<String> keySerializer(){
        return new StringRedisSerializer();
    }

    //使用Jackson序列化器
    private RedisSerializer<Object> valueSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }

}

