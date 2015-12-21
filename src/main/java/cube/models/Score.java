package cube.models;

import org.joda.time.DateTime;

/**
 * @author wenyu
 * @since 12/19/15
 */
public class Score {

    private String userName;

    private Integer score;

    private DateTime date;

    /**
     * Default constructor.
     */
    public Score() {

    }

    public Score(Integer score) {
        this.score = score;
    }

    public Score(String userName, Integer score, DateTime date) {
        this.userName = userName;
        this.score = score;
        this.date = date;
    }

    /**
     * Get player's name.
     * @return the player name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get score.
     * @return the score
     */
    public Integer getValue() {
        return score;
    }

    /**
     * Get date.
     * @return the date
     */
    public DateTime getDate() {
        return date;
    }

    /**
     * Set player's name.
     * @param userName the player name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Set score.
     * @param score the score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Set date.
     * @param date the date
     */
    public void setDate(DateTime date) {
        this.date = date;
    }
}
