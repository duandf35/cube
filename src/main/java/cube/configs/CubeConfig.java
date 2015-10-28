package cube.configs;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class CubeConfig implements Config {

    // Config names
    private static final String WIDTH  = "WIDTH";
    private static final String HEIGHT = "HEIGHT";
    private static final String COLOR  = "COLOR";
    private static final String STROKE_WIDTH = "STROKE_WIDTH";

    // Default settings
    private static final Integer DEF_W  = 40;
    private static final Integer DEF_H  = 40;
    private static final Color   DEF_COLOR = new Color(230, 154, 221);
    private static final Float   DEF_STROKE = 1f;

    private static final Map<String, Object> CONF_MAP = new HashMap<>();

    private final static CubeConfig CONF = new CubeConfig();

    private CubeConfig() {

    }

    public static CubeConfig getInstance() {
        CONF_MAP.put(WIDTH, DEF_W);
        CONF_MAP.put(HEIGHT, DEF_H);
        CONF_MAP.put(COLOR, DEF_COLOR);
        CONF_MAP.put(STROKE_WIDTH, DEF_STROKE);

        return CONF;
    }

    public void load(Map<String, Object> override) {
        if (override.get(WIDTH) instanceof Integer) {
            CONF_MAP.replace(WIDTH, override.get(WIDTH));
        }

        if (override.get(HEIGHT) instanceof Integer) {
            CONF_MAP.replace(HEIGHT, override.get(HEIGHT));
        }

        if (override.get(COLOR) instanceof Color) {
            CONF_MAP.replace(COLOR, override.get(COLOR));
        }

        if (override.get(STROKE_WIDTH) instanceof Float) {
            CONF_MAP.replace(STROKE_WIDTH, override.get(STROKE_WIDTH));
        }
    }

    public Integer getWidth() {
        return (Integer) CONF_MAP.get(WIDTH);
    }

    public Integer getHeight() {
        return (Integer) CONF_MAP.get(HEIGHT);
    }

    public Color getColor() {
        return (Color) CONF_MAP.get(COLOR);
    }

    public Float getStrokeWidth() {
        return (Float) CONF_MAP.get(STROKE_WIDTH);
    }
}
