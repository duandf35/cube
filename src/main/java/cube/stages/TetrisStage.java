package cube.stages;

import com.google.common.base.Preconditions;
import cube.configs.StageConfig;
import cube.listeners.KeyboardListener;
import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Score;
import cube.monitors.Monitor;
import cube.services.RecordService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class MainStage extends Stage {
    private final StageConfig config;

    /**
     * Current active tetris which will response keyboard action.
     */
    private ITetris tetris;
    private Monitor stageMonitor;
    private JLabel scoreDisplay, bestScoreDisplay;
    private ControlStage gameControlStage;
    private KeyboardListener keyboardListener;
    private RecordService<Score> scoreService;

    private Integer xBoundary, yBoundary;

    public MainStage(KeyboardListener keyboardListener, Monitor stageMonitor, RecordService<Score> scoreService) {
        this.stageMonitor = stageMonitor;
        this.keyboardListener = keyboardListener;
        this.scoreService = scoreService;

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
        updateScore();
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
    public long getScore() {
        return scoreService.get().getValue();
    }

    @Override
    public List<Score> getAllScores() {
        return scoreService.getAll();
    }

    @Override
    public void updateScore() {
        scoreDisplay.setText("Score: " + getScore());
    }

    @Override
    public long getBestScore() {
        return scoreService.getBest().getValue();
    }

    @Override
    public void registerControlStage(final ControlStage gameControlStage) {
        this.gameControlStage = gameControlStage;
        add(gameControlStage);
    }

    @Override
    public void setControlStage() {
        Preconditions.checkNotNull(gameControlStage, "gameControlStage has not been registered!");

        add(gameControlStage);
        SwingUtilities.getWindowAncestor(this).revalidate();
        SwingUtilities.getWindowAncestor(this).repaint();
    }

    @Override
    public void unsetControlStage() {
        Preconditions.checkNotNull(gameControlStage, "gameControlStage has already been removed!");

        remove(gameControlStage);
        SwingUtilities.getWindowAncestor(this).revalidate();
        SwingUtilities.getWindowAncestor(this).repaint();
    }

    @Override
    public void reset() {
        stageMonitor.reset();
        updateBestScore();
    }

    private void updateBestScore() {
        bestScoreDisplay.setText("Best: " + getBestScore());
    }

    private void initStage() {
        // The size of JPanel will be 0,0 until it is displayed because the components and layout are not calculated beforehand.
        // See also: http://stackoverflow.com/questions/12010587/how-to-get-real-jpanel-size
        setPreferredSize(new Dimension(config.getWidth(), config.getHeight()));

        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(config.getBackgroundColor());
        setLayout(new GridLayout(10, 1));

        initScoreDisplays();
    }

    private void initScoreDisplays() {
        JPanel displayPanel = new JPanel();

        scoreDisplay = new JLabel();
        scoreDisplay.setForeground(config.getScoreDisplayColor());
        displayPanel.add(scoreDisplay);

        bestScoreDisplay = new JLabel();
        bestScoreDisplay.setForeground(config.getScoreDisplayColor());
        updateBestScore();
        displayPanel.add(bestScoreDisplay);

        displayPanel.setBackground(config.getBackgroundColor());
//        setLayer(displayPanel, JLayeredPane.DRAG_LAYER);
        add(displayPanel);
    }
}
