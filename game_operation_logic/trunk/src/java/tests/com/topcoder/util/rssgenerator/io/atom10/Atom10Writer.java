
package com.topcoder.util.rssgenerator.io.atom10;
import java.io.IOException;

import com.topcoder.util.rssgenerator.io.RSSWriteException;

/**
 * Purpose: This class writes out streams in the Atom 1.0 format. It implements the RSSWriter contract and writes character data in the XML format.
 * <p>Implementation: The two methods of the contract are implemented using writing algorithms tailored to the Atom 1.0 format.</p>
 * <p>Thread Safety: This class is thread safe as it has no state.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm538d]
 */
public class Atom10Writer implements com.topcoder.util.rssgenerator.io.RSSWriter {

/**
 * <p>Purpose: Constructs this object.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Nothing needs to be done.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm52d7]
 */
    public  Atom10Writer() {        
        // your code here
    } 

/**
 * <p>Purpose: Writes the given feed into the given character stream.</p>
 * <p>Args: feed - The feed to be written. Must not be null.</p>
 * <p>destination - The character stream to which the feed must be written. Must not be null.</p>
 * <p>Implementation: Please refer to the CS 1.3.8</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: RSSWriteException - If any exception occurs during the writing of the feed.</p>
 * <p>IllegalArgumentException - If either feed or destination is null.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm52bf]
 * @param feed 
 * @param destination 
 */
    public void writeFeed(com.topcoder.util.rssgenerator.RSSFeed feed, java.io.Writer destination) {        
        // your code here
    } 

/**
 * <p>Purpose: Writes the given item into the given character stream.</p>
 * <p>Args: item - The item to be written. Must not be null.</p>
 * <p>destination - The character stream to which the item must be written. Must not be null.</p>
 * <p>Implementation: Please refer to the CS 1.3.9</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: RSSWriteException - If any exception occurs during the writing of the item.</p>
 * <p>IllegalArgumentException - If either item or destination is null.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm52a7]
 * @param item 
 * @param destination 
 */
    public void writeItem(com.topcoder.util.rssgenerator.RSSItem item, java.io.Writer destination) throws RSSWriteException{        
        try {
			destination.write(item.getId());
		} catch (IOException e) {
			throw new RSSWriteException("",e);
		}
    } 
 }
