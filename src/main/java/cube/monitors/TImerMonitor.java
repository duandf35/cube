package cube.monitors;

import cube.monitors.timers.Activable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Hold all timers.
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
        timers.stream().forEach(Activable::activate);
    }

    @Override
    public void deactivate(final Activable timer) {

    }

    @Override
    public void deactivateAll() {
        timers.stream().forEach(Activable::deactivate);
    }
}
