
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class acts as the base class for all classes which wish to implement the sub-interfaces of the RSSObject interface using decoration. This class itself implements the RSSObject interface by decorating over an RSSObject instance and implementing all methods of the interface (as well as java.io.Externalizable) by passing the method calls to the underlying object. A class wishing to implement a sub-interface of the RSSObject interface can extend this class and only implement the methods added by the specific sub-interface. <p>Implementation: We store a reference to the underlying RSSObject over which we are decorating. All calls to the methods of RSSObject and Externalizable are passed to the underlying object. Two methods to get and set the underlying object are added.</p> <p>Thread Safety: This class is not thread safe as it is mutable.</p>
 *
 */
public class BaseRSSObjectDecorator implements com.topcoder.util.rssgenerator.RSSObject {
    private String elementText;


/**
 * <p>Purpose: This variable represents the underlying RSSObject instance over which we are decorating. It is not frozen as it may be set using the setObject method. It may be retrieved using the getObject method. It is initialized to a null reference. However, if it is null when a method of the RSSObject interface is called, an IllegalStateException will be thrown. All the methods of this class use this variable.</p>
 *
 */
    private com.topcoder.util.rssgenerator.RSSObject object = null;

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSObject interface will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Nothing needs to be done.</p> <p>Exceptions: None.</p>
 *
 */
    public  BaseRSSObjectDecorator() {
        // your code here
    }

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call setObject(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 *
 *
 * @param object
 */
    public  BaseRSSObjectDecorator(com.topcoder.util.rssgenerator.RSSObject object) {
        // your code here
    }

/**
 * <p>Purpose: This method returns the underlying object being decorated.</p> <p>Args: None.</p> <p>Implementation: Simply delegate the call to the underlying object.</p> <p>Returns: The underlying object being decorated.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSObject getObject() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns the text-content of the element represented by this RSSObject.</p> <p>Args: None.</p> <p>Implementation: Simply return the member variable.</p> <p>Returns: The text-content of the element represented by this RSSObject.</p> <p>Exceptions: None.</p>
 *
 *
 * @param object
 */
    public void setObject(com.topcoder.util.rssgenerator.RSSObject object) {
        // your code here
    }

/**
 * <p>Purpose: This method returns the name of the element represented by this RSSObject.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: The name of the element represented by this RSSObject.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String getElementName() {
        return null;
    }

/**
 * <p>Purpose: This method returns the text-content of the element represented by this RSSObject.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: The text-content of the element represented by this RSSObject.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String getElementText() {
        // your code here
        return elementText;
    }

/**
 * <p>Purpose: This method returns the value of the desired attribute. A null will be returned if the attribute does not exist.</p> <p>Args: name - The name of the desired attribute. Must not be null or empty.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: The value of the desired attribute.</p> <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 * @return
 */
    public String getAttribute(String name) {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns the names of all the attributes of this RSSObject which have a non-null value.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: The names of all the attributes of this RSSObject which have a non-null value.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String[] getAttributeNames() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns the child element under the desired name. A null will be returned if none exists. If more than one child element exists under the same name, the first of these (as defined by the underlying implementation) will be returned.</p> <p>Args: name - The name under which the child element exists. Must not be null or empty.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: The child element under the desired name.</p> <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSObject getChildElement(String name) {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns all the child elements under the desired name.</p> <p>Args: name - The name under which the child elements exist. Must not be null or empty.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: All the child elements under the desired name.</p> <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSObject[] getChildElements(String name) {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns all names under which at least one child element exists.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: All names under which at least one child element exists.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String[] getAllChildElementNames() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns all child elements of this RSSObject.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: All child elements of this RSSObject.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSObject[] getAllChildElements() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method sets the name of the element represented by this RSSObject.</p> <p>Args: name - The desired name of the element represented by this RSSObject. Must not be null or empty.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 */
    public void setElementName(String name) {
        // your code here
    }

/**
 * <p>Purpose: This method sets the text-content of the element represented by this RSSObject.</p> <p>Args: name - The desired text-content of the element represented by this RSSObject. Possibly null or empty.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param text
 */
    public void setElementText(String text) {
        this.elementText = text;
    }

/**
 * <p>Purpose: This method sets the value of the desired attribute.</p> <p>Args: name - The name of the attribute whose value is to be set. Must not be null or empty.</p> <p>value - The value desired of the attribute. Possibly null or empty.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 * @param value
 */
    public void setAttribute(String name, String value) {
        // your code here
    }

/**
 * <p>Purpose: This method clears all attributes of this RSSObject.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 */
    public void clearAttributes() {
        // your code here
    }

/**
 * <p>Purpose: This method sets the child element under the given name. Any existing child elements under the name are replaced by the given element.</p> <p>Args: name - The name under which the child element must be set. Must not be null or empty.</p> <p>element - The element to be set under that name. Possibly null.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 * @param element
 */
    public void setChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element) {
        // your code here
    }

/**
 * <p>Purpose: This method sets the child elements under the given name. Any existing child elements under the name are replaced by the given elements.</p> <p>Args: name - The name under which the child elements must be set. Must not be null or empty.</p> <p>elements - The elements to be set under that name. Possibly null.&nbsp;All of the elements of the array must be non-null.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty, or if elements contains a null element.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 * @param elements
 */
    public void setChildElements(String name, com.topcoder.util.rssgenerator.RSSObject[] elements) {
        // your code here
    }

/**
 * <p>Purpose: This method adds a child element under the given name.</p> <p>Args: name - The name under which the child element must be added. Must not be null or empty.</p> <p>element - The element to be added under that name. Must not be null.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty, or the element is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 * @param element
 */
    public void addChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element) {
        // your code here
    }

/**
 * <p>Purpose: This method removes a child element from under the given name.</p> <p>Args: name - The name under which the child element must be removed from. Must not be null or empty.</p> <p>element - The element to be removed, from under that name. Must not be null.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty, or the element is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 * @param element
 */
    public void removeChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element) {
        // your code here
    }

/**
 * <p>Purpose: This method clears all child elements under the given name.</p> <p>Args: name - The name from which all child element must be cleared. Must not be null or empty.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 */
    public void clearChildElements(String name) {
        // your code here
    }

/**
 * <p>Purpose: This method clears all child elements of this RSSObject.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 */
    public void clearAllChildElements() {
        // your code here
    }

/**
 * <p>Purpose: This method returns the Base URI property of this object.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: The base URI property of this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public java.net.URI getBaseUri() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns the language property of this object.</p> <p>Args: None.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: The language property of this object.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String getLanguage() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method sets the Base URI property of this object.</p> <p>Args: baseUri - The Base URI desired. Possibly null.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param baseUri
 */
    public void setBaseUri(java.net.URI baseUri) {
        // your code here
    }

/**
 * <p>Purpose: This method sets the language property of this object.</p> <p>Args: language - The language desired. Possibly null or empty.</p> <p>Implementation: Simply delegate to the underlying object.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param language
 */
    public void setLanguage(String language) {
        // your code here
    }

/**
 * <p>Purpose: Reconstructs this object from its externalized form.</p> <p>Args: input - The ObjectInput where this object's externalized data exists. Must not be null.</p> <p>Implementation: Do setObject((RSSObject) input.readObject())</p> <p>Returns: None.</p> <p>Exceptions: IOException - Thrown by method calls to ObjectInput.</p> <p>IllegalArgumentException - If input is null.</p>
 *
 *
 * @param input
 */
    public void readExternal(java.io.ObjectInput input) {
        // your code here
    }

/**
 * <p>Purpose: Externalizes this object so that it can be later read back.</p> <p>Args: output - The ObjectOutput to which this object's externalized form is to be written. Must not be null.</p> <p>Implementation: Do output.writeObject(object)</p> <p>Returns: None.</p> <p>Exceptions: IOException - May occur while writing to the ObjectOutput.</p> <p>IllegalArgumentException - If output is null.</p> <p>IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param output
 */
    public void writeExternal(java.io.ObjectOutput output) {
        // your code here
    }
/**
 *
 *
 */
    public com.topcoder.util.rssgenerator.RSSObject rSSObject;
 }
