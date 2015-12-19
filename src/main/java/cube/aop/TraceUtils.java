package cube.aop;

/**
 * @author wenyu
 * @since 11/17/15
 */
public abstract class TraceUtils {

    public static final String POSITION_LOGGER = "PositionLogger";
    public static final String ACTION_LOGGER = "ActionLogger";

    public enum Actions {

        /**
         * Tetris moving.
         */
        MOVING("MOVING"),

        /**
         * Tetris rotating.
         */
        ROTATING("ROTATING"),

        /**
         * Tetris digesting.
         */
        DIGESTING("DIGESTING"),

        /**
         * Tetris erasing.
         */
        ERASING("ERASING");

        private String name;

        Actions(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

