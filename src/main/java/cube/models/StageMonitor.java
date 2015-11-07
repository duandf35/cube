package cube.models;

import cube.configs.StageConfig;

import java.util.ArrayList;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class StageMonitor implements Monitor {
    private StageConfig config;
    private ArrayList<ArrayList<Position>> monitor;

    public StageMonitor() {
        config = StageConfig.getInstance();
        monitor = new ArrayList<>(config.getYMonitorSize());
    }

    @Override
    public boolean isErasable() {
        return false;
    }

    @Override
    public boolean isErasable(Integer line) {
        return false;
    }

    @Override
    public void eraseLine() {

    }

    @Override
    public void moveLine() {

    }
}
