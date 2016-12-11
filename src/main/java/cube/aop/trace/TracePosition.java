package cube.aop.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cube.aop.TraceUtils;

/**
 * @author wenyu
 * @since 11/9/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TracePosition {

    TraceUtils.Action action();
}
