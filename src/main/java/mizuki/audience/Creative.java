package mizuki.audience;

import redis.clients.jedis.Jedis;

class Creative {
    private int id;
    private String code;
    private String provider;
    private static Jedis jedis;

    Creative() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
