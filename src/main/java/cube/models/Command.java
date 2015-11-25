package cube.models;

/**
 * @author wenyu
 * @since 11/7/15
 */
public interface Command {

    Integer moveX();

    Integer moveY();

    boolean rotate();
}
