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

            if (stage.getTetris() == null) {
                stage.setTetris((ITetris) factory.build());
            }

            ITetris tetris = stage.getTetris();

            if (isRotatable(command, tetris)) {
                rotateTetris(tetris);
            }

            if (hasMovingCommand(command)) {
                if (!isBlockedByOtherTetris(command, tetris) && !isBlockedByEWBoundary(command, tetris) && !isBlockedByNSBoundary(command, tetris)) {
                    moveTetris(command, tetris);
                } else if (0 < command.moveY()){
                    stage.digestTetris();
                    Thread.sleep(1000);
                }
            }

            stage.repaint();
        } catch (InterruptedException e) {
            LOG.error("Error happened during action performing", e);
        }
    }

    private boolean hasMovingCommand(Command command) {
        return 0 != command.moveX() || 0 != command.moveY();
    }

    private boolean isBlockedByOtherTetris(Command command, ITetris tetris) {
        boolean blocked = false;

        for (ICube cube: tetris.getCubes()) {
            Position nextPosition = tetris.getNextMovePosition(command, cube);
            blocked |= isBlockedByOtherCubes(nextPosition, stage.getCubes());
        }

        return blocked;
    }

    private boolean isBlockedByOtherCubes(Position nextPosition, Map<Position, ICube> cubesInStage) {
        return null != cubesInStage.get(nextPosition);
    }

    private boolean isBlockedByEWBoundary(Command command, ITetris tetris) {
        boolean reachEBoundary = false,
                reachWBoundary = false,
                blocked = false;

        for (ICube cube: tetris.getCubes()) {
            Position p = cube.getPosition();
            reachWBoundary |= (p.getX() <= 0);
            reachEBoundary |= (p.getX() >= stage.getXBoundary());
        }

        if (reachWBoundary)  {
            blocked = command.moveX() < 0;
        } else if (reachEBoundary) {
            blocked = command.moveX() > 0;
        }

        return blocked;
    }

    private boolean isBlockedByNSBoundary(Command command, ITetris tetris) {
        boolean reachNBoundary = false,
                reachSBoundary = false,
                blocked = false;

        for (ICube cube: tetris.getCubes()) {
            Position p = cube.getPosition();
            reachNBoundary |= (p.getY() <= 0);
            reachSBoundary |= (p.getY() >= stage.getYBoundary());
        }

        if (reachNBoundary) {
            blocked = command.moveY() < 0;
        } else if (reachSBoundary) {
            blocked = command.moveY() > 0;
        }

        return blocked;
    }

    @TracePosition(action = TraceUtils.Actions.MOVING)
    private void moveTetris(Command command, ITetris tetris) {
        tetris.move(command);
    }

    /**
     * Can rotate if center is NOT at the boundary and next position is away from it.
     * @param command the command
     * @param tetris  the tetris
     * @return true if can rotate
     */
    private boolean isRotatable(Command command, ITetris tetris) {
        boolean canRotate = true;

        if (command.rotate()) {
            for (ICube c: tetris.getCenter()) {
                canRotate &= !(isReachBoundary(c.getPosition()));
            }

            if (canRotate) {
                for (ICube c: tetris.getCubes()) {
                    Position np = tetris.getNextRotatePosition(c);
                    canRotate &= !isBlockedByOtherCubes(np, stage.getCubes());
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
