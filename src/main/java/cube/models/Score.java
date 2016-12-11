package cube.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

/**
 * @author wenyu
 * @since 12/19/15
 */
@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "value")
    private Long value;

    /**
     * Used by Hibernate, H2 seems not support joda time.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "timestamp")
    private Date timestamp;

    /**
     * Default constructor, used by Hibernate.
     */
    public Score() {

    }

    /**
     * Used for creating new value instance of current game.
     * Should be removed after playerName and timestamp information is added.
     * @param value the value
     */
    @Deprecated
    public Score(Long value) {
        this.value = value;
    }

    /**
     * Used for loading historical value from database.
     * @param playerName the player's name
     * @param value      the value
     * @param timestamp  the timestamp
     */
    public Score(Long id, String playerName, Long value, Date timestamp) {
        this.id = id;
        this.playerName = playerName;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    /**
     * Get player's name.
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Get value.
     * @return the value
     */
    public Long getValue() {
        return value;
    }

    /**
     * Get timestamp.
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set player's name.
     * @param playerName the player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Set value.
     * @param value the value
     */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * Set timestamp.
     * @param timestamp the timestamp.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Score: [value: " + value + ", playerName: " + playerName + ", timestamp: " + timestamp + "]";
    }
}
