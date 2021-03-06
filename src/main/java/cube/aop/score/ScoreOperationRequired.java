package cube.aop.score;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cube.aop.TraceUtils;

/**
 * @author wenyu
 * @since 12/24/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ScoreOperationRequired {

    TraceUtils.ScoreOperation operation();
}
