package cube.aop.control;

import cube.aop.TraceUtils;
import cube.services.ComponentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Wenyu
 * @since 1/2/16
 */
privileged aspect ComponentController {

    private static final Logger LOG = LogManager.getLogger(ComponentController.class);

    pointcut methodWithGameStatusAnnotation() : execution(* * (..)) && @annotation(cube.aop.control.GameStatus);

    after() : methodWithGameStatusAnnotation() {
        MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
        TraceUtils.Status status = method.getMethod().getAnnotation(GameStatus.class).status();

        if (TraceUtils.Status.GAME_START == status) {
            ComponentManager.getInstance().remove();
            ComponentManager.getInstance().reset();
        } else if (TraceUtils.Status.GAME_OVER == status) {
            ComponentManager.getInstance().add();
        } else {
            LOG.warn("Unknown status {} received.", status);
        }
    }
}
