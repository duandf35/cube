package cube.aop.score;

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
}
