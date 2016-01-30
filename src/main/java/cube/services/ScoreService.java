package cube.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import cube.daos.IScoreDAO;
import cube.daos.ScoreDAO;
import cube.models.Score;
import cube.monitors.mq.HitCountMessageQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * @author wenyu
 * @since 12/24/15
 */
public class ScoreService implements IScoreService {

    private static final Logger LOG = LogManager.getLogger(ScoreService.class);

    /**
     * Basic unit of score.
     */
    private static final int scoreUnit = 1;

    /**
     * Score cache.
     */
    private long scoreCache;

    /**
     * The Score instance of current game.
     */
    private Score currentScore;

    /**
     * The player name.
     */
    private String player;

    /**
     * DAO.
     */
    private IScoreDAO scoreDAO;

    private HitCountMessageQueue messageQueue;

    public ScoreService() {
        scoreCache = 0;
        currentScore = new Score(scoreCache);
        scoreDAO = new ScoreDAO();
        messageQueue = new HitCountMessageQueue();
    }

    @Override
    public void update() {
        scoreCache += scoreUnit;
        currentScore.setValue(scoreCache);
    }

    @Override
    public void update(final Long hitCount) {
        Preconditions.checkNotNull(hitCount, "hitCount must not be null.");

        if (0L == hitCount) {
            LOG.info("Updating score, current Score = {} + {}", scoreCache, scoreUnit);

            update();
        } else {
            LOG.info("Updating score, current score = {} + {} * {}", scoreCache, hitCount, scoreUnit);
            putMessage(scoreCache + " + " + hitCount + " * " + scoreUnit);

            scoreCache += hitCount * scoreUnit;
            currentScore.setValue(scoreCache);
        }
    }

    @Override
    public Score get() {
        return currentScore;
    }

    @Override
    public Score getBest() {
        return scoreDAO.getBest();
    }

    @Override
    public void reset() {
        scoreCache = 0;
        currentScore.setValue(0L);
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(final String player) {
        this.player = player;
    }

    @Override
    public void save() {
        currentScore.setTimestamp(new Date());
        currentScore.setPlayerName(player);
        scoreDAO.save(currentScore);
    }

    @Override
    public List<Score> getAll() {
        return scoreDAO.getAll();
    }

    @Override
    public List<Score> getByPlayer(final String player) {
        return ImmutableList.of();
    }

    @Override
    public void putMessage(final String message) {
        messageQueue.enqueue(message);
    }

    @Override
    public String getMessage() {
        return "Score: " + (messageQueue.hasNext() ? messageQueue.dequeue() : scoreCache);
    }
}
