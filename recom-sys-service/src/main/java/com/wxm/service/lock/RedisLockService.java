package com.wxm.service.lock;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import com.wxm.log.CommonLogger;

@Service
public class RedisLockService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String LOCK_VALUE = "lockValue";
    private static final long DEFAULT_EXPIRE_SECOND = 10;

    public boolean lockJob(String key) {
        return lockJob(key, DEFAULT_EXPIRE_SECOND);
    }

    public boolean lockJob(String key, long expireSecond) {
        return lock(key, expireSecond, true);
    }

   /**
    * 
   * @Title: lock 
   * @param  key
   * @param  expireSecond
   * @param  exceptionReturn 如果连接工厂、连接、操作异常，那么根据 exceptionReturn 返回值
   * @return boolean 
   * @throws
    */
    public boolean lock(String key, long expireSecond, boolean exceptionReturn) {
        try {
            return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.set(key.getBytes(), LOCK_VALUE.getBytes(),
                            Expiration.seconds(expireSecond), SetOption.SET_IF_ABSENT);
                }
            });
        } catch (Exception ex) {
            CommonLogger.error("redis set nx ex error, ex:", ex);
            return exceptionReturn;
        }
    }
    
    public void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

}
