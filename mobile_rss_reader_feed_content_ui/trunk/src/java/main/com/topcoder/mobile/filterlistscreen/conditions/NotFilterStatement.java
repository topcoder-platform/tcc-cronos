
package com.topcoder.mobile.filterlistscreen.conditions;
import com.topcoder.mobile.filterlistscreen.*;
import com.topcoder.mobile.filterlistscreen.FilterCondition;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.FilterStatement;

/**
 * <p>This class implements FilterStatement and acts as composite conditions to filter entries.</p>
 * <p>This is an NOT aggregation of the given FilterCondition instance. In order to pass this filter, the entry must not pass the condition to be negated.</p>
 * <p>Thread Safety : This class is immutable and so is thread safe.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e48]
 */
public class NotFilterStatement implements FilterStatement {

/**
 * <p>
 * Represents the FilterCondition instance to be negated by this filter statement.
 * </p>
 * 
 * <p>
 * Initial Value: It is immutable and set in constructor
 * </p>
 * <p>
 * Accessed In: getAggregatedConditions
 * </p>
 * <p>
 * Modified In: None
 * </p>
 * <p>
 * Utilized In: isValid
 * </p>
 * <p>
 * Valid Values: Not null
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7aad]
 */
    private final FilterCondition filterToNegate;

/**
 * <p>
 * Constructor with a FilterCondition instance to be negated.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * check the argument;
 * this.filterToNegate = filterToNegate;
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7aa6]
 * @param filterToNegate the FilterCondition instance to be negated by this filter statement
 * @throws IllegalArgumentException if filterToNegate is null
 */
    public  NotFilterStatement(FilterCondition filterToNegate) {        
        this.filterToNegate = filterToNegate;
    } 

/**
 * <p>
 * This method validates the given entry according the FilterCondition to be negated.
 * </p>
 * 
 * <p>
 * If the entry pass the validation of the condition to be negated, the this method return false, otherwise
 * this method returns true.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * return !filterToNegate.isValid(entry);
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7a9d]
 * @return false if the entry satisfies any of the condition to be negated, true otherwise
 * @param entry the entry to validate
 * @throws IllegalArgumentException if entry is null
 */
    public boolean isValid(FilterListEntry entry) {        
        // your code here
        return false;
    } 

/**
 * <p>
 * Retrieves the FilterCondition instance to be negated by this filter statement.
 * The returned value is in array representation which is defined by the contract.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * return new FilterCondition[] {filterToNegate};
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7a94]
 * @return the FilterCondition instance to be negated by this filter statement.
 */
    public FilterCondition[] getAggregatedConditions() {        
        // your code here
        return null;
    } 
 }
