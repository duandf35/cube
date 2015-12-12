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

    /**
     * Build random tetris.
     *
     * O - 0, S - 1, Z - 2, L - 3, J - 4, T - 5, I - 6
     *
     * @return the tetirs
     */
    @Override
    public ITetris build() {
        return build(config.getTetrisIdBound());
    }

    private ITetris build(final int bound) {
        ITetris tetris;
        int id = RAND.nextInt(bound);

        LOG.debug("Building tetirs id: {}.", id);
        switch (id) {
            case 0: tetris = O(); break;
            case 1: tetris = S(); break;
            case 2: tetris = Z(); break;
            case 3: tetris = L(); break;
            case 4: tetris = J(); break;
            case 5: tetris = T(); break;
            default: tetris = O();
        }

        return tetris;
    }

    private ITetris build(ICube[] rimCubes, ICube[] centerCubes, Rotator rotator) {
        List<ICube> rim = new ArrayList<>();
        List<ICube> center = new ArrayList<>();

        for (ICube r : rimCubes) {
            rim.add(r);
        }

        for (ICube c : centerCubes) {
            c.setColor(config.getCenterColor());
            center.add(c);
        }

        return new Tetris(rim, center, rotator);
    }

    private ITetris O() {
        ICube cube1 = new Cube(new Position(0, 0));
        ICube cube2 = new Cube(new Position(config.getWidth(), 0));
        ICube cube3 = new Cube(new Position(0, config.getHeight()));
        ICube cube4 = new Cube(new Position(config.getWidth(), config.getWidth()));

        ICube[] rim = { cube1, cube2, cube3, cube4 };
        ICube[] center = {};

        return build(rim, center, new Rotator(Rotator.NON_ROTATABLE));
    }

    private ITetris S() {
        ICube cube1 = new Cube(new Position(config.getWidth(), 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), config.getWidth()));
        ICube cube4 = new Cube(new Position(0, 2 * config.getHeight()));

        ICube[] rim = { cube1, cube2, cube4 };
        ICube[] center = { cube3 };

        return build(rim, center, new Rotator(Rotator.DIAMETER_3));
    }

    private ITetris Z() {
        ICube cube1 = new Cube(new Position(0, 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), config.getWidth()));
        ICube cube4 = new Cube(new Position(config.getWidth(), 2 * config.getHeight()));

        ICube[] rim = { cube1, cube3, cube4 };
        ICube[] center = { cube2 };

        return build(rim, center, new Rotator(Rotator.DIAMETER_3));
    }

    private ITetris L() {
        ICube cube1 = new Cube(new Position(0, 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(0, 2 * config.getWidth()));
        ICube cube4 = new Cube(new Position(config.getWidth(), 2 * config.getHeight()));

        ICube[] rim = { cube1, cube3, cube4 };
        ICube[] center = { cube2 };

        return build(rim, center, new Rotator(Rotator.DIAMETER_3));
    }

    private ITetris J() {
        ICube cube1 = new Cube(new Position(config.getWidth(), 0));
        ICube cube2 = new Cube(new Position(config.getWidth(), config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), 2 * config.getWidth()));
        ICube cube4 = new Cube(new Position(0, 2 * config.getHeight()));

        ICube[] rim = { cube1, cube3, cube4 };
        ICube[] center = { cube2 };

        return build(rim, center, new Rotator(Rotator.DIAMETER_3));
    }

    private ITetris T() {
        ICube cube1 = new Cube(new Position(config.getWidth(), 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), config.getWidth()));
        ICube cube4 = new Cube(new Position(2 * config.getWidth(), config.getHeight()));

        ICube[] rim = { cube1, cube2, cube4 };
        ICube[] center = { cube3 };

        return build(rim, center, new Rotator(Rotator.DIAMETER_3));
    }
}
