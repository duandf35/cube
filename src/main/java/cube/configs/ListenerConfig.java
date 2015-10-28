package cube.configs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/25/15
 */
public class ListenerConfig implements Config {

    private static final String X_UPDATE = "X_UPDATE";
    private static final String Y_UPDATE = "Y_UPDATE";

    private static final Map<String, Object> CONF_MAP = new HashMap<>();

    private static final ListenerConfig CONF = new ListenerConfig();

    private ListenerConfig() {

    }

    public static ListenerConfig getInstance() {
        CubeConfig cubeConfig = CubeConfig.getInstance();

        CONF_MAP.put(X_UPDATE, cubeConfig.getWidth());
        CONF_MAP.put(Y_UPDATE, cubeConfig.getHeight());

        return CONF;
    }

    public void load(Map<String, Object> override) {
        if (override.get(X_UPDATE) instanceof Integer) {
            CONF_MAP.replace(X_UPDATE, override.get(X_UPDATE));
        }

        if (override.get(Y_UPDATE) instanceof Integer) {
            CONF_MAP.replace(Y_UPDATE, override.get(Y_UPDATE));
        }
    }

    public Integer getUpdateX() {
        return (Integer) CONF_MAP.get(X_UPDATE);
    }

    public Integer getUpdateY() {
        return (Integer) CONF_MAP.get(Y_UPDATE);
    }
}
