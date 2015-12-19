package cube.models;

import com.google.common.base.Preconditions;

/**
 * Define the name of tetris.
 * O - 0, S - 1, Z - 2, L - 3, J - 4, T - 5, I - 6
 *
 * @author wenyu
 * @since 12/19/15
 */
public enum TetrisType {

    O(0, "O"),

    S(1, "S"),

    Z(2, "Z"),

    L(3, "L"),

    J(4, "J"),

    T(5, "T"),

    I(6, "I");

    private int id;
    private String name;

    TetrisType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Return id of tetris.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Return name of tetris.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Look up tetris type based off of id.
     * @param id the id
     * @return the matched tetris type
     */
    public static TetrisType fromId(int id) {
        TetrisType matchedType = null;

        for (TetrisType type : TetrisType.values()) {
            if (id == type.getId()) {
                matchedType = type;
                break;
            }
        }

        return matchedType;
    }

    /**
     * Look up tetris type based off of name.
     * @param name the name
     * @return the matched tetris type
     */
    public static TetrisType fromName(String name) {
        Preconditions.checkNotNull(name, "Tetris name must not be null.");

        TetrisType matchedType = null;

        for (TetrisType type : TetrisType.values()) {
            if (name.equals(type.getName())) {
                matchedType = type;
                break;
            }
        }

        return matchedType;
    }
}
