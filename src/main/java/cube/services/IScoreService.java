package cube.services;

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
    void update(Long hitCount);

    /**
     * Put score message into queue.
     * @param message the message
     */
    void putMessage(String message);

    /**
     * Get score message from queue.
     * @return the message
     */
    String getMessage();
}
