
package com.orpheus.game.persistence;
/**
 * An interface representing the persistent information about a particular hosting slot
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm318c]
 */
public interface HostingSlot extends java.io.Serializable {
/**
 * Gets the ID of this hosting slot, or null if none has yet been assigned.
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f53]
 * @return 
 */
    public Long getId();
/**
 * Returns a Domain object represented the domain assigned to this hosting slot
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f4c]
 * @return 
 */
    public Domain getDomain();
/**
 * Returns the ID of the image information associated with this hosting slot
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f45]
 * @return 
 */
    public long getImageId();
/**
 * Returns the unique IDs of the brain teasers in this slot's brain teaser series
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f3e]
 * @return 
 */
    public long[] getBrainTeaserIds();
/**
 * Returns the ID of the puzzle assigned to this slot, or null if there is none
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f37]
 * @return 
 */
    public Long getPuzzleId();
/**
 * Returns the sequence number of this slot within its block
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f30]
 * @return 
 */
    public int getSequenceNumber();
/**
 * Returns an array of DomainTarget objects representing the 'minihunt targets'; for this hosting slot
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f28]
 * @return 
 */
    public DomainTarget[] getDomainTargets();
/**
 * Returns the amount of the winning bid in the auction for this slot
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f21]
 * @return 
 */
    public int getWinningBid();
/**
 * Returns a Date representing the date and time at which this hosting slot began hosting, or null if it has not yet started hosting
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f1a]
 * @return 
 */
    public java.util.Date getHostingStart();
/**
 * Returns a Date representing the date and time at which this hosting slot stopped hosting, or null if it has not yet stopped (including if it hasn't begun)
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f13]
 * @return 
 */
    public java.util.Date getHostingEnd();
}


