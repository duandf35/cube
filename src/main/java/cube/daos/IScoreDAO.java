package cube.daos;

import cube.models.Score;

import java.util.List;

/**
 * @author wenyu
 * @since 12/23/15
 */
public interface IScoreDAO {

    /**
     * Save score to database.
     * @param score the score
     */
    void save(final Score score);

    /**
     * Get all score records.
     * @return all score records
     */
    List<Score> getAll();

    /**
     * Get the best score record.
     * @return the best score record
     */
    Score getBest();

}
