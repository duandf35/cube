package cube.services;

import cube.configs.CubeConfig;
import cube.models.Cube;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.models.Rotator;
import cube.models.Tetris;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class TetrisFactory implements Factory<ITetris> {
    private final CubeConfig config;

    public TetrisFactory() {
        config = CubeConfig.getInstance();
    }

    @Override
    public ITetris build() {
        return buildS();
    }

    private Tetris buildS() {
        List<ICube> center = new ArrayList<>(1);
        List<ICube> rim = new ArrayList<>(3);

        ICube cube1 = new Cube(new Position(config.getWidth(), 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), config.getWidth()));
        ICube cube4 = new Cube(new Position(0, 2 * config.getHeight()));

        center.add(cube3);
        rim.add(cube1);
        rim.add(cube2);
        rim.add(cube4);

        return new Tetris(rim, center, new Rotator(Rotator.DIAMETER_3));
    }

//    private List<Position> build2() {
//
//    }
//
//    private List<Position> build3() {
//
//    }
}
