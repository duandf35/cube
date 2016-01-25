package cube.monitors.timers;

import java.util.TimerTask;

/**
 * A builder to provide new TimerTask.
 *
 * The builder is used for rescheduling an existing Timer since TimerTask can not be reused.
 *
 * @author Wenyu
 * @since 1/24/16
 */
public interface TimerTaskBuilder {

    /**
     * Return a new instance of TimerTask.
     * @return the timer task
     */
    TimerTask build();
}
