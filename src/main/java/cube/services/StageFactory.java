package cube.services;

import com.google.common.base.Preconditions;
import cube.aop.control.GameControllerHelper;
import cube.aop.score.ScoreMonitorHelper;
import cube.listeners.KeyboardListener;
import cube.listeners.Listener;
import cube.listeners.TetrisActionListener;
import cube.models.Score;
import cube.monitors.Monitor;
import cube.monitors.StageMonitor;
import cube.stages.ControlStage;
import cube.stages.GameControlStage;
import cube.stages.MainStage;
import cube.stages.Stage;

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

            return new MainStage(keyboardListener, monitor, scoreService);
        }
    }
}
