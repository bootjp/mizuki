package mizuki.audience

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.exceptions.JedisException



object RedisManager {
    private var jedisPool: JedisPool = this.connect()
    init {
        this.connect()
    }
    fun getJedisResource(): Jedis {
        try {
            if (this.jedisPool.resource.isConnected) {
                this.jedisPool = this.connect()
            }
        } catch (e: JedisException) {
            e.printStackTrace()
        }

        return this.jedisPool.resource
    }

    private fun connect() : JedisPool {
        val JedisPoolConfig = GenericObjectPoolConfig()
        JedisPoolConfig.maxIdle = 200
        JedisPoolConfig.minIdle = 20
        return JedisPool(JedisPoolConfig, "localhost")
    }
}
