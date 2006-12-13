
package com.topcoder.util.rssgenerator.datastore;

/**
 * <p>Purpose: This class implements the SearchCriteria interface in a direct and simple manner.</p>
 * <p>Implementation: We immutably store the filter at the time of construction as a member variable. We return the member variable every time the getSearchFilter method is called which is the sole method of the interface being implemented. A single constructor allows construction of this object.</p>
 * <p>Thread Safety: This class is thread safe as it is immutable.</p>
 * 
 * @poseidon-object-id [Im24d61a5dm10e29ade829mm5019]
 */
public class SearchCriteriaImpl implements com.topcoder.util.rssgenerator.SearchCriteria {

/**
 * <p>Purpose: This variables stores the search filter of this SearchCriteria object as an instance of the Filter object of the Search Builder component.. It is frozen as it is set immutably at the time of construction. It is initialized by an argument to the constructor. It is referenced by the getSearchCriteria method. It will never have a null value.</p>
 * 
 * @poseidon-object-id [Im24d61a5dm10e29ade829mm4e0e]
 */

/**
 * <p>Purpose: Constructs this SearchCriteria instance from the given filter.</p>
 * <p>Args: searchFilter - The Filter object that this SearchCriteria represents. Must not be null.</p>
 * <p>Implementation: Simply assign searchFilter to the member variable of the same name.</p>
 * <p>Exceptions: IllegalArgumentException - If searchFilter is null.</p>
 * 
 * @poseidon-object-id [Im24d61a5dm10e29ade829mm4de6]
 * @param searchFilter 
 */
    public  SearchCriteriaImpl(com.topcoder.search.builder.filter.Filter searchFilter) {        
        // your code here
    }

/**
 * <p>Purpose: This method returns the Filter which represents this search criteria.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply return the member variable.</p>
 * <p>Returns: The Filter which represents this search criteria.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im24d61a5dm10e29ade829mm4dce]
 * @return 
 */
    public com.topcoder.search.builder.filter.Filter getSearchFilter() {        
        // your code here
        return null;
    } 
/**
 * <p></p>
 * 
 * @poseidon-object-id [I31eb106cm10e52597651mm3b83]
 */
    public com.topcoder.search.builder.filter.Filter filter;
 }
