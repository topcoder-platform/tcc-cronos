package com.topcoder.mobile.filterlistscreen;

import com.topcoder.mobile.filterlistscreen.FilterListEntry;

/**
 * <p>
 * This interface defines the contract that any data class needs to follow in order be the data model used by
 * FilterList.
 * </p>
 * <p>
 * A FilterList data model maintains a collection of FilterListEntry instances. Adding, inserting, removing, getting and
 * setting operations to the underlying FilterListEntry instances are provided. Besides, the model provides a way to get
 * the total number of FilterListEntry instance it contains.
 * </p>
 * <p>
 * Thread Safety : Implementations of this interface are not required to be thread safe.
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e54]
 */
public interface FilterListModel {
    /**
     * <p>
     * Adds a new entry at the end of the list.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c4c]
     * @param entry
     *            The FilterListEntry instance representing a row in the data model
     * @throws IllegalArgumentException
     *             if entry is null or if the size of the entry is zero or not the same as the entries added before
     */
    public void add(FilterListEntry entry);

    /**
     * <p>
     * Adds a new entry at the specific index. The given index means the index of all the visible entries, the invisible
     * ones are skipped.
     * </p>
     * <p>
     * The specific index must within the range of the list or a java runtime IndexOutOfBoundsException is thrown. Only
     * the visible entries will be counted.
     * </p>
     * <p>
     * The entry at the specified index (and all subsequent entries) will be displaced after the insert entry.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c42]
     * @param index
     *            the given index to insert the given FilterListEntry instance
     * @param entry
     *            the entry to insert to the model at a specified index
     * @throws IllegalArgumentException
     *             if entry is null or if the size of the entry is zero or not the same as the entries added before
     * @throws IndexOutOfBoundsException
     *             if the given index is out of bounds [0, list size).
     */
    public void insert(int index, FilterListEntry entry);

    /**
     * <p>
     * Removes the entry at the specified index and returns the entry that was removed from the list.The given index
     * means the index of all the visible entries, the invisible ones are skipped.
     * </p>
     * <p>
     * The specific index must within the range of the list or a java runtime IndexOutOfBoundsException is thrown. Only
     * the visible entries are counted.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c37]
     * @return the entry that was removed from the list
     * @param index
     *            the index of the entry to be removed
     * @throws IndexOutOfBoundsException
     *             if the given index is out of bounds [0, list size).
     */
    public FilterListEntry remove(int index);

    /**
     * <p>
     * Gets the entry at the specified index. The given index means the index of all the visible entries, the invisible
     * ones are skipped.
     * </p>
     * <p>
     * The specific index must within the range of the list or a java runtime IndexOutOfBoundsException is thrown.Only
     * the visible entries are counted.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c2d]
     * @return the entry that is at the given index
     * @param index
     *            the index of the entry to retrieve
     * @throws IndexOutOfBoundsException
     *             if the given index is not out of bounds [0, list size).
     */
    public FilterListEntry get(int index);

    /**
     * <p>
     * Gets the size of the entries contained in this model. Note, only the visible entries are counted.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c24]
     * @return the size of the entries contained in this model.
     */
    public int getSize();

    /**
     * <p>
     * Updates the given entry at the given index with a new entry. The given index means the index of all the visible
     * entries, the invisible ones are skipped.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c1b]
     * @return the old entry that was at the given index
     * @param index
     *            the index to set the given entry
     * @param entry
     *            the given entry to set
     * @throws IllegalArgumentException
     *             if entry is null or if the size of the entry is zero or not the same as the entries added before
     * @throws IndexOutOfBoundsException
     *             if the given index is not out of bounds [0, list size).
     */
    public FilterListEntry set(int index, FilterListEntry entry);
}
