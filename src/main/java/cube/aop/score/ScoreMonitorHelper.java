package cube.aop.score;

import cube.models.Score;
import cube.services.RecordService;
import cube.services.ScoreService;

/**
 * @author Wenyu
 * @since 1/2/16
 */
public final class ScoreMonitorHelper {

    private ScoreMonitorHelper() {

    }

    public static void inject(final RecordService<Score> scoreService) {
        ScoreMonitor.aspectOf().setScoreService(scoreService);
    }
}
