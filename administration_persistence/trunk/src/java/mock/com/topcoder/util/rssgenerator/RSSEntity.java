
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSObject contract to represent an entity (person, corporation, etc.). It adds three properties - name, link and email-address. The contract consists of the get/set methods for these properties. Maps to an atom person construct. Other entities such as the RSS 2.0 author,managing editor and web master may also be mapped. <p>Implementation: Implementations might decorate over existsng properties of the RSSObject instance or add member variables to provide the new properties.</p> <p>Thread Safety: Implementations need not be thread safe.</p>
 * 
 */
public interface RSSEntity extends com.topcoder.util.rssgenerator.RSSObject {
/**
 * <p>Purpose: This method returns the name of this entity.</p> <p>Args: None.</p> <p>Returns: The name of this entity.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getName();
/**
 * <p>Purpose: This method returns the link to this entity.</p> <p>Args: None.</p> <p>Returns: The link to this entity.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public java.net.URI getLink();
/**
 * <p>Purpose: This method returns the email address of this entity.</p> <p>Args: None.</p> <p>Returns: The email address of this entity.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getEmailAddress();
/**
 * <p>Purpose: This method sets the name of this entity.</p> <p>Args: name - The name of this entity. Possibly null indicating no name or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param name 
 */
    public void setName(String name);
/**
 * <p>Purpose: This method sets the link to this entity.</p> <p>Args: link - The link of this entity. Possibly null indicating no link.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param link 
 */
    public void setLink(java.net.URI link);
/**
 * <p>Purpose: This method sets the email address of this entity.</p> <p>Args: emailAddress - The email address of this entity. Possibly null indicating no email-address or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param emailAddress 
 */
    public void setEmailAddress(String emailAddress);
}


