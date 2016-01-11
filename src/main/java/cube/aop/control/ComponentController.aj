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

    pointcut methodWithGameStatusAnnotation() : execution(* * (..)) && @annotation(cube.aop.control.ControlStatus);

    after() : methodWithGameStatusAnnotation() {
        MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
        TraceUtils.Status status = method.getMethod().getAnnotation(ControlStatus.class).status();

        if (TraceUtils.Status.GAME_START == status) {
            ComponentManager.getInstance().hideFinalScore();
            ComponentManager.getInstance().remove();
            ComponentManager.getInstance().removeRecords();
        } else if (TraceUtils.Status.GAME_OVER == status) {
            ComponentManager.getInstance().showFinalScore();
            ComponentManager.getInstance().reset();
            ComponentManager.getInstance().add();
        } else if (TraceUtils.Status.SHOW_RECORDS == status) {
            ComponentManager.getInstance().addRecords();
        } else {
            LOG.warn("Unknown status {} received.", status);
        }
    }
}