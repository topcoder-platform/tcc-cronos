
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSObject contract to represent a ctagoery. A category consists of three additional properties - category name, category domain and a label. This interface adds get/set methods for all three of them to the contract. Maps to an atom:category and RSS 2.0 category. <p>Implementations: Implementations might decorate over existing properties of an RSSObject instance or add member variables to support the additonal properties.</p> <p>Thread Safety: Implementations are not required to be thread safe.</p>
 * 
 */
public interface RSSCategory extends com.topcoder.util.rssgenerator.RSSObject {
/**
 * <p>Purpose: This method returns the name of this category.</p> <p>Args: None.</p> <p>Returns: The name of this category.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getName();
/**
 * <p>Purpose: This method returns the domain of this category.</p> <p>Args: None.</p> <p>Returns: The domain of this category.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public java.net.URI getDomain();
/**
 * <p>Purpose: This method returns the label of this category.</p> <p>Args: None.</p> <p>Returns: The label of this category.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getLabel();
/**
 * <p>Purpose: This method sets the name of this category.</p> <p>Args: name - The name of this category. Possibly null to indicate no name or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param name 
 */
    public void setName(String name);
/**
 * <p>Purpose: This method sets the domain of this category.</p> <p>Args: domain - The domain of this category. Possibly null to indicate no domain.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param domain 
 */
    public void setDomain(java.net.URI domain);
/**
 * <p>Purpose: This method sets the label of this category.</p> <p>Args: label - The label of this category. Possibly null to indicate no label or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param label 
 */
    public void setLabel(String label);
}


