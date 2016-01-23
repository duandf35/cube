package cube.services;

import cube.models.HitCount;
import cube.models.Score;

/**
 * @author Wenyu
 * @since 1/23/16
 */
public interface IScoreService extends RecordService<Score>, DBIOService<Score>, PlayerService {

    /**
     * Update score by hit count.
     * @param hitCount the hit count
     */
    void update(HitCount hitCount);
}
