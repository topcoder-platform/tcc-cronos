package com.orpheus.game.persistence;

/**
 * The Game interface represents an individual game managed by the application. It carries a unique identifier, a start date, and an end date, and can provide a BallColor representing the color associated with this game and a game name string computed based on the game id and color.
 * <p><strong>Thread Safety</strong></p>
 * <p>Implementations should strive to be thread-safe, but they can expect to be used in a thread-safe manner.</p>
 * 
 */
public interface Game extends java.io.Serializable {
    /**
     * Gets the identifier for this game, as a Long. The identifier
     * may be null if this Game has not yet been assigned one.
     * 
     * 
     * @return the id
     */
    public Long getId();

    /**
     * Gets the name of this game, which is the concatenation of the
     * name of the associated BallColor with the ID of this game. If
     * this game does not have an ID or BallColor yet assigned then
     * that part of the name is represented by a single question
     * mark.
     * 
     * 
     * @return the name
     */
    public String getName();

    /**
     * Returns the BallColor object assigned to this game, or null if there is none.
     * 
     * 
     * @return the color of the ball
     */
    public BallColor getBallColor();

    /**
     * Returns the number of keys required to attempt to win this
     * game
     * 
     * 
     * @return the key count
     */
    public int getKeyCount();

    /**
     * Retrieves the planned or past start date for this game; will
     * not be null.
     * 
     * 
     * @return the start date
     */
    public java.util.Date getStartDate();

    /**
     * Retrieves the end date of this game, if it has already ended, or null if it hasn't.
     * 
     * 
     * @return the end date
     */
    public java.util.Date getEndDate();

    /**
     * Retrieves an array of HostingBlock objects representing the
     * hosting blocks within this game
     * 
     * 
     * @return the array of HostingBlock objects
     */
    public HostingBlock[] getBlocks();
}
