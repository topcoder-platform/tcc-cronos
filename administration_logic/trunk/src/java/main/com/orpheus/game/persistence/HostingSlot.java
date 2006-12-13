package com.orpheus.game.persistence;

/**
 * An interface representing the persistent information about a particular hosting slot
 * <p><strong>Thread Safety</strong></p>
 * <p>Implementations should strive to be thread-safe, but they can expect to be used in a thread-safe manner.</p>
 * 
 */
public interface HostingSlot extends java.io.Serializable {

    /**
     * Gets the ID of this hosting slot, or null if none has yet been
     * assigned.
     * 
     * 
     * @return the id
     */
    public Long getId();

    /**
     * Returns a Domain object represented the domain assigned to
     * this hosting slot
     * 
     * 
     * @return the domain
     */
    public Domain getDomain();

    /**
     * Returns the ID of the image information associated with this
     * hosting slot
     * 
     * 
     * @return the image id
     */
    public long getImageId();

    /**
     * Returns the unique IDs of the brain teasers in this slot's
     * brain teaser series
     * 
     * 
     * @return the brain teaser id
     */
    public long[] getBrainTeaserIds();

    /**
     * Returns the ID of the puzzle assigned to this slot, or null if there is none
     * 
     * 
     * @return puzzle id
     */
    public Long getPuzzleId();

    /**
     * Returns the sequence number of this slot within its block
     * 
     * 
     * @return sequence number
     */
    public int getSequenceNumber();

    /**
     * Returns an array of DomainTarget objects representing the
     * 'minihunt targets'; for this hosting slot
     * 
     * 
     * @return the array of DomainTarget objects
     */
    public DomainTarget[] getDomainTargets();

    /**
     * Returns the amount of the winning bid in the auction for this
     * slot
     * 
     * 
     * @return the amount of the winning bid
     */
    public int getWinningBid();

    /**
     * Returns a Date representing the date and time at which this
     * hosting slot began hosting, or null if it has not yet started
     * hosting
     * 
     * 
     * @return the start date of the hosting
     */
    public java.util.Date getHostingStart();

    /**
     * Returns a Date representing the date and time at which this hosting slot stopped hosting, or null if it has not yet stopped (including if it hasn't begun)
     * 
     * 
     * @return the end date of the hosting
     */
    public java.util.Date getHostingEnd();
}
