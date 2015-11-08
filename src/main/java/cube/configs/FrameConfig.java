package cube.configs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class FrameConfig implements Config {

    // Config names
    private static final String WIDTH = "WIDTH";
    private static final String HEIGHT = "HEIGHT";
    private static final String DELAY  = "DELAY";
    private static final String TITLE = "TITLE";

    // Default settings
    private static final Integer DEF_W  = 600;
    private static final Integer DEF_H  = 600;
    private static final Integer DEF_DELAY  = 100;
    private static final String  DEF_TITLE  = "CUBE MAIN CONSOLE";

    private static final Map<String, Object> CONF_MAP = new HashMap<>();

    private final static FrameConfig CONF = new FrameConfig();

    private FrameConfig() {

    }

    public static FrameConfig getInstance() {
        CONF_MAP.put(WIDTH, DEF_W);
        CONF_MAP.put(HEIGHT, DEF_H);
        CONF_MAP.put(DELAY, DEF_DELAY);
        CONF_MAP.put(TITLE, DEF_TITLE);

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

        if (override.get(DELAY) instanceof Integer) {
            CONF_MAP.replace(DELAY, override.get(DELAY));
        }

        if (override.get(TITLE) instanceof String) {
            CONF_MAP.replace(TITLE, override.get(TITLE));
        }
    }

    public Integer getWidth() {
        return (Integer) CONF_MAP.get(WIDTH);
    }

    public Integer getHeight() {
        return (Integer) CONF_MAP.get(HEIGHT);
    }

    public Integer getDelay() {
        return (Integer) CONF_MAP.get(DELAY);
    }

    public String getTitle() {
        return (String) CONF_MAP.get(TITLE);
    }
}
