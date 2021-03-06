package cube.stages;

import javax.swing.*;

import java.awt.*;
import java.util.Map;
import java.util.TimerTask;

import cube.aop.trace.TracePerformance;
import cube.configs.StageConfig;
import cube.listeners.KeyboardListener;
import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.monitors.IStageMonitor;
import cube.monitors.TimerMonitor;
import cube.monitors.timers.TimerTaskBuilder;
import cube.monitors.timers.TimerWrapper;
import cube.services.IHitCountService;
import cube.services.IScoreService;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class TetrisStage extends ContainerStage {
    private final StageConfig config;

    /**
     * Current active tetris which will response keyboard action.
     */
    private ITetris tetris;

    private IStageMonitor stageMonitor;

    private KeyboardListener keyboardListener;

    private IScoreService scoreService;

    private IHitCountService hitCountService;

    private Integer xBoundary, yBoundary;

    private FinalScoreDialog finalScore;

    private JLabel scoreDisplay, bestScoreDisplay, hitCountDisplay, bestHitCountDisplay;

    public TetrisStage(KeyboardListener keyboardListener, IStageMonitor stageMonitor, IScoreService scoreService, IHitCountService hitCountService) {
        this.stageMonitor = stageMonitor;
        this.keyboardListener = keyboardListener;
        this.scoreService = scoreService;
        this.hitCountService = hitCountService;

        config = StageConfig.getInstance();

        xBoundary = config.getXBoundary();
        yBoundary = config.getYBoundary();

        addKeyListener(keyboardListener);

        initStage();
    }

    @Override
    public void paint(Graphics g) {

        // Clear the trail
        super.paint(g);

        if (null != tetris) {
            tetris.paint(g);
        }

        stageMonitor.refresh(g);
        updateHitCount();
    }

    @Override
    public Command getKeyboardAction() {
        return keyboardListener.getKeyboardAction();
    }

    @Override
    public Integer getXBoundary() {
        return xBoundary;
    }

    @Override
    public Integer getYBoundary() {
        return yBoundary;
    }

    @Override
    public ITetris getTetris() {
        return tetris;
    }

    @Override
    public void setTetris(ITetris tetris) {
        this.tetris = tetris;
    }

    @Override
    public Map<Integer, Map<Integer, ICube>> getCubes() {
        return stageMonitor.getCubes();
    }

    @Override
    public synchronized void digestTetris() {
        tetris.digest();
        tetris.getCubes().stream().forEach(c -> stageMonitor.add(c));
        tetris = null;
    }

    @Override
    public void reset() {
        stageMonitor.reset();
        scoreService.reset();
        hitCountService.reset();

        updateBestScore();
    }

    @Override
    public void showFinalScore() {
        if (null == finalScore) {
            finalScore = new FinalScoreDialog();
        }

        finalScore.setScoreAndPlayer(scoreService.get().getValue(), hitCountService.getBest(), scoreService.getPlayer());
        finalScore.setVisible(true);
    }

    @Override
    public void hideFinalScore() {
        if (null != finalScore) {
            finalScore.setVisible(false);
        }
    }

    @Override
    public void registerTimer() {
        TimerTaskBuilder taskBuilder = () ->
                new TimerTask() {
                    @Override
                    public void run() {
                        updateScore();
                    }
                };

        TimerMonitor.getInstance()
                    .register(new TimerWrapper("Score Update Timer",
                                                taskBuilder,
                                                0,
                                                config.getScoreUpdatePeriod()));
    }

    private void updateScore() {
        scoreDisplay.setText(scoreService.getMessage());
    }

    private void updateBestScore() {
        bestScoreDisplay.setText("Best: " + scoreService.getBest().getValue());
    }

    private void updateHitCount() {
        hitCountDisplay.setText("Hit: " + hitCountService.get());
        bestHitCountDisplay.setText("Best Hit: " + hitCountService.getBest());
    }

    private void initStage() {
        // The size of JPanel will be 0,0 until it is displayed because the components and layout are not calculated beforehand.
        // See also: http://stackoverflow.com/questions/12010587/how-to-get-real-jpanel-size
        setPreferredSize(new Dimension(config.getWidth(), config.getHeight()));

        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(config.getBackgroundColor());
        setLayout(new GridLayout(10, 0));

        initDisplay();
    }

    @TracePerformance
    private void initDisplay() {
        JPanel scoreAndPlayerDisplay = new JPanel();
        scoreAndPlayerDisplay.setBackground(config.getBackgroundColor());
        add(scoreAndPlayerDisplay);

        initScoreDisplay(scoreAndPlayerDisplay);
        initPlayerDisplay(scoreAndPlayerDisplay);
    }

    @TracePerformance
    private void initScoreDisplay(final JPanel scoreAndPlayerDisplay) {
        scoreDisplay = new JLabel();
        scoreDisplay.setForeground(config.getScoreDisplayColor());
        updateScore();
        scoreAndPlayerDisplay.add(scoreDisplay);

        bestScoreDisplay = new JLabel();
        bestScoreDisplay.setForeground(config.getScoreDisplayColor());
        updateBestScore();
        scoreAndPlayerDisplay.add(bestScoreDisplay);

        hitCountDisplay = new JLabel();
        hitCountDisplay.setForeground(config.getScoreDisplayColor());
        scoreAndPlayerDisplay.add(hitCountDisplay);

        bestHitCountDisplay = new JLabel();
        bestHitCountDisplay.setForeground(config.getScoreDisplayColor());
        scoreAndPlayerDisplay.add(bestHitCountDisplay);
    }

    @TracePerformance
    private void initPlayerDisplay(final JPanel scoreAndPlayerDisplay) {
        JTextField playerInput = new JTextField("player1");
        JLabel player = new JLabel("Player Name:");

        player.setForeground(config.getScoreDisplayColor());
        playerInput.setBackground(config.getBackgroundColor());
        playerInput.setForeground(config.getScoreDisplayColor());

        // Set default player name
        scoreService.setPlayer(playerInput.getText());
        playerInput.addActionListener(e -> scoreService.setPlayer(playerInput.getText()));

        scoreAndPlayerDisplay.add(player);
        scoreAndPlayerDisplay.add(playerInput);
    }
}
