package cube.stages;

import cube.configs.StageConfig;

import javax.swing.*;
import java.util.Date;

/**
 * A stage contains one score record.
 *
 * @author Wenyu
 * @since 1/10/16
 */
public class ScoreRecordStage extends SubStage {
    private final StageConfig config;

    private JLabel scoreLabel, playerLabel, dateLabel;
    private Long score;
    private String player;
    private Date date;

    public ScoreRecordStage(final Long score, String player, Date date) {
        this.score = score;
        this.player = player;
        this.date = date;

        config = StageConfig.getInstance();
        setBackground(config.getBackgroundColor());
        setupLabels();
    }

    private void setupLabels() {
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(config.getScoreDisplayColor());
        add(scoreLabel);

        playerLabel = new JLabel("Player: " + player);
        playerLabel.setForeground(config.getScoreDisplayColor());
        add(playerLabel);

        dateLabel = new JLabel("Date: " + date);
        dateLabel.setForeground(config.getScoreDisplayColor());
        add(dateLabel);
    }
}
