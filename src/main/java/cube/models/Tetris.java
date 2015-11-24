 package cube.models;

import cube.aop.TraceAction;
import cube.aop.TraceUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author wenyu
 * @since 11/3/15
 */
public class Tetris implements ITetris {
    private String id = UUID.randomUUID().toString().replace("-", "");

    private boolean digested;
    private List<ICube> cubes, rim, center;
    private Rotator rotator;

    public Tetris(List<ICube> rim, List<ICube> center, Rotator rotator) {
        digested = false;

        this.rotator = rotator;
        this.rim = rim;
        this.center = center;

        cubes = new ArrayList<>();
        cubes.addAll(rim);
        cubes.addAll(center);

        rotator.setRim(rim);
        rotator.setCenter(center);
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
    public List<ICube> getCenter() {
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
    public void erase(Position position) {

    }

    @Override
    public void move(Command command) {
        Map<String, Object> change = command.get();

        cubes.stream().forEach(cube -> {
            cube.moveX((Integer) change.get(TetrisCommand.DO_MOVE_X));
            cube.moveY((Integer) change.get(TetrisCommand.DO_MOVE_Y));
        });
    }

    @Override
    public void rotate() {
        rotator.rotate();
    }

    @Override
    public Position getNextMovePosition(Command command, ICube cube) {
        Position p = new Position(cube.getPosition());
        Map<String, Object> change = command.get();

        p.moveX((Integer) change.get(TetrisCommand.DO_MOVE_X));
        p.moveY((Integer) change.get(TetrisCommand.DO_MOVE_Y));

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
    public String toString() {
        String info =
                getPositions().stream()
                              .map(p -> "x = " + p.getX() + ", y = " + p.getY())
                              .collect(Collectors.joining("\n"));

        return "Tetris " + getId() + " position:\n" + info;
    }
}
