 package cube.models;

import cube.aop.trace.TraceAction;
import cube.aop.trace.TracePosition;
import cube.aop.trace.TraceUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author wenyu
 * @since 11/3/15
 */
public class Tetris implements ITetris {
    private String id = UUID.randomUUID().toString().replace("-", "");

    private boolean digested;
    private TetrisType type;

    private ICube center;
    private List<ICube> cubes, rim;

    private Rotator rotator;

    public Tetris(ICube center, List<ICube> rim, Rotator rotator, TetrisType type) {
        digested = false;

        this.center = center;
        this.rim = rim;
        this.rotator = rotator;
        this.type = type;

        cubes = new ArrayList<>();

        if (null != center) {
            cubes.add(center);
        }

        cubes.addAll(rim);
    }

    @Override
    public TetrisType getType() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<ICube> getCubes() {
        return cubes;
    }

    @Override
    public List<ICube> getRim() {
        return rim;
    }

    @Override
    public ICube getCenter() {
        return center;
    }

    @Override
    public List<Position> getPositions() {
        return cubes.stream().map(ICube::getPosition).collect(Collectors.toList());
    }

    @Override
    @TraceAction(action = TraceUtils.Actions.DIGESTING)
    public void digest() {
        digested = true;
    }

    @Override
    public boolean isDigested() {
        return digested;
    }

    @Override
    @TracePosition(action = TraceUtils.Actions.MOVING)
    public void moveX(Command command) {
        cubes.stream().forEach(cube -> {
            cube.moveX(command.moveX());
        });
    }

    @Override
    @TracePosition(action = TraceUtils.Actions.MOVING)
    public void moveY(Command command) {
        cubes.stream().forEach(cube -> {
            cube.moveY(command.moveY());
        });
    }

    @Override
    @TracePosition(action = TraceUtils.Actions.ROTATING)
    public void rotate() {
        rotator.rotate();
    }

    @Override
    public Position getNextMovePosition(Command command, ICube cube) {
        Position p = new Position(cube.getPosition());

        p.moveX(command.moveX());
        p.moveY(command.moveY());

        return p;
    }

    @Override
    public Position getNextRotatePosition(ICube cube) {
        Position p = new Position(cube.getPosition());
        rotator.rotate(p);

        return p;
    }

    @Override
    public void paint(Graphics g) {
        cubes.stream().forEach(c -> c.paint(g));
    }

    @Override
    public void setColor(Color color) {
        cubes.stream().forEach(c -> c.setColor(color));
    }

    @Override
    public String toString() {
        String info =
                getPositions().stream()
                              .map(p -> "x = " + p.getX() + ", y = " + p.getY())
                              .collect(Collectors.joining("\n"));

        return "Tetris " + getId() + " position:\n" + info;
    }
}
