
package com.topcoder.mobile.filterlistscreen;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
/**
 * <p>This interface represents a filter condition used in the filtering operation.</p>
 * <p>A filter condition is a single condition, it is used to define the condition to filter a given FilterListEntry instance.</p>
 * <p>This interface contains only a single method, that is, isValid method, it accepts only a FilterListEntry instance and returns whether the entry is true or not according the condition defined by this interface.</p>
 * <p>The action condition used to filter an entry can be set through constructor in implementation classes, this interface doesn't provide a way to set the underlying condition.</p>
 * <p>In this component, a condition is defined as (operation, value, index), the operation means the filter operation, it can be operations such as equals, less than. The value means the base value to compare, it can be of int type or string type. The index means the column index of an entry, the column value at the index will be used. If the index is negative, then this condition will be applied to all the columns.</p>
 * <p>Thread Safety : Implementations of this class are not required to be thread safe.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e4c]
 */
public interface FilterCondition {
/**
 * <p>This method is used to determine whether the given entry satisfies this condition. If it satisfied, true should be returned, otherwise it should return false.</p>
 * <p>Note, implementations should not modify the given entry. The validation result should be returned instead of updating the entry visibility attribute.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7d60]
 * @return true if the entry satisfies this condition, false otherwise
 * @param entry the entry to validate
 * @throws IllegalArgumentException if entry is null
 * @throws IndexOutOfBoundsException if the index to get column &gt;= column size of an entry.
 * @throws InvalidDataTypeException if the column of the entry to be compared cannot be converted to the desired type (optional)
 */
    public boolean isValid(FilterListEntry entry);
}


