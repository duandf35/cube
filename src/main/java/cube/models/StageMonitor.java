package cube.models;

import cube.configs.StageConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class StageMonitor implements Monitor {
    private StageConfig config;
    private Map<Position, ICube> cubes;

    public StageMonitor() {
        config = StageConfig.getInstance();
        cubes = new HashMap<>();
    }

    @Override
    public Map<Position, ICube> getCubes() {
        return cubes;
    }

    @Override
    public void add(ICube cube) {
        cubes.put(cube.getPosition(), cube);
    }

    @Override
    public void remove(Position position) {
        ICube cube = cubes.get(position);

        if (null != cube) {
            cube.dispose();
            cubes.remove(position);
            cube = null;
        }
    }

    @Override
    public boolean isErasable(Integer line) {
        return false;
    }

    @Override
    public void erase(Integer line) {

    }

    @Override
    public void refresh() {

    }
}
