package cube.stages;

import cube.aop.TraceUtils;
import cube.aop.control.GameStatus;
import cube.configs.StageConfig;
import cube.listeners.Listener;

import javax.swing.*;

/**
 * @author Wenyu
 * @since 1/3/16
 */
public class GameControlStage extends SubStage {
    private final StageConfig config;
    private Listener stageListener;

    public GameControlStage(final Listener stageListener) {
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

    @GameStatus(status = TraceUtils.Status.GAME_START)
    private void start() {
        stageListener.activate();
    }

    private void records() {

    }
}
