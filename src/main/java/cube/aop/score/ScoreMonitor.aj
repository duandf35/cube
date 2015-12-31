package cube.aop.score;

import cube.services.ScoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect to handle score update/save operations.
 *
 * @author wenyu
 * @since 12/19/15
 */
privileged aspect ScoreMonitor {

    private static final Logger LOG = LogManager.getLogger(ScoreMonitor.class);

    private final ScoreService scoreService = ScoreService.getInstance();

    pointcut methodWithScoreOperationRequiredAnnotation() : execution(* * (..)) && @annotation(cube.aop.score.ScoreOperationRequired);

    after() : methodWithScoreOperationRequiredAnnotation() {
        MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
        ScoreOperation ops = method.getMethod().getAnnotation(ScoreOperationRequired.class).operation();

        if (ScoreOperation.UPDATE == ops) {
            scoreService.update();
        } else if (ScoreOperation.SAVE == ops) {
            scoreService.save();
        } else {
            LOG.warn("Unknown operation {} received.", ops);
        }
    }
}
