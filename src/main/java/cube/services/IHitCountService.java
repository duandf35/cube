package cube.services;

/**
 * @author Wenyu
 * @since 1/23/16
 */
public interface IHitCountService extends RecordService<Long> {

    /**
     * Turn on can reset flag.
     */
    void canResetOn();

    /**
     * Turn off can reset flag.
     */
    void canResetOff();

    /**
     * Return can reset flag.
     * @return true if can reset
     */
    boolean canReset();
}
