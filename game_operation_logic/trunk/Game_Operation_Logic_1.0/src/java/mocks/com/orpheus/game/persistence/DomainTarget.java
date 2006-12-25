
package com.orpheus.game.persistence;
/**
 * Represents an object to be sought by players on a host site.
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm318f]
 */
public interface DomainTarget extends java.io.Serializable {
/**
 * The unique identifier of this target, or null if none has yet been assigned
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm300d]
 * @return 
 */
    public Long getId();
/**
 * Returns the sequence number of this target among those assigned to the same hosting slot
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm3006]
 * @return 
 */
    public int getSequenceNumber();
/**
 * Returns the path and file parts of the URI at which the target is located
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2fff]
 * @return 
 */
    public String getUriPath();
/**
 * Returns the plain text identifier of the target
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2ff8]
 * @return 
 */
    public String getIdentifierText();
/**
 * Returns a hash of the target's identifier
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2ff1]
 * @return 
 */
    public String getIdentifierHash();
/**
 * Returns the unique identifier of a downloadable object constituting an image to be presented to users as the clue for this target
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2fea]
 * @return 
 */
    public long getClueImageId();
}


