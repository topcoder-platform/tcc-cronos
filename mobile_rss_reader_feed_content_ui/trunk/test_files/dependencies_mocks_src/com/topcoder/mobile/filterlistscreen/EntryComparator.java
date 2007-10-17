
package com.topcoder.mobile.filterlistscreen;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.InvalidDataTypeException;
/**
 * <p>This interface defines the contract that compares two FilterListEntry instances.</p>
 * <p>This interface is utilized by the FilterListSorter interface. FilterListSorter uses this interface to decide order of FilterListEntry instances in the sorting.</p>
 * <p>This interface only has a method, which takes two FilterListEntry instance and returns an integer representing their respective order. What columns of FilterListEntry to compare should be provided by the implementation constructors.</p>
 * <p>Thread Safety : There is no thread safety requirement for the implementations.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e4f]
 */
public interface EntryComparator {
/**
 * <p>Compares the given two FilterListEntry instance to determines their ordering.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7d73]
 * @return an integer with 0 value means the first argument is equal to the second argument; a value less than 0
 * means the first argument is less than the second argument; and a value greater than 0 means the first argument
 * is greater than the second argument.
 * @param entry1 one FilterListEntry to compare
 * @param entry2 another FilterListEntry to compare
 * @throws IllegalArgumentException if either argument is null
 * @throws IndexOutOfBoundsException if any index to get column of the entry is out of bounds
 * @throws InvalidDataTypeException if any column cannot be converted to the desired value (optional)
 */
    public int compare(FilterListEntry entry1, FilterListEntry entry2);
}


