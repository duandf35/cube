package cube.models;

/**
 * Model to hold hit count information.
 *
 * @author Wenyu
 * @since 1/23/16
 */
public class HitCount {

    private Long hitCount;

    private Long bestHitCount;

    public HitCount(final Long hitCount, final Long bestHitCount) {
        this.hitCount = hitCount;
        this.bestHitCount = bestHitCount;
    }

    /**
     * Get hit count.
     * @return the hit count
     */
    public Long getHitCount() {
        return hitCount;
    }

    /**
     * Get the best hit count.
     * @return the best hit count
     */
    public Long getBestHitCount() {
        return bestHitCount;
    }

    /**
     * Set the hit count.
     * @param hitCount the hit count
     */
    public void setHitCount(final Long hitCount) {
        this.hitCount = hitCount;
    }

    /**
     * Set the best hit count.
     * @param bestHitCount the best hit count
     */
    public void setBestHitCount(final Long bestHitCount) {
        this.bestHitCount = bestHitCount;
    }
}
