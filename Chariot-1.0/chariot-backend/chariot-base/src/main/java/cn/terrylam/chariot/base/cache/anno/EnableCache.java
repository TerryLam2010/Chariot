/**
 * Created on  13-09-04
 */
package cn.terrylam.chariot.base.cache.anno;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnableCache {
}
