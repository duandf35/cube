package cube.aop.control;

import cube.stages.Stage;

/**
 * @author Wenyu
 * @since 1/2/16
 */
public final class GameControllerHelper {

    private GameControllerHelper() {

    }

    public static void inject(final Stage stage) {
        GameController.aspectOf().setStage(stage);
    }
}
