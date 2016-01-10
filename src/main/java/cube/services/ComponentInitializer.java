package cube.services;

import cube.aop.score.ScoreMonitorHelper;
import cube.listeners.KeyboardListener;
import cube.listeners.Listener;
import cube.listeners.StageListener;
import cube.models.Score;
import cube.monitors.Monitor;
import cube.monitors.StageMonitor;
import cube.stages.GameControlStage;
import cube.stages.Stage;
import cube.stages.SubStage;
import cube.stages.TetrisStage;

/**
 * @author wenyu
 * @since 10/24/15
 */
public final class ComponentInitializer implements Factory<Stage> {
    private static final ComponentInitializer INITIALIZER = new ComponentInitializer();

    private final RecordService<Score> scoreService;

    private ComponentInitializer() {
        scoreService = new ScoreService();
    }

    public static ComponentInitializer getInstance() {
        return INITIALIZER;
    }

    @Override
    public Stage build() {
        Stage container = buildContainer();
        Listener containerListener = new StageListener(container);
        SubStage controlStage = new GameControlStage(containerListener);
        SubStage[] subStages = new SubStage[] { controlStage };

        container.add(controlStage);

        ComponentManager.getInstance()
                        .register(container)
                        .register(subStages)
                        .register(ScoreRecordStageManager.getInstance()
                                                         .register(scoreService));

        return container;
    }

    private Stage buildContainer() {
        KeyboardListener keyboardListener = new KeyboardListener();
        Monitor monitor = new StageMonitor();
        ScoreMonitorHelper.inject(scoreService);

        return new TetrisStage(keyboardListener, monitor, scoreService);
    }
}
