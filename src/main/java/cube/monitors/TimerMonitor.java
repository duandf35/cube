package cube.monitors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import cube.monitors.timers.Activable;

/**
 * Monitor that holds all timer references for controlling.
 *
 * @author Wenyu
 * @since 1/23/16
 */
public final class TimerMonitor implements ITimerMonitor {
    private static final Logger LOG = LogManager.getLogger(TimerMonitor.class);
    private static final TimerMonitor MONITOR = new TimerMonitor();

    private List<Activable> timers;

    private TimerMonitor() {
        timers = new ArrayList<>();
    }

    public static TimerMonitor getInstance() {
        return MONITOR;
    }

    @Override
    public void register(final Activable timer) {
        if (timers.contains(timer)) {
            LOG.warn("Timer has already been registered.");
        } else {
            timers.add(timer);
        }
    }

    @Override
    public void activate(final Activable timer) {

    }

    @Override
    public void activateAll() {
        timers.forEach(Activable::activate);
    }

    @Override
    public void deactivate(final Activable timer) {

    }

    @Override
    public void deactivateAll() {
        timers.forEach(Activable::deactivate);
    }
}
