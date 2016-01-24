package services

import cube.models.ITetris
import cube.models.TetrisType
import cube.services.factories.TetrisFactory
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author wenyu
 * @date 12/18/15
 */
class TetrisFactorySpec extends Specification {

    private static final MAX_RUN = 5000
    private static final ACC_LOWER_PROB = 0.120
    private static final ACC_UPPER_PROB = 0.160

    def factory

    def setup() {
        factory = TetrisFactory.getInstance()
    }

    /**
     * Theoretical probability = 1 / 7 = 14.29 %
     */
    @Unroll
    def 'probability of generating each tetris should be the same'() {
        given:
        Map<Integer, Integer> resultMap = new HashMap<>()

        when:
        thousandsRun(resultMap)

        then:
        expect == checkResult(resultMap.get(tetrisId) / MAX_RUN)

        where:
        tetrisId             | expect
        TetrisType.O.getId() | true
        TetrisType.S.getId() | true
        TetrisType.Z.getId() | true
        TetrisType.L.getId() | true
        TetrisType.J.getId() | true
        TetrisType.T.getId() | true
        TetrisType.I.getId() | true
    }

    private void thousandsRun(Map<Integer, Integer> resultMap) {
        for (int r = 0; r < MAX_RUN; r++) {
            ITetris tetris = factory.build()

            int count = resultMap.get(tetris.getType().getId()) ?: 0
            resultMap.put(tetris.getType().getId(), ++count)
        }
    }

    private static boolean checkResult(Double prob) {
        return ACC_LOWER_PROB < prob && prob < ACC_UPPER_PROB
    }
}
