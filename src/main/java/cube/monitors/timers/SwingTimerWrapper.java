package cube.monitors.timers;

import com.google.common.base.Preconditions;

import javax.swing.*;

/**
 * @author Wenyu
 * @since 1/24/16
 */
public class SwingTimerWrapper implements Activable {

    private final Timer timer;

    public SwingTimerWrapper(final Timer timer) {
        Preconditions.checkNotNull(timer, "timer must not be null.");

        this.timer = timer;
    }

    @Override
    public void activate() {
        timer.start();
    }

    @Override
    public void deactivate() {
        timer.stop();
    }
}
