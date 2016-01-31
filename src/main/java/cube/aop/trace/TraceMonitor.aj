package cube.aop.trace;

import cube.aop.TraceUtils;
import cube.models.ITetris;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author wenyu
 * @since 11/9/15
 */
privileged aspect TraceMonitor {
    private static final Logger POS_LOGGER = LogManager.getLogger(TraceUtils.POSITION_LOGGER);
    private static final Logger ACT_LOGGER = LogManager.getLogger(TraceUtils.ACTION_LOGGER);
    private static final Logger PERF_LOGGER = LogManager.getLogger(TraceUtils.PERFORMANCE_LOGGER);

    // Add execution to avoid invoking advice twice
    pointcut methodWithTracePositionAnnotation() : execution(* *(..)) && @annotation(cube.aop.trace.TracePosition);

    // TODO: Refactor point cut evaluation to include annotation arguments
    pointcut methodWithTraceActionAnnotation() : execution(* *(..)) && @annotation(cube.aop.trace.TraceAction);

    pointcut methodWithTracePerformanceAnnotation() : execution(* *(..)) && @annotation(cube.aop.trace.TracePerformance);

    /**
     * Trace position changing.
     */
    Object around() : methodWithTracePositionAnnotation() {
        Object retVal;

        try {
            MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
            String label = method.getMethod().getAnnotation(TracePosition.class).action().toString();

            ITetris tetris = getTetris(thisJoinPoint);
            String tetrisInformation = null != tetris ? tetris.toString() : null;

            POS_LOGGER.debug("[" + label + "] [FROM] {}\n", tetrisInformation);
            retVal = proceed();
            POS_LOGGER.debug("[" + label + "] [TO] {}\n", tetrisInformation);
        } catch (Throwable t) {
            POS_LOGGER.error("[POSITION] Exception happen during method intercepting. Error: {}", t);
            retVal = null;
        }

        return retVal;
    }

    /**
     * Trace action.
     */
    Object around() : methodWithTraceActionAnnotation() {
        Object retVal;

        try {
            retVal = proceed();

            MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
            String label = method.getMethod().getAnnotation(TraceAction.class).action().toString();

            ITetris tetris = getTetris(thisJoinPoint);
            String tetrisInformation = null != tetris ? tetris.toString() : null;

            ACT_LOGGER.debug("[" + label + "] {}\n", tetrisInformation);
        } catch (Throwable t) {
            ACT_LOGGER.error("[ACTION] Exception happen during method intercepting. Error: {}", t);
            retVal = null;
        }

        return retVal;
    }

    /**
     * Trace performance.
     */
    Object around() : methodWithTracePerformanceAnnotation() {
        Object retVal;
        StopWatch stopWatch = new StopWatch();

        try {
            MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
            String methodName = thisJoinPoint.getTarget().toString().split("[@\\[]")[0] + "." + method.getName();
            String args = String.join(",", method.getParameterNames());

            stopWatch.start();
            retVal = proceed();
            stopWatch.stop();
            PERF_LOGGER.debug("Time[" + stopWatch + "]" + " method: '" + methodName + "' invoking with args: [" + args + "].");
            stopWatch.reset();
        }  catch (Throwable t) {
            PERF_LOGGER.error("Error happen during method performance monitoring invoking.", t);
            retVal = null;
        }

        return retVal;
    }

    private ITetris getTetris(JoinPoint joinPoint) {
        ITetris tetris = null;
        Object[] args = joinPoint.getArgs();

        // If tetris is the argument of the joinPoint
        if (null != args) {
            for (Object arg : args) {
                if (arg instanceof ITetris) {
                    tetris = (ITetris) arg;
                    break;
                }
            }
        }

        // If joinPoint is the tetris
        if (null == tetris && joinPoint.getThis() instanceof ITetris) {
            tetris = (ITetris) joinPoint.getThis();
        }

        return tetris;
    }
}
