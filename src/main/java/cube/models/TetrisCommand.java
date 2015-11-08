package cube.models;

/**
 * @author wenyu
 * @since 11/7/15
 */
public class TetrisCommand implements Command {
    private Integer[] d;
    private Integer r;

    public TetrisCommand(Integer[] d, Integer r) {
        this.d = d;
        this.r = r;
    }

    @Override
    public Integer[] getPositionChange() {
        return d;
    }

    @Override
    public Integer getDoRotateFlag() {
        return r;
    }
}
