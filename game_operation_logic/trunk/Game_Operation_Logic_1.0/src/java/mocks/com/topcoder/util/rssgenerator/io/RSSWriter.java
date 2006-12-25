
package com.topcoder.util.rssgenerator.io;
import com.topcoder.util.rssgenerator.io.RSSWriteException;
/**
 * Purpose: This interface gives the contract for writing RSS feeds and items into character streams. The contract consists of just two methods, one to write items and another to write feeds.
 * <p>Implementation: Implementations will write the given feed/item into particular formats - say RSS 2.0 or Atom 1.0. The procedure fo writing is of course format dependent.</p>
 * <p>Thread Safety: Implementations are required to be thread safe.</p>
 * 
 * @poseidon-object-id [Im36376badm10e13f61c4dmm6a32]
 */
public interface RSSWriter {
/**
 * <p>Purpose: Writes the given feed into the given character stream.</p>
 * <p>Args: feed - The feed to be written. Must not be null.</p>
 * <p>destination - The character stream to which the feed must be written. Must not be null.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: RSSWriteException - If any exception occurs during the writing of the feed.</p>
 * <p>IllegalArgumentException - If either feed or destination is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm4efc]
 * @param feed 
 * @param destination 
 */
    public void writeFeed(com.topcoder.util.rssgenerator.RSSFeed feed, java.io.Writer destination);
/**
 * <p>Purpose: Writes the given item into the given character stream.</p>
 * <p>Args: item - The item to be written. Must not be null.</p>
 * <p>destination - The character stream to which the item must be written. Must not be null.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: RSSWriteException - If any exception occurs during the writing of the item.</p>
 * <p>IllegalArgumentException - If either item or destination is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm4e92]
 * @param item 
 * @param destination 
 */
    public void writeItem(com.topcoder.util.rssgenerator.RSSItem item, java.io.Writer destination) throws RSSWriteException;
}


