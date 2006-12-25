
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSObject contract to represent an RSS Generator. It adds three properties - the name and version of the generator and a link to it. Mps to an atom;geenrator lement as well as the RSS 2.0 generator element.
 * <p>Implementation: Implementations might decorate over properties ofan RSSObject instance or use member variables to provide the properties.</p>
 * <p>Thread Safety: Implementations need not be thread safe.</p>
 * 
 * @poseidon-object-id [Im36376badm10e13f61c4dmm7ea2]
 */
public interface RSSGenerator extends com.topcoder.util.rssgenerator.RSSObject {
/**
 * <p>Purpose: This method returns the name of this generator.</p>
 * <p>Args: None.</p>
 * <p>Returns: The name of this generator.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm42eb]
 * @return 
 */
    public String getName();
/**
 * <p>Purpose: This method returns the version of this generator.</p>
 * <p>Args: None.</p>
 * <p>Returns: The version of this generator.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm42c6]
 * @return 
 */
    public String getVersion();
/**
 * <p>Purpose: This method returns the link to this generator.</p>
 * <p>Args: None.</p>
 * <p>Returns: The link to this generator.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm42a1]
 * @return 
 */
    public java.net.URI getLink();
/**
 * <p>Purpose: This method sets the name of this generator.</p>
 * <p>Args: name - The name of this generator. Possibly null indicating no name or empty.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm427c]
 * @param name 
 */
    public void setName(String name);
/**
 * <p>Purpose: This method sets the version of this generator.</p>
 * <p>Args: version - The version of this generator. Possibly null indicating no version-info or empty.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm423c]
 * @param version 
 */
    public void setVersion(String version);
/**
 * <p>Purpose: This method sets the link to this generator.</p>
 * <p>Args: link - The link to this generator. Possibly null indicating no link.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm41fc]
 * @param link 
 */
    public void setLink(java.net.URI link);
}


