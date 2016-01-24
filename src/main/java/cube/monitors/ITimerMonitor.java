package cube.monitors;

import cube.monitors.timers.Activable;

/**
 * @author Wenyu
 * @since 1/23/16
 */
public interface ITimerMonitor {

    /**
     * Register timer.
     * @param timer the timer
     */
    void register(Activable timer);

    /**
     * Activate timer.
     * @param timer the timer
     */
    void activate(Activable timer);

    /**
     * Activate all timers.
     */
    void activateAll();

    /**
     * Deactivate timer.
     * @param timer the timer
     */
    void deactivate(Activable timer);

    /**
     * Deactivate all timers.
     */
    void deactivateAll();
}
