
package com.orpheus.game.persistence;
/**
 * Represents a 'block'; of hosting slots. Blocks serve as an organizational unit for hosting auctions, and furthermore help to obscure the specific sequence of upcoming domains, even from sponsors privy to the auction details.
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm3189]
 */
public interface HostingBlock extends java.io.Serializable {
/**
 * Returns the ID of this block as a Long, or null if no ID has yet been assigned.
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f71]
 * @return 
 */
    public Long getId();
/**
 * Returns the sequence number of this block within its game
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f6a]
 * @return 
 */
    public int getSequenceNumber();
/**
 * Returns an array of HostingSlot objects representing all the slots associated with this block
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f63]
 * @return 
 */
    public HostingSlot[] getSlots();
/**
 * Returns the maximum hosting time for this block, in minutes
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f5c]
 * @return 
 */
    public int getMaxHostingTimePerSlot();
}


