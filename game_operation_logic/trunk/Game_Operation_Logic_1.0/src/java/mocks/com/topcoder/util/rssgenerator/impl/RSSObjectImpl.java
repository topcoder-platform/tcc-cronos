
package com.topcoder.util.rssgenerator.impl;
import java.util.HashMap;

/**
 * Purpose: This class gives a simple implementation of the RSSObject interface. It implements all of the methods of that contract as well as those of its super-interface - java.io.Externalizable. It can be constructed either as an empty object with a default name, a copy of an existing object or from a given set of attributes and children.
 * <p>Implementation: We use member Strings to store the element name and text. A pair of Map objects serve to store the attributes and child elements. Externalization is achieved by first writing out (or reading in) the element name, text-content and attributes followed by recursively externalizing the child elements.</p>
 * <p>Thread Safety: This class is not thread safe as it is mutable.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm78b0]
 */
public class RSSObjectImpl implements com.topcoder.util.rssgenerator.RSSObject {

/**
 * <p>Purpose: This variable stores the name of the XML element represented by this object. It is initialized to DEFAULT_NAME. It is not frozen as it can be changed through the setElementName method. It may be retrieved using the getElementName method. &nbsp;It will not be null or empty.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm789c]
 */
    private String name = DEFAULT_NAME;

/**
 * <p>Purpose: This variable stores the text-content of the XML element represented by this object. It is initialized to null. It is not frozen as it can be changed through the setElementText method. It may be retrieved using the getElementText method. &nbsp;It is possibly null (indicating no text-content) or empty (indicating empty text-content). The difference is evident in the case of child-less elements where a null stands for &lt;elementName/&gt; and an empty string for &lt;elementName&gt;&lt;/elementName&gt;.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm76ed]
 */
    private String text = null;

/**
 * <p>Purpose: This map stores the attributes of this RSSObject. Since the RSSObject is an abstraction of an XML element, all attribute names and values will be Strings. Therefore this map is a Map&lt;String,String&gt;. It is initialized to an empty hash map. The keys of this map will never be null or empty. The value will never be null, possibly empty. The values of this map may be retrieved using the getAttribute method. The keys may be retrieved using the getAttributeNames method. The values may be set using the setAttribute method and all values cleared using the clearAttributes method. Note that trying to set a null value will result in the key being removed. This variable is frozen as the reference does not change after construction.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm76dc]
 */
    private final java.util.Map attributes = new HashMap();

/**
 * <p>Purpose: This map stores the child elements of this RSSObject. As desired by the contract, the children are organized under names, which may or may not be the same as their element names. Note that one name may contain multiple children. Therefore this map is a Map&lt;String,List&lt;String&gt;&gt;. It is initialized to an empty hash map. The keys of this map will never be null or empty. The value will never be null or an empty list. The values of this map may be retrieved using the getChildElement or getChildElements or getAllChildElements methods. The keys may be retrieved using the getAllChildElementNames method. The values may be set using the setChildElement, setChildElements, addChildElement, removeChildElement methods and values cleared using the clearChildElements or clearAllChildElements methods. Note that trying to set a null or empty array as value will result in the key being removed. This variable is frozen as the reference does not change after construction.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm76cb]
 */
    private final java.util.Map childElements = new HashMap();

/**
 * <p>Purpose: This variable stores the default element name if none is specified at the time of contruction. It is required as the element name is not allowed to be null or empty, which would normally be used as defaults for Strings. This variable is frozen as it will not change and static as it is common for all instances of the class. It can be retrieved by directly referencing it as it is public.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7142]
 */
    public static final String DEFAULT_NAME = "rss_object_impl";

/**
 * <p>Purpose: Constructs this RSSObject as the representation of an empty XML element with the default name. The no-args constructor is also required when this object is being constructed from an externalized representation through reflection.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Since the variables have to be initialized to their default values, nothing needs to be done.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm788b]
 */
    public  RSSObjectImpl() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object as a copy of the given RSSObject.</p>
 * <p>Args: object - The RSSObject to be copied. Must not be null.</p>
 * <p>Implementation: 1. Copy the element name and text-content. 2. Get the attribute names. For each name, copy the attribute. 3. Get the child element names. For each name, copy the child elements under that name.</p>
 * <p>Exceptions: IllegalArgumentException - If object is null. Also thrown by setElementName, setAttribute and setChildElements which are called by this method.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7868]
 * @param object 
 */
    public  RSSObjectImpl(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object fom the given element name, text content, attributes and children.</p>
 * <p>Args: name - The name of the XML element represented by this RSSObject. Must not be null or empty.</p>
 * <p>text - The text-content of the XML element represented by this RSSObject. Possibly null or empty.</p>
 * <p>attributes - A Map&lt;String,String&gt; of the attributes desired. Possibly null implying an empty set of attributes. All keys must be non-null and non-empty Strings. All values must be String objects (possibly null or empty).</p>
 * <p>childElements - A Map&lt;String,List&lt;RSSObject&gt;&gt; of the child elements desired. Possibly null implying no children. All keys must be non-null and non-empty Strings. All values must be List objects, consisting of RSSObject instances. Values may be null or empty lists.</p>
 * <p>Implementation: 1. Assign the element name and text-content to the member variables. 2. Copy the attributes. 3. Copy the child elements.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is null or empty or either map has illegal keys or values.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7825]
 * @param name 
 * @param text 
 * @param attributes 
 * @param childElements 
 */
    public  RSSObjectImpl(String name, String text, java.util.Map attributes, java.util.Map childElements) {        
        // your code here
    } 

/**
 * <p>Purpose: This method returns the name of the element represented by this RSSObject.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply return the member variable.</p>
 * <p>Returns: The name of the element represented by this RSSObject.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm769c]
 * @return 
 */
    public String getElementName() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the text-content of the element represented by this RSSObject.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply return the member variable.</p>
 * <p>Returns: The text-content of the element represented by this RSSObject.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7677]
 * @return 
 */
    public String getElementText() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the value of the desired attribute. A null will be returned if the attribute does not exist.</p>
 * <p>Args: name - The name of the desired attribute. Must not be null or empty.</p>
 * <p>Implementation: Simply return attributes.get(name)</p>
 * <p>Returns: The value of the desired attribute.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7652]
 * @param name 
 * @return 
 */
    public String getAttribute(String name) {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the names of all the attributes of this RSSObject which have a non-null value.</p>
 * <p>Args: None.</p>
 * <p>Implementation: 1. Get the key set of the attributes map. 2. Convert the set into a string array and return it.</p>
 * <p>Returns: The names of all the attributes of this RSSObject which have a non-null value.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7612]
 * @return 
 */
    public String[] getAttributeNames() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the child element under the desired name. A null will be returned if none exists. If more than one child element exists under the same name, the first of these will be returned.</p>
 * <p>Args: name - The name under which the child element exists. Must not be null or empty.</p>
 * <p>Implementation: 1. Get childElements.get(name) List. 2. If the List is null or empty, return null. 3. Return the list.get(0) (The first element).</p>
 * <p>Returns: The child element under the desired name.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm75ed]
 * @param name 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSObject getChildElement(String name) {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns all the child elements under the desired name.</p>
 * <p>Args: name - The name under which the child elements exist. Must not be null or empty.</p>
 * <p>Implementation: 1. Get childElements.get(name) List. 2. If the list is null, return an empty array. 3. Use list.toArray to convert the list into an array of RSSObject instances and return it.</p>
 * <p>Returns: All the child elements under the desired name.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is either null or empty.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm75ad]
 * @param name 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSObject[] getChildElements(String name) {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns all names under which at least one child element exists.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Get the key set of the childElements map and return it as an array of Strings.</p>
 * <p>Returns: All names under which at least one child element exists.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm756d]
 * @return 
 */
    public String[] getAllChildElementNames() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns all child elements of this RSSObject.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Get the value set of the childElements map. Concatenate all the lists and convert the entire list into an array of RSSObject instances and return it.</p>
 * <p>Returns: All child elements of this RSSObject.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7548]
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSObject[] getAllChildElements() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method sets the name of the element represented by this RSSObject.</p>
 * <p>Args: name - The desired name of the element represented by this RSSObject. Must not be null or empty.</p>
 * <p>Implementation: Simply assign the argument to the member variable.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7523]
 * @param name 
 */
    public void setElementName(String name) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the text-content of the element represented by this RSSObject.</p>
 * <p>Args: name - The desired text-content of the element represented by this RSSObject. Possibly null or empty.</p>
 * <p>Implementation: Simply assign the argument to the member variable.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm74e3]
 * @param text 
 */
    public void setElementText(String text) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the value of the desired attribute.</p>
 * <p>Args: name - The name of the attribute whose value is to be set. Must not be null or empty.</p>
 * <p>value - The value desired of the attribute. Possibly null or empty.</p>
 * <p>Implementation: If value is null do attributes.remove(name), otherwise do attributes.put(name, value)</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm74a3]
 * @param name 
 * @param value 
 */
    public void setAttribute(String name, String value) {        
        // your code here
    } 

/**
 * <p>Purpose: This method clears all attributes of this RSSObject.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Do attributes.clear()</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm743a]
 */
    public void clearAttributes() {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the child element under the given name. Any existing child elements under the name are replaced by the given element.</p>
 * <p>Args: name - The name under which the child element must be set. Must not be null or empty.</p>
 * <p>element - The element to be set under that name. Possibly null.</p>
 * <p>Implementation: 1. If element is null, do childElements.remove(name). 2. Create a new List containing only the given element. 3. Do childElements.put(name, list)</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7415]
 * @param name 
 * @param element 
 */
    public void setChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the child elements under the given name. Any existing child elements under the name are replaced by the given elements.</p>
 * <p>Args: name - The name under which the child elements must be set. Must not be null or empty.</p>
 * <p>elements - The elements to be set under that name. Possibly null. All of the elements of the array must be non-null.</p>
 * <p>Implementation: 1. If the array is null or empty, do childElements.remove(name). 2. Create a List from the given array. 2. Do childElements.put(name, list)</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is null or empty, or elements contains a null element.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm73ac]
 * @param name 
 * @param elements 
 */
    public void setChildElements(String name, com.topcoder.util.rssgenerator.RSSObject[] elements) {        
        // your code here
    } 

/**
 * <p>Purpose: This method adds a child element under the given name.</p>
 * <p>Args: name - The name under which the child element must be added. Must not be null or empty.</p>
 * <p>element - The element to be added under that name. Must not be null.</p>
 * <p>Implementation: 1. If childElements.get(name) returns null, create an empty List and do childElements.put(name, list). 2. Add this element to the list.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is null or empty, or the element is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7343]
 * @param name 
 * @param element 
 */
    public void addChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element) {        
        // your code here
    } 

/**
 * <p>Purpose: This method removes a child element from under the given name.</p>
 * <p>Args: name - The name under which the child element must be removed from. Must not be null or empty.</p>
 * <p>element - The element to be removed, from under that name. Must not be null.</p>
 * <p>Implementation: 1. If childElements.get(name) returns null, return. 2. Do list.remove(element) 3. If the list is empty, do childElements.remove(name)</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is null or empty, or the element is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm72da]
 * @param name 
 * @param element 
 */
    public void removeChildElement(String name, com.topcoder.util.rssgenerator.RSSObject element) {        
        // your code here
    } 

/**
 * <p>Purpose: This method clears all child elements under the given name.</p>
 * <p>Args: name - The name from which all child element must be cleared. Must not be null or empty.</p>
 * <p>Implementation: Do childElements.remove(name)</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If the name is null or empty.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7271]
 * @param name 
 */
    public void clearChildElements(String name) {        
        // your code here
    } 

/**
 * <p>Purpose: This method clears all child elements of this RSSObject.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Do childElements.clear()</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7231]
 */
    public void clearAllChildElements() {        
        // your code here
    } 

/**
 * <p>Purpose: This method returns the Base URI property of this object.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:base&quot;.</p>
 * <p>Returns: The base URI property of this object.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm720c]
 * @return 
 */
    public java.net.URI getBaseUri() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the language property of this object.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:lang&quot;.</p>
 * <p>Returns: The language property of this object.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm71e7]
 * @return 
 */
    public String getLanguage() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method sets the Base URI property of this object.</p>
 * <p>Args: baseUri - The Base URI desired. Possibly null indicating the property should be removed.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:base&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm71c2]
 * @param baseUri 
 */
    public void setBaseUri(java.net.URI baseUri) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the language property of this object.</p>
 * <p>Args: language - The language desired. Possibly null indicating the property should be removed or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:lang&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm7182]
 * @param language 
 */
    public void setLanguage(String language) {        
        // your code here
    } 

/**
 * <p>Purpose: Reconstructs this object from its externalized form.</p>
 * <p>Args: input - The ObjectInput where this object's externalized data exists. Must not be null.</p>
 * <p>Implementation: The basic procedure is to read in the name, text, followed by reading in each of the attributes, followed by reading in each of the child elements.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IOException - Thrown by method calls to ObjectInput.</p>
 * <p>IllegalArgumentException - If input is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm6505]
 * @param input 
 */
    public void readExternal(java.io.ObjectInput input) {        
        // your code here
    } 

/**
 * <p>Purpose: Externalizes this object so that it can be later read back.</p>
 * <p>Args: output - The ObjectOutput to which this object's externalized form is to be written. Must not be null.</p>
 * <p>Implementation: The basic procedure is to write out the name, text, followed by writing out each of the attributes, followed by writing out each of the child elements.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IOException - May occur while writing to the ObjectOutput.</p>
 * <p>IllegalArgumentException - If output is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm64c5]
 * @param output 
 */
    public void writeExternal(java.io.ObjectOutput output) {        
        // your code here
    } 
/**
 * <p></p>
 * 
 * @poseidon-object-id [Im12375f2bm10e564f3ea3mm2c8d]
 * @poseidon-type com.topcoder.util.rssgenerator.RSSObject
 */
    public java.util.Collection rSSObject = new java.util.TreeSet();
 }
