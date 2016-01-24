package cube.services.factories;

import cube.aop.score.ScoreMonitorHelper;
import cube.listeners.IStageListener;
import cube.listeners.KeyboardListener;
import cube.listeners.StageListener;
import cube.monitors.IStageMonitor;
import cube.monitors.StageMonitor;
import cube.services.HitCountService;
import cube.services.IHitCountService;
import cube.services.IScoreService;
import cube.services.ScoreService;
import cube.stages.ContainerStage;
import cube.stages.GameControlStage;
import cube.stages.SubStage;
import cube.stages.TetrisStage;

/**
 * @author wenyu
 * @since 10/24/15
 */
public final class ComponentInitializer implements Factory<ContainerStage> {
    private static final ComponentInitializer INITIALIZER = new ComponentInitializer();

    private final IScoreService scoreService;

    private ComponentInitializer() {
        scoreService = new ScoreService();
    }

    public static ComponentInitializer getInstance() {
        return INITIALIZER;
    }

    @Override
    public ContainerStage build() {
        ContainerStage container = buildContainer();
        IStageListener containerListener = new StageListener(container);
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

    private ContainerStage buildContainer() {
        KeyboardListener keyboardListener = new KeyboardListener();
        IStageMonitor IStageMonitor = new StageMonitor();
        IHitCountService hitCountService = new HitCountService();
        ScoreMonitorHelper.inject(scoreService);
        ScoreMonitorHelper.inject(hitCountService);

        return new TetrisStage(keyboardListener, IStageMonitor, scoreService, hitCountService);
    }
}
