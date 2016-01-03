package cube.aop.control;

import cube.listeners.Listener;

/**
 * @author Wenyu
 * @since 1/2/16
 */
public final class GameControllerHelper {

    private GameControllerHelper() {

    }

    public static void inject(final Listener listener) {
        GameController.aspectOf().setTetrisActionListener(listener);
    }
}
