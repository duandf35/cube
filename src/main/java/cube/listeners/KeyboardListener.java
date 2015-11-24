package cube.listeners;

import cube.configs.ListenerConfig;
import cube.models.Command;
import cube.models.TetrisCommand;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class KeyboardListener extends KeyAdapter {
    private final ListenerConfig config;

    private Integer dx, dy, dxu, dyu;
    private boolean dr;

    public KeyboardListener() {
        config = ListenerConfig.getInstance();

        dr = false;
        dx = 0;
        dy = 0;
        dxu = config.getUpdateX();
        dyu = config.getUpdateY();
    }

    public Command getKeyboardAction() {
        return new TetrisCommand(dx, dy, dr);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT : dx = - dxu; break;
            case KeyEvent.VK_RIGHT: dx =   dxu; break;
            case KeyEvent.VK_UP   : dy = - dyu; break;
            case KeyEvent.VK_DOWN : dy =   dyu; break;
            case KeyEvent.VK_SPACE: dr =  true; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (KeyEvent.VK_LEFT  == key ||
            KeyEvent.VK_RIGHT == key ||
            KeyEvent.VK_UP    == key ||
            KeyEvent.VK_DOWN  == key ||
            KeyEvent.VK_SPACE == key) {
            dx = 0;
            dy = 0;
            dr = false;
        }
    }
}
