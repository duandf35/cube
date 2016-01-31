package cube.aop;

/**
 * @author wenyu
 * @since 11/17/15
 */
public abstract class TraceUtils {
    public static final String POSITION_LOGGER = "PositionLogger";
    public static final String ACTION_LOGGER = "ActionLogger";
    public static final String PERFORMANCE_LOGGER = "PerformanceLogger";

    /**
     * Tetris actions.
     */
    public enum Action {

        /**
         * Tetris moving.
         */
        MOVING,

        /**
         * Tetris rotating.
         */
        ROTATING,

        /**
         * Tetris digesting.
         */
        DIGESTING,

        /**
         * Tetris erasing.
         */
        ERASING
    }

    /**
     * Game statuses.
     */
    public enum Status {

        /**
         * Game start.
         */
        GAME_START,

        /**
         * Game over.
         */
        GAME_OVER,

        /**
         * Show game records.
         */
        SHOW_RECORDS
    }

    /**
     * Score operations.
     */
    public enum ScoreOperation {
        /**
         * Update score.
         */
        UPDATE,

        /**
         * Save score to database.
         */
        SAVE
    }
}

