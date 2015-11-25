package cube.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class TetrisCommand implements Command {
    private static final String DO_MOVE_X = "DO_MOVE_X";
    private static final String DO_MOVE_Y = "DO_MOVE_Y";
    private static final String DO_ROTATE = "DO_ROTATE";

    private Map<String, Object> commands;

    public TetrisCommand(Integer dx, Integer dy, boolean dr) {
        commands = new HashMap<>();

        commands.put(DO_MOVE_X, dx);
        commands.put(DO_MOVE_Y, dy);
        commands.put(DO_ROTATE, dr);
    }

    @Override
    public Integer moveX() {
        return (Integer) commands.get(DO_MOVE_X);
    }

    @Override
    public Integer moveY() {
        return (Integer) commands.get(DO_MOVE_Y);
    }

    @Override
    public boolean rotate() {
        return (Boolean) commands.get(DO_ROTATE);
    }
}
