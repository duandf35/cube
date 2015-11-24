package cube.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class TetrisCommand implements Command {
    public static final String DO_MOVE_X = "DO_MOVE_X";
    public static final String DO_MOVE_Y = "DO_MOVE_Y";
    public static final String DO_ROTATE = "DO_ROTATE";

    private Map<String, Object> commands;

    public TetrisCommand(Integer dx, Integer dy, boolean dr) {
        commands = new HashMap<>();

        commands.put(DO_MOVE_X, dx);
        commands.put(DO_MOVE_Y, dy);
        commands.put(DO_ROTATE, dr);
    }

    @Override
    public Map<String, Object> get() {
        return commands;
    }
}
