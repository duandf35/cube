package cube.configs;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class StageConfig implements Config {

    private static final String WIDTH  = "WIDTH";
    private static final String HEIGHT = "HEIGHT";
    private static final String BACKGROUND = "BACKGROUND";
    private static final String X_BOUNDARY = "X_BOUNDARY";
    private static final String Y_BOUNDARY = "Y_BOUNDARY";
    private static final String X_MONITOR_SIZE = "X_MONITOR_SIZE";
    private static final String Y_MONITOR_SIZE = "Y_MONITOR_SIZE";

    private static final Integer DEF_W  = 600;
    private static final Integer DEF_H  = 600;
    private static final Color   DEF_BG  = new Color(7, 6, 8);

    private static final Map<String, Object> CONF_MAP = new HashMap<>();

    private final static StageConfig CONF = new StageConfig();

    private StageConfig() {

    }

    public static StageConfig getInstance() {
        CubeConfig cubeConfig = CubeConfig.getInstance();

        CONF_MAP.put(WIDTH, DEF_W);
        CONF_MAP.put(HEIGHT, DEF_H);
        CONF_MAP.put(BACKGROUND, DEF_BG);

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

        if (override.get(BACKGROUND) instanceof Color) {
            CONF_MAP.replace(BACKGROUND, override.get(BACKGROUND));
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

    public Color getBackground() {
        return (Color) CONF_MAP.get(BACKGROUND);
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
