package cube.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
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
    private final Random rand;

    private TetrisFactory() {
        config = CubeConfig.getInstance();
        rand = new Random(config.getTetrisRollingSeed());
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
        int id = rand.nextInt(bound);

        // TODO: move position into the enum ?
        LOG.debug("Building new tetirs, id: {}, name: {}.", id, TetrisType.fromId(id));

        switch (id) {
            case 0:  tetris = O(); break;
            case 1:  tetris = S(); break;
            case 2:  tetris = Z(); break;
            case 3:  tetris = L(); break;
            case 4:  tetris = J(); break;
            case 5:  tetris = T(); break;
            default: tetris = I(); break;
        }

        return tetris;
    }

    private ITetris build(ICube center, List<ICube> rim, Rotator rotator, TetrisType type) {
        if (null != center) {
            center.setColor(config.getCenterColor());
        }

        return new Tetris(center, rim, rotator, type);
    }

    private ITetris O() {
        ICube cube1 = new Cube(new Position(0, 0));
        ICube cube2 = new Cube(new Position(config.getWidth(), 0));
        ICube cube3 = new Cube(new Position(0, config.getHeight()));
        ICube cube4 = new Cube(new Position(config.getWidth(), config.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube2, cube3, cube4);

        return build(null, rim, new Rotator(), TetrisType.O);
    }

    private ITetris S() {
        ICube cube1 = new Cube(new Position(config.getWidth(), 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), config.getHeight()));
        ICube cube4 = new Cube(new Position(0, 2 * config.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube2, cube4);

        return build(cube3, rim, new Rotator(), TetrisType.S);
    }

    private ITetris Z() {
        ICube cube1 = new Cube(new Position(0, 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), config.getHeight()));
        ICube cube4 = new Cube(new Position(config.getWidth(), 2 * config.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube3, cube4);

        return build(cube2, rim, new Rotator(), TetrisType.Z);
    }

    private ITetris L() {
        ICube cube1 = new Cube(new Position(0, 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(0, 2 * config.getHeight()));
        ICube cube4 = new Cube(new Position(config.getWidth(), 2 * config.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube3, cube4);

        return build(cube2, rim, new Rotator(), TetrisType.L);
    }

    private ITetris J() {
        ICube cube1 = new Cube(new Position(config.getWidth(), 0));
        ICube cube2 = new Cube(new Position(config.getWidth(), config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), 2 * config.getHeight()));
        ICube cube4 = new Cube(new Position(0, 2 * config.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube3, cube4);

        return build(cube2, rim, new Rotator(), TetrisType.J);
    }

    private ITetris T() {
        ICube cube1 = new Cube(new Position(config.getWidth(), 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(config.getWidth(), config.getHeight()));
        ICube cube4 = new Cube(new Position(2 * config.getWidth(), config.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube2, cube4);

        return build(cube3, rim, new Rotator(), TetrisType.T);
    }

    private ITetris I() {
        ICube cube1 = new Cube(new Position(0, 0));
        ICube cube2 = new Cube(new Position(0, config.getHeight()));
        ICube cube3 = new Cube(new Position(0, 2 * config.getHeight()));
        ICube cube4 = new Cube(new Position(0, 3 * config.getHeight()));

        List<ICube> rim = ImmutableList.of(cube1, cube2, cube4);

        return build(cube3, rim, new Rotator(), TetrisType.I);
    }

    /**
     * Define the name of tetris.
     * O - 0, S - 1, Z - 2, L - 3, J - 4, T - 5, I - 6
     */
    public enum TetrisType {

        O(0, "O"),

        S(1, "S"),

        Z(2, "Z"),

        L(3, "L"),

        J(4, "J"),

        T(5, "T"),

        I(6, "I");

        private int id;
        private String name;

        TetrisType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * Return id of tetris.
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * Return name of tetris.
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Look up tetris type based off of id.
         * @param id the id
         * @return the matched tetris type
         */
        public static TetrisType fromId(int id) {
            TetrisType matchedType = null;

            for (TetrisType type : TetrisType.values()) {
                if (id == type.getId()) {
                    matchedType = type;
                    break;
                }
            }

            return matchedType;
        }

        /**
         * Look up tetris type based off of name.
         * @param name the name
         * @return the matched tetris type
         */
        public static TetrisType fromName(String name) {
            Preconditions.checkNotNull(name, "Tetris name must not be null.");

            TetrisType matchedType = null;

            for (TetrisType type : TetrisType.values()) {
                if (name.equals(type.getName())) {
                    matchedType = type;
                    break;
                }
            }

            return matchedType;
        }
    }
}
