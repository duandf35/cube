package cube.stages;

import cube.configs.StageConfig;
import cube.listeners.KeyboardListener;
import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Score;
import cube.monitors.Monitor;

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
    private Monitor monitor;
    private JLabel scoreDisplay;

    private KeyboardListener keyboardListener;
    private Integer xBoundary, yBoundary;

    public MainStage(KeyboardListener keyboardListener, Monitor monitor) {
        config = StageConfig.getInstance();

        this.monitor = monitor;
        this.keyboardListener = keyboardListener;

        xBoundary = config.getXBoundary();
        yBoundary = config.getYBoundary();

        addKeyListener(keyboardListener);
        initStage();
    }

    @Override
    public void paint(Graphics g) {

        // Clear the trail
        super.paint(g);

        if (tetris != null) {
            tetris.paint(g);
        }

        monitor.refresh(g);
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
        return monitor.getCubes();
    }

    @Override
    public void digestTetris() {
        tetris.digest();
        tetris.getCubes().stream().forEach(c -> monitor.add(c));
        tetris = null;
    }

    @Override
    public void updateScore() {
        String score = String.valueOf(monitor.getScore());
        scoreDisplay.setText("Score: " + score);
    }

    @Override
    public Integer getScore() {
        return monitor.getScore();
    }

    @Override
    public List<Score> getAllScores() {
        return monitor.getAllScores();
    }

    private void initStage() {
        // The size of JPanel will be 0,0 until it is displayed because the components and layout are not calculated beforehand.
        // See also: http://stackoverflow.com/questions/12010587/how-to-get-real-jpanel-size
        setPreferredSize(new Dimension(config.getWidth(), config.getHeight()));

        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(config.getBackground());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scoreDisplay = new JLabel();
        scoreDisplay.setForeground(Color.GREEN);
        add(scoreDisplay);
    }
}
