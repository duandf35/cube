package cube.services;

import cube.listeners.KeyboardListener;
import cube.models.Monitor;
import cube.models.StageMonitor;
import cube.stages.MainStage;
import cube.stages.Stage;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class StageFactory implements Factory<Stage> {

    public Stage build() {
        Monitor monitor = new StageMonitor();
        KeyboardListener keyboardListener = new KeyboardListener();

        return new MainStage(keyboardListener, monitor);
    }
}
