package cube.configs;

import java.util.Map;

/**
 * @author wenyu
 * @since 10/21/15
 */
public interface Config {

    void load(Map<String, Object> override);
}
