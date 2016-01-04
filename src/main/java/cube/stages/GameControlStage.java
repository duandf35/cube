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
public class GameControlStage extends ControlStage {
    private final StageConfig config;
    private Listener tetrisActionListener;

    public GameControlStage(final Listener tetrisActionListener) {
        this.tetrisActionListener = tetrisActionListener;

        config = StageConfig.getInstance();

        initStage();
    }

    private void initStage() {
        setBackground(config.getBackgroundColor());
        setupButtons();
    }

    private void setupButtons() {
        JButton start = new JButton("Start");
        JButton records = new JButton("Records");

        start.addActionListener(e -> start());
        records.addActionListener(e -> records());

        add(start);
        add(records);
    }

    @GameStatus(status = TraceUtils.Status.GAME_START)
    private void start() {
        tetrisActionListener.activate();
    }

    private void records() {

    }
}
