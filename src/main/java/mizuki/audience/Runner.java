package mizuki.audience;

import static spark.Spark.before;
import static spark.Spark.get;

public class Runner {
    private static final String RenderJsStart = "(function() { ";
    private static final String RenderJsEnd = " })();";
    private static redis.clients.jedis.Jedis Jedis;

    public static void main(String[] args) {
        try {
            before((req, res) -> {
                res.type("application/javascript");
            });

            get("/", (req, res) -> RenderJsStart + "console.log('hello world');" + RenderJsEnd);

            get("/pick", (req, res) -> {

                if (Jedis == null) {
                    Jedis = RedisManager.getInstance().getJedisResource();
                }

                // hasn't targeting cookie.
                if (req.cookie("__mizuki_uid__") == null) {
                    return (new Creative()).getFillerCreative(Jedis);
                }

                // get re-targeting data.
                // and response creative;
                return "";
            });

            get("/sync", (req, res) -> {
                return "";
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
