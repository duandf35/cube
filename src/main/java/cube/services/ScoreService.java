package cube.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import cube.daos.IScoreDAO;
import cube.daos.ScoreDAO;
import cube.models.HitCount;
import cube.models.Score;
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

    public ScoreService() {
        scoreCache = 0;
        currentScore = new Score(scoreCache);
        scoreDAO = new ScoreDAO();
    }

    @Override
    public void update() {
        scoreCache += scoreUnit;
        currentScore.setValue(scoreCache);

        LOG.info("Updating score, current score: {}.", scoreCache);
    }

    @Override
    public void update(HitCount hitCount) {
        Preconditions.checkNotNull(hitCount, "hitCount must not be null.");
        Long count = hitCount.getHitCount();

        if (0L == count) {
            update();
        } else {
            scoreCache += count * scoreUnit;
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
}
