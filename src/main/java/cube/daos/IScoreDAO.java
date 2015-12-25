package cube.daos;

import cube.models.Score;

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
}
