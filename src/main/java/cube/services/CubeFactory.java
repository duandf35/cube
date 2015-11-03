package cube.services;

import cube.models.BasicCube;
import cube.models.Cube;
import cube.models.Position;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class CubeFactory implements Factory<Cube> {

    @Override
    public Cube build() {
        return new BasicCube(new Position(0, 0));
    }
}
