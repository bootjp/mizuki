package me.bootjp.nagisa.audience

import redis.clients.jedis.Jedis
import redis.clients.jedis.Tuple

object CreativeManager {
    private var Jedis: Jedis = RedisManager.getJedisResource()

    fun getFillerCreativeByCategory(category: String): String? {
        try {

            if (Jedis.exists(category)!!) {

                return Jedis.get(category)
            }

            throw RuntimeException("does not creative: " + category)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
             val filler: MutableSet<Tuple>? = Jedis.zrangeWithScores("filler", 0L, 1L)
//            filler.takeIf {  }
//            filler
//            return filler

            return ""
        }
    }
}
