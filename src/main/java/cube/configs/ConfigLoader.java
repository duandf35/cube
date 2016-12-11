package cube.configs;

import java.util.HashMap;
import java.util.Map;

import cube.exceptions.ConfigLoaderException;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class ConfigLoader {
    private static final ConfigLoader LOADER = new ConfigLoader();

    private ConfigLoader() {

    }

    public static ConfigLoader getInstance() {
        return LOADER;
    }

    public void load() throws ConfigLoaderException {
        // TODO: load config file
        Map<String, Object> overrides = new HashMap<>();

        FrameConfig.getInstance().load(overrides);
        StageConfig.getInstance().load(overrides);
        CubeConfig.getInstance().load(overrides);
        ListenerConfig.getInstance().load(overrides);
    }
}
