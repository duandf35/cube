package cube.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author wenyu
 * @since 12/28/15
 */
public abstract class IStageListener implements ActionListener {

    /**
     * Perform action based on received event.
     * @param event the action event.
     */
    public abstract void actionPerformed(ActionEvent event);

    /**
     * Register timers.
     */
    public abstract void registerTimers();
}
