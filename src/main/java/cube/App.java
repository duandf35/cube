package cube;

import cube.configs.ConfigLoader;
import cube.configs.FrameConfig;
import cube.exceptions.ConfigLoaderException;
import cube.listeners.Listener;
import cube.listeners.TetrisActionListener;
import cube.services.StageFactory;
import cube.services.TetrisFactory;
import cube.services.Factory;
import cube.stages.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * @author wenyu
 * @since 10/21/15
 */
public class App extends JFrame {
    private final FrameConfig config;

    private Stage stage;
    private Factory tetrisFactory, stageFactory;
    private Listener tetrisActionListener;

    public App() {
        config = FrameConfig.getInstance();

        try {
            bootstrap();
            initFrame();
            registerFactories();
            registerStages();
            registerListeners();
            activateListeners();
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

    private void registerFactories() {
        tetrisFactory = TetrisFactory.getInstance();
        stageFactory = StageFactory.getInstance();
    }

    private void registerStages() {
        Objects.requireNonNull(stageFactory, "stageFactory has not been registered yet !");

        stage = (Stage) stageFactory.build();
        add(stage);
    }

    private void registerListeners() {
        Objects.requireNonNull(stage, "Stage has not been registered yet !");
        Objects.requireNonNull(tetrisFactory, "tetrisFactory has not been registered yet !");

        tetrisActionListener = new TetrisActionListener(stage, tetrisFactory);
    }

    private void activateListeners() {
        tetrisActionListener.activate();
    }

    /**
     * Entrance of the application.
     * @param args the args.
     */
    public static void main(String... args) {
        EventQueue.invokeLater(() -> new App());
    }
}
