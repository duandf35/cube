package cube.services;

import cube.configs.StageConfig;

import javax.swing.*;
import java.awt.*;

/**
 * @author Wenyu
 * @since 1/10/16
 */
public class FinalScoreDialog extends JDialog {
    private final StageConfig config;

    private JLabel scoreLabel, playerLabel;

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

    public void setScoreAndPlayer(final Long score, final String player) {
        scoreLabel.setText("Score: " + score);
        playerLabel.setText("Player: " + player);
    }

    public void setContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1));

        JPanel infoPanel = new JPanel();
        scoreLabel = new JLabel();
        infoPanel.add(scoreLabel);

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
