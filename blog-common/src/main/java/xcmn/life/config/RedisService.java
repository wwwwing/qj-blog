package xcmn.life.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> template;

    public RedisTemplate<String, Object> getTemplate() {
        return template;
    }


    /**
     * 获取Hash的所有值
     */
    public List<Object> getHashValues(String key) {
        HashOperations<String, String, Object> hps = template.opsForHash();

        return hps.values(key);

    }

    /**
     * 获取Hash指定keys的数据
     * @param hashKey
     * @param keys
     * @return
     */
    public List<Object> getHashByKeys(String hashKey,List<Object> keys){
        return template.opsForHash().multiGet(hashKey, keys);
    }

    /**
     * 存储数据或修改数据
     *
     * @param modelMap
     * @param mapName
     */
    public void setKey(String mapName, Map<String, Object> modelMap) {
        HashOperations<String, String, Object> hps = template.opsForHash();
        hps.putAll(mapName, modelMap);
    }

    /**
     * 存储数据 过期时间
     *
     * @param
     * @param
     */
    public void setObjectKeyExpire(String key, Object value, Long minutes) {
        template.opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
    }


    /**
     * 存储数据 过期时间
     *
     * @param
     * @param
     */
    public void setKeyExpire(String key, String value, Long minutes) {
        template.opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
    }

    /**
     * 存储数据 过期时间(秒)
     *
     * @param
     * @param
     */
    public void setKeyExpireBySeconds(String key, String value, Long seconds) {
        template.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }


    /**
     * 存储数据 过期时间
     *
     * @param
     * @param
     */
    public void setHashExpire(String key, String hashKey, Object value, Long minutes) {
        BoundHashOperations<String, Object, Object> redisOper = template.boundHashOps(key);
        redisOper.put(hashKey, value);
        redisOper.expire(minutes, TimeUnit.MINUTES);
    }


    /**
     * 删除数据 过期时间
     *
     * @param
     * @param
     */
    public void deleteHashExpire(String key, String hashKey) {
        BoundHashOperations<String, Object, Object> redisOper = template.boundHashOps(key);
        redisOper.delete(hashKey);
    }

    /**
     * 获取数据 过期时间
     *
     * @param
     * @param
     */
    public Object getHashExpire(String key, String hashKey) {
        BoundHashOperations<String, Object, Object> redisOper = template.boundHashOps(key);
        return redisOper.get(hashKey);

    }
}
