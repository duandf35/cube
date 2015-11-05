package cube;

import cube.configs.ConfigLoader;
import cube.configs.FrameConfig;
import cube.exceptions.ConfigLoaderException;
import cube.listeners.TetrisActionListener;
import cube.listeners.KeyboardListener;
import cube.services.TetrisFactory;
import cube.services.Factory;
import cube.stages.MainStage;
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
    private Factory factory;
    private TetrisActionListener tetrisActionListener;

    public App() {
        config = FrameConfig.getInstance();

        try {
            bootstrap();
            initFrame();
            registerCubeFactory();
            registerStages();
            registerActionListeners();
            activateActionListeners();
        } catch (ConfigLoaderException e) {
            System.out.println(e.getMessage());
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
        setLocationRelativeTo(null);

        pack();
    }

    private void registerCubeFactory() {
        factory = new TetrisFactory();
    }

    private void registerStages() {
        KeyboardListener keyboardListener = new KeyboardListener();
        stage = new MainStage(keyboardListener);
        add(stage);
    }

    private void registerActionListeners() {
        Objects.requireNonNull(stage, "Stage has not been registered yet !");
        Objects.requireNonNull(factory, "Factory has not been registered yet !");

        tetrisActionListener = new TetrisActionListener(stage, factory);
    }

    private void activateActionListeners() {
        new Timer(config.getDelay(), tetrisActionListener).start();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }
}
