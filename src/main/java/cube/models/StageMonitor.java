package cube.models;

import cube.configs.StageConfig;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class StageMonitor implements Monitor {
    private StageConfig config;
    private Map<Position, ICube> cubes;
    // Matrix to trace each position
    private HashMap<Integer, ArrayList<Position>> monitor;

    public StageMonitor() {
        config = StageConfig.getInstance();
        cubes = new HashMap<>();
        monitor = new HashMap<>();
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
    public void refresh(Graphics g) {
        monitor.entrySet().stream().forEach(line -> {
            if (config.getXMonitorSize() == line.getValue().size()) {
                line.getValue().stream().forEach(p -> remove(p));
                line.getValue().clear();
            }
        });

        cubes.entrySet().stream().forEach(e -> {
            e.getValue().paint(g);
        });
    }

    private void remove(Position position) {
        ICube cube = cubes.get(position);

        cube.dispose();
        cubes.remove(position);
        cube = null;
    }
}
