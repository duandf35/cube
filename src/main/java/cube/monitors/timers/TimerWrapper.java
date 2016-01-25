package cube.monitors.timers;

import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.UUID;

/**
 * @author Wenyu
 * @since 1/23/16
 */
public class TimerWrapper implements Activable {

    private final Logger log = LogManager.getLogger(this);

    private final String label;

    private final TimerTaskBuilder taskBuilder;
    private final Integer delay, period;

    private Timer timer;

    public TimerWrapper(final String label, final TimerTaskBuilder taskBuilder, final Integer delay, final Integer period) {
        Preconditions.checkNotNull(taskBuilder, "taskBuilder must not be null.");
        Preconditions.checkArgument(delay >= 0, "delay must greater than 0 ms.");
        Preconditions.checkArgument(period >= 1000, "period must no less than 1000 ms.");

        if (null == label || "".equals(label)) {
            this.label = UUID.randomUUID().toString().replace("-", "");
        } else {
            this.label = label;
        }

        this.taskBuilder = taskBuilder;
        this.delay = delay;
        this.period = period;
    }

    @Override
    public void activate() {
        log.info("Activating timer: {}", label);

        timer = new Timer();
        timer.schedule(taskBuilder.build(), delay, period);
    }

    @Override
    public void deactivate() {
        log.info("Deactivating timer: {}", label);

        timer.purge();
        timer.cancel();
    }
}
