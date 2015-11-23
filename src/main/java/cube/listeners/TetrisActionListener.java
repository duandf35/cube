package cube.listeners;

import cube.aop.TracePosition;
import cube.aop.TraceUtils;
import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.services.Factory;
import cube.stages.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class TetrisActionListener implements ActionListener {
    private static final Logger LOG = LogManager.getLogger(TetrisActionListener.class.getName());

    private Stage stage;
    private Factory factory;

    public TetrisActionListener(Stage stage, Factory factory) {
        this.stage = stage;
        this.factory = factory;
    }

    /**
     * Listener to keyboard action and update the coordinate of tetris.
     * @param event the keyboard action
     */
    public void actionPerformed(ActionEvent event) {
        try {
            Command command = stage.getKeyboardAction();
            ITetris tetris = stage.getTetris();

            if (stage.getTetris() == null) {
                stage.setTetris((ITetris) factory.build());
            }

            if (isRotatable(command, tetris)) {
                rotateTetris(tetris);
            }

            if (isMovable(command, tetris)) {
                adjustBoundary(command, tetris);
                moveTetris(command, tetris);
            } else {
                // TODO: use CountDownLatch
                stage.digestTetris();
                Thread.sleep(1000);
            }

            stage.repaint();
        } catch (InterruptedException e) {
            LOG.error("Error happened during action performing", e);
        }
    }

    private boolean isMovable(Command command, ITetris tetris) {
        boolean canMove = true;
        Integer[] d = command.doMove();

        if (d[0] == 0 && d[1] == 0) {
            canMove = false;
        } else {
            for (ICube cube: tetris.getCubes()) {
                Position nextPosition = tetris.getNextMovePosition(d, cube);
                canMove &= isMovable(nextPosition, stage.getCubes());
            }
        }

        return canMove;
    }

    private boolean isMovable(Position nextPosition, Map<Position, ICube> cubesInStage) {
        return (cubesInStage.get(nextPosition) == null);
    }

    @TracePosition(action = TraceUtils.Actions.MOVING)
    private void moveTetris(Command command, ITetris tetris) {
        Integer[] d = command.doMove();
        tetris.move(d);
    }

    /**
     * Adjust position change based on the position to the boundary.
     *
     * Can move if tetris is at the boundary but next position is away from it
     * Can NOT move tetris is at the boundary and next position is approach to it
     *
     * @param command the command
     * @param tetris  the tetris
     */
    private void adjustBoundary(Command command, ITetris tetris) {
        boolean reachEBoundary = false,
                reachWBoundary = false,
                reachNBoundary = false,
                reachSBoundary = false;

        Integer[] d = command.doMove();
        for (ICube cube: tetris.getCubes()) {
            Position p = cube.getPosition();
            reachWBoundary |= (p.getX() <= 0);
            reachEBoundary |= (p.getX() >= stage.getXBoundary());
            reachNBoundary |= (p.getY() <= 0);
            reachSBoundary |= (p.getY() >= stage.getYBoundary());
        }

        if (reachWBoundary && d[0] < 0) {
            d[0] = 0;
        }

        if (reachEBoundary && d[0] > 0) {
            d[0] = 0;
        }

        if (reachNBoundary && d[1] < 0) {
            d[1] = 0;
        }

        if (reachSBoundary && d[1] > 0) {
            d[1] = 0;
        }
    }

    /**
     * Can rotate if center is NOT at the boundary and next position is away from it.
     * @param command the command
     * @param tetris  the tetris
     * @return true if can rotate
     */
    private boolean isRotatable(Command command, ITetris tetris) {
        boolean canRotate = true;

        if (command.doRotate()) {
            for (ICube c: tetris.getCenter()) {
                canRotate &= !(isReachBoundary(c.getPosition()));
            }

            if (canRotate) {
                for (ICube c: tetris.getCubes()) {
                    Position np = tetris.getNextRotatePosition(c);
                    canRotate &= isMovable(np, stage.getCubes());
                }
            }
        } else {
            canRotate = false;
        }

        return canRotate;
    }

    private boolean isReachBoundary(Position p) {
        return (0 >= p.getX() || stage.getXBoundary() <= p.getX() ||
                0 >= p.getY() || stage.getYBoundary() <= p.getY());
    }

    @TracePosition(action = TraceUtils.Actions.ROTATING)
    private void rotateTetris(ITetris tetris) {
        tetris.rotate();
    }
}
