package com.topcoder.mobile.filterlistscreen.models;

import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.FilterListModel;
import java.util.Vector;

/**
 * <p>
 * This class implements the FilterListModel interface.
 * </p>
 * <p>
 * A FilterList data model maintains a collection of FilterListEntry instances. Adding, inserting, removing, getting and
 * setting operations to the underlying FilterListEntry instances are provided. Besides, the model provides a way to get
 * the total number of FilterListEntry instance it contains.
 * </p>
 * <p>
 * </p>
 * <p>
 * Thread Safety : This class is mutable and so is not thread safe.
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e41]
 */
public class DefaultFilterListModel implements FilterListModel {

    /**
     * <p>
     * This vector stores all the entries in this model.
     * </p>
     * <p>
     * Initial Value: The reference is immutable and set to an empty vector in constructor
     * </p>
     * <p>
     * Accessed In: get, getSize
     * </p>
     * <p>
     * Modified In: add, insert, remove, set
     * </p>
     * <p>
     * Valid Values: It can be empty but not null, element in this vector is non-null FilterListEntry
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm799c]
     */
    private final Vector entries;

    /**
     * <p>
     * Default constructor for this model.
     * </p>
     * <p>
     * Implementation Note: entries = new Vector();
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7996]
     */
    public DefaultFilterListModel() {
        entries = new Vector();
    }

    /**
     * <p>
     * Adds a new entry at the end of the list.
     * </p>
     * <p>
     * The columns of the given entry are marked as immutable in this method after it is added.
     * </p>
     * <p>
     * Implementation Note: check the argument; entries.addElement(entry); entry.makeColumnsImmutable();
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm798f]
     * @param entry
     *            The FilterListEntry instance representing a row in the data model
     * @throws IllegalArgumentException
     *             if entry is null or if the size of the entry is zero or not the same as the entries added before
     */
    public void add(FilterListEntry entry) {
        if(entry == null) {
            throw new IllegalArgumentException("null entry");
        }

        if (entry.getColumnSize() == 0) {
            if(entries.size() > 0) {
                FilterListEntry old = (FilterListEntry)entries.elementAt(0);
                if(old.getColumnSize() != entry.getColumnSize()) {
                    throw new IllegalArgumentException("not same colums");
                }                                             
            } 
        }
        entries.addElement(entry);
        entry.makeColumnsImmutable();
    }

    /**
     * <p>
     * Inserts a new entry at the specific index.The given index means the index of all the visible entries, the
     * invisible ones are skipped.
     * </p>
     * <p>
     * The columns of the given entry are marked as immutable in this method after it is added.
     * </p>
     * <p>
     * The specific index must within the range of the list or a java runtime IndexOutOfBoundsException is thrown.
     * </p>
     * <p>
     * The entry at the specified index (and all subsequent entries) will be displaced after the insert entry.
     * </p>
     * <p>
     * Implementation Note:
     * </p>
     * <p>
     * check the argument;
     * </p>
     * <p>
     * get the real index 'index1' by the given index, developer can loop the underlying entries to calculate the real
     * index;
     * </p>
     * <p>
     * entries.insertElementAt(entry, index1);
     * </p>
     * <p>
     * entry.makeColumnsImmutable();
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7985]
     * @param index
     *            the given index to insert the given FilterListEntry instance
     * @param entry
     *            the entry to insert to the model at a specified index
     * @throws IllegalArgumentException
     *             if entry is null or if the size of the entry is zero or not the same as the entries added before
     * @throws IndexOutOfBoundsException
     *             if the given index is out of bounds [0, list size).
     */
    public void insert(int index, FilterListEntry entry) {
    	entries.insertElementAt(entry, index);
    }

    /**
     * <p>
     * Removes the entry at the specified index and returns the entry that was removed from the list. The given index
     * means the index of all the visible entries, the invisible ones are skipped.
     * </p>
     * <p>
     * The specific index must within the range of the list or a java runtime IndexOutOfBoundsException is thrown. Only
     * the visible entries are counted.
     * </p>
     * <p>
     * Implementation Note:
     * </p>
     * <p>
     * FilterListEntry entry = get(index);
     * </p>
     * <p>
     * get the real index 'index1' by the given index, developer
     * </p>
     * <p>
     * can loop the underlying entries to calculate the real index;
     * </p>
     * <p>
     * entries.removeElementAt(index1);
     * </p>
     * <p>
     * return entry;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm797a]
     * @return the entry that was removed from the list
     * @param index
     *            the index of the entry to be removed
     * @throws IndexOutOfBoundsException
     *             if the given index is out of bounds [0, list size).
     */
    public FilterListEntry remove(int index) {
    	FilterListEntry entry = (FilterListEntry) entries.elementAt(index);
    	entries.removeElementAt(index);
        return entry;
    }

    /**
     * <p>
     * Gets the entry at the specified index.The given index means the index of all the visible entries, the invisible
     * ones are skipped.
     * </p>
     * <p>
     * The specific index must within the range of the list or a java runtime IndexOutOfBoundsException is thrown.Only
     * the visible entries are counted.
     * </p>
     * <p>
     * Implementation Note:
     * </p>
     * <p>
     * get the real index 'index1' by the given index, developer
     * </p>
     * <p>
     * can loop the underlying entries to calculate the real index;
     * </p>
     * <p>
     * return (FilterListEntry) entries.elementAt(index1);
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7970]
     * @return the entry that is at the given index
     * @param index
     *            the index of the entry to retrieve
     * @throws IndexOutOfBoundsException
     *             if the given index is out of bounds [0, list size).
     */
    public FilterListEntry get(int index) {
        if (index < 0 || index >= entries.size()) {
            throw new IndexOutOfBoundsException("the given index is out of bounds [0, list size)");
        }
        return (FilterListEntry) entries.elementAt(index);
    }

    /**
     * <p>
     * Gets the size of the entries contained in this model.Only the visible entries are counted.
     * </p>
     * <p>
     * Implementation Note:
     * </p>
     * <p>
     * loops all the entries and only counts the visible entries.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7967]
     * @return the size of the entries contained in this model.
     */
    public int getSize() {
        int count = 0;
        for (int i = 0; i < entries.size(); i++) {
            FilterListEntry entry = (FilterListEntry) entries.elementAt(i);
            if (entry.isVisible()) {
                count++;
            }
        }
        return count;
    }

    /**
     * <p>
     * Updates the given entry at the given index with a new entry.The given index means the index of all the visible
     * entries, the invisible ones are skipped.
     * </p>
     * <p>
     * The columns of the given entry are marked as immutable in this method after it is added.
     * </p>
     * <p>
     * Implementation Note:
     * </p>
     * <p>
     * check the argument;
     * </p>
     * <p>
     * FilterListEntry oldEntry = get(index);
     * </p>
     * <p>
     * get the real index 'index1' by the given index, developer
     * </p>
     * <p>
     * can loop the underlying entries to calculate the real index;
     * </p>
     * <p>
     * entries.setElementAt(entry, index1);
     * </p>
     * <p>
     * entry.makeColumnsImmutable();
     * </p>
     * <p>
     * return oldEntry;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm795e]
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
    public FilterListEntry set(int index, FilterListEntry entry) {
        FilterListEntry oldEntry = (FilterListEntry)entries.elementAt(index);
        entries.setElementAt(entry, index);
        entry.makeColumnsImmutable();
        return oldEntry;
    }
}
