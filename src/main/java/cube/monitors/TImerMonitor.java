package cube.monitors;

import cube.monitors.timers.Activable;

/**
 * Hold all timers.
 *
 * @author Wenyu
 * @since 1/23/16
 */
public final class TimerMonitor implements ITimerMonitor {

    private static final TimerMonitor MONITOR = new TimerMonitor();

    private TimerMonitor() {

    }

    public static TimerMonitor getInstance() {
        return MONITOR;
    }

    @Override
    public void register(final Activable timer) {

    }

    @Override
    public void activate(final Activable timer) {

    }

    @Override
    public void activateAll() {

    }

    @Override
    public void deactivate(final Activable timer) {

    }

    @Override
    public void deactivateAll() {

    }
}
