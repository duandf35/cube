package cube.services;

import cube.listeners.KeyboardListener;
import cube.models.Score;
import cube.monitors.Monitor;
import cube.monitors.StageMonitor;
import cube.stages.MainStage;
import cube.stages.Stage;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class StageFactory implements Factory<Stage> {
    private static final StageFactory FACTORY = new StageFactory();

    private StageFactory() {

    }

    public static StageFactory getInstance() {
        return FACTORY;
    }

    public Stage build() {
        KeyboardListener keyboardListener = new KeyboardListener();

        return new MainStage(keyboardListener, buildMonitor());
    }

    private Monitor buildMonitor() {
        RecordService<Score> scoreService = new ScoreService();

        return new StageMonitor(scoreService);
    }
}
