package cube.models;

/**
 * @author wenyu
 * @since 11/7/15
 */
public interface Command {
    public static final Integer DO_ROTATE = 1;

    Integer[] getPositionChange();

    Integer getDoRotateFlag();
}
