package cube.listeners;

import com.google.common.base.Preconditions;
import cube.aop.TraceUtils;
import cube.aop.control.ControlStatus;
import cube.aop.score.ScoreOperationRequired;
import cube.configs.ListenerConfig;
import cube.models.Command;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.models.TetrisCommand;
import cube.services.factories.Factory;
import cube.services.factories.TetrisFactory;
import cube.stages.ContainerStage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class StageListener extends IStageListener {
    private static final Logger LOG = LogManager.getLogger(StageListener.class);

    private final ListenerConfig config;

    private ContainerStage tetrisStage;
    private Factory tetrisFactory;

    /**
     * Timer {@code javax.swing.Timer} to notify this listener.
     */
    private final javax.swing.Timer mainTimer;

    /**
     * Timer {@code java.util.Timer} to generate gravity command {@code cube.models.TetrisCommand}.
     */
    private Timer gravityTimer;

    /**
     * Flag to indicate if the listener is active. Used by de(activate) methods to avoid duplicate operating.
     */
    private boolean isActive = false;

    public StageListener(final ContainerStage tetrisStage) {
        this.tetrisStage = Preconditions.checkNotNull(tetrisStage, "tetrisStage must not be null.");

        tetrisFactory = TetrisFactory.getInstance();
        config = ListenerConfig.getInstance();
        mainTimer = new javax.swing.Timer(config.getMainTimerDelay(), this);

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
            Command command = tetrisStage.getKeyboardAction();

            if (isGameContinue()) {
                applyAction(command, tetrisStage.getTetris());
                tetrisStage.repaint();
            }
        } catch (InterruptedException e) {
            LOG.error("Error happened during action performing.", e);
        }
    }

    /**
     * Activate this listener.
     */
    @Override
    public synchronized void activate() {
        if (!isActive) {
            LOG.info("Game start, activating listener...");

            isActive = true;
            mainTimer.start();
            activateGravity();
        }
    }

    /**
     * Deactivate this listener.
     */
    @Override
    public synchronized void deactivate() {
        if (isActive) {
            LOG.info("Game Over, Shutting down...");

            isActive = false;
            gravityTimer.cancel();
            mainTimer.stop();
        }
    }

    /**
     * Check if new tetris can be generated.
     * @return true if new tetris can be put on the state
     */
    private boolean isGameContinue() {
        boolean isGameContinue = true;

        if (null == tetrisStage.getTetris()) {
            ITetris newTetris = (ITetris) tetrisFactory.build();

            if (isBlockedByOtherTetris(newTetris)) {
                isGameContinue = false;
                saveFinalScore();
            } else {
                tetrisStage.setTetris(newTetris);
            }
        }

        return isGameContinue;
    }

    /**
     * AOP JoinPoint for saving point action and show control panel.
     */
    @ControlStatus(status = TraceUtils.Status.GAME_OVER)
    @ScoreOperationRequired(operation = TraceUtils.ScoreOperation.SAVE)
    private void saveFinalScore() {
        deactivate();
    }

    /**
     * Apply keyboard action.
     * @param command the keyboard action
     * @param tetris  the active tetris on the tetrisStage
     * @throws InterruptedException
     */
    private synchronized void applyAction(Command command, ITetris tetris) throws InterruptedException {
        if (isRotatable(command, tetris)) {
            tetris.rotate();
        }

        if (0 != command.moveX()) {
            if (isBlockedByOtherTetris(command, tetris)) {
                if (isBlockedByNSBoundary(command, tetris)) {
                    tetrisStage.digestTetris();
                }
            } else if (!isBlockedByEWBoundary(command, tetris)){
                tetris.moveX(command);
            }
        } else if (0 < command.moveY()) {
            if (isBlockedByOtherTetris(command, tetris) || isBlockedByNSBoundary(command, tetris)) {
                tetrisStage.digestTetris();
            } else {
                tetris.moveY(command);
            }
        }
    }

    /**
     * Apply gravity.
     * @param tetris the active tetris on the tetrisStage
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
        gravityTimer = new Timer();
        gravityTimer.schedule(new TimerTask() {

            @Override
            public synchronized void run() {
                try {
                    if (null != tetrisStage.getTetris()) {
                        applyGravity(tetrisStage.getTetris());
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
        return null != tetrisStage.getCubes().get(nextPosition.getY())
            && null != tetrisStage.getCubes().get(nextPosition.getY()).get(nextPosition.getX());
    }

    private boolean isBlockedByEWBoundary(Command command, ITetris tetris) {
        boolean reachEBoundary = false,
                reachWBoundary = false,
                blocked = false;

        for (ICube cube: tetris.getCubes()) {
            Position p = cube.getPosition();
            reachWBoundary |= (p.getX() <= 0);
            reachEBoundary |= (p.getX() >= tetrisStage.getXBoundary());
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
            reachSBoundary |= (p.getY() >= tetrisStage.getYBoundary());
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
        return (0 >= p.getX() || tetrisStage.getXBoundary() <= p.getX() ||
                0 >= p.getY() || tetrisStage.getYBoundary() <= p.getY());
    }

    private boolean isOutOfBoundary(Position p) {
        return (0 > p.getX() || tetrisStage.getXBoundary() < p.getX() ||
                0 > p.getY() || tetrisStage.getYBoundary() < p.getY());
    }
}
