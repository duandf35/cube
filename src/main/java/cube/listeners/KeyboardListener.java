package cube.listeners;

import cube.configs.ListenerConfig;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class KeyboardListener extends KeyAdapter {
    private final ListenerConfig config;

    private Integer dx, dy, dxu, dyu;

    public KeyboardListener() {
        config = ListenerConfig.getInstance();

        dx = 0;
        dy = 0;
        dxu = config.getUpdateX();
        dyu = config.getUpdateY();
    }

    public Integer[] getKeyboardAction() {
        Integer[] newCoords = new Integer[2];

        newCoords[0] = dx;
        newCoords[1] = dy;

        return newCoords;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT : dx = - dxu; break;
            case KeyEvent.VK_RIGHT: dx =   dxu; break;
            case KeyEvent.VK_UP   : dy = - dyu; break;
            case KeyEvent.VK_DOWN : dy =   dyu; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (KeyEvent.VK_LEFT == key || KeyEvent.VK_RIGHT == key || KeyEvent.VK_UP == key || KeyEvent.VK_DOWN == key) {
            dx = 0;
            dy = 0;
        }
    }
}
