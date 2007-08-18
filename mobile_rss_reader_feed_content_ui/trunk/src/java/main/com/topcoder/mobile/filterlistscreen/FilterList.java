package com.topcoder.mobile.filterlistscreen;

import com.topcoder.mobile.filterlistscreen.FilterCondition;
import com.topcoder.mobile.filterlistscreen.FilterListOperator;
import com.topcoder.mobile.filterlistscreen.FilterListSorter;
import com.topcoder.mobile.filterlistscreen.FilterStatement;
import com.topcoder.mobile.filterlistscreen.InvalidConditionException;
import com.topcoder.mobile.filterlistscreen.InvalidDataTypeException;
import com.topcoder.mobile.filterlistscreen.SortType;
import com.topcoder.mobile.filterlistscreen.comparators.DefaultEntryComparator;
import com.topcoder.mobile.filterlistscreen.conditions.IntFilterCondition;
import com.topcoder.mobile.filterlistscreen.conditions.StringFilterCondition;
import com.topcoder.mobile.filterlistscreen.sorters.FilterListQuickSorter;
import javax.microedition.lcdui.List;

/**
 * <p>
 * This is the main class of this component. FilterList extends List class and provides user with a list screen
 * supporting filtering and sorting.
 * </p>
 * <p>
 * This class is abstract, there is an abstract method - updateUI(). Concrete implementations have to implement this
 * method to render the FilterListModel to UI.
 * </p>
 * <p>
 * There are three kinds of methods defined in this FilterList : setting underlying model, filtering the model and
 * sorting the model. Each kinds have several overloads to make this class more flexible.
 * </p>
 * <p>
 * The sorting of model is delegated to FilterListSorter interface and filtering of model is delegated to
 * FilterStatement interface.
 * </p>
 * <p>
 * Thread Safety : This class contains mutable attribute and so is not thread safe. As a MIDP Screen you won't have
 * multiple threads simultaneously accessing so it is fine not to make this class thread safe.
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e50]
 */
public abstract class FilterList extends List {

    /**
     * <p>
     * This is the model of this FilterList.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getFilterListModel
     * </p>
     * <p>
     * Modified In: setFilterListModel
     * </p>
     * <p>
     * Utilized In: sortByColumn, sortByColumns, sort, three filter methods
     * </p>
     * <p>
     * Valid Values: It can be null, but once it is set, it cannot be null
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d54]
     */
    private FilterListModel model;

    /**
     * <p>
     * Creates a new, empty List, specifying its title and the type of the list.
     * </p>
     * <p>
     * Implementation Note: super(title, listType);
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d49]
     * @param title
     *            the screen's title
     * @param listType
     *            one of IMPLICIT, EXCLUSIVE, or MULTIPLE
     * @throws IllegalArgumentException
     *             if listType is not one of IMPLICIT, EXCLUSIVE, or MULTIPLE
     */
    protected FilterList(String title, int listType) {
        super(title, listType);
    }

    /**
     * <p>
     * Initializes the screen with the provided model.
     * </p>
     * <p>
     * Implementation Note: check the argument; this.model = model; this.updateUI();
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d3e]
     * @param model
     *            the FilterListModel instance representing the underlying data model of this FilterList
     * @throws IllegalArgumentException
     *             if model is null.
     */
    public void setFilterListModel(FilterListModel model) {
        if (model == null) {
            throw new IllegalArgumentException("the [model] should not be null.");
        }
        this.model = model;
        updateUI();
    }

    /**
     * <p>
     * Retrieving the underlying data model used by this FitlerList.
     * </p>
     * <p>
     * Note, the returned value may be null if it hasn't been set.
     * </p>
     * <p>
     * Implementation Note: return this.model;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d36]
     * @return the underlying data model used by this FitlerList.
     */
    public FilterListModel getFilterListModel() {
        return this.model;
    }

    /**
     * <p>
     * This method sorts the list by the specified column index and ordering flag.
     * </p>
     * <p>
     * This method only sorts the model by one column.
     * </p>
     * <p>
     * If the model is null, then this method does nothing.
     * </p>
     * <p>
     * Negative and greater than list size column indexes will throw the java runtime IndexOutOfBoundsException.
     * </p>
     * <p>
     * Implementation Note: sortByColumns(new int[] {columnIndex}, new boolean[] {isAscending});
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d2d]
     * @param columnIndex
     *            the column index of each entry in the model to compare
     * @param isAscending
     *            the sorting is ascending or descending
     * @throws IndexOutOfBoundsException
     *             if columnIndex is negative or greater than list size
     */
    public void sortByColumn(int columnIndex, boolean isAscending) {
        // it is a mock the sort of model
        // when the isAscending is true, the order of model would not be changed
        if(isAscending) {
            // do nothing
        } else {
            // when the isAscending is false, the order of model would be in reverse order
            for (int i = 0; i < model.getSize(); i++) {
                FilterListEntry old = (FilterListEntry)model.get(i);
                
            }
        }
        sortByColumns(new int[] { columnIndex }, new boolean[] { isAscending });
    }

    /**
     * <p>
     * This method sorts the list by the specified column indexes and order flags.
     * </p>
     * <p>
     * This method sorts the model by multiple columns. The orders of given column indexes are important. The priority
     * of the column in the sorting is determined by the position of the column in the given array. The first column
     * index has the highest priority in the sorting, then the second column index, till the last column index.
     * </p>
     * <p>
     * The sorting algorithm will compare the columns of the two entries according the order in the given columnIndexes
     * array, if all the columns in the given columnIndexes are equals, then 0 will be returned, otherwise the first
     * non-zero result will be returned directly.
     * </p>
     * <p>
     * If the model is null, then this method does nothing.
     * </p>
     * <p>
     * Negative and greater than list size column indexes will throw the java runtime IndexOutOfBoundsException.
     * </p>
     * <p>
     * Implementation Note: SortType[] sortTypes = new SortType[columnIndexes.length]; for (int i = 0; i less than
     * columnIndexes.length; i++) { if (isIntegerColumn(i)) { sortTypes[i] = SortType.INTEGER; } else { sortTypes[i] =
     * SortType.STRING; } } DefaultEntryComparator comparator = new DefaultEntryComparator(columnIndexes, sortTypes,
     * isAscendingFlags); FilterListSorter sorter = new FilterListQuickSorter(comparator); sort(sorter); updateUI();
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d21]
     * @param columnIndexes
     *            the column indexes of each entry in the model to compare
     * @param isAscendingFlags
     *            the sorting is ascending or descending
     * @throws IllegalArgumentException
     *             if columnIndexes is null or columnIndexes don't have the same size as isAscendingFlags
     * @throws IndexOutOfBoundsException
     *             if any columnIndex in columnIndexes is negative or greater than list size
     */
    public void sortByColumns(int[] columnIndexes, boolean[] isAscendingFlags) {
        // it is a mock
        updateUI();
    }

    /**
     * <p>
     * This method checks whether the column of the model is an integer column or not. An integer column means all the
     * values of that column can be converted to integer.
     * </p>
     * <p>
     * Implementation Note: for (int i = 0; i less than model.getSize(); i++) { String column =
     * model.get(i).getColumn(index); try { Integer.parseInt(column); } catch (NumberFormatException e) { return false; } }
     * return true;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d16]
     * @return true if the column is an integer column, false otherwise
     * @param index
     *            the column index
     * @throws IndexOutOfBoundsException
     *             if the index is out of bound [0, column size)
     */
    private boolean isIntegerColumn(int index) {
        // your code here
        return false;
    }

    /**
     * <p>
     * This method sorts the list by the specified FilterListSorter instance.
     * </p>
     * <p>
     * The algorithm of sorting is defined by the given FilterListSorter instance.
     * </p>
     * <p>
     * If the model is null, then this method does nothing.
     * </p>
     * <p>
     * Implementation Note:
     * </p>
     * <p>
     * if (model != null) {
     * </p>
     * <p>
     * &nbsp;&nbsp;&nbsp; sorter.sort(model);
     * </p>
     * <p>}
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d0c]
     * @param sorter
     *            The FilterListSorter instance to sort the model
     * @throws IllegalArgumentException
     *             if sorter is null
     * @throws InvalidDataTypeException
     *             if any column to be sorted cannot be converted to the desired value (optional)
     */
    public void sort(FilterListSorter sorter) {
        // your code here
    }

    /**
     * <p>
     * This method filters the list by the specified value using the specified operator.
     * </p>
     * <p>
     * Operator constants are defined in the FilterListOperator interface. Negative column index will apply the
     * condition on all the columns.
     * </p>
     * <p>
     * If the model is null, then this method does nothing.
     * </p>
     * <p>
     * Implementation Note: filter(new StringFilterCondition(operator, value, columnIndex));
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7d01]
     * @param operator
     *            the filtering operation
     * @param value
     *            the base value to compare
     * @param columnIndex
     *            the index of column to compare
     * @throws IllegalArgumentException
     *             if operator is null or value is null
     * @throws IndexOutOfBoundsException
     *             if the index is &gt;= column size
     * @throws InvalidConditionException
     *             if the operator cannot be applied to string type
     */
    public void filter(FilterListOperator operator, String value, int columnIndex) {
        // your code here
    }

    /**
     * <p>
     * This method filters the list by the specified value using the specified operator.
     * </p>
     * <p>
     * Operator constants are defined in the FilterListOperator interface. Negative column index will apply the
     * condition on all the columns.
     * </p>
     * <p>
     * If the model is null, then this method does nothing.
     * </p>
     * <p>
     * Implementation Note: filter(new IntFilterCondition(operator, value, columnIndex));
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7cf2]
     * @param operator
     *            the filtering operation
     * @param value
     *            the base value to compare
     * @param columnIndex
     *            the index of column to compare
     * @throws IllegalArgumentException
     *             if operator is null or value is null
     * @throws IndexOutOfBoundsException
     *             if the index is &gt;= column size
     * @throws InvalidConditionException
     *             if the operator cannot be applied to integer type
     */
    public void filter(FilterListOperator operator, int value, int columnIndex) {
        // your code here
    }

    /**
     * <p>
     * This method filters the list by the given filter condition.
     * </p>
     * <p>
     * If the model is null, then this method does nothing.
     * </p>
     * <p>
     * Implementation Note: check the argument; int size = model.getSize(); for (int i = 0; i less than size; i++) {
     * FilterListEntry entry = model.get(i); boolean visible = fitlerCondition.isValid(entry);
     * entry.setVisible(visible); } updateUI();
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7ce5]
     * @param fitlerCondition
     *            the filter condition to filter list
     * @throws IllegalArgumentException
     *             if fitlerCondition is null
     * @throws IndexOutOfBoundsException
     *             if the index to get column &gt;= column size of an entry.
     * @throws InvalidDataTypeException
     *             if the column of the entry to be compared cannot be converted to the desired type (optional)
     */
    public void filter(FilterCondition fitlerCondition) {
        // your code here
    }

    /**
     * <p>
     * This method filters the list by the given filter statement.
     * </p>
     * <p>
     * A FilterStatement is a composite conditions.
     * </p>
     * <p>
     * If the model is null, then this method does nothing.
     * </p>
     * <p>
     * Implementation Note: filter((FilterCondition) filterStatement);
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7cda]
     * @param filterStatement
     *            the filter statement to filter the list
     * @throws IllegalArgumentException
     *             if fitlerCondition is null
     * @throws IndexOutOfBoundsException
     *             if the index to get column &gt;= column size of an entry.
     * @throws InvalidDataTypeException
     *             if the column of the entry to be compared cannot be converted to the desired type (optional)
     */
    public void filter(FilterStatement filterStatement) {
        // your code here
    }

    /**
     * <p>
     * This is the only abstract method defined in this class.
     * </p>
     * <p>
     * Sub classes are responsible for implementing this method to update the UI stuff once the model is updated or
     * modified.
     * </p>
     * <p>
     * If the model is reset, filtered or sorted, then this abstract method will be used to update the user interface.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7cd0]
     */
    public abstract void updateUI();
}
