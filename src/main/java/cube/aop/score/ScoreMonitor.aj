package cube.aop.score;

import com.google.common.base.Preconditions;
import cube.aop.TraceUtils;
import cube.models.Score;
import cube.services.RecordService;
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

    private RecordService<Score> scoreService;

    pointcut methodWithScoreOperationRequiredAnnotation() : execution(* * (..)) && @annotation(cube.aop.score.ScoreOperationRequired);

    after() : methodWithScoreOperationRequiredAnnotation() {
        MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
        TraceUtils.ScoreOperation ops = method.getMethod().getAnnotation(ScoreOperationRequired.class).operation();

        Preconditions.checkNotNull(scoreService, "scoreService has not been registered!");

        if (TraceUtils.ScoreOperation.UPDATE == ops) {
            scoreService.update();
        } else if (TraceUtils.ScoreOperation.SAVE == ops) {
            scoreService.save();
        } else {
            LOG.warn("Unknown operation {} received.", ops);
        }
    }

    public void setScoreService(final RecordService<Score> scoreService) {
        this.scoreService = scoreService;
    }
}
