package cube.models;

import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
    private long id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "value")
    private long value;

    /**
     * Used by Hibernate, H2 seems not support joda time.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "timestamp")
    private Date h2Timestamp;

    /**
     * Not used by Hibernate.
     */
    @Transient
    private DateTime timestamp;

    /**
     * Default constructor, used by Hibernate.
     */
    public Score() {

    }

    /**
     * Used for creating new value instance of current game.
     * Should be removed after playerName and timestamp information is added.
     * @param value the value.
     */
    @Deprecated
    public Score(long value) {
        this.value = value;
    }

    /**
     * Used for loading historical value from database.
     * @param playerName the player's name.
     * @param value    the value.
     * @param timestamp     the timestamp.
     */
    public Score(long id, String playerName, long value, DateTime timestamp) {
        this.id = id;
        this.playerName = playerName;
        this.value = value;
        this.timestamp = timestamp;
    }

    public long getId() {
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
    public long getValue() {
        return value;
    }

    /**
     * Get h2 timestamp.
     * @return the timestamp
     */
    public Date getH2Timestamp() {
        return h2Timestamp;
    }

    /**
     * Get timestamp.
     * @return the timestamp
     */
    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setId(long id) {
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
    public void setValue(long value) {
        this.value = value;
    }

    /**
     * Set H2 timestamp.
     * @param h2Timestamp the h2 timestamp.
     */
    public void setH2Timestamp(Date h2Timestamp) {
        this.h2Timestamp = h2Timestamp;
        this.timestamp = new DateTime(h2Timestamp);
    }

    /**
     * Set timestamp.
     * @param timestamp the timestamp
     */
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Score: [id: " + id + ", value: " + value + ", playerName: " + playerName + ", timestamp: " + timestamp + "]";
    }
}
