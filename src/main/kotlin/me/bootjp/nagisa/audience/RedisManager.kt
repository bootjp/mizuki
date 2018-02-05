package me.bootjp.nagisa.audience

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.exceptions.JedisException

object RedisManager {
    private val jedisPool: JedisPool

    init {
        val JedisPoolConfig = GenericObjectPoolConfig()
        JedisPoolConfig.maxIdle = 200
        JedisPoolConfig.minIdle = 20
        this.jedisPool = JedisPool()
    }
    fun getJedisResource(): Jedis {
        try {
            return jedisPool.resource
        } catch (e: Exception) {
            when(e) {
                is JedisException ->
                    throw e
                else ->
                    throw e
            }
        }
    }
}
