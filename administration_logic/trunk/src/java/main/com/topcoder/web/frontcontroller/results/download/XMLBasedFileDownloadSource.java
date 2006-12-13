
package com.topcoder.web.frontcontroller.results.download;
import java.util.HashMap;

import com.topcoder.web.frontcontroller.results.DownloadDataRetrievalException;
import org.w3c.dom.*;

/**
 * <p>
 * This class implements the DownloadSource interface and returns DownloadData objects for the 
 * requested download ID based on the information loaded from an XML based configuration file.
 * The schema for the XML configuration file is as defined in section 1.3.13 of the component 
 * specification. All the information from the configuration file is loaded in the constructor
 * and the DownloadData objects for them are created and stored in the "idDownloadDataMapping" Map instance
 * member.
 * This class is thread-safe as its only instance member is immutable.
 * </p>
 * 
 */
public class XMLBasedFileDownloadSource implements com.topcoder.web.frontcontroller.results.DownloadSource {

/**
 * <p>
 * Stores the Mapping from the download ID to the DownloadData mapping. The key in the Map will be an 
 * instance of String representing the download id and the value corresponding to it will be an instance of
 * DownloadData (more specifically SimpleDownloadData instance). This Map is initialized in place 
 * and populated with the values read from the XML configuration file in the constructor and is immutable
 * later on. Both the key or the value in the Map cannot be null.
 * </p>
 * 
 */
    private final java.util.Map idDownloadDataMapping = new HashMap();


/**
 * <p>
 * Constructor. Populates the "idDownloadDataMapping" instance member from the values read from the xml element passed 
 * as mentioned in the implementation hint below:
 * 
 * Implementation Hint:
 * -------------------
 * a. Read the value of the attribute "url" from the sub-element "config-file" of the "element" parameter passed.
 * b. Read and parse the XML file specified by the location obtained in step 1. (the XML schema is expected to be as defined in section 1.3.13 of the component specification).
 * c. Create SimpleDownloadData instances from the value of the each of the downloadData read from the CML configuration file.
 *    and storing the Mapping in the "idDownloadDataMapping" Map instance member.
 * </p>
 * 
 * 
 * @param element the DOM Element whose sub-element will specify the URL of the XML configuration file
 * @throws IllegalArgumentException: if the parameter "element" is null or if there is an exception or required entities are found to be missing during the parsing of the XML file.
 */
    public  XMLBasedFileDownloadSource(org.w3c.dom.Element element) {        
        

    } 

/**
 * <p>
 * Returns the instance of DownloadData corresponding to the ID passed.
 * If on lookup in the "idDownloadDataMapping" Map a null is returned, then a DownloadDataRetrievalException is thrown,
 * else it simply returns the looked up value.
 * </p>
 * 
 * 
 * @param id the id for the DownloadData
 * @return the DownloadData corresponding to the id
 * @throws DownloadDataRetrievalException: if no DownloadData for the specified ID is found
 * @throws IllegalArgumentException: if the parameter "id" is null or an empty String on trimming
 */
    public com.topcoder.web.frontcontroller.results.DownloadData getDownloadData(String id) throws com.topcoder.web.frontcontroller.results.DownloadDataRetrievalException {        
        // your code here
        return null;
    } 
 }
