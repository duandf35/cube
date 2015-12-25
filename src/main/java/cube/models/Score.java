package cube.models;

import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wenyu
 * @since 12/19/15
 */
@Entity
@Table(name = "scores")
public class Score {

    private int id;

    private String playerName;

    private int value;

    private DateTime date;

    /**
     * Default constructor, used by Hibernate.
     */
    public Score() {

    }

    /**
     * Used for creating new value instance of current game.
     * Should be removed after playerName and date information is added.
     * @param value the value.
     */
    @Deprecated
    public Score(Integer value) {
        this.value = value;
    }

    /**
     * Used for loading historical value from database.
     * @param playerName the player's name.
     * @param value    the value.
     * @param date     the date.
     */
    public Score(int id, String playerName, Integer value, DateTime date) {
        this.id = id;
        this.playerName = playerName;
        this.value = value;
        this.date = date;
    }

    @Id
    public int getId() {
        return id;
    }

    /**
     * Get player's name.
     * @return the player name
     */
    @Column(name = "player_name")
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Get value.
     * @return the value
     */
    @Column(name = "value")
    public int getValue() {
        return value;
    }

    /**
     * Get date.
     * @return the date
     */
    @Column(name = "date")
    public DateTime getDate() {
        return date;
    }

    public void setId(int id) {
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
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Set date.
     * @param date the date
     */
    public void setDate(DateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Score: [value: " + value + ", playerName: " + playerName + ", date: " + date + "]";
    }
}
