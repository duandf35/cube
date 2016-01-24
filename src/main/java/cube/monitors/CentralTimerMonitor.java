package cube.monitors;

/**
 * Hold all timers.
 *
 * @author Wenyu
 * @since 1/23/16
 */
public final class CentralTimerMonitor {

    private static final CentralTimerMonitor MONITOR = new CentralTimerMonitor();

    private CentralTimerMonitor() {

    }

    public static CentralTimerMonitor getInstance() {
        return MONITOR;
    }
}
