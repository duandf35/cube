package cube.services;

import java.util.List;

/**
 * @author Wenyu
 * @since 1/23/16
 */
public interface DBIOService<T> {

    /**
     * Save to database.
     */
    void save();

    /**
     * Get all records from database.
     * @return the list of records
     */
    List<T> getAll();

    /**
     * Get records for one player.
     * @param  player the player
     * @return the list of records
     */
    List<T> getByPlayer(String player);
}
