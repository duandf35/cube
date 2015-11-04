package cube.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenyu
 * @since 11/3/15
 */
public class Tetris implements ITetris {
    private boolean digested;
    private List<ICube> cubes;
    private Map<Position, ICube> tetris;

    public Tetris(List<Position> positions) {
        digested = false;
        cubes = new ArrayList<>();
        tetris = new HashMap<>();

        for(Position p: positions) {
            ICube cube = new Cube(p);

            cubes.add(cube);
            tetris.put(p, cube);
        }
    }

    @Override
    public Map<Position, ICube> getTetris() {
        return tetris;
    }

    @Override
    public List<Position> getPosition() {
        return null;
    }

    @Override
    public void setPosition(List<Position> positions) {

    }

    @Override
    public void rotate() {

    }

    @Override
    public void erase(Position position) {

    }

    @Override
    public void digest() {
        digested = true;
    }

    @Override
    public boolean isDigested() {
        return digested;
    }

    @Override
    public void paint(Graphics g) {
        for(ICube cube: cubes) {
            cube.paint(g);
        }
    }
}
