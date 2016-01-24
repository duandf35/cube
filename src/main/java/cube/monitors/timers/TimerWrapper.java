package cube.monitors.timers;

import com.google.common.base.Preconditions;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Wenyu
 * @since 1/23/16
 */
public class TimerWrapper implements Activable {

    private final Timer timer;
    private final TimerTask task;
    private final Integer delay, period;

    public TimerWrapper(final Timer timer, final TimerTask task, final Integer delay, final Integer period) {
        Preconditions.checkNotNull(timer, "timer must not be null.");
        Preconditions.checkNotNull(task, "task must not be null.");

        this.timer = timer;
        this.task = task;
        this.delay = delay;
        this.period = period;
    }

    @Override
    public void activate() {
        timer.schedule(task, delay, period);
    }

    @Override
    public void deactivate() {
        timer.cancel();
    }
}
