package mizuki.audience;

import static spark.Spark.get;

public class Main {
    private static final String RenderJsStart = "(function() {";
    private static final String RenderJsEnd = "})();";
    public static void main(String[] args) {
        try {
            get("/", (req, res) -> {

                StringBuilder responseBody = new StringBuilder();
                responseBody.append(RenderJsStart);
                responseBody.append("console.log('hello world');");

                return responseBody.append(RenderJsEnd).toString();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
