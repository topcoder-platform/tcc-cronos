
package com.topcoder.util.rssgenerator.impl;

import com.topcoder.util.rssgenerator.RSSCategory;
import com.topcoder.util.rssgenerator.RSSLink;
import com.topcoder.util.rssgenerator.RSSText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Purpose: This class implements the RSSBaseItem interface by decorating over an RSSObject instance. <p>Implementation: We extend the BaseRSSObjectDecorator&nbsp; class so that we only have to implement the functions specific to RSSBaseItem. These functions are implemented by decorating over child elements of the RSSObject instance. Please refer to CS 1.3.1 to see how it works.</p> <p>Thread Safety: This class is not thread safe despite having no variables, as its base class is mutable.</p>
 *
 */
public class RSSBaseItemImpl extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator implements com.topcoder.util.rssgenerator.RSSBaseItem {
    private String id;

    private List categories = new ArrayList();

    private RSSLink selfLink;

    private RSSText description;

    private Date publishedDate;

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSObject or RSSBaseItem interfaces will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 *
 */
    public  RSSBaseItemImpl() {
        // your code here
    }

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 *
 *
 * @param object
 */
    public  RSSBaseItemImpl(com.topcoder.util.rssgenerator.RSSObject object) {
        // your code here
    }

/**
 * <p>Purpose: This method returns the ID of this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:id&quot;.</p> <p>Returns: The ID of this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String getId() {
        return id;
    }

/**
 * <p>Purpose: This method returns the title of this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:title&quot;.</p> <p>Returns: The title of this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSText getTitle() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns the link where this object may be found.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:selfLink&quot;.</p> <p>Returns: The link where this object may be found.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSLink getSelfLink() {
        return selfLink;
    }

/**
 * <p>Purpose: This method returns all links associated with this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p> <p>Returns: All links that are part of this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSLink[] getLinks() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns the description of this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:description&quot;.</p> <p>Returns: The description of this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSText getDescription() {
        return description;
    }

/**
 * <p>Purpose: This method returns the copyright-text of this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:copyright&quot;.</p> <p>Returns: The copyright-text of this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSText getCopyright() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns the date associated with the publishing of this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:published&quot;.</p> <p>Returns: The date associated with the publishing of this object.</p> <p>Exceptions:IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public Date getPublishedDate() {
        return this.publishedDate;
    }

/**
 * <p>Purpose: This method returns the date when this object was last updated.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:updated&quot;.</p> <p>Returns: The date when this object was last updated.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public java.util.Date getUpdatedDate() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns all authors associated with this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:author&quot;.</p> <p>Returns: All authors associated with this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSEntity[] getAuthors() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns all categories associated with this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:category&quot;.</p> <p>Returns: All categories associated with this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public RSSCategory[] getCategories() {
        return (RSSCategory[]) categories.toArray(new RSSCategory[categories.size()]);
    }

/**
 * <p>Purpose: This method sets the ID of this object. Note that while non-null and non-empty IDs are expected, many items/feeds may not have IDs and thus may have a null or empty value here. In this case the ID is not really an identifier and an ID may be generated when there is need for one.</p> <p>Args: id - The desired ID of this object. Possibly null indicating no ID or empty.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:id&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param id
 */
    public void setId(String id) {
        this.id = id;
    }

/**
 * <p>Purpose: This method sets the title of this object.</p> <p>Args: title - The desired title of this object. Possibly null indicating no title.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:title&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param title
 */
    public void setTitle(com.topcoder.util.rssgenerator.RSSText title) {
        // your code here
    }

/**
 * <p>Purpose: This method sets the link that points to where this object may be found.</p> <p>Args: selfLink - The link where this object may be found. Possibly null indicating no such link.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:selfLink&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param selfLink
 */
    public void setSelfLink(com.topcoder.util.rssgenerator.RSSLink selfLink) {
        this.selfLink = selfLink;
    }

/**
 * <p>Purpose: This method sets all the links associated with this object.</p> <p>Args: links - The array of links to be associated with this object. Possibly null implying an empty array. All of the elements of the array must be non-null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param links
 */
    public void setLinks(com.topcoder.util.rssgenerator.RSSLink[] links) {
        // your code here
    }

/**
 * <p>Purpose: This method adds to the links associated with this object.</p> <p>Args: link - The link to be added to those associated with this object. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If link is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param link
 */
    public void addLink(com.topcoder.util.rssgenerator.RSSLink link) {
        // your code here
    }

/**
 * <p>Purpose: This method removes from the links associated with this object.</p> <p>Args: link - The link to be removed from those associated with this object. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If link is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param link
 */
    public void removeLink(com.topcoder.util.rssgenerator.RSSLink link) {
        // your code here
    }

/**
 * <p>Purpose: This method clears all links associated with this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 */
    public void clearLinks() {
        // your code here
    }

/**
 * <p>Purpose: This method sets the description of this object.</p> <p>Args: description - The desired description of this object. Possibly null indicating no description.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:description&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param description
 */
    public void setDescription(RSSText description) {
        this.description = description;
    }

/**
 * <p>Purpose: This method sets the copyright-text of this object.</p> <p>Args: description - The desired copyright-text of this object. Possibly null indicating no copyright-text.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:copyright&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param copyright
 */
    public void setCopyright(com.topcoder.util.rssgenerator.RSSText copyright) {
        // your code here
    }

/**
 * <p>Purpose: This method sets the date associated with the publishing of this object.</p> <p>Args: publishedDate - The desired the date associated with the publishing of this object. Possibly null indicating no publishing date.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:published&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param publishedDate
 */
    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

/**
 * <p>Purpose: This method sets the date when this object was last updated.</p> <p>Args: updatedDate - The desired date when this object was last updated. Possibly null indicating no last-update date.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:updated&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param updatedDate
 */
    public void setUpdatedDate(java.util.Date updatedDate) {
        // your code here
    }

/**
 * <p>Purpose: This method sets all the authors associated with this object.</p> <p>Args: links - The array of authors to be associated with this object. Possibly null implying an empty array. All of the elements of the array must be non-null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:author&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param authors
 */
    public void setAuthors(com.topcoder.util.rssgenerator.RSSEntity[] authors) {
        // your code here
    }

/**
 * <p>Purpose: This method adds to the authors associated with this object.</p> <p>Args: author - The author to be added to those associated with this object. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:author&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If author is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param author
 */
    public void addAuthor(com.topcoder.util.rssgenerator.RSSEntity author) {
        // your code here
    }

/**
 * <p>Purpose: This method removes from the authors associated with this object.</p> <p>Args: author - The author to be removed from those associated with this object. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:author&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If author is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param author
 */
    public void removeAuthor(com.topcoder.util.rssgenerator.RSSEntity author) {
        // your code here
    }

/**
 * <p>Purpose: This method clears all authors associated with this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:author&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 */
    public void clearAuthors() {
        // your code here
    }

/**
 * <p>Purpose: This method sets all the categories associated with this object.</p> <p>Args: categories - The array of categories to be associated with this object. Possibly null implying an empty array. All of the elements of the array must be non-null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:category&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param categories
 */
    public void setCategories(com.topcoder.util.rssgenerator.RSSCategory[] categories) {
        // your code here
    }

/**
 * <p>Purpose: This method adds to the categories associated with this object.</p> <p>Args: category - The category to be added to those associated with this object. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:category&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If category is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param category
 */
    public void addCategory(com.topcoder.util.rssgenerator.RSSCategory category) {
        categories.add(category);
    }

/**
 * <p>Purpose: This method removes from the categories associated with this object.</p> <p>Args: category - The category to be removed from those associated with this object. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:category&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If category is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param category
 */
    public void removeCategory(com.topcoder.util.rssgenerator.RSSCategory category) {
        // your code here
    }

/**
 * <p>Purpose: This method clears all categories associated with this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:category&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 */
    public void clearCategories() {
        // your code here
    }

/**
 * <p>Purpose: This method returns all extension elements associated with this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:extension&quot;.</p> <p>Returns: All extension elements associated with this object.</p> <p>Exceptions: None.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSObject[] getExtensionElements() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method sets all the extension elements associated with this object.</p> <p>Args: elements - The array of extension elements to be associated with this object. Possibly null implying an empty array. All of the elements of the array must be non-null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:extension&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 *
 *
 * @param elements
 */
    public void setExtensionElements(com.topcoder.util.rssgenerator.RSSObject[] elements) {
        // your code here
    }

/**
 * <p>Purpose: This method adds to the extension elements associated with this object.</p> <p>Args: element - The extension element to be added to those associated with this object. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:extension&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If element is null.</p>
 *
 *
 * @param element
 */
    public void addExtensionElement(com.topcoder.util.rssgenerator.RSSObject element) {
        // your code here
    }

/**
 * <p>Purpose: This method removes from the extension elements associated with this object.</p> <p>Args: element - The extension element to be rmoved from those associated with this object. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:extension&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If element is null.</p> <p></p>
 *
 *
 * @param element
 */
    public void removeExtensionElement(com.topcoder.util.rssgenerator.RSSObject element) {
        // your code here
    }

/**
 * <p>Purpose: This method clears all extension elements associated with this object.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:extension&quot;.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 *
 */
    public void clearExtensionElements() {
        // your code here
    }
 }
