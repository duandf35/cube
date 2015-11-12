package cube.aop;

import cube.models.Tetris;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author wenyu
 * @since 11/9/15
 */
privileged aspect TracePositionMonitor {
    private static final Logger P_LOG = LogManager.getLogger("PositionLogger");

    // Add execution to avoid invoking advice twice
    pointcut methodWithTracePositionAnnotation() : execution(* *(..)) && @annotation(TracePosition);

    Object around() : methodWithTracePositionAnnotation() {
        Object retVal;
        try {
            MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
            String label = method.getMethod().getAnnotation(TracePosition.class).label();

            Tetris tetris = null;
            for (Object arg : thisJoinPoint.getArgs()) {
                if (arg instanceof Tetris) {
                    tetris = (Tetris) arg;
                    break;
                }
            }

            P_LOG.debug("[" + label + "]" + "[FROM] {}\n", tetris.toString());
            retVal = proceed();
            P_LOG.debug("[" + label + "]" + "[TO] {}\n", tetris.toString());
        } catch (Throwable t) {
            P_LOG.error("Exception happen during method intercepting. Error: {}", t.getClass());
            retVal = null;
        }

        return retVal;
    }
}
