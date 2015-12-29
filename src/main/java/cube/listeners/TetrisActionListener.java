package cube.listeners;

import com.google.common.base.Preconditions;
import cube.aop.score.ScoreOperation;
import cube.aop.score.ScoreOperationRequired;
import cube.configs.ListenerConfig;
import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.models.TetrisCommand;
import cube.services.Factory;
import cube.stages.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class TetrisActionListener extends Listener {
    private static final Logger LOG = LogManager.getLogger(TetrisActionListener.class);

    private final ListenerConfig config;

    private Stage stage;
    private Factory factory;

    /**
     * Timer {@code javax.swing.Timer} to notify this listener.
     */
    private final javax.swing.Timer mainTimer;

    /**
     * Timer {@code java.util.Timer} to generate gravity command {@code cube.models.TetrisCommand}.
     */
    private final Timer gravityTimer;

    public TetrisActionListener(Stage stage, Factory factory) {
        config = ListenerConfig.getInstance();
        gravityTimer = new Timer();
        mainTimer = new javax.swing.Timer(config.getMainTimerDealy(), this);

        this.stage = stage;
        this.factory = factory;

        activateGravity();
    }

    /**
     * Listener to keyboard action and update the coordinate of tetris.
     *
     * @param event the keyboard action
     */
    @Override
    public synchronized void actionPerformed(ActionEvent event) {
        try {
            Command command = stage.getKeyboardAction();

            if (isGameContinue()) {
                applyAction(command, stage.getTetris());
                stage.repaint();
            }
        } catch (InterruptedException e) {
            LOG.error("Error happened during action performing.", e);
        }
    }

    /**
     * Activate this listener.
     */
    @Override
    public void activate() {
        mainTimer.start();
    }

    /**
     * Deactivate this listener.
     */
    private void deactivate() {
        LOG.info("Game Over, Shutting down... Final score: {}.", stage.getScore());

        gravityTimer.cancel();
        mainTimer.stop();
    }

    /**
     * Check if new tetris can be generated.
     * @return true if new tetris can be put on the state
     */
    private boolean isGameContinue() {
        boolean isGameContinue = true;

        if (null == stage.getTetris()) {
            ITetris newTetris = (ITetris) factory.build();

            if (isBlockedByOtherTetris(newTetris)) {
                isGameContinue = false;
                saveFinalScore();
                deactivate();
            } else {
                stage.setTetris(newTetris);
            }
        }

        return isGameContinue;
    }

    /**
     * AOP JoinPoint for saving point action.
     */
    @ScoreOperationRequired(operation = ScoreOperation.SAVE)
    private void saveFinalScore() {

    }

    /**
     * Apply keyboard action.
     * @param command the keyboard action
     * @param tetris  the active tetris on the stage
     * @throws InterruptedException
     */
    private synchronized void applyAction(Command command, ITetris tetris) throws InterruptedException {
        if (isRotatable(command, tetris)) {
            tetris.rotate();
        }

        if (0 != command.moveX()) {
            if (isBlockedByOtherTetris(command, tetris)) {
                if (isBlockedByNSBoundary(command, tetris)) {
                    stage.digestTetris();
                }
            } else if (!isBlockedByEWBoundary(command, tetris)){
                tetris.moveX(command);
            }
        } else if (0 < command.moveY()) {
            if (isBlockedByOtherTetris(command, tetris) || isBlockedByNSBoundary(command, tetris)) {
                stage.digestTetris();
            } else {
                tetris.moveY(command);
            }
        }
    }

    /**
     * Apply gravity.
     * @param tetris the active tetris on the stage
     * @throws InterruptedException
     */
    private void applyGravity(ITetris tetris) throws InterruptedException {
        Preconditions.checkNotNull(tetris, "Tetris must not be null.");

        Command gravityCommand = new TetrisCommand(0, config.getGravity(), false);

        applyAction(gravityCommand, tetris);
    }

    /**
     * Activate gravity.
     */
    private void activateGravity() {
        gravityTimer.schedule(new TimerTask() {

            @Override
            public synchronized void run() {
                try {
                    if (null != stage.getTetris()) {
                        applyGravity(stage.getTetris());
                    }
                } catch (InterruptedException e) {
                    LOG.error("Error happened during gravity performing.", e);
                }
            }
        }, config.getGravityApplyDelay(), config.getGravityApplyPeriod());
    }

    private boolean isBlockedByOtherTetris(ITetris tetris) {
        boolean blocked = false;

        for (ICube cube: tetris.getCubes()) {
            blocked |= isBlockedByOtherCubes(cube.getPosition());
        }

        return blocked;
    }

    private boolean isBlockedByOtherTetris(Command command, ITetris tetris) {
        boolean blocked = false;

        for (ICube cube: tetris.getCubes()) {
            Position nextPosition = tetris.getNextMovePosition(command, cube);
            blocked |= isBlockedByOtherCubes(nextPosition);
        }

        return blocked;
    }

    private boolean isBlockedByOtherCubes(Position nextPosition) {
        return null != stage.getCubes().get(nextPosition.getY())
            && null != stage.getCubes().get(nextPosition.getY()).get(nextPosition.getX());
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

    /**
     * Can rotate if center is NOT at the boundary and next position is away from it.
     * @param command the command
     * @param tetris  the tetris
     * @return true if can rotate
     */
    private boolean isRotatable(Command command, ITetris tetris) {
        boolean canRotate;

        if (command.rotate()) {
            canRotate = null != tetris.getCenter()
                     && !(isReachBoundary(tetris.getCenter().getPosition()));

            if (canRotate) {
                for (ICube c: tetris.getCubes()) {
                    Position np = tetris.getNextRotatePosition(c);
                    canRotate &= !isBlockedByOtherCubes(np) && !isOutOfBoundary(np);
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

    private boolean isOutOfBoundary(Position p) {
        return (0 > p.getX() || stage.getXBoundary() < p.getX() ||
                0 > p.getY() || stage.getYBoundary() < p.getY());
    }
}
