package cube.services;

import cube.models.HitCount;

/**
 * @author Wenyu
 * @since 1/19/16
 */
public class HitCountService implements IHitCountService {

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
        hitCount.setHitCount(0L);
    }
}
