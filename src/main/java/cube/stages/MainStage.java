package cube.stages;

import cube.configs.StageConfig;
import cube.listeners.KeyboardListener;
import cube.models.Cube;

import java.awt.*;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class MainStage extends Stage {
    private final StageConfig config;

    private Cube cube;
    private KeyboardListener keyboardListener;
    private Integer xBoundary, yBoundary;

    public MainStage(KeyboardListener keyboardListener) {
        config = StageConfig.getInstance();

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
        this.cube = cube;
    }

    @Override
    public void digestCube(Cube cube) {
        cube = null;
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
