package cube.stages;

import cube.configs.StageConfig;
import cube.listeners.KeyboardListener;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class MainStage extends Stage {
    private final StageConfig config;

    /**
     * Current active tetris which will response keyboard action
     */
    private ITetris tetris;

    /**
     * All digested tetris in the stage
     */
    private Map<Position, ICube> cubes;

    private KeyboardListener keyboardListener;
    private Integer xBoundary, yBoundary;

    public MainStage(KeyboardListener keyboardListener) {
        config = StageConfig.getInstance();

        this.keyboardListener = keyboardListener;

        xBoundary = config.getXBoundary();
        yBoundary = config.getYBoundary();

        cubes = new HashMap<>();

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
    }

    @Override
    public Integer[] getKeyboardAction() {
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
    public Map<Position, ICube> getCubes() {
        return cubes;
    }

    @Override
    public void digestTetris() {
        tetris.digest();

        for(ICube c: tetris.getCubes()) {
            cubes.put(c.getPosition(), c);
        }
    }

    @Override
    public void refresh() {

    }

    private void initStage() {
        // The size of JPanel will be 0,0 until it is displayed because the components and layout are not calculated beforehand.
        // See also: http://stackoverflow.com/questions/12010587/how-to-get-real-jpanel-size
        setPreferredSize(new Dimension(config.getWidth(), config.getHeight()));

        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(config.getBackground());
    }
}
