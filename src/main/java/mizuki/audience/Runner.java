package mizuki.audience;

import redis.clients.jedis.Jedis;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;

import static spark.Spark.*;

public class Runner {
    private static final String RenderJsStart = "(function() { ";
    private static final String RenderJsEnd = " })();";
    private static Jedis jedis;
    private static BufferedImage BUFFER_EMPTY_GIF;
    static final String REDISHOST = System.getenv("REDIS_HOST");

    public static void main(String[] args) {
        try {
            System.out.println(System.getenv("REDIS_HOST"));

            before((req, res) -> {
                res.type("application/javascript");
                if (req.cookie("__mizuki_uid_one_day__") == null) {
                    res.cookie("__mizuki_uid_one_day__", "1", 86400);
                }
            });

            get("/", (req, res) -> {
                res.type("image/gif");

                String requestType = req.queryParams("type");
                requestType = requestType == null ? "" : requestType;

                switch (requestType) {
                case "oneday":
                    if (req.cookie("__mizuki_uid_one_day__") == null) {
                        if (jedis == null) {
                            jedis = RedisManager.getInstance().getJedisResource();
                        }
                        res.redirect(jedis.get("URL_ONE_DAY_IMAGE"));

                        return res.raw();
                    }
                    break;
                }

                if (BUFFER_EMPTY_GIF == null) {
                    try {
                        BUFFER_EMPTY_GIF = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                HttpServletResponse raw = res.raw();
                try {
                    ImageIO.write(BUFFER_EMPTY_GIF, "gif", raw.getOutputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return raw;
            });

            get("/pick", (req, res) -> {

                if (jedis == null) {
                    jedis = RedisManager.getInstance().getJedisResource();
                }

                CreativeManager creativeManager = new CreativeManager(jedis);

                // has opt out cookie or hasn't targeting cookie.
                // response filler network creative.
                if (req.cookie("__mizuki_opt_out__") != null || req.cookie("__mizuki_uid__") == null) {
                    return creativeManager.getFillerCreativeByCategory("_filler");
                }

                // get re-targeting data.
                // and response creative;
                return creativeManager.getFillerCreativeByCategory(null);
            });

            post("/opt_out", (req, res) -> {
                res.cookie("__mizuki_opt_out__", "1");

                return "";
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
