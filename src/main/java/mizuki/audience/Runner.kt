package mizuki.audience

import redis.clients.jedis.Jedis
import spark.kotlin.before
import spark.kotlin.get
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.util.*
import javax.imageio.ImageIO


object Runner {
    private var Jedis: Jedis = RedisManager.getJedisResource()

    private val TRACKING_COOKIE_NAME: String = "__MIZUKI__TRACK__" // @todo create and move audience class

    private val KEY_DIR_SITE: String = "directoryList" // @todo and move create site class

    val BUFFER_EMPTY_GIF: BufferedImage = ImageIO.read(ByteArrayInputStream(Base64.getDecoder().decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7")))

    @JvmStatic fun main(args: Array<String>) {
        try {
            before() {

            }
            get("/track") {
                type("image/gif")
                var requestType: String?  = params("type")
                if (requestType == null) {
                    requestType = ""
                }

                when (requestType) {
                    "oneday" -> {
                        if (request.cookie("oneday") != null) {
                            redirect(Jedis.get("URL_ONE_DAY_IMAGE"))
                            response.cookie("oneday", "true")
                        }
                    }
                }

                val raw = response.raw()
                try {
                    ImageIO.write(BUFFER_EMPTY_GIF, "gif", raw.outputStream)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return@get raw
            }

            get("/render") {

                val hasTrackingCookie:Boolean = request.cookie(TRACKING_COOKIE_NAME) != null

                if (hasTrackingCookie) {
                    // @todo get creative by audience
                    // @todo get re-targeting data.
                    // @todo and response creative;
                }

                var siteUrl:String = request.host()
                if (Jedis.hexists(KEY_DIR_SITE, siteUrl)) {
                    // @todo not null safe
                    siteUrl += request.pathInfo().split(Regex("/"))[0]
                }

                if (false) {
                    Jedis.hget("CATEGORY", siteUrl)
                    // get site category
                    // return creative.
                }

                // return filler network
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
