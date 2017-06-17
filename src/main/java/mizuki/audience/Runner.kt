package mizuki.audience

import redis.clients.jedis.Jedis
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.util.*
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletResponse

class Runner {
    private val RenderJsStart = "(function() { "
    private val RenderJsEnd = " })();"
    private var jedis: Jedis? = null
    private var BUFFER_EMPTY_GIF: BufferedImage? = null
    internal val REDIS_HOST = System.getenv("REDIS_HOST")

    @JvmStatic fun main(args: Array<String>) {
        try {
            println(System.getenv("REDIS_HOST"))

            before { req, res ->
                res.type("application/javascript")
                if (req.cookie("__mizuki_uid_one_day__") == null) {
                    res.cookie("__mizuki_uid_one_day__", "1", 86400)
                }
            }

            get("/") { req, res ->
                res.type("image/gif")

                var requestType = req.queryParams("type")
                requestType = if (requestType == null) "" else requestType

                when (requestType) {
                    "oneday" -> if (req.cookie("__mizuki_uid_one_day__") == null) {
                        if (jedis == null) {
                            jedis = RedisManager.getInstance().jedisResource
                        }
                        res.redirect(jedis!!.get("URL_ONE_DAY_IMAGE"))

                        return@get res.raw()
                    }
                }

                if (BUFFER_EMPTY_GIF == null) {
                    try {
                        BUFFER_EMPTY_GIF = ImageIO.read(ByteArrayInputStream(Base64.getDecoder().decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7")))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                val raw = res.raw()
                try {
                    ImageIO.write(BUFFER_EMPTY_GIF, "gif", raw.getOutputStream())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                raw
            }

            get("/pick") { req, res ->

                if (jedis == null) {
                    jedis = RedisManager.getInstance().jedisResource
                }

                val creativeManager = CreativeManager(jedis)

                // has opt out cookie or hasn't targeting cookie.
                // response filler network creative.
                if (req.cookie("__mizuki_opt_out__") != null || req.cookie("__mizuki_uid__") == null) {
                    return@get creativeManager.getFillerCreativeByCategory("_filler")
                }

                // get re-targeting data.
                // and response creative;
                creativeManager.getFillerCreativeByCategory(null)
            }

            post("/opt_out") { req, res ->
                res.cookie("__mizuki_opt_out__", "1")

                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
