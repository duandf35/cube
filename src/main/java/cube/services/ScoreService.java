package cube.services;

import cube.daos.IScoreDAO;
import cube.daos.ScoreDAO;
import cube.models.Score;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wenyu
 * @since 12/24/15
 */
public class ScoreService implements RecordService<Score> {

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
     * All historical score records.
     */
    private List<Score> scoreList;

    /**
     * DAO.
     */
    private IScoreDAO scoreDAO;

    private static ScoreService SERVICE = new ScoreService();

    private ScoreService() {
        scoreCache = 0;
        currentScore = new Score(scoreCache);
        scoreList = new ArrayList<>();
        scoreDAO = new ScoreDAO();
    }

    public static ScoreService getInstance() {
        return SERVICE;
    }

    @Override
    public void update() {
        scoreCache += scoreUnit;
        currentScore.setValue(scoreCache);

        LOG.info("Updating score, current score: {}.", scoreCache);
    }

    @Override
    public Score get() {
        return currentScore;
    }

    @Override
    public List<Score> getAll() {
        return scoreDAO.getAll();
    }

    @Override
    public Score getBest() {
        return scoreDAO.getBest();
    }

    @Override
    public void save() {
        currentScore.setTimestamp(new Date());
        scoreDAO.save(currentScore);
    }
}
