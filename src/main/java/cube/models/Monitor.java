package cube.models;

/**
 * @author wenyu
 * @since 11/7/15
 */
public interface Monitor {

    boolean isErasable();

    boolean isErasable(Integer line);

    void eraseLine();

    void moveLine();
}
