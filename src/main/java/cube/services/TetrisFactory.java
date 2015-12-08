package cube.services;

import cube.aop.TraceUtils;
import cube.configs.CubeConfig;
import cube.models.Cube;
import cube.models.ICube;
import cube.models.ITetris;
import cube.models.Position;
import cube.models.Rotator;
import cube.models.Tetris;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class TetrisFactory implements Factory<ITetris> {
    private final static TetrisFactory FACTORY = new TetrisFactory();
    private final static Logger LOG = LogManager.getLogger(TraceUtils.ACTION_LOGGER);
    private final CubeConfig config;
    private final Random RAND;

    private TetrisFactory() {
        config = CubeConfig.getInstance();
        RAND = new Random(config.getTetrisRollingSeed());
    }

    public static TetrisFactory getInstance() {
        return FACTORY;
    }

    @Override
    public ITetris build() {
        return getRandomTetirs(config.getTetrisIdBound());
    }

    /**
     * Build random tetris.
     *
     * O - 0, S - 1, Z - 2, L - 3, J - 4, T - 5, I - 6
     *
     * @return the tetirs
     */
    private ITetris getRandomTetirs(final int bound) {
        ITetris tetris;
        int id = RAND.nextInt(bound);

        LOG.debug("Building tetirs id: {}.", id);
        switch (id) {
            case 0: tetris = buildO(); break;
            default: tetris = buildS();
        }

        return tetris;
    }

    private ITetris buildS() {
        List<ICube> center = new ArrayList<>(1);
        List<ICube> rim = new ArrayList<>(3);

        ICube cube1 = new Cube(new Position(config.getWidth(), 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), config.getWidth()));
        ICube cube4 = new Cube(new Position(0, 2 * config.getHeight()));

        cube3.setColor(config.getCenterColor());
        center.add(cube3);
        rim.add(cube1);
        rim.add(cube2);
        rim.add(cube4);

        return new Tetris(rim, center, new Rotator(Rotator.DIAMETER_3));
    }

    private ITetris buildO() {
        List<ICube> rim = new ArrayList<>(4);
        List<ICube> center = new ArrayList<>();

        ICube cube1 = new Cube(new Position(0, 0));
        ICube cube2 = new Cube(new Position(config.getWidth(), 0));
        ICube cube3 = new Cube(new Position(0, config.getHeight()));
        ICube cube4 = new Cube(new Position(config.getWidth(), config.getWidth()));

        rim.add(cube1);
        rim.add(cube2);
        rim.add(cube3);
        rim.add(cube4);

        return new Tetris(rim, center, new Rotator(Rotator.NON_ROTATABLE));
    }
}
