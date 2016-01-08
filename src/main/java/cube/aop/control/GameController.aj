package cube.aop.control;

import com.google.common.base.Preconditions;
import cube.aop.TraceUtils;
import cube.stages.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Wenyu
 * @since 1/2/16
 */
privileged aspect GameController {

    private static final Logger LOG = LogManager.getLogger(GameController.class);

    private Stage tetrisStage;

    pointcut methodWithGameStatusAnnotation() : execution(* * (..)) && @annotation(cube.aop.control.GameStatus);

    after() : methodWithGameStatusAnnotation() {
        MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
        TraceUtils.Status status = method.getMethod().getAnnotation(GameStatus.class).status();

        Preconditions.checkNotNull(tetrisStage, "tetrisStage has not been registered!");

        if (TraceUtils.Status.GAME_START == status) {
            tetrisStage.unsetControlStage();
        } else if (TraceUtils.Status.GAME_OVER == status) {
            tetrisStage.setControlStage();
            tetrisStage.reset();
        } else {
            LOG.warn("Unknown status {} received.", status);
        }
    }

    public void setStage(final Stage tetrisStage) {
        this.tetrisStage = tetrisStage;
    }
}
