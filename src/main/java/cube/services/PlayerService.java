package cube.services;

/**
 * @author Wenyu
 * @since 1/23/16
 */
public interface PlayerService {

    /**
     * Get current player.
     * @return the current player
     */
    String getPlayer();

    /**
     * Set current player
     * @param player the current player
     */
    void setPlayer(String player);
}
