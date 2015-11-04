package cube.services;

import cube.configs.CubeConfig;
import cube.models.Cube;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.models.Tetris;

import java.util.LinkedList;
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
        return new Tetris(build01());
    }

    private List<Position> build01() {
        List<Position> positions = new LinkedList<>();

        positions.add(new Position(config.getWidth(), 0));
        positions.add(new Position(0, config.getHeight()));
        positions.add(new Position(config.getWidth(), config.getWidth()));
        positions.add(new Position(0, 2 * config.getHeight()));

        return positions;
    }

//    private List<Position> build02() {
//
//    }
//
//    private List<Position> build03() {
//
//    }
}
