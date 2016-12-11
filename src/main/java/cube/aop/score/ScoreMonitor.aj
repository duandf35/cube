package cube.aop.score;

import com.google.common.base.Preconditions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.TimerTask;

import cube.aop.TraceUtils;
import cube.configs.ListenerConfig;
import cube.monitors.TimerMonitor;
import cube.monitors.timers.TimerRegister;
import cube.monitors.timers.TimerTaskBuilder;
import cube.monitors.timers.TimerWrapper;
import cube.services.IHitCountService;
import cube.services.IScoreService;

/**
 * Aspect to handle score update/save operations.
 *
 * @author wenyu
 * @since 12/19/15
 */
privileged aspect ScoreMonitor implements TimerRegister {
    private static final Logger LOG = LogManager.getLogger(ScoreMonitor.class);

    private IScoreService scoreService;
    private IHitCountService hitCountService;

    pointcut methodWithScoreOperationRequiredAnnotation() : execution(* * (..)) && @annotation(cube.aop.score.ScoreOperationRequired);

    after() : methodWithScoreOperationRequiredAnnotation() {
        MethodSignature method = (MethodSignature) thisJoinPoint.getSignature();
        TraceUtils.ScoreOperation ops = method.getMethod().getAnnotation(ScoreOperationRequired.class).operation();

        Preconditions.checkNotNull(scoreService, "scoreService has not been registered!");

        if (TraceUtils.ScoreOperation.UPDATE == ops) {
            hitCountService.update();
            hitCountService.canResetOff();
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
     * Register timer.
     */
    @Override
    public void registerTimer() {
        ListenerConfig config = ListenerConfig.getInstance();

        TimerTaskBuilder taskBuilder = () ->
                new TimerTask() {
                    @Override
                    public void run() {
                        if (hitCountService.canReset()) {
                            hitCountService.reset();
                        }

                        hitCountService.canResetOn();
                    }
                };

        TimerMonitor.getInstance()
                    .register(new TimerWrapper("Hit Count Reset Timer",
                                               taskBuilder,
                                               config.getGravityApplyDelay(),
                                               config.getHitCountPeriod()));
    }
}
