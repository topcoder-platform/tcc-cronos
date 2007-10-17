
package com.topcoder.mobile.filterlistscreen.comparators;
import com.topcoder.mobile.filterlistscreen.EntryComparator;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.InvalidDataTypeException;
import com.topcoder.mobile.filterlistscreen.SortType;

/**
 * <p>This class implements the EntryComparator interface to compare two FilterListEntry instances.</p>
 * <p>This implementation supports integer comparison and string comparison according to the parameters given in the constructor. This implementation also supports multiple columns comparison instead of only single column comparison, which should be very useful in the sorting.</p>
 * <p>This class can be utilized by FilterListSorter interface.</p>
 * <p>Thread Safety : This class is immutable and so is thread safe.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e4b]
 */
public class DefaultEntryComparator implements EntryComparator {

/**
 * <p>This is the indexes of columns that will be used in the entry comparison.</p>
 * <p>Initial Value: Set by the given value in constructor</p>
 * <p>Accessed In: None</p>
 * <p>Modified In: None</p>
 * <p>Utilized In: compare</p>
 * <p>Valid Values: It cannot be null, element of this array can be any integer value within the range of the entry column size</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b66]
 */
    private final int[] columnIndexes;

/**
 * <p>This flag array is used to specify whether the sorting should be done in ascending or descending for each columns for sorting..</p>
 * <p>This variable will affect the result of compare method.</p>
 * <p>Initial Value: Set by the given value in constructor</p>
 * <p>Accessed In: None</p>
 * <p>Modified In: None</p>
 * <p>Utilized In: compare</p>
 * <p>Valid Values: It can be any boolean value</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b60]
 */
    private final boolean[] isAscendingFlags;

/**
 * <p>This is the sort type of columns being compared.</p>
 * <p>If the sort type is integer, then the column value will be converted to numeric value for comparison, otherwise the text value will be used. If it fails to convert the column string to an integer, InvalidDataTypeException will be thrown.</p>
 * <p>Initial Value: Set by the given value in constructor</p>
 * <p>Accessed In: None</p>
 * <p>Modified In: None</p>
 * <p>Utilized In: compare</p>
 * <p>Valid Values: It cannot be null, element of this array can be not null. The size of this array must be the same as the columnIndexes array.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b5b]
 */
    private final SortType[] sortTypes;

/**
 * <p>Constructor with the column indexes, sort types and ascending/descending flag given.</p>
 * <p>Implementation Note: checking the arguments; clone the given columnIndexes, sortTypes and isAscendingFlags to the corresponding member variables;</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b50]
 * @param columnIndexes the indexes of columns to compare
 * @param sortTypes the sort types of each column to compare
 * @param isAscendingFlags the ascending/descending flag array, for each element, true represents ascending and false represents descending
 * @throws IllegalArgumentException if columnIndexes or sortType is null, or sortTypes array contains null element,
 * or the size of columnIndexes is not the same as the size of sortTypes
 */
    public  DefaultEntryComparator(int[] columnIndexes, SortType[] sortTypes, boolean[] isAscendingFlags) {        
        // TODO clone
        this.columnIndexes = columnIndexes;
        this.sortTypes = sortTypes;
        this.isAscendingFlags = isAscendingFlags;
    } 

/**
 * <p>Compares the given two FilterListEntry instance to determines their ordering.</p>
 * <p>This is a multiple columns comparison, the position of column index in the given columnIndexes is used to determine the priority of the columns in the comparison. The first column has highest priority and the last column has the least priority. If the two columns are equals, then the next two columns will be used to comparison. If all of the columns are equals, then 0 will be returned.</p>
 * <p>Implementation Note: check the arguments; for (int i = 0; i less than columnIndexes.length; i++) { int result = 0; if (sortTypes[i] == SortType.INTEGER) { int value1 = parse entry1.getColumn(i) to an integer, if fails, throw InvalidDataTypeException; int value2 = parse entry2.getColumn(i) to an integer, if fails, throw InvalidDataTypeException; result = value1 - value2; } else { result = entry1.getColumn(i).compareTo(entry2.getColumn(i)); } if (result != 0) { return isAscending ? result : -result; } } return 0;</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b44]
 * @return an integer with 0 value means the first argument is equal to the second argument; a value less than 0
 * means the first argument is less than the second argument; and a value greater than 0 means the first argument
 * is greater than the second argument.
 * @param entry1 one FilterListEntry to compare
 * @param entry2 another FilterListEntry to compare
 * @throws IllegalArgumentException if either argument is null
 * @throws IndexOutOfBoundsException if any index to get column of the entry is out of bounds
 * @throws InvalidDataTypeException if any column cannot be converted to integer value but the sort type is INTEGER
 */
    public int compare(FilterListEntry entry1, FilterListEntry entry2) {        
        // your code here
        return 0;
    } 
 }
