package cube.stages;

import cube.configs.StageConfig;
import cube.listeners.KeyboardListener;
import cube.models.Cube;
import cube.models.Position;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class MainStage extends Stage {
    private final StageConfig config;

    /**
     * Current active cube which will response keyboard action
     */
    private Cube cube;

    /**
     * All digested cubes in the stage
     */
    private Map<Position, Cube> cubes;

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

        if (cube != null) {
            cube.paint(g);
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
    public Cube getCube() {
        return cube;
    }

    @Override
    public void setCube(Cube cube) {
        Objects.requireNonNull(cube, "cube must not be null.");

        this.cube = cube;
    }

    @Override
    public void digestCube() {
        cube.digest();
        cubes.put(cube.getPosition(), cube);
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
