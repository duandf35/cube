package cube.aop.score;

import cube.services.ScoreService;

/**
 * @author Wenyu
 * @since 1/2/16
 */
public final class ScoreMonitorHelper {

    private ScoreMonitorHelper() {

    }

    public static void inject(final ScoreService scoreService) {
        ScoreMonitor.aspectOf().setScoreService(scoreService);
    }
}
