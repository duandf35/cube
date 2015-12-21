package cube.monitors;

import cube.aop.TraceAction;
import cube.aop.TraceUtils;
import cube.configs.StageConfig;
import cube.models.ICube;
import cube.models.Position;
import cube.models.Score;
import cube.services.RecordService;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class StageMonitor implements Monitor {
    private StageConfig config;

    private Map<Position, ICube> cubes;
    // Matrix to trace each position
    private HashMap<Integer, List<Position>> monitor;

    private RecordService<Score> scoreService;

    public StageMonitor(RecordService<Score> scoreService) {
        config = StageConfig.getInstance();
        cubes = new HashMap<>();
        monitor = new HashMap<>();

        this.scoreService = scoreService;
    }

    @Override
    public Map<Position, ICube> getCubes() {
        return cubes;
    }

    @Override
    public void add(ICube cube) {
        cubes.put(cube.getPosition(), cube);
        List<Position> line = monitor.get(cube.getPosition().getY());

        if (null == line) {
            line = new ArrayList<>();
            line.add(cube.getPosition());
            monitor.put(cube.getPosition().getY(), line);
        } else {
            line.add(cube.getPosition());
            monitor.replace(cube.getPosition().getY(), line);
        }
    }

    @Override
    public void refresh(Graphics g) {
        monitor.entrySet().stream().forEach(line -> {
            if (config.getXMonitorSize() == line.getValue().size()) {
                removeLine(line);
                scoreService.update();
            }
        });

        cubes.entrySet().stream().forEach(e ->
            e.getValue().paint(g)
        );
    }

    @Override
    public Integer getScore() {
        return scoreService.get().getValue();
    }

    @Override
    public List<Score> getAllScores() {
        return scoreService.getAll();
    }

    @TraceAction(action = TraceUtils.Actions.ERASING)
    private void removeLine(Map.Entry<Integer, List<Position>> line) {
        line.getValue().stream().forEach(p -> remove(p));
        line.getValue().clear();
    }

    private void remove(Position position) {
        ICube cube = cubes.get(position);

        cube.dispose();
        cubes.remove(position);
    }
}
