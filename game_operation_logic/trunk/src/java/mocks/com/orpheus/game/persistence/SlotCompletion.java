
package com.orpheus.game.persistence;
/**
 * Represents the recorded data about a player's completion of a hosting slot.
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm318b]
 */
public interface SlotCompletion {
/**
 * Returns the ID of the hosting slot that was completed
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2fe1]
 * @return 
 */
    public long getSlotId();
/**
 * Returns the ID of the player who completed the slot
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2fda]
 * @return 
 */
    public long getPlayerId();
/**
 * Returns a Date representing the date and time at which the slot was completed
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2fd3]
 * @return 
 */
    public java.util.Date getTimestamp();
/**
 * Returns the text of the key associated with this completion
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2fcc]
 * @return 
 */
    public String getKeyText();
/**
 * Returns the download object ID of an image containing the key text, to be presented to users instead of plain text.
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2fc5]
 * @return 
 */
    public long getKeyImageId();
}


