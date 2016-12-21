package mizuki.audience;

import redis.clients.jedis.Jedis;

class Creative {
    private int id;
    private String code;
    private String provider;
    private static Jedis jedis;

    String getFillerCreative(Jedis jedis) {
        return jedis.get("filler");
    }
}
