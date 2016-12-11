package cube.stages;

import javax.swing.*;

import java.awt.*;

import cube.configs.StageConfig;

/**
 * @author Wenyu
 * @since 1/10/16
 */
public class FinalScoreDialog extends JDialog {
    private final StageConfig config;

    private JLabel scoreLabel, bestHitCountLabel, playerLabel;

    public FinalScoreDialog() {
        config = StageConfig.getInstance();

        setPreferredSize(new Dimension(config.getFinalScoreDialogWidth(), config.getFinalScoreDialogHeight()));
        setBackground(config.getBackgroundColor());
        setResizable(false);
        setVisible(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);

        setContent();
    }

    public void setScoreAndPlayer(final Long score, final Long bestHitCount, final String player) {
        scoreLabel.setText("Score: " + score);
        bestHitCountLabel.setText("Best Hit: " + bestHitCount);
        playerLabel.setText("Player: " + player);
    }

    private void setContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1));

        JPanel infoPanel = new JPanel();

        scoreLabel = new JLabel();
        infoPanel.add(scoreLabel);

        bestHitCountLabel = new JLabel();
        infoPanel.add(bestHitCountLabel);

        playerLabel = new JLabel();
        infoPanel.add(playerLabel);

        contentPanel.add(infoPanel);

        JPanel buttonPanel = new JPanel();
        JButton ok = new JButton("OK");
        ok.addActionListener(e -> setVisible(false));
        buttonPanel.add(ok);
        contentPanel.add(buttonPanel);

        add(contentPanel);
    }
}
