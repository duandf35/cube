package cube.stages;

import cube.aop.TraceUtils;
import cube.aop.control.ControlStatus;
import cube.configs.StageConfig;
import cube.listeners.IStageListener;

import javax.swing.*;

/**
 * @author Wenyu
 * @since 1/3/16
 */
public class GameControlStage extends SubStage {
    private final StageConfig config;
    private IStageListener stageListener;

    public GameControlStage(final IStageListener stageListener) {
        this.stageListener = stageListener;

        config = StageConfig.getInstance();

        initStage();
    }

    private void initStage() {
        setBackground(config.getBackgroundColor());
        setupButtons();
    }

    private void setupButtons() {
        JButton start = new JButton("Start");
        JButton records = new JButton("Scores");

        start.addActionListener(e -> start());
        records.addActionListener(e -> records());

        add(start);
        add(records);
    }

    @ControlStatus(status = TraceUtils.Status.GAME_START)
    private void start() {
        stageListener.activate();
    }

    @ControlStatus(status = TraceUtils.Status.SHOW_RECORDS)
    private void records() {

    }
}
