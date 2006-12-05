
package com.orpheus.game.persistence;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.puzzle.*;
import com.topcoder.web.frontcontroller.results.*;
/**
 * Represents the recorded data about a player's completion of a hosting slot.
 * <p><strong>Thread Safety</strong></p>
 * <p>Implementations should strive to be thread-safe, but they can expect to be used in a thread-safe manner.</p>
 * 
 */
public interface SlotCompletion extends java.io.Serializable {
/**
 * Returns the ID of the hosting slot that was completed
 * 
 * 
 * @return the slot id
 */
    public long getSlotId();
/**
 * Returns the ID of the player who completed the slot
 * 
 * 
 * @return the player id
 */
    public long getPlayerId();
/**
 * Returns a Date representing the date and time at which the slot was completed
 * 
 * 
 * @return the completion date of this slot
 */
    public java.util.Date getTimestamp();
/**
 * Returns the text of the key associated with this completion
 * 
 * 
 * @return the key text
 */
    public String getKeyText();
/**
 * Returns the download object ID of an image containing the key text, to be presented to users instead of plain text.
 * 
 * 
 * @return the download object ID of an image containing the key text
 */
    public long getKeyImageId();
}


