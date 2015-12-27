package cube.configs;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class StageConfig implements Config {

    /**
     * The actual width of stage.
     */
    private static final String WIDTH  = "WIDTH";

    /**
     * The actual height of stage.
     */
    private static final String HEIGHT = "HEIGHT";

    /**
     * The background color of stage.
     */
    private static final String BACKGROUND_COLOR = "BACKGROUND_COLOR";

    /**
     * The display color of score.
     */
    private static final String SCORE_DISPLAY_COLOR = "SCORE_DISPLAY_COLOR";

    /**
     * The x-axis boundary position.
     * x-boundary = stage.width - cube.width
     */
    private static final String X_BOUNDARY = "X_BOUNDARY";

    /**
     * The y-axis boundary position.
     * y-boundary = stage.height - cube.height
     */
    private static final String Y_BOUNDARY = "Y_BOUNDARY";

    /**
     * The maximum number of cubes one row can contain.
     * x-size = stage.width / cube.width
     */
    private static final String X_MONITOR_SIZE = "X_MONITOR_SIZE";

    /**
     * The maximum number of cubes one column can contain.
     * y-size = stage.height / cube.height
     */
    private static final String Y_MONITOR_SIZE = "Y_MONITOR_SIZE";

    private static final Integer DEF_W  = 600;
    private static final Integer DEF_H  = 600;
    private static final Color   DEF_BG = new Color(7, 6, 8);
    private static final Color   DEF_SG = Color.GREEN;

    private static final Map<String, Object> CONF_MAP = new HashMap<>();

    private final static StageConfig CONF = new StageConfig();

    private StageConfig() {

    }

    public static StageConfig getInstance() {
        CubeConfig cubeConfig = CubeConfig.getInstance();

        CONF_MAP.put(WIDTH, DEF_W);
        CONF_MAP.put(HEIGHT, DEF_H);
        CONF_MAP.put(BACKGROUND_COLOR, DEF_BG);
        CONF_MAP.put(SCORE_DISPLAY_COLOR, DEF_SG);

        CONF_MAP.put(X_BOUNDARY, (Integer) CONF_MAP.get(WIDTH) - cubeConfig.getWidth());
        CONF_MAP.put(Y_BOUNDARY, (Integer) CONF_MAP.get(HEIGHT) - cubeConfig.getHeight());

        CONF_MAP.put(X_MONITOR_SIZE, (Integer) CONF_MAP.get(WIDTH) / cubeConfig.getWidth());
        CONF_MAP.put(Y_MONITOR_SIZE, (Integer) CONF_MAP.get(HEIGHT) / cubeConfig.getHeight());

        return CONF;
    }

    @Override
    public void load(Map<String, Object> override) {
        if (override.get(WIDTH) instanceof Integer) {
            CONF_MAP.replace(WIDTH, override.get(WIDTH));
        }

        if (override.get(HEIGHT) instanceof Integer) {
            CONF_MAP.replace(HEIGHT, override.get(HEIGHT));
        }

        if (override.get(BACKGROUND_COLOR) instanceof Color) {
            CONF_MAP.replace(BACKGROUND_COLOR, override.get(BACKGROUND_COLOR));
        }

        if (override.get(SCORE_DISPLAY_COLOR) instanceof Color) {
            CONF_MAP.replace(SCORE_DISPLAY_COLOR, override.get(SCORE_DISPLAY_COLOR));
        }

        if (override.get(X_BOUNDARY) instanceof Integer) {
            CONF_MAP.replace(X_BOUNDARY, override.get(X_BOUNDARY));
        }

        if (override.get(Y_BOUNDARY) instanceof Integer) {
            CONF_MAP.replace(Y_BOUNDARY, override.get(Y_BOUNDARY));
        }

        if (override.get(X_MONITOR_SIZE) instanceof Integer) {
            CONF_MAP.replace(X_MONITOR_SIZE, override.get(X_MONITOR_SIZE));
        }

        if (override.get(Y_MONITOR_SIZE) instanceof Integer) {
            CONF_MAP.replace(Y_MONITOR_SIZE, override.get(Y_MONITOR_SIZE));
        }
    }

    public Integer getWidth() {
        return (Integer) CONF_MAP.get(WIDTH);
    }

    public Integer getHeight() {
        return (Integer) CONF_MAP.get(HEIGHT);
    }

    public Color getBackgroundColor() {
        return (Color) CONF_MAP.get(BACKGROUND_COLOR);
    }

    public Color getScoreDisplayColor() {
        return (Color) CONF_MAP.get(SCORE_DISPLAY_COLOR);
    }

    public Integer getXBoundary() {
        return (Integer) CONF_MAP.get(X_BOUNDARY);
    }

    public Integer getYBoundary() {
        return (Integer) CONF_MAP.get(Y_BOUNDARY);
    }

    public Integer getXMonitorSize() {
        return (Integer) CONF_MAP.get(X_MONITOR_SIZE);
    }

    public Integer getYMonitorSize() {
        return (Integer) CONF_MAP.get(Y_MONITOR_SIZE);
    }
}
