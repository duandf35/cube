package cube.aop.score;

import com.google.common.base.Preconditions;
import cube.aop.TraceUtils;
import cube.configs.ListenerConfig;
import cube.services.IHitCountService;
import cube.services.IScoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Aspect to handle score update/save operations.
 *
 * @author wenyu
 * @since 12/19/15
 */
privileged aspect ScoreMonitor {

    private static final Logger LOG = LogManager.getLogger(ScoreMonitor.class);

    private final Timer hitCountTimer = new Timer();

    private IScoreService scoreService;
    private IHitCountService hitCountService;

    pointcut methodWithScoreOperationRequiredAnnotation() : execution(* * (..)) && @annotation(cube.aop.score.ScoreOperationRequired);

    after() : methodWithScoreOperationRequiredAnnotation() {
        MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
        TraceUtils.ScoreOperation ops = method.getMethod().getAnnotation(ScoreOperationRequired.class).operation();

        Preconditions.checkNotNull(scoreService, "scoreService has not been registered!");

        if (TraceUtils.ScoreOperation.UPDATE == ops) {
            hitCountService.update();
            scoreService.update(hitCountService.get());
        } else if (TraceUtils.ScoreOperation.SAVE == ops) {
            scoreService.save();
        } else {
            LOG.warn("Unknown operation {} received.", ops);
        }
    }

    public void setScoreService(final IScoreService scoreService) {
        this.scoreService = scoreService;
    }

    public void setHitCountService(final IHitCountService hitCountService) {
        this.hitCountService = hitCountService;
    }

    /**
     * Activate timer to periodically reset hit count.
     */
    public void activateHitCountPeriod() {
        ListenerConfig config = ListenerConfig.getInstance();

        LOG.debug("Activating hit count reset timer...");

        hitCountTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                hitCountService.reset();
            }
        }, config.getGravityApplyDelay(), config.getHitCountPeriod());
    }
}
