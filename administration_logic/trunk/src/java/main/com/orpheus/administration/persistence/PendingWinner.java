package com.orpheus.administration.persistence;

/**
 * An interface that represents the essential details associating a pending game winner with the game he has provisionally won and the payout he will receive if approved. It is used in the DTO in the AdminData EJBs, but the EJBs do not operate on it, instead letting the DAOs work with them.
 * <p><strong>Thread Safety</strong></p>
 * Implementations are expected to be thread-safe.
 * 
 */
public interface PendingWinner {
    /**
     * <p>Gets the player ID</p>
     * 
     * 
     * @return the player ID
     */
    public long getPlayerId();

    /**
     * <p>Gets the game ID</p>
     * 
     * 
     * @return  the game ID
     */
    public long getGameId();

    /**
     * <p>Gets the payout to this player should he/she win</p>
     * 
     * 
     * @return the payout to this player should he/she win
     */
    public int getPayout();
}
