package cube.models;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class TetrisCommand implements Command {
    private static final Integer DO_ROTATE = 1;

    private Integer[] d;
    private Integer r;

    public TetrisCommand(Integer[] d, Integer r) {
        this.d = d;
        this.r = r;
    }

    @Override
    public Integer[] doMove() {
        return d;
    }

    @Override
    public boolean doRotate() {
        return (DO_ROTATE.equals(r));
    }
}
