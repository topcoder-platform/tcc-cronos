
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSObject interface to represent a Link object. It adds get/set methods for 6 properties of the link - The URI the link points to, the relationship of the link (self, alternate, enclosure, etc.), the mime-type of the content pointed to by the link, the language of the content, a title for the link, and the length of the content. Corresponds to an atom:link element. There is no RSS 2.0 analogue although the enclosure and link elements can be mapped. <p>Implementation: Implementations might use either member variables or decoration over existing properties of the RSSObject to provide the additional properties.</p> <p>Thread Safety: Implementations are not required to be thread safe.</p>
 * 
 */
public interface RSSLink extends com.topcoder.util.rssgenerator.RSSObject {
/**
 * <p>Purpose: This method returns the URL where this RSSLink points to.</p> <p>Args: None.</p> <p>Returns: The URL where this RSSLink points to.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public java.net.URI getLink();
/**
 * <p>Purpose: This method returns the relationship of the link.</p> <p>Args: None.</p> <p>Returns: The relationship of the link.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getRelationship();
/**
 * <p>Purpose: This method returns the mime-type of the content at the link.</p> <p>Args: None.</p> <p>Returns: The mime-type of the content at the link.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getMimeType();
/**
 * <p>Purpose: This method returns the language of the content at the link.</p> <p>Args: None.</p> <p>Returns: The language of the content at the link.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getLinkLanguage();
/**
 * <p>Purpose: This method returns the title of the link.</p> <p>Args: None.</p> <p>Returns: The title of the link.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getTitle();
/**
 * <p>Purpose: This method returns the length of the content at the link.</p> <p>Args: None.</p> <p>Returns: The length of the content at the link.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public Integer getLength();
/**
 * <p>Purpose: This method sets the URL where this RSSLink points to.</p> <p>Args: link - The URL where this RSSLink should point to. Possibly null indicating the link property should be removed.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param link 
 */
    public void setLink(java.net.URI link);
/**
 * <p>Purpose: This method sets the relationship of this link.</p> <p>Args: relationship - The relationship of this link. Possibly null indicating the relationship property should be removed or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param relationship 
 */
    public void setRelationship(String relationship);
/**
 * <p>Purpose: This method sets the mime-type of the content at this link.</p> <p>Args: mimeType - The mime-type of the content at this link. Possibly null indicating the mime-type property should be removed or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param mimeType 
 */
    public void setMimeType(String mimeType);
/**
 * <p>Purpose: This method sets the language of the content at this link.</p> <p>Args: mimeType - The language of the content at this link. Possibly null indicating the mime-type property should be removed or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param language 
 */
    public void setLinkLanguage(String language);
/**
 * <p>Purpose: This method sets the title of this link.</p> <p>Args: title - The title of this link. Possibly null indicating the title property should be removed or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param title 
 */
    public void setTitle(String title);
/**
 * <p>Purpose: This method sets the length of the content at this link.</p> <p>Args: length - The length of the content at this link. Possibly null indicating the length property should be removed.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param length 
 */
    public void setLength(Integer length);
}


