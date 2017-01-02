package mizuki.audience;

import redis.clients.jedis.Jedis;

class CreativeManager {

    private static Jedis jedis;

    CreativeManager(Jedis _jedis) {
        jedis = _jedis;
    }

    String getFillerCreativeByCategory(String category) {
        try {
            if (category == null || category.equals("")) {
                category = "filler";
            }

            if (jedis.exists(category)) {
                return jedis.get(category);
            }

            throw new RuntimeException("does not creative: " + category);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jedis.get("filler");
        }
    }

}
