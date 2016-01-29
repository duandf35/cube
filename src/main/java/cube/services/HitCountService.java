package cube.services;

import cube.models.HitCount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Wenyu
 * @since 1/19/16
 */
public class HitCountService implements IHitCountService {
    private static final Logger LOG = LogManager.getLogger(HitCountService.class);

    private volatile boolean resetFlag = true;

    private HitCount hitCount;

    public HitCountService() {
        hitCount = new HitCount(0L, 0L);
    }

    @Override
    public void update() {
        Long count = hitCount.getHitCount();
        Long bestCount = hitCount.getBestHitCount();

        if (count + 1 > bestCount) {
            hitCount.setBestHitCount(count + 1);
        }

        hitCount.setHitCount(count + 1);
    }

    @Override
    public Long get() {
        return hitCount.getHitCount();
    }

    @Override
    public Long getBest() {
        return hitCount.getBestHitCount();
    }

    @Override
    public void reset() {
        LOG.debug("Reset hit count.");

        hitCount.setHitCount(0L);
    }

    @Override
    public void canResetOn() {
        resetFlag = true;
    }

    @Override
    public void canResetOff() {
        resetFlag = false;
    }

    @Override
    public boolean canReset() {
        return resetFlag;
    }
}
