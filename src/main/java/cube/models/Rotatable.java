package cube.models;

import java.util.List;

/**
 * @author wenyu
 * @since 11/7/15
 */
public interface Rotatable {

    /**
     * Set rim cubes whose position shall be changed during rotating.
     * @param rim the cubes in the rim
     */
    void setRim(List<ICube> rim);

    /**
     * Set center cube.
     * @param center the center cube
     */
    void setCenter(ICube center);

    /**
     * Apply rotating action for all cubes of tetris.
     */
    void rotate();

    /**
     * Apply rotating action for one cube of tetris.
     * @param p the start position
     */
    void rotate(Position p);
}
