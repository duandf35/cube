package cube.monitors.timers;

import com.google.common.base.Preconditions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

import java.util.UUID;

/**
 * @author Wenyu
 * @since 1/24/16
 */
public class SwingTimerWrapper implements Activable {

    private final Logger log = LogManager.getLogger(this);

    private final String label;

    private final Timer timer;

    public SwingTimerWrapper(final String label, final Timer timer) {
        Preconditions.checkNotNull(timer, "timer must not be null.");

        if (null == label || "".equals(label)) {
            this.label = UUID.randomUUID().toString().replace("-", "");
        } else {
            this.label = label;
        }

        this.timer = timer;
    }

    @Override
    public void activate() {
        log.info("Activating timer: {}", label);

        timer.start();
    }

    @Override
    public void deactivate() {
        log.info("Deactivating timer: {}", label);

        timer.stop();
    }
}
