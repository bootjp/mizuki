package mizuki.audience;

import static spark.Spark.get;

public class Runner {
    private static final String RenderJsStart = "(function() { ";
    private static final String RenderJsEnd = " })();";
    private static RedisManager redisManager;

    public static void main(String[] args) {
        try {
            get("/", (req, res) -> RenderJsStart + "console.log('hello world');" + RenderJsEnd);

            get("/pick", (req, res) -> {
                if (redisManager == null) {
                    redisManager = RedisManager.getInstance();
                }

                return "";
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
