/**
 * Created on  13-09-10 10:33
 */
package cn.terrylam.chariot.base.cache.support;


import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class CachedAnnoConfig extends CacheAnnoConfig {

    private boolean enabled;
    private TimeUnit timeUnit;
    private long expire;
    private int localLimit;
    private boolean cacheNullValue;
    private String serialPolicy;
    private String keyConvertor;

    private Function<Object, Boolean> unlessEvaluator;

    public boolean isEnabled() {
        return enabled;
    }

    public long getExpire() {
        return expire;
    }

    public int getLocalLimit() {
        return localLimit;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public void setLocalLimit(int localLimit) {
        this.localLimit = localLimit;
    }

    public boolean isCacheNullValue() {
        return cacheNullValue;
    }

    public void setCacheNullValue(boolean cacheNullValue) {
        this.cacheNullValue = cacheNullValue;
    }

    public String getSerialPolicy() {
        return serialPolicy;
    }

    public void setSerialPolicy(String serialPolicy) {
        this.serialPolicy = serialPolicy;
    }

    public String getKeyConvertor() {
        return keyConvertor;
    }

    public void setKeyConvertor(String keyConvertor) {
        this.keyConvertor = keyConvertor;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Function<Object, Boolean> getUnlessEvaluator() {
        return unlessEvaluator;
    }

    public void setUnlessEvaluator(Function<Object, Boolean> unlessEvaluator) {
        this.unlessEvaluator = unlessEvaluator;
    }

}
