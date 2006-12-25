
package com.orpheus.game.persistence;
/**
 * The Game interface represents an individual game managed by the application. It carries a unique identifier, a start date, and an end date, and can provide a BallColor representing the color associated with this game and a game name string computed based on the game id and color.
 * 
 * 
 */
public interface Game extends java.io.Serializable {
/**
 * Gets the identifier for this game, as a Long. The identifier may be null if this Game has not yet been assigned one.
 * 
 * 
 * @return 
 */
    public Long getId();
/**
 * Gets the name of this game, which is the concatenation of the name of the associated BallColor with the ID of this game. If this game does not have an ID or BallColor yet assigned then that part of the name is represented by a single question mark.
 * 
 * 
 * @return 
 */
    public String getName();
/**
 * Returns the BallColor object assigned to this game, or null if there is none.
 * 
 * 
 * @return 
 */
    public BallColor getBallColor();
/**
 * Returns the number of keys required to attempt to win this game
 * 
 * 
 * @return 
 */
    public int getKeyCount();
/**
 * Retrieves the planned or past start date for this game; will not be null.
 * 
 * 
 * @return 
 */
    public java.util.Date getStartDate();
/**
 * Retrieves the end date of this game, if it has already ended, or null if it hasn't.
 * 
 * 
 * @return 
 */
    public java.util.Date getEndDate();
/**
 * Retrieves an array of HostingBlock objects representing the hosting blocks within this game
 * 
 * 
 * @return 
 */
    public HostingBlock[] getBlocks();
}


