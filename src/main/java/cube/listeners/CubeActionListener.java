package cube.listeners;

import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.services.Factory;
import cube.stages.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wenyu
 * @since 10/22/15
 */
public class CubeActionListener implements ActionListener {
    private Stage stage;
    private Factory factory;

    public CubeActionListener(Stage stage, Factory factory) {
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

        Integer[] nextCoords = stage.getKeyboardAction();

        ITetris tetris = stage.getTetris();
        Map<Position, ICube> cubesInStage = stage.getCubes();

        List<Position> nextPositions = calculateNextPositions(nextCoords, tetris);

        if (isMovable(nextPositions, cubesInStage)) {
            moveTetris(nextPositions, tetris);
        }

        stage.repaint();
    }

    private List<Position> calculateNextPositions(Integer[] nextCoords, ITetris tetris) {
        List<Position> positions = new ArrayList<>(tetris.getPosition());

        for(Position p: positions) {
            p.setX(p.getX() + nextCoords[0]);
            p.setY(p.getY() + nextCoords[1]);
        }

        return positions;
    }

    private boolean isMovable(List<Position> nextPositions, Map<Position, ICube> cubesInStage) {
        boolean canMove = true;

        for(Position p: nextPositions) {
            canMove &= (cubesInStage.get(p) == null);
        }

        return canMove;
    }

    private void moveTetris(List<Position> nextPositions, ITetris tetris) {
        tetris.setPosition(nextPositions);
    }

    private boolean isReachYBoundary() {
        return false;
    }

    private boolean isReachXBoundary() {
        return false;
    }
}
