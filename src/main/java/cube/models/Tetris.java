 package cube.models;

 import java.awt.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.UUID;
 import java.util.stream.Collectors;

 import cube.aop.TraceUtils;
 import cube.aop.trace.TraceAction;
 import cube.aop.trace.TracePosition;

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

    private Rotator tetrisRotator;

    public Tetris(ICube center, List<ICube> rim, Rotator tetrisRotator, TetrisType type) {
        this.center = center;
        this.rim = rim;
        this.tetrisRotator = tetrisRotator;
        this.type = type;

        digested = false;
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
    @TraceAction(action = TraceUtils.Action.DIGESTING)
    public void digest() {
        digested = true;
    }

    @Override
    public boolean isDigested() {
        return digested;
    }

    @Override
    @TracePosition(action = TraceUtils.Action.MOVING)
    public void moveX(Command command) {
        cubes.forEach(cube -> {
            cube.moveX(command.moveX());
        });
    }

    @Override
    @TracePosition(action = TraceUtils.Action.MOVING)
    public void moveY(Command command) {
        cubes.forEach(cube -> {
            cube.moveY(command.moveY());
        });
    }

    @Override
    @TracePosition(action = TraceUtils.Action.ROTATING)
    public void rotate() {
        tetrisRotator.rotate();
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
        tetrisRotator.rotate(p);

        return p;
    }

    @Override
    public void paint(Graphics g) {
        cubes.forEach(c -> c.paint(g));
    }

    @Override
    public void setColor(Color color) {
        cubes.forEach(c -> c.setColor(color));
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
