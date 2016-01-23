package cube.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import cube.configs.StageConfig;
import cube.models.Score;
import cube.stages.ScoreRecordStage;

import java.util.List;

/**
 * @author Wenyu
 * @since 1/10/16
 */
public class ScoreRecordStageManager {
    private static final ScoreRecordStageManager MANAGER = new ScoreRecordStageManager();

    private final StageConfig config;
    private IScoreService scoreService;
    private ScoreRecordStage[] scoreRecordStages;

    private ScoreRecordStageManager() {
        config = StageConfig.getInstance();
    }

    public static ScoreRecordStageManager getInstance() {
        return MANAGER;
    }

    /**
     * Register score service.
     * @param  scoreService the score service
     * @return the manager itself for method chaining
     */
    public ScoreRecordStageManager register(final IScoreService scoreService) {
        this.scoreService = scoreService;

        return this;
    }

    /**
     * Build record stages. Each stage is one score record.
     * @return the manager itself for method chaining
     */
    public ScoreRecordStageManager buildStages() {
        Preconditions.checkNotNull(scoreService, "scoreService has not been registered.");

        List<Score> scores = scoreService.getAll();

        List<Score> displayScores;
        if (null == scores || scores.isEmpty()) {
            displayScores = ImmutableList.of();
        } else if (config.getMaxRecordsLoad() >= scores.size()){
            displayScores = ImmutableList.copyOf(scores);
        } else {
            displayScores = ImmutableList.copyOf(scores.subList(0, config.getMaxRecordsLoad()));
        }

        scoreRecordStages = new ScoreRecordStage[displayScores.size()];
        for (int i = 0; i < displayScores.size(); i++) {
            Score score = displayScores.get(i);
            scoreRecordStages[i] = new ScoreRecordStage(score.getValue(), score.getPlayerName(), score.getTimestamp());
        }

        return this;
    }

    /**
     * Get record stages.
     * @return the record stages
     */
    public ScoreRecordStage[] getStages() {
        Preconditions.checkNotNull(scoreRecordStages, "scoreRecordStages has not been built.");

        return scoreRecordStages;
    }
}
