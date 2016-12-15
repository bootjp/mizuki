package mizuki.audience;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

class RedisManager {
    private final int REDIS_INDEX = Integer.parseInt(System.getenv("REDIS_INDEX"));
    private static final RedisManager redisManager = new RedisManager();
    private static JedisPool jedisPool;

    private RedisManager() {
        GenericObjectPoolConfig JedisPoolConfig = new GenericObjectPoolConfig();
        JedisPoolConfig.setMaxIdle(200);
        JedisPoolConfig.setMinIdle(20);
        jedisPool = new JedisPool(JedisPoolConfig, System.getenv("REDIS_HOST"));
    }

    static RedisManager getInstance() {
        return redisManager;
    }


}
