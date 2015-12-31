package cube.configs;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class CubeConfig implements Config {

    /**
     * The width of cube.
     */
    private static final String WIDTH  = "WIDTH";

    /**
     * The height of cube.
     */
    private static final String HEIGHT = "HEIGHT";

    /**
     * The border of cube.
     */
    private static final String BORDER = "BORDER";

    /**
     * The color of rim cube.
     */
    private static final String COLOR  = "COLOR";

    /**
     * The color of cube border.
     */
    private static final String COLOR_BORDER  = "COLOR_BORDER";

    /**
     * The color of center cube.
     */
    private static final String COLOR_CENTER  = "COLOR_CENTER";

    /**
     * The stroke width when drawing cube.
     */
    private static final String STROKE_WIDTH = "STROKE_WIDTH";

    /**
     * The maximum id used to set upper bound of Random.nextInt() when selecting tetris.
     */
    private static final String TETRIS_ID_BOUND = "TETRIS_ID_BOUND";

    /**
     * The random seed.
     */
    private static final String TETRIS_ROLLING_SEED = "TETRIS_ROLLING_SEED";

    private static final Integer DEF_W  = 50;
    private static final Integer DEF_H  = 50;
    private static final Integer DEF_B  = 5;
    private static final Color   DEF_COLOR = new Color(230, 154, 221);
    private static final Color   DEF_COLOR_B = new Color(247, 237, 250);
    private static final Color   DEF_COLOR_C = new Color(233, 216, 90);
    private static final Float   DEF_STROKE = 3f;
    private static final Integer DEF_TID_BOUND = 7;
    private static final Integer DEF_TROLLING_SEED = 23;

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
        CONF_MAP.put(COLOR_CENTER, DEF_COLOR_C);
        CONF_MAP.put(STROKE_WIDTH, DEF_STROKE);
        CONF_MAP.put(TETRIS_ID_BOUND, DEF_TID_BOUND);
        CONF_MAP.put(TETRIS_ROLLING_SEED, DEF_TROLLING_SEED);

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

        if (override.get(BORDER) instanceof Integer) {
            CONF_MAP.replace(BORDER, override.get(BORDER));
        }

        if (override.get(COLOR) instanceof Color) {
            CONF_MAP.replace(COLOR, override.get(COLOR));
        }

        if (override.get(COLOR_BORDER) instanceof Color) {
            CONF_MAP.replace(COLOR_BORDER, override.get(COLOR_BORDER));
        }

        if (override.get(COLOR_CENTER) instanceof Color) {
            CONF_MAP.replace(COLOR_CENTER, override.get(COLOR_CENTER));
        }

        if (override.get(STROKE_WIDTH) instanceof Float) {
            CONF_MAP.replace(STROKE_WIDTH, override.get(STROKE_WIDTH));
        }

        if (override.get(TETRIS_ID_BOUND) instanceof Integer) {
            CONF_MAP.replace(TETRIS_ID_BOUND, override.get(TETRIS_ID_BOUND));
        }

        if (override.get(TETRIS_ROLLING_SEED) instanceof Integer) {
            CONF_MAP.replace(TETRIS_ROLLING_SEED, override.get(TETRIS_ROLLING_SEED));
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

    public Color getCenterColor() {
        return (Color) CONF_MAP.get(COLOR_CENTER);
    }

    public Float getStrokeWidth() {
        return (Float) CONF_MAP.get(STROKE_WIDTH);
    }

    public Integer getTetrisIdBound() {
        return (Integer) CONF_MAP.get(TETRIS_ID_BOUND);
    }

    public Integer getTetrisRollingSeed() {
        return (Integer) CONF_MAP.get(TETRIS_ROLLING_SEED);
    }
}
