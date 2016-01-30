package cube.listeners;

import cube.monitors.timers.TimerRegister;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author wenyu
 * @since 12/28/15
 */
public abstract class IStageListener implements ActionListener, TimerRegister {

    /**
     * Perform action based on received event.
     * @param event the action event.
     */
    public abstract void actionPerformed(ActionEvent event);
}
