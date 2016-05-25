package cola.machine.config;

/**
 * @author dozen.zhang
 *
 */
public class CacheConfig {

    public void setRedis(RedisConfig redis) {
        this.redis = redis;
    }

    public void setEhcache(EhcacheConfig ehcache) {
        this.ehcache = ehcache;
    }

    /**
     * redis config
     */
    private RedisConfig redis =new RedisConfig();
    /**
     * ehcache config 
     */
    private EhcacheConfig ehcache=new EhcacheConfig() ;

    public RedisConfig getRedis() {
        return redis;
    }

    public EhcacheConfig getEhcache() {
        return ehcache;
    }
}
