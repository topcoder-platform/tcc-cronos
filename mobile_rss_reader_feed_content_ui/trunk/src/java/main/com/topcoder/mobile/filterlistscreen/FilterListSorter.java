
package com.topcoder.mobile.filterlistscreen;
import com.topcoder.mobile.filterlistscreen.FilterListModel;
import com.topcoder.mobile.filterlistscreen.InvalidDataTypeException;
/**
 * <p>This interface defines the contract that sorts the given FilterListModel instance.</p>
 * <p>This interface is utilized by the FilterList abstract interface to sort the underlying data model defined as FilterListModel.</p>
 * <p>This interface only has a method, which takes one FilterListModel instance. After sorting, the FilterListEntry instances in the model should be in order. How to determine the relative positions of any two FilterListEntry instances is up to the implementation classes. This design provides EntryComparator interface and its implementation should be helpful on this issue.</p>
 * <p>This interface has one implementation in this design : FilterListQuickSorter. FilterListQuickSorter uses the quick sort algorithm to sort the model.</p>
 * <p>Thread Safety : There is no thread safety requirement for the implementations.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e46]
 */
public interface FilterListSorter {
/**
 * <p>
 *  Sorts the model to make the model in order.
 *  </p>
 * 
 *  <p>
 *  Implementations can have different sorting algorithm and entry comparator to perform the
 *  sorting action.
 *  </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7bc5]
 * @param model the data model used by FilterList
 * @throws IllegalArgumentException if model is null
 * @throws InvalidDataTypeException if any column to be sorted cannot be converted to the desired value (optional)
 */
    public void sort(FilterListModel model);
}


