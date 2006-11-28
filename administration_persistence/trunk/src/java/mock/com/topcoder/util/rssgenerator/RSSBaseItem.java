
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSObject interface to represent a base object that has the common properties of both items as well as feeds. The interface adds get/set methods for a host of properties including a few multi-values ones. These are Id, Title, Self-link, Links*, Description, Copyright, Published Date, Updated Date, Authors* and Categories*. (* = multi-valued) While some of these properties are simple objects, others are themseleves extensions of the RSSObject interface. <p>Implementation: Implementations would use decoration over existing properties of the RSSObject interface or add member variables to store the properties required.</p> <p>Thread Safety: Implementations are not required to be thread safe.</p>
 * 
 */
public interface RSSBaseItem extends com.topcoder.util.rssgenerator.RSSObject {
/**
 * <p>Purpose: This method returns the ID of this object.</p> <p>Args: None.</p> <p>Returns: The ID of this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getId();
/**
 * <p>Purpose: This method returns the title of this object.</p> <p>Args: None.</p> <p>Returns: The title of this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSText getTitle();
/**
 * <p>Purpose: This method returns the link where this object may be found.</p> <p>Args: None.</p> <p>Returns: The link where this object may be found.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSLink getSelfLink();
/**
 * <p>Purpose: This method returns all links associated with this object.</p> <p>Args: None.</p> <p>Returns: All links that are part of this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSLink[] getLinks();
/**
 * <p>Purpose: This method returns the description of this object.</p> <p>Args: None.</p> <p>Returns: The description of this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSText getDescription();
/**
 * <p>Purpose: This method returns the copyright-text of this object.</p> <p>Args: None.</p> <p>Returns: The copyright-text of this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSText getCopyright();
/**
 * <p>Purpose: This method returns the date associated with the publishing of this object.</p> <p>Args: None.</p> <p>Returns: The date associated with the publishing of this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public java.util.Date getPublishedDate();
/**
 * <p>Purpose: This method returns the date when this object was last updated.</p> <p>Args: None.</p> <p>Returns: The date when this object was last updated.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public java.util.Date getUpdatedDate();
/**
 * <p>Purpose: This method returns all authors associated with this object.</p> <p>Args: None.</p> <p>Returns: All authors associated with this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSEntity[] getAuthors();
/**
 * <p>Purpose: This method returns all categories associated with this object.</p> <p>Args: None.</p> <p>Returns: All categories associated with this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSCategory[] getCategories();
/**
 * <p>Purpose: This method sets the ID of this object.&nbsp;Note that while non-null and non-empty IDs are expected, many items/feeds may not have IDs and thus may have a null or empty value here. In this case the ID is not really an identifier and an ID may be generated when there is need for one.</p> <p>Args: id - The desired ID of this object. Possibly null indicating no ID or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param id 
 */
    public void setId(String id);
/**
 * <p>Purpose: This method sets the title of this object.</p> <p>Args: title - The desired title of this object. Possibly null indicating no title.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param title 
 */
    public void setTitle(com.topcoder.util.rssgenerator.RSSText title);
/**
 * <p>Purpose: This method sets the link that points to where this object may be found.</p> <p>Args: selfLink - The link where this object may be found. Possibly null indicating no such link.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param selfLink 
 */
    public void setSelfLink(com.topcoder.util.rssgenerator.RSSLink selfLink);
/**
 * <p>Purpose: This method sets all the links associated with this object.</p> <p>Args: links - The array of links to be associated with this object. Possibly null implying an empty array. All of the elements of the array must be non-null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 * 
 * 
 * @param links 
 */
    public void setLinks(com.topcoder.util.rssgenerator.RSSLink[] links);
/**
 * <p>Purpose: This method adds to the links associated with this object.</p> <p>Args: link - The link to be added to those associated with this object. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If link is null.</p>
 * 
 * 
 * @param link 
 */
    public void addLink(com.topcoder.util.rssgenerator.RSSLink link);
/**
 * <p>Purpose: This method removes from the links associated with this object.</p> <p>Args: link - The link to be removed from those associated with this object. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If link is null.</p>
 * 
 * 
 * @param link 
 */
    public void removeLink(com.topcoder.util.rssgenerator.RSSLink link);
/**
 * <p>Purpose: This method clears all links associated with this object.</p> <p>Args: None.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 */
    public void clearLinks();
/**
 * <p>Purpose: This method sets the description of this object.</p> <p>Args: description - The desired description of this object. Possibly null indicating no description.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param description 
 */
    public void setDescription(com.topcoder.util.rssgenerator.RSSText description);
/**
 * <p>Purpose: This method sets the copyright-text of this object.</p> <p>Args: description - The desired copyright-text of this object. Possibly null indicating no copyright-text.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param copyright 
 */
    public void setCopyright(com.topcoder.util.rssgenerator.RSSText copyright);
/**
 * <p>Purpose: This method sets the date associated with the publishing of this object.</p> <p>Args: publishedDate - The desired the date associated with the publishing of this object. Possibly null indicating no publishing date.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param publishedDate 
 */
    public void setPublishedDate(java.util.Date publishedDate);
/**
 * <p>Purpose: This method sets the date when this object was last updated.</p> <p>Args: updatedDate - The desired date when this object was last updated. Possibly null indicating no last-update date.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param updatedDate 
 */
    public void setUpdatedDate(java.util.Date updatedDate);
/**
 * <p>Purpose: This method sets all the authors associated with this object.</p> <p>Args: links - The array of authors to be associated with this object. Possibly null implying an empty array. All of the elements of the array must be non-null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 * 
 * 
 * @param authors 
 */
    public void setAuthors(com.topcoder.util.rssgenerator.RSSEntity[] authors);
/**
 * <p>Purpose: This method adds to the authors associated with this object.</p> <p>Args: author - The author to be added to those associated with this object. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If author is null.</p>
 * 
 * 
 * @param author 
 */
    public void addAuthor(com.topcoder.util.rssgenerator.RSSEntity author);
/**
 * <p>Purpose: This method removes from the authors associated with this object.</p> <p>Args: author - The author to be removed from those associated with this object. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If author is null.</p>
 * 
 * 
 * @param author 
 */
    public void removeAuthor(com.topcoder.util.rssgenerator.RSSEntity author);
/**
 * <p>Purpose: This method clears all authors associated with this object.</p> <p>Args: None.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 */
    public void clearAuthors();
/**
 * <p>Purpose: This method sets all the categories associated with this object.</p> <p>Args: categories - The array of categories to be associated with this object. Possibly null implying an empty array. All of the elements of the array must be non-null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 * 
 * 
 * @param categories 
 */
    public void setCategories(com.topcoder.util.rssgenerator.RSSCategory[] categories);
/**
 * <p>Purpose: This method adds to the categories associated with this object.</p> <p>Args: category - The category to be added to those associated with this object. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If category is null.</p>
 * 
 * 
 * @param category 
 */
    public void addCategory(com.topcoder.util.rssgenerator.RSSCategory category);
/**
 * <p>Purpose: This method removes from the categories associated with this object.</p> <p>Args: category - The category to be removed from those associated with this object. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If category is null.</p>
 * 
 * 
 * @param category 
 */
    public void removeCategory(com.topcoder.util.rssgenerator.RSSCategory category);
/**
 * <p>Purpose: This method clears all categories associated with this object.</p> <p>Args: None.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 */
    public void clearCategories();
/**
 * <p>Purpose: This method returns all extension elements associated with this object.</p> <p>Args: None.</p> <p>Returns: All extension elements associated with this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSObject[] getExtensionElements();
/**
 * <p>Purpose: This method sets all the extension elements associated with this object.</p> <p>Args: elements - The array of extension elements to be associated with this object. Possibly null implying an empty array. All of the elements of the array must be non-null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 * 
 * 
 * @param elements 
 */
    public void setExtensionElements(com.topcoder.util.rssgenerator.RSSObject[] elements);
/**
 * <p>Purpose: This method adds to the extension elements associated with this object.</p> <p>Args: element - The extension element to be added to those associated with this object. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If element is null.</p>
 * 
 * 
 * @param element 
 */
    public void addExtensionElement(com.topcoder.util.rssgenerator.RSSObject element);
/**
 * <p>Purpose: This method removes from the extension elements associated with this object.</p> <p>Args: element - The extension element to be rmoved from those associated with this object. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If element is null.</p>
 * 
 * 
 * @param element 
 */
    public void removeExtensionElement(com.topcoder.util.rssgenerator.RSSObject element);
/**
 * <p>Purpose: This method clears all extension elements associated with this object.</p> <p>Args: None.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 */
    public void clearExtensionElements();
}


