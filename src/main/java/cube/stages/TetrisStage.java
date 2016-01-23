package cube.stages;

import cube.configs.StageConfig;
import cube.listeners.KeyboardListener;
import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.monitors.Monitor;
import cube.services.IHitCountService;
import cube.services.IScoreService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class TetrisStage extends Stage {
    private final StageConfig config;

    /**
     * Current active tetris which will response keyboard action.
     */
    private ITetris tetris;
    private Monitor stageMonitor;
    private JLabel scoreDisplay, bestScoreDisplay, hitCountDisplay, bestHitCountDisplay;
    private FinalScoreDialog finalScore;
    private KeyboardListener keyboardListener;
    private IScoreService scoreService;
    private IHitCountService hitCountService;

    private Integer xBoundary, yBoundary;

    public TetrisStage(KeyboardListener keyboardListener, Monitor stageMonitor, IScoreService scoreService, IHitCountService hitCountService) {
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
        updateScore();
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

        finalScore.setScoreAndPlayer(scoreService.get().getValue(), scoreService.getPlayer());
        finalScore.setVisible(true);
    }

    @Override
    public void hideFinalScore() {
        if (null != finalScore) {
            finalScore.setVisible(false);
        }
    }

    private void updateScore() {
        scoreDisplay.setText("Score: " + scoreService.get().getValue());
    }

    private void updateBestScore() {
        bestScoreDisplay.setText("Best: " + scoreService.getBest().getValue());
    }

    private void updateHitCount() {
        hitCountDisplay.setText("Hit: " + hitCountService.get());
        bestHitCountDisplay.setText("Best hit: " + hitCountService.getBest());
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

    // TODO: Extract out ?
    private void initDisplay() {
        JPanel scoreAndPlayerDisplay = new JPanel();
        scoreAndPlayerDisplay.setBackground(config.getBackgroundColor());
        add(scoreAndPlayerDisplay);

        initScoreDisplay(scoreAndPlayerDisplay);
        initPlayerDisplay(scoreAndPlayerDisplay);
    }

    // TODO: How put scores display on the top of Graphics. Note: JLayeredPanel doesn't work.
    private void initScoreDisplay(final JPanel scoreAndPlayerDisplay) {
        scoreDisplay = new JLabel();
        scoreDisplay.setForeground(config.getScoreDisplayColor());
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
