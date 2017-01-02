package mizuki.audience;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

class RedisManager {
//    private final int REDIS_INDEX = Integer.parseInt(System.getenv("REDIS_INDEX"));
//    private final String REDIS_HOST = System.getenv("REDIS_HOST");
    private JedisPool jedisPool;
    private static final RedisManager redisManager = new RedisManager();

    private RedisManager() {
        GenericObjectPoolConfig JedisPoolConfig = new GenericObjectPoolConfig();
        JedisPoolConfig.setMaxIdle(200);
        JedisPoolConfig.setMinIdle(20);
        jedisPool = new JedisPool(JedisPoolConfig, "localhost");
    }

    static RedisManager getInstance() {
        return redisManager;
    }

    Jedis getJedisResource() {
        try {
            if (this.jedisPool.getResource().isConnected()) {
                return jedisPool.getResource();
            }
        } catch (JedisException e) {
            // @todo re-connect refactoring
            e.printStackTrace();
            GenericObjectPoolConfig JedisPoolConfig = new GenericObjectPoolConfig();
            JedisPoolConfig.setMaxIdle(200);
            JedisPoolConfig.setMinIdle(20);
            this.jedisPool = new JedisPool(JedisPoolConfig, System.getenv("REDIS_HOST"));
        } finally {
            return this.jedisPool.getResource();
        }
    }
}
