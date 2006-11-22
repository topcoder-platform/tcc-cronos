
package com.topcoder.util.rssgenerator.io.atom10;
import com.topcoder.util.rssgenerator.*;
import com.topcoder.util.rssgenerator.impl.*;
import com.topcoder.util.rssgenerator.impl.atom10.*;

/**
 * Purpose: This class parses streams in the Atom 1.0 format. It implements the RSSParser contract and assumes the stream to be character data in the XML format.
 * <p>Implementation: The two methods of the contract are implemented using parsing algorithms tailored to the Atom 1.0 format. We use the Lightweight XML Parser component to parse XML data.</p>
 * <p>Thread Safety: This class is thread safe as it has no state.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm53a1]
 */
public class Atom10Parser implements com.topcoder.util.rssgenerator.io.RSSParser {

/**
 * <p>Purpose: Constructs this object.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Nothing needs to be done.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm5379]
 */
    public  Atom10Parser() {        
        // your code here
    } 

/**
 * <p>Purpose: This method parses the given character stream into an RSSFeed object and returns it.</p>
 * <p>Args: source - The character stream containing the RSS feed data. Must not be null.</p>
 * <p>Implementation: Please refer to the CS 1.3.6</p>
 * <p>Returns: An RSSFeed object, created by parsing the given stream.</p>
 * <p>Exceptions: RSSParseException - If any exception occurs during the parsing.</p>
 * <p>IllegalArgumentException - If source is null.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm5361]
 * @param source 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSFeed parseFeed(java.io.Reader source) {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method parses the given character stream into an RSSItem object and returns it.</p>
 * <p>Args: source - The character stream containing the RSS item data. Must not be null.</p>
 * <p>Implementation: Please refer to the CS 1.3.7</p>
 * <p>Returns: An RSSItem object, created by parsing the given stream.</p>
 * <p>Exceptions: RSSParseException - If any exception occurs during the parsing.</p>
 * <p>IllegalArgumentException - If source is null.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm5349]
 * @param source 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSItem parseItem(java.io.Reader source) {        
        // your code here
        return null;
    } 
 }
