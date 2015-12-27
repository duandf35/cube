package cube.services;

import java.util.List;

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
     * Get current record.
     * @return the record
     */
    T get();

    /**
     * Get all records.
     */
    List<T> getAll();

    /**
     * Get the best record.
     * @return the best record
     */
    T getBest();

    /**
     * Save record.
     */
    void save();
}
