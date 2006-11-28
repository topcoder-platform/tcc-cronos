
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class implements the RSSCategory contract by decorating over attributes of an RSSObject instance. <p>Implementation: We extend RSSBaseObjectDecorator so that we need only implement the methods added by the RSSCategory interface. We implement the get/set methods by decorating over three attributes of the RSSObject. See 1.3.1 CS to see how the decoration is generally done.</p> <p>Thread Safety: This class is not thread safe depsite not having members as its super class is not thread safe.</p>
 *
 */
public class RSSCategoryImpl extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator implements com.topcoder.util.rssgenerator.RSSCategory {
    private String name;

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSObject or RSSCategory interfaces will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 *
 */
    public  RSSCategoryImpl() {
        // your code here
    }

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 *
 *
 * @param object
 */
    public  RSSCategoryImpl(com.topcoder.util.rssgenerator.RSSObject object) {
        // your code here
    }

/**
 * <p>Purpose: This method returns the name of this category.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:term&quot;. (As 'name' is a common name;) for an attribute, we use 'term' from Atom 1.0 terminology)</p> <p>Returns: The name of this category.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String getName() {
        return name;
    }

/**
 * <p>Purpose: This method returns the domain of this category.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:domain&quot;.</p> <p>Returns: The domain of this category.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public java.net.URI getDomain() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method returns the label of this category.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:label&quot;.</p> <p>Returns: The label of this category.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String getLabel() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method sets the name of this category.</p> <p>Args: name - The name of this category. Possibly null to indicate no name or empty.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:term&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param name
 */
    public void setName(String name) {
        this.name = name;
    }

/**
 * <p>Purpose: This method sets the domain of this category.</p> <p>Args: domain - The domain of this category. Possibly null to indicate no domain.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:domain&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param domain
 */
    public void setDomain(java.net.URI domain) {
        // your code here
    }

/**
 * <p>Purpose: This method sets the label of this category.</p> <p>Args: label - The label of this category. Possibly null to indicate no label or empty.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:label&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param label
 */
    public void setLabel(String label) {
        // your code here
    }
 }
