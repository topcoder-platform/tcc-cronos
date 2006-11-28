
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface abstracts out a generic XML element that is part of an RSS Feed/Entry. It lays the contract for an XML node, which has an element name, text content, a set of attributes and a list of child elements. In keeping with the fact that this is an abstraction of an XML element, attributes must have String values. Child elements are stored under keys, which (preferably)may or may not be the same as the name of the child element. The contract also provides for two basic properties expected of every RSSObject, a base URI property and a language property. The contract also complete control over the element and any attribute can be set or gotten. This interface extends java.io.Externalizable and all impementations must be externalizable so that they can be read from and written into persistence. <p>Implementation: Implementations might use Maps for easy access to attributes and child elements and member variables to store the name and content. The two required properties - base URI and language - might be provided either through member variables or through decoration over specific attributes/child elements. Externalization will involve some way of storing all of the properties and child elements, along with the element name and text. Externalization also requires that implementations have a no-args constructor.</p> <p>Thread Safety: Implementations are not required to be thread safe, since mutability of properties is required.</p>
 * 
 */
public interface RSSObject extends java.io.Externalizable {
/**
 * <p>Purpose: This method returns the name of the element represented by this RSSObject.</p> <p>Args: None.</p> <p>Returns: The name of the element represented by this RSSObject.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getElementName();
/**
 * <p>Purpose: This method returns the text-content of the element represented by this RSSObject.</p> <p>Args: None.</p> <p>Returns: The text-content of the element represented by this RSSObject.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getElementText();
/**
 * <p>Purpose: This method returns the value of the desired attribute. A null will be returned if the attribute does not exist.</p> <p>Args: name - The name of the desired attribute. Must not be null or empty.</p> <p>Returns: The value of the desired attribute.</p> <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p>
 * 
 * 
 * @param name 
 * @return 
 */
    public String getAttribute(String name);
/**
 * <p>Purpose: This method returns the names of all the attributes of this RSSObject which have a non-null value.</p> <p>Args: None.</p> <p>Returns: The names of all the attributes of this RSSObject which have a non-null value.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String[] getAttributeNames();
/**
 * <p>Purpose: This method returns the child element under the desired name. A null will be returned if none exists. If more than one child element exists under the same name, the first of these (as defined by the implementation) will be returned.</p> <p>Args: name - The name under which the child element exists. Must not be null or empty.</p> <p>Returns: The child element under the desired name.</p> <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p>
 * 
 * 
 * @param name 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSObject getChildElement(String name);
/**
 * <p>Purpose: This method returns all the child elements under the desired name.</p> <p>Args: name - The name under which the child elements exist. Must not be null or empty.</p> <p>Returns: All the child elements under the desired name.</p> <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p>
 * 
 * 
 * @param name 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSObject[] getChildElements(String name);
/**
 * <p>Purpose: This method returns all names under which at least one child element exists.</p> <p>Args: None.</p> <p>Returns: All names under which at least one child element exists.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String[] getAllChildElementNames();
/**
 * <p>Purpose: This method returns all child elements of this RSSObject.</p> <p>Args: None.</p> <p>Returns: All child elements of this RSSObject.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSObject[] getAllChildElements();
/**
 * <p>Purpose: This method sets the name of the element represented by this RSSObject.</p> <p>Args: name - The desired name of the element represented by this RSSObject. Must not be null or empty.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p>
 * 
 * 
 * @param name 
 */
    public void setElementName(String name);
/**
 * <p>Purpose: This method sets the text-content of the element represented by this RSSObject.</p> <p>Args: name - The desired text-content of the element represented by this RSSObject. Possibly null or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param text 
 */
    public void setElementText(String text);
/**
 * <p>Purpose: This method sets the value of the desired attribute.</p> <p>Args: name - The name of the attribute whose value is to be set. Must not be null or empty.</p> <p>value - The value desired of the attribute. Possibly null or empty.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p>
 * 
 * 
 * @param name 
 * @param value 
 */
    public void setAttribute(String name, String value);
/**
 * <p>Purpose: This method clears all attributes of this RSSObject.</p> <p>Args: None.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 */
    public void clearAttributes();
/**
 * <p>Purpose: This method sets the child element under the given name. Any existing child elements under the name are replaced by the given element.</p> <p>Args: name - The name under which the child element must be set. Must not be null or empty.</p> <p>element - The element to be set under that name. Possibly null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p>
 * 
 * 
 * @param name 
 * @param element 
 */
    public void setChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element);
/**
 * <p>Purpose: This method sets the child elements under the given name. Any existing child elements under the name are replaced by the given elements.</p> <p>Args: name - The name under which the child elements must be set. Must not be null or empty.</p> <p>elements - The elements to be set under that name. Possibly null.&nbsp;All of the elements of the array must be non-null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty, or if elements contains a null element.</p>
 * 
 * 
 * @param name 
 * @param elements 
 */
    public void setChildElements(String name, com.topcoder.util.rssgenerator.RSSObject[] elements);
/**
 * <p>Purpose: This method adds a child element under the given name.</p> <p>Args: name - The name under which the child element must be added. Must not be null or empty.</p> <p>element - The element to be added under that name. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty, or the element is null.</p>
 * 
 * 
 * @param name 
 * @param element 
 */
    public void addChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element);
/**
 * <p>Purpose: This method removes a child element from under the given name.</p> <p>Args: name - The name under which the child element must be removed from. Must not be null or empty.</p> <p>element - The element to be removed, from under that name. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty, or the element is null.</p> <p></p>
 * 
 * 
 * @param name 
 * @param element 
 */
    public void removeChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element);
/**
 * <p>Purpose: This method clears all child elements under the given name.</p> <p>Args: name - The name from which all child element must be cleared. Must not be null or empty.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p>
 * 
 * 
 * @param name 
 */
    public void clearChildElements(String name);
/**
 * <p>Purpose: This method clears all child elements of this RSSObject.</p> <p>Args: None.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 */
    public void clearAllChildElements();
/**
 * <p>Purpose: This method returns the Base URI property of this object.</p> <p>Args: None.</p> <p>Returns: The base URI property of this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public java.net.URI getBaseUri();
/**
 * <p>Purpose: This method returns the language property of this object.</p> <p>Args: None.</p> <p>Returns: The language property of this object.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getLanguage();
/**
 * <p>Purpose: This method sets the Base URI property of this object.</p> <p>Args: baseUri - The Base URI desired. Possibly null indicating the property should be removed.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param baseUri 
 */
    public void setBaseUri(java.net.URI baseUri);
/**
 * <p>Purpose: This method sets the language property of this object.</p> <p>Args: language - The language desired. Possibly null indicating the property should be removed or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param language 
 */
    public void setLanguage(String language);
}


