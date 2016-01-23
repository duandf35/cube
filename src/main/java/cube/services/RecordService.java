package cube.services;

/**
 * Interface for defining recording function.
 *
 * @author wenyu
 * @since 12/20/15
 */
public interface RecordService<T> {

    /**
     * Update record.
     */
    void update();

    /**
     * Update record by a given value.
     * @param v   the value
     * @param <V> the type of the vale
     */
    <V> void update(V v);

    /**
     * Get current record.
     * @return the record
     */
    T get();

    /**
     * Get the best record.
     * @return the best record
     */
    T getBest();

    /**
     * Reset current score.
     */
    void reset();
}
