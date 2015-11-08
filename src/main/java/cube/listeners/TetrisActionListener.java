package cube.listeners;

import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.services.Factory;
import cube.stages.Stage;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class TetrisActionListener implements ActionListener {
    private static final Logger A_LOG = LogManager.getLogger("ActionLogger");
    private static final Logger P_LOG = LogManager.getLogger("PositionLogger");

    private Stage stage;
    private Factory factory;

    public TetrisActionListener(Stage stage, Factory factory) {
        this.stage = stage;
        this.factory = factory;
    }

    /**
     * Listener to keyboard action and update the coordinate of tetris.
     * @param e the keyboard action
     */
    public void actionPerformed(ActionEvent e) {
        if (stage.getTetris() == null) {
            stage.setTetris((ITetris) factory.build());
        }

        Command command = stage.getKeyboardAction();
        Integer[] d = command.getPositionChange();
        ITetris tetris = stage.getTetris();

        if (isRotatable(command.getDoRotateFlag(), tetris)) {
            A_LOG.debug("Attempt to rotate.\n");
            rotateTetris(tetris);
        }

        if (isMovable(d, tetris)) {
            A_LOG.debug("Attempt to move.\n");
            adjustBoundary(d, tetris);
            moveTetris(d, tetris);
        }

        printTetrisPositon(tetris);
        stage.repaint();
    }

    private boolean isMovable(Integer[] d, ITetris tetris) {
        boolean canMove = true;

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

    private void moveTetris(Integer[] d, ITetris tetris) {
        tetris.move(d);
    }

    /**
     * Adjust position change based on the position to the boundary.
     *
     * Can move if tetris is at the boundary but next position is away from it
     * Can NOT move tetris is at the boundary and next position is approach to it
     *
     * @param d      the position change
     * @param tetris the tetris
     */
    private void adjustBoundary(Integer[] d, ITetris tetris) {
        boolean reachEBoundary = false,
                reachWBoundary = false,
                reachNBoundary = false,
                reachSBoundary = false;

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
     * @param dr     the will rotate flag
     * @param tetris the tetris
     * @return true if can rotate
     */
    private boolean isRotatable(Integer dr, ITetris tetris) {
        boolean canRotate = true;

        if (Command.DO_ROTATE.equals(dr)) {
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

    private boolean isReachButton(Position p) {
        return false;
    }

    private void rotateTetris(ITetris tetris) {
        tetris.rotate();
    }

    private void printTetrisPositon(ITetris tetris) {
        String tetrisPosition =
                tetris.getPositions()
                      .stream()
                      .map(p -> "x = " + p.getX() + ", y = " + p.getY())
                      .collect(Collectors.joining("\n"));

        P_LOG.debug("\nTetris at:\n" + tetrisPosition + "\n");
    }
}
