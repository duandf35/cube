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
    private static final String BORDER = "BORDER";
    private static final String COLOR  = "COLOR";
    private static final String COLOR_BORDER  = "COLOR_BORDER";
    private static final String STROKE_WIDTH = "STROKE_WIDTH";

    // Default settings
    private static final Integer DEF_W  = 40;
    private static final Integer DEF_H  = 40;
    private static final Integer DEF_B  = 5;
    private static final Color   DEF_COLOR = new Color(230, 154, 221);
    private static final Color   DEF_COLOR_B = new Color(7, 6, 8);
    private static final Float   DEF_STROKE = 3f;

    private static final Map<String, Object> CONF_MAP = new HashMap<>();

    private final static CubeConfig CONF = new CubeConfig();

    private CubeConfig() {

    }

    public static CubeConfig getInstance() {
        CONF_MAP.put(WIDTH, DEF_W);
        CONF_MAP.put(HEIGHT, DEF_H);
        CONF_MAP.put(BORDER, DEF_B);
        CONF_MAP.put(COLOR, DEF_COLOR);
        CONF_MAP.put(COLOR_BORDER, DEF_COLOR_B);
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

        if (override.get(BORDER) instanceof Integer) {
            CONF_MAP.replace(BORDER, override.get(BORDER));
        }

        if (override.get(COLOR) instanceof Color) {
            CONF_MAP.replace(COLOR, override.get(COLOR));
        }

        if (override.get(COLOR_BORDER) instanceof Color) {
            CONF_MAP.replace(COLOR_BORDER, override.get(COLOR_BORDER));
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

    public Integer getBorder() {
        return (Integer) CONF_MAP.get(BORDER);
    }

    public Color getColor() {
        return (Color) CONF_MAP.get(COLOR);
    }

    public Color getBorderColor() {
        return (Color) CONF_MAP.get(COLOR_BORDER);
    }

    public Float getStrokeWidth() {
        return (Float) CONF_MAP.get(STROKE_WIDTH);
    }
}
