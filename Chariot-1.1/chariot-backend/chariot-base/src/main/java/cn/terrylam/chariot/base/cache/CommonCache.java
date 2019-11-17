package cn.terrylam.chariot.base.cache;

import cn.terrylam.framework.util.ContextHolderUtils;
import cn.terrylam.framework.util.Pager;
import cn.terrylam.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author TerryLam 2019/8/29
 * @version 1.0
 * @description
 **/
@Component
public class CommonCache<T> {

    private final static String LOCK_KEY = "lockData:key-";
    private final static String REFRESH_KEY = "cacheData:key-";
    private final static Integer GET_LOCK_TRY_NUM = 3;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Function<String, Object> objFunction;

    public void setObjFunction(Function<String, Object> objFunction) {
        this.objFunction = objFunction;
    }

    public List<T> getListDataByCache(String key, long time, TimeUnit timeUnit) {
        checkFunction();
        Object obj = redisTemplate.opsForValue().get(key);
        List<T> list = (List<T>) obj;
        if (Objects.isNull(list) || noCache()) {
            list = (List<T>) objFunction.apply(key);
            redisTemplate.opsForValue().setIfAbsent(key, list, time, timeUnit);
        }
        return list;
    }

    public List<T> getListDataByCache(Function<String, List<T>> objFunction, String key, long time, TimeUnit timeUnit) {
        if (Objects.isNull(objFunction)) {
            throw new NullFunctionException("object function is null");
        }
        Object obj = redisTemplate.opsForValue().get(key);
        List<T> list = (List<T>) obj;
        if (Objects.isNull(list) || noCache()) {
            list = (List<T>) objFunction.apply(key);
            redisTemplate.opsForValue().setIfAbsent(key, list, time, timeUnit);
        }
        return list;
    }

    public T getDataByCache(Function<String, T> objFunction, String key, long time, TimeUnit timeUnit) {
        if (Objects.isNull(objFunction)) {
            throw new NullFunctionException("object function is null");
        }
        T t = (T) redisTemplate.opsForValue().get(key);
        if (Objects.isNull(t) || noCache()) {
            t = (T) objFunction.apply(key);
            redisTemplate.opsForValue().setIfAbsent(key, t, time, timeUnit);
        }
        return t;
    }

    public T getDataByCache(String key, long time, TimeUnit timeUnit) {
        checkFunction();
        T t = (T) redisTemplate.opsForValue().get(key);
        if (Objects.isNull(t) || noCache()) {
            t = (T) objFunction.apply(key);
            redisTemplate.opsForValue().setIfAbsent(key, t, time, timeUnit);
        }
        return t;
    }

    public Pager<T> getPagerDataByCache(String key, long time, TimeUnit timeUnit) {
        checkFunction();

        Object obj = redisTemplate.opsForValue().get(key);
        Pager<T> pager = (Pager<T>) obj;
        if (Objects.isNull(pager) || noCache()) {

            pager = (Pager<T>) objFunction.apply(key);
            redisTemplate.opsForValue().setIfAbsent(key, pager, time, timeUnit);
        }
        return pager;
    }

    public Pager<T> getPagerDataByCache(Function<String, Pager<T>> objFunction, String key, long time, TimeUnit timeUnit) {
        if (Objects.isNull(objFunction)) {
            throw new NullFunctionException("object function is null");
        }
        Object obj = redisTemplate.opsForValue().get(key);
        Pager<T> pager = (Pager<T>) obj;
        if (Objects.isNull(pager) || noCache()) {
            pager = (Pager<T>) objFunction.apply(key);
            redisTemplate.opsForValue().setIfAbsent(key, pager, time, timeUnit);
        }
        return pager;
    }

    /**
     * @param key
     * @param time     时隔多久刷新一次
     * @param timeUnit
     * @author TerryLam 12:17 2019/9/9
     * @description 不是自动刷新，而是会判断上一次插入缓存的时间，时间差大于之前时间，则读库并刷新缓存。
     * 默认设置一天的过期时间。刷新时间不可大于1 天
     **/
    public T getDataByCacheAutoRefresh(Function<String, T> objFunction, String key, long time, TimeUnit timeUnit) {
        boolean needRefresh = isNeedRefresh(key, timeUnit, time);
        // 多次重试获取锁失败，则直接返回旧数据
        try {
            checkLock(key);
        } catch (LockTimeOutException e) {
            return (T) redisTemplate.opsForValue().get(key);
        }
        T t = (T) redisTemplate.opsForValue().get(key);
        if (Objects.isNull(t) || needRefresh) {
            if (lock(key)) {
                t = objFunction.apply(key);
                redisTemplate.opsForValue().setIfAbsent(key, t, 1, TimeUnit.DAYS);
                stringRedisTemplate.opsForValue().set(REFRESH_KEY + key, String.valueOf(System.currentTimeMillis()));
                unlock(key);
            }
        }
        return t;
    }

    private void checkFunction() throws NullFunctionException {
        if (Objects.isNull(this.objFunction)) {
            throw new NullFunctionException("object function is null");
        }
    }

    private void checkLock(String key) throws LockTimeOutException {
        String lockKey = LOCK_KEY + key;
        String timestamp = stringRedisTemplate.opsForValue().get(lockKey);
        if (StringUtils.isNotBlank(timestamp)) {
            // 被锁
            int tryNum = 1;
            while (tryNum <= GET_LOCK_TRY_NUM) {
                try {
                    Thread.sleep(100);
                    timestamp = stringRedisTemplate.opsForValue().get(lockKey);
                    if (StringUtils.isBlank(timestamp)) {
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tryNum++;
            }
            throw new LockTimeOutException();
        }
    }

    private boolean lock(String key) {
        String lockKey = LOCK_KEY + key;
        String timestamp = stringRedisTemplate.opsForValue().get(lockKey);
        if (StringUtils.isBlank(timestamp)) {
            stringRedisTemplate.opsForValue().setIfAbsent(lockKey, String.valueOf(System.currentTimeMillis()), 30, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    private boolean unlock(String key) {
        String lockKey = LOCK_KEY + key;
        String timestamp = stringRedisTemplate.opsForValue().get(lockKey);
        if (StringUtils.isNotBlank(timestamp)) {
            stringRedisTemplate.opsForValue().getOperations().delete(lockKey);
            return true;
        }
        return false;
    }

    private boolean isNeedRefresh(String key, TimeUnit timeUnit, long duration) {
        String timestamp = stringRedisTemplate.opsForValue().get(REFRESH_KEY + key);
        boolean refreshFlag = false;
        if (StringUtils.isNotBlank(timestamp)) {
            long minus = System.currentTimeMillis() - Long.parseLong(timestamp);
            if (minus > timeUnit.toMillis(duration)) {
                //刷新
                refreshFlag = true;
            }
        }
        return refreshFlag;
    }

    /**
     * @author TerryLam 16:11 2019/10/22
     * @description 若有请求header No-Cache 则直走DB
     * @return boolean
     **/
    private boolean noCache() {
        HttpServletRequest request = ContextHolderUtils.getRequest();
        if (Objects.nonNull(request)) {
            String cacheControl = request.getHeader("Cache-Control");
            if (StringUtils.isNotBlank(cacheControl) && "no-cache".equals(cacheControl)) {
                return true;
            }
        }
        return false;
    }
}
