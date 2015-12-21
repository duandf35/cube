package cube.configs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/25/15
 */
public class ListenerConfig implements Config {

    private static final String GRAVITY = "GRAVITY";
    private static final String GRAVITY_APPLY_DELAY = "GRAVITY_APPLY_DELAY";
    private static final String GRAVITY_APPLY_PERIOD = "GRAVITY_APPLY_PERIOD";
    private static final String DIGEST_DELAY = "DIGEST_DELAY";

    private static final String X_UPDATE = "X_UPDATE";
    private static final String Y_UPDATE = "Y_UPDATE";

    private static final Integer DEF_GRAVITY_APP_PER = 1000;
    private static final Integer DEF_GRAVITY_APP_DEL = 1000;
    private static final Integer DEF_DIGEST_DEL = 1000;

    private static final Map<String, Object> CONF_MAP = new HashMap<>();

    private static final ListenerConfig CONF = new ListenerConfig();

    private ListenerConfig() {

    }

    public static ListenerConfig getInstance() {
        CubeConfig cubeConfig = CubeConfig.getInstance();

        CONF_MAP.put(GRAVITY, cubeConfig.getHeight());
        CONF_MAP.put(GRAVITY_APPLY_DELAY, DEF_GRAVITY_APP_DEL);
        CONF_MAP.put(GRAVITY_APPLY_PERIOD, DEF_GRAVITY_APP_PER);
        CONF_MAP.put(DIGEST_DELAY, DEF_GRAVITY_APP_DEL);

        CONF_MAP.put(X_UPDATE, cubeConfig.getWidth());
        CONF_MAP.put(Y_UPDATE, cubeConfig.getHeight());

        return CONF;
    }

    @Override
    public void load(Map<String, Object> override) {
        if (override.get(GRAVITY) instanceof Integer) {
            CONF_MAP.replace(GRAVITY, override.get(GRAVITY));
        }

        if (override.get(GRAVITY_APPLY_DELAY) instanceof Integer) {
            CONF_MAP.replace(GRAVITY_APPLY_DELAY, override.get(GRAVITY_APPLY_DELAY));
        }

        if (override.get(GRAVITY_APPLY_PERIOD) instanceof Integer) {
            CONF_MAP.replace(GRAVITY_APPLY_PERIOD, override.get(GRAVITY_APPLY_PERIOD));
        }

        if (override.get(DIGEST_DELAY) instanceof Integer) {
            CONF_MAP.replace(DIGEST_DELAY, override.get(DIGEST_DELAY));
        }

        if (override.get(X_UPDATE) instanceof Integer) {
            CONF_MAP.replace(X_UPDATE, override.get(X_UPDATE));
        }

        if (override.get(Y_UPDATE) instanceof Integer) {
            CONF_MAP.replace(Y_UPDATE, override.get(Y_UPDATE));
        }
    }

    public Integer getGravity() {
        return (Integer) CONF_MAP.get(GRAVITY);
    }

    public Integer getGravityApplyDelay() {
        return (Integer) CONF_MAP.get(GRAVITY_APPLY_DELAY);
    }

    public Integer getGravityApplyPeriod() {
        return (Integer) CONF_MAP.get(GRAVITY_APPLY_PERIOD);
    }

    public Integer getDigestDelay() {
        return (Integer) CONF_MAP.get(DIGEST_DELAY);
    }

    public Integer getUpdateX() {
        return (Integer) CONF_MAP.get(X_UPDATE);
    }

    public Integer getUpdateY() {
        return (Integer) CONF_MAP.get(Y_UPDATE);
    }
}
