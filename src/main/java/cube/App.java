package cube;

import cube.configs.ConfigLoader;
import cube.configs.FrameConfig;
import cube.exceptions.ConfigLoaderException;
import cube.listeners.CubeActionListener;
import cube.listeners.KeyboardListener;
import cube.models.BasicCube;
import cube.models.Cube;
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

    private Cube cube;
    private Stage stage;
    private CubeActionListener cubeActionListener;

    // Inject services
    public App() {
        config = FrameConfig.getInstance();

        try {
            bootstrap();
            initFrame();
            registerCubes();
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

    private void registerCubes() {
        cube = new BasicCube(0, 0);
    }

    private void registerStages() {
        Objects.requireNonNull(cube, "Cube has not been registered yet !");

        KeyboardListener keyboardListener = new KeyboardListener();
        stage = new MainStage(cube, keyboardListener);
        add(stage);
    }

    private void registerActionListeners() {
        Objects.requireNonNull(cube, "Cube has not been registered yet !");
        Objects.requireNonNull(stage, "Stage has not been registered yet !");


        cubeActionListener = new CubeActionListener(cube, stage);
    }

    private void activateActionListeners() {
        new Timer(config.getDelay(), cubeActionListener).start();
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
