package cube.services;

import cube.models.BasicCube;
import cube.models.Cube;

/**
 * @author wenyu
 * @since 10/24/15
 */
public class CubeFactory implements Factory<Cube> {

    @Override
    public Cube build() {
        return new BasicCube(0, 0);
    }
}
