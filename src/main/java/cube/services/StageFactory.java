package cube.services;

import cube.aop.score.ScoreMonitorHelper;
import cube.listeners.KeyboardListener;
import cube.models.Score;
import cube.monitors.Monitor;
import cube.monitors.StageMonitor;
import cube.stages.Stage;
import cube.stages.TetrisStage;

/**
 * @author wenyu
 * @since 10/24/15
 */
public final class StageFactory {

    private StageFactory() {

    }

    public static class ModelStageFactory implements Factory<Stage> {
        private static final ModelStageFactory FACTORY = new ModelStageFactory();

        private ModelStageFactory() {

        }

        public static ModelStageFactory getInstance() {
            return FACTORY;
        }

        @Override
        public Stage build() {
            KeyboardListener keyboardListener = new KeyboardListener();
            Monitor monitor = new StageMonitor();
            RecordService<Score> scoreService = new ScoreService();
            ScoreMonitorHelper.inject(scoreService);

            return new TetrisStage(keyboardListener, monitor, scoreService);
        }
    }
}
