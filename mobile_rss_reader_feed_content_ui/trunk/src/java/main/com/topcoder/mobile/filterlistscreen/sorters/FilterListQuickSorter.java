package com.topcoder.mobile.filterlistscreen.sorters;

import com.topcoder.mobile.filterlistscreen.EntryComparator;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.FilterListModel;
import com.topcoder.mobile.filterlistscreen.FilterListSorter;
import com.topcoder.mobile.filterlistscreen.InvalidDataTypeException;

/**
 * <p>
 * This class implements the FilterListSorter interface so can be used to sort FilterListModel.
 * </p>
 * 
 * <p>
 * FilterListQuickSorter applies the quick sort algorithm to sort the model.
 * </p>
 * 
 * <p>
 * Thread Safety : This class is immutable and so is thread safe.
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e53]
 */
public class FilterListQuickSorter implements FilterListSorter {

/**
 * <p>
 * This variable is used to compare two entities to determine their positions during the sorting.
 * </p>
 * 
 * <p>
 * Initial Value: It is immutable and set in constructor
 * </p>
 * 
 * <p>
 * Accessed In: getEntryComparator
 * </p>
 * 
 * <p>
 * Modified In: None
 * </p>
 * 
 * <p>
 * Utilized In: sort, compare
 * </p>
 * 
 * <p>
 * Valid Values: non-null EntryComparator instance
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7952]
 */
    private final EntryComparator comparator;

/**
 * <p>
 * Constructor that accepts a EntryComparator instance.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * check the argument;
 * this.comparator = comparator;
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm794b]
 * @param comparator the EntryComparator instance to compare FilterListEntry instances
 * @throws IllegalArgumentException if comparator is null
 */
    public  FilterListQuickSorter(EntryComparator comparator) {        
        this.comparator = comparator;
    } 

/**
 * <p>
 * Sorts the model to make the model in order.
 * </p>
 * 
 * <p>
 * The FilterListEntry comparison is done by the given EntryComparator instance while the sorting algorithm
 * is quick sort algorithm.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * sort(model, 0, model.getSize() - 1);
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7942]
 * @param model the data model used by FilterList to be sorted
 * @throws IllegalArgumentException if model is null
 * @throws InvalidDataTypeException if any column to be sorted cannot be converted to the desired value (optional)
 */
    public void sort(FilterListModel model) {        
        // your code here
    } 

/**
 * <p>
 * Retrieves the entry comparator used by this sorter.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * return this.comparator;
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7939]
 * @return the entry comparator used by this sorter.
 */
    public EntryComparator getEntryComparator() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This method calculates the position of partition element in the partial part of model from the start index to the
 * end index.
 * </p>
 * 
 * <p>
 * It chooses one element, called the partition element, then shifts the elements of the array so that everything
 * less than the partition element comes before it in the array, and everything greater than the partition element
 * comes after it. The partition element is then at its permanent place in the array.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * Please refers to the algorithm sections in CS
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm792f]
 * @return the index of the partition element
 * @param model the data model used by FilterList to be sorted
 * @param start the left index of the partial array to get the partition element
 * @param end the right index of the partial array to get the partition element
 * @throws InvalidDataTypeException if any column to be sorted cannot be converted to the desired value (optional)
 */
    private int partition(FilterListModel model, int start, int end) {        
        // your code here
        return 0;
    } 

/**
 * <p>
 * This method is used to compare the two FilterListEntry instances to determine their relative positions.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * return comparator.compare(entry1, entry2);
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7922]
 * @return an integer with 0 value means the first argument is equal to the second argument; a value less than 0
 * means the first argument is less than the second argument; and a value greater than 0 means the first argument
 * is greater than the second argument.
 * @param entry1 the first FilterListEntry instance to compare
 * @param entry2 the second FilterListEntry instance to compare
 * @throws InvalidDataTypeException if any column to be sorted cannot be converted to the desired value (optional)
 */
    private int compare(FilterListEntry entry1, FilterListEntry entry2) {        
        // your code here
        return 0;
    } 

/**
 * <p>Sorts the partial part of the model using quick sort algorithm. Which part to sort is specified by the given start index and end index.</p>
 * <p>Implementation Note: if (end &gt; start) { int p = partition(model, start, end); sort(model, start, p - 1); sort(model, p + 1, end); }</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7915]
 * @param model the data model used by FilterList to be sorted
 * @param start the left index of the partial array to be sorted
 * @param end the right index of the partial array to be sorted
 * @throws InvalidDataTypeException if any column to be sorted cannot be converted to the desired value (optional)
 */
    private void sort(FilterListModel model, int start, int end) {        
        // your code here
    } 

/**
 * <p>
 * Swaps the two FilterListEntry instances in the model.
 * </p>
 * 
 * <p>
 * The two FilterListEntry instances are specified using the given two indexes.
 * </p>
 * 
 * <p>
 * Implementation Note:
 * FilterListEntry entry1 = model.get(index1);
 * FilterListEntry entry2 = model.get(index2);
 * model.set(index1, entry2);
 * model.set(index2, entry1);
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7908]
 * @param model the data model used by FilterList to be sorted
 * @param index1 one of the FilterListEntry instances to be swapped
 * @param index2 another of the FilterListEntry instances to be swapped
 */
    private void swap(FilterListModel model, int index1, int index2) {        
        // your code here
    } 
 }
