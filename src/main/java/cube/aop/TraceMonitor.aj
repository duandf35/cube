package cube.aop;

import cube.models.ITetris;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author wenyu
 * @since 11/9/15
 */
privileged aspect TraceMonitor {
    private static final Logger P_LOG = LogManager.getLogger(TraceUtils.POSITION_LOGGER);
    private static final Logger A_LOG = LogManager.getLogger(TraceUtils.ACTION_LOGGER);

    // Add execution to avoid invoking advice twice
    pointcut methodWithTracePositionAnnotation() : execution(* *(..)) && @annotation(cube.aop.TracePosition);

    pointcut methodWithTraceActionAnnotation() : execution(* *(..)) && @annotation(cube.aop.TraceAction);

    Object around() : methodWithTracePositionAnnotation() {
        Object retVal;
        try {

            MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
            String label = method.getMethod().getAnnotation(TracePosition.class).action().getName();

            ITetris tetris = getTetris(thisJoinPoint);
            String tetrisInformation = null != tetris ? tetris.toString() : null;

            P_LOG.debug("[" + label + "] [FROM] {}\n", tetrisInformation);
            retVal = proceed();
            P_LOG.debug("[" + label + "] [TO] {}\n", tetrisInformation);
        } catch (Throwable t) {
            P_LOG.error("[POSITION] Exception happen during method intercepting. Error: {}", t);
            retVal = null;
        }

        return retVal;
    }

    Object around() : methodWithTraceActionAnnotation() {
        Object retVal;

        try {
            retVal = proceed();

            MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
            String label = method.getMethod().getAnnotation(TraceAction.class).action().getName();

            ITetris tetris = getTetris(thisJoinPoint);
            String tetrisInformation = null != tetris ? tetris.toString() : null;

            A_LOG.debug("[" + label + "] {}\n", tetrisInformation);
        } catch (Throwable t) {
            A_LOG.error("[ACTION] Exception happen during method intercepting. Error: {}", t);
            retVal = null;
        }

        return retVal;
    }

    private ITetris getTetris(JoinPoint joinPoint) {
        ITetris tetris = null;
        Object[] args = joinPoint.getArgs();

        if (null != args) {
            for (Object arg : args) {
                if (arg instanceof ITetris) {
                    tetris = (ITetris) arg;
                    break;
                }
            }
        }

        if (null == tetris) {
            tetris = (ITetris) joinPoint.getThis();
        }

        return tetris;
    }
}
