package cube.monitors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import cube.aop.TraceUtils;
import cube.aop.score.ScoreOperationRequired;
import cube.aop.trace.TraceAction;
import cube.configs.CubeConfig;
import cube.configs.StageConfig;
import cube.models.ICube;
import cube.models.Position;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class StageMonitor implements IStageMonitor {
    private static final Logger LOG = LogManager.getLogger(StageMonitor.class);

    private CubeConfig cubeConfig;
    private StageConfig stageConfig;

    /**
     * Matrix to track each digested cubes in the stage.
     */
    private Map<Integer, Map<Integer, ICube>> cubes;

    public StageMonitor() {
        cubeConfig = CubeConfig.getInstance();
        stageConfig = StageConfig.getInstance();

        // Operate HashMap in nested loop may cause ConcurrentModificationException be thrown
        cubes = new ConcurrentHashMap<>();
    }

    @Override
    public Map<Integer, Map<Integer, ICube>> getCubes() {
        return cubes;
    }

    @Override
    public void add(final ICube cube) {
        Integer x = cube.getPosition().getX();
        Integer y = cube.getPosition().getY();

        Map<Integer, ICube> line = cubes.get(y);

        if (null == line) {
            line = new ConcurrentHashMap<>();
        }

        line.put(x, cube);
        // Don't use HashMap.replace, it puts new value only if old value exists!
        cubes.put(y, line);
    }

    @Override
    public synchronized void refresh(Graphics g) {
        List<Integer> removedLines = new ArrayList<>();

        cubes.entrySet().stream().forEach(lineEntry -> {
            if (stageConfig.getXMonitorSize() == lineEntry.getValue().size()) {
                removedLines.add(lineEntry.getKey());
            }
        });

        if (!removedLines.isEmpty()) {
            relocateLines(removedLines);
        }

        // Repaint cubes
        cubes.entrySet()
             .stream()
             .forEach(lineEntry -> lineEntry.getValue()
                                            .entrySet()
                                            .stream()
                                            .forEach(cubeEntry -> cubeEntry.getValue()
                                                                           .paint(g))
        );
    }

    @Override
    public void reset() {
        cubes.entrySet().stream()
                        .forEach(lineEntry -> {
                            lineEntry.getValue()
                                     .entrySet()
                                     .stream()
                                     .forEach(cubeEntry -> cubeEntry.getValue()
                                                                    .dispose());
            lineEntry.getValue().clear();
        });

        cubes.clear();
    }

    /**
     * Remove lines and relocate rest lines to fill the gaps.
     * @param removedLines the removed lines
     */
    private void relocateLines(List<Integer> removedLines) {
        // Sort rest lines from max to min
        List<Integer> restLines = cubes.keySet()
                                       .stream()
                                       .filter(k -> !removedLines.contains(k))
                                       .sorted((k1, k2) -> Integer.compare(k2, k1))
                                       .collect(Collectors.toList());

        removedLines.stream().forEach(this::removeLine);

        LOG.debug("Relocating lines, removed lines: {}, rest lines: {}.", removedLines, restLines);

        // Reorganize monitor
        int newLineNum = stageConfig.getYBoundary();
        for (Integer oldLineNum : restLines) {
            if (oldLineNum != newLineNum) {
                relocateLine(newLineNum, cubes.get(oldLineNum));

                LOG.debug("Moving line from {} to {}.", oldLineNum, newLineNum);

                cubes.put(newLineNum, cubes.get(oldLineNum));
                cubes.put(oldLineNum, new ConcurrentHashMap<>());
            }

            newLineNum -= cubeConfig.getHeight();
        }

        restLines.clear();
        removedLines.clear();
    }

    private void relocateLine(final Integer newLineNum, final Map<Integer, ICube> line) {
        line.entrySet()
            .stream()
            .forEach(cubeEntry -> cubeEntry.getValue()
                                           .getPosition()
                                           .setY(newLineNum));
    }

    @ScoreOperationRequired(operation = TraceUtils.ScoreOperation.UPDATE)
    @TraceAction(action = TraceUtils.Action.ERASING)
    private void removeLine(final Integer lineNum) {
        cubes.get(lineNum)
             .entrySet()
             .stream()
             .forEach(cubeEntry -> remove(cubeEntry.getValue()
                                                   .getPosition()));
        cubes.get(lineNum).clear();
    }

    private void remove(final Position position) {
        cubes.get(position.getY()).get(position.getX()).dispose();
        cubes.get(position.getY()).remove(position.getX());
    }
}
