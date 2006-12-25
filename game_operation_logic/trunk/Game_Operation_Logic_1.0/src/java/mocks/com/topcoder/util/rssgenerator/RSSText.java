
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSObject contract to accomodate a Text object. A text object is used to represent text of a particular type. For example, the Atom 1.0 format recognizes the types - &quot;text&quot;, &quot;html&quot; and &quot;xhtml&quot;. It adds a pair of methods to get and set the type property. The text-content may be retrieved using the getText method of the RSSObject. Maps to an Atom 1.0 text construct. No RSS 2.0 analogue although any String can be mapped.
 * <p>Implementation: Implementations may provide the type property either as a member variable or by decorating over an attribute/element of the RSSObject.</p>
 * <p>Thread Safety: Implementations are not required to be thread safe.</p>
 * 
 * @poseidon-object-id [Im36376badm10e13f61c4dmm7e06]
 */
public interface RSSText extends com.topcoder.util.rssgenerator.RSSObject {
/**
 * <p>Purpose: This method returns the type of the text of this RSSText.</p>
 * <p>Args: None.</p>
 * <p>Returns: The type of the text of this RSSText.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm63d2]
 * @return 
 */
    public String getType();
/**
 * <p>Purpose: This method sets the type of the text of this RSSText.</p>
 * <p>Args: type - The type of this text. Possibly null indicating the property should be removed or empty.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm63ad]
 * @param type 
 */
    public void setType(String type);
}


