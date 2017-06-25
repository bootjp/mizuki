package mizuki.audience

import redis.clients.jedis.Jedis
import redis.clients.jedis.Tuple

object CreativeManager {
    private var Jedis: Jedis = RedisManager.getJedisResource()

    fun getFillerCreativeByCategory(category: String): Creative {
        try {

            if (Jedis.exists(category)!!) {

                return Jedis.get(category)
            }

            throw RuntimeException("does not creative: " + category)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
             val filler: Tuple = Jedis.zrangeWithScores("filler", 0L, 1L)
            filler

        }
    }
}
