package com.zs.common.redis.config;

import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 86740
 */
@Component
public class RedisUtil {
    @Resource
    public RedisTemplate<String, Object> redisTemplate;


    /**
     * @param key   key
     * @param value value
     * @param time  过期时间
     */
    public void setObject(@NotNull String key, @NotNull Object value, long time, @NotNull TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * String类型的set,无过期时间
     *
     * @param key   key
     * @param value value
     */
    public void setObject(@NotNull String key, @NotNull Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 批量设置key和value
     *
     * @param map key和value的集合
     */
    public void setMultiObject(@NotNull Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 如果key不存在，则设置
     *
     * @param key   key
     * @param value value
     * @return 返回是否成功
     */
    @Nullable
    public Boolean setKey(@NotNull String key, @NotNull Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 批量插入key，如果key不存在的话
     *
     * @param map key和value的集合
     * @return 是否成功
     */
    @Nullable
    public Boolean setMultiKey(@NotNull Map<String, Object> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * String类型的get
     *
     * @param key key
     * @return 返回value对应的对象
     */
    @Nullable
    public Object get(@NotNull String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Nullable
    public List<Object> getMulti(@NotNull Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 删除对应key
     *
     * @param key key
     * @return 返回是否删除成功
     */
    @Nullable
    public Boolean del(@NotNull String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys key的集合
     * @return 返回删除成功的个数
     */
    @Nullable
    public Long del(@NotNull List<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 给某个key设置过期时间
     *
     * @param key  key
     * @param time 过期时间
     * @return 返回是否设置成功
     */
    @Nullable
    public Boolean expire(@NotNull String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 返回某个key的过期时间
     *
     * @param key key
     * @return 返回key剩余的过期时间
     */
    @Nullable
    public Long getExpire(@NotNull String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 返回是否存在该key
     *
     * @param key key
     * @return 是否存在该key
     */
    @Nullable
    public Boolean exists(@NotNull String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 给key的值加上delta值
     *
     * @param key   key
     * @param delta 参数
     * @return 返回key+delta的值
     */
    @Nullable
    public Long increment(@NotNull String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 给key的值减去delta
     *
     * @param key   key
     * @param delta 参数
     * @return 返回key - delta的值
     */
    @Nullable
    public Long decrement(@NotNull String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }


    /**
     * set hash类型
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   value
     */
    public void hashKey(@NotNull String key, @NotNull String hashKey, @NotNull Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }


    /**
     * set hash类型,并设置过期时间
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   value
     * @param time    过期时间
     * @return 返回是否成功
     */
    public Boolean setHash(@NotNull String key, @NotNull String hashKey, @NotNull Object value, long time) {
        hashKey(key, hashKey, value);
        return expire(key, time);
    }

    /**
     * 批量设置hash
     *
     * @param key  key
     * @param map  hashKey和value的集合
     * @param time 过期时间
     * @return 是否成功
     */
    public Boolean setHash(@NotNull String key, @NotNull Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    /**
     * 获取hash类型的值
     *
     * @param key     key
     * @param hashKey hashKey
     * @return 返回对应的value
     */
    @Nullable
    public Object getHash(@NotNull String key, @NotNull String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取key下所有的hash值以及hashKey
     *
     * @param key key
     * @return 返回数据
     */
    @NotNull
    public Map<Object, Object> entries(@NotNull String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 批量删除
     *
     * @param key     key
     * @param hashKey hashKey数组集合
     */
    public void deleteKeys(@NotNull String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 判断是否存在hashKey
     *
     * @param key     key
     * @param hashKey hashKey
     * @return 是否存在
     */
    @NotNull
    public Boolean hasKey(@NotNull String key, @NotNull String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

}
