
package com.topcoder.mobile.filterlistscreen.conditions;
import com.topcoder.mobile.filterlistscreen.*;
import com.topcoder.mobile.filterlistscreen.FilterCondition;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.FilterStatement;
import com.topcoder.mobile.filterlistscreen.InvalidDataTypeException;

/**
 * <p>This class implements FilterStatement and acts as composite conditions to filter entries.</p>
 * <p>This is an AND aggregation of the given FilterCondition instances. In order to pass this filter, the entry must pass all the conditions which are aggregated by this filter statement.</p>
 * <p>Thread Safety : This class is immutable and so is thread safe.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e47]
 */
public class AndFilterStatement implements FilterStatement {

/**
 * <p>Represents the FilterCondition instance aggregated by this AND filter statement.</p>
 * <p>Initial Value: It is immutable and set in constructor</p>
 * <p>Accessed In: getAggregatedConditions</p>
 * <p>Modified In: None</p>
 * <p>Utilized In: isValid</p>
 * <p>Valid Values: Not null, element cannot be null</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b1b]
 */
    private final FilterCondition[] filters;

/**
 * <p>
 * Constructor with a collection of FilterCondition instances.
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b13]
 * @param filters the FilterCondition collection to be aggregated with AND relationship
 * 
 * <p>
 * Implementation Note:
 * check the argument;
 * clone the given filters to the corresponding member variable
 * @throws IllegalArgumentException if filters is null or contains null element, or if the size of the filters
 * is less than 2
 */
    public  AndFilterStatement(FilterCondition[] filters) {        
        this.filters = filters;
    } 

/**
 * <p>
 * This method is used to determine whether the given entry satisfies all the conditions aggregated by
 * this filter statement.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * for (int i = 0; i less than filters.length; i++) {
 * if (!filters[i].isValid(entry)) {
 * return false;
 * }
 * }
 * return true;
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b0a]
 * @return true if the entry satisfies all this conditions of this filter statement, false otherwise
 * @param entry the entry to validate
 * @throws IllegalArgumentException if entry is null
 * @throws IndexOutOfBoundsException if the index to get column &gt;= column size of an entry.
 * @throws InvalidDataTypeException if the column of the entry to be compared cannot be converted to the desired type
 * (optional)
 */
    public boolean isValid(FilterListEntry entry) {        
        // your code here
        return false;
    } 

/**
 * <p>
 * Retrieves all the aggregated FilterCondition instances by this filter statement.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * return a clone of filters
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7aff]
 * @return all the aggregated FilterCondition instances by this filter statement.
 */
    public FilterCondition[] getAggregatedConditions() {        
        // your code here
        return null;
    } 
 }
