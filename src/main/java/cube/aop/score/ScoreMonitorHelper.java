package cube.aop.score;

import cube.services.IHitCountService;
import cube.services.IScoreService;

/**
 * @author Wenyu
 * @since 1/2/16
 */
public final class ScoreMonitorHelper {

    private ScoreMonitorHelper() {

    }

    public static void inject(final IScoreService scoreService) {
        ScoreMonitor.aspectOf().setScoreService(scoreService);
    }

    public static void inject(final IHitCountService hitCountService) {
        ScoreMonitor.aspectOf().setHitCountService(hitCountService);
        ScoreMonitor.aspectOf().registerTimer();
    }
}
