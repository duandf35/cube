package cube.services.factories;

import com.google.common.base.Preconditions;
import cube.aop.score.ScoreMonitorHelper;
import cube.aop.trace.TracePerformance;
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
        registerContainerListener(container);

        SubStage controlStage = new GameControlStage();
        SubStage[] subStages = new SubStage[] { controlStage };

        container.add(controlStage);

        ComponentManager.getInstance()
                        .register(container)
                        .register(subStages)
                        .register(ScoreRecordStageManager.getInstance()
                                                         .register(scoreService));

        return container;
    }

    @TracePerformance
    private ContainerStage buildContainer() {
        KeyboardListener keyboardListener = new KeyboardListener();
        IStageMonitor stageMonitor = new StageMonitor();
        IHitCountService hitCountService = new HitCountService();
        ScoreMonitorHelper.inject(scoreService);
        ScoreMonitorHelper.inject(hitCountService);

        ContainerStage containerStage = new TetrisStage(keyboardListener, stageMonitor, scoreService, hitCountService);
        containerStage.registerTimer();

        return containerStage;
    }

    private void registerContainerListener(final ContainerStage container) {
        Preconditions.checkNotNull(container, "container must not be null.");

        IStageListener containerListener = new StageListener(container);
        containerListener.registerTimer();
    }
}
