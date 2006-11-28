
package com.topcoder.util.rssgenerator.io;
import com.topcoder.util.rssgenerator.io.RSSParseException;
/**
 * Purpose: This interface provides the contract for parsing a character stream containing RSS feeds or items . The contract consists of only two methods - one for parsing feeds and one for parsing items. <p>Implementation: Implementations would parse streams of particular formats - RSS 2.0 or Atom 1.0 say - into RSSFeed and RSSItem instances. The steps involved in the actual parsing are format dependent of course.</p> <p>Thread Safety: Implementations are required to be thread safe.</p>
 * 
 */
public interface RSSParser {
/**
 * <p>Purpose: This method parses the given character stream into an RSSFeed object and returns it.</p> <p>Args: source - The character stream containing the RSS feed data. Must not be null.</p> <p>Returns: An RSSFeed object, created by parsing the given stream.</p> <p>Exceptions: RSSParseException - If any exception occurs during the parsing.</p> <p>IllegalArgumentException - If source is null.</p>
 * 
 * 
 * @param source 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSFeed parseFeed(java.io.Reader source);
/**
 * <p>Purpose: This method parses the given character stream into an RSSItem object and returns it.</p> <p>Args: source - The character stream containing the RSS item data. Must not be null.</p> <p>Returns: An RSSItem object, created by parsing the given stream.</p> <p>Exceptions: RSSParseException - If any exception occurs during the parsing.</p> <p>IllegalArgumentException - If source is null.</p>
 * 
 * 
 * @param source 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSItem parseItem(java.io.Reader source);
}


