package cube;

import cube.aop.control.GameControllerHelper;
import cube.configs.ConfigLoader;
import cube.configs.FrameConfig;
import cube.exceptions.ConfigLoaderException;
import cube.listeners.TetrisActionListener;
import cube.services.StageFactory;
import cube.services.TetrisFactory;
import cube.stages.ControlStage;
import cube.stages.GameControlStage;
import cube.stages.Stage;

import javax.swing.*;
import java.awt.*;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class App extends JFrame {
    private final FrameConfig config;

    public App() {
        config = FrameConfig.getInstance();

        try {
            bootstrap();
            initFrame();
            registerComponents();
        } catch (ConfigLoaderException e) {
            System.exit(1);
        }
    }

    /**
     * Load config override from file.
     */
    private void bootstrap() throws ConfigLoaderException {
        ConfigLoader.getInstance().load();
    }

    /**
     * Initialize JFrame.
     */
    private void initFrame() {
        setTitle(config.getTitle());
        setPreferredSize(new Dimension(config.getWidth(), config.getHeight()));

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();
        setVisible(true);
        // Let JFrame appear in the center of the monitor, must set to null after pack() is called
        setLocationRelativeTo(null);
    }

    private void registerComponents() {
        Stage mainStage = StageFactory.ModelStageFactory
                                      .getInstance()
                                      .build();

        ControlStage gameControlStage = new GameControlStage(new TetrisActionListener(mainStage, TetrisFactory.getInstance()));

        mainStage.registerControlStage(gameControlStage);
        GameControllerHelper.inject(mainStage);

        add(mainStage);
    }

    /**
     * Entrance of the application.
     * @param args the args
     */
    public static void main(String... args) {
        EventQueue.invokeLater(() -> new App());
    }
}
