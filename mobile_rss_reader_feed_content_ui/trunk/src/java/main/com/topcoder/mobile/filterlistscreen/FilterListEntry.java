package com.topcoder.mobile.filterlistscreen;

import javax.microedition.lcdui.Image;

/**
 * <p>
 * This interface defines the contract that any data class needs to follow in order be an entry of this FilterList.
 * </p>
 * <p>
 * A FilterList entry maintains columns in string representation. Though each string is stored in string representation,
 * but it can be an integer if it is. A FilterList entry has associated Image instance to be display in list screen. A
 * FilterList entry has a visibility property which is used to determine whether to render this entry in UI or not. This
 * property is very useful in the filtering operation.
 * </p>
 * <p>
 * The columns of a FilterListEntry can be made to immutable after calling makeColumnsImmutable(). In this case, if the
 * addColumn and removeColumn methods are used, IllegalStateException will be thrown.
 * </p>
 * <p>
 * Thread Safety : Implementations of this interface are not required to be thread safe.
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e4d]
 */
public interface FilterListEntry {
    /**
     * <p>
     * Adds a new column value to the end of existing columns.
     * </p>
     * <p>
     * The new column will be added to the end of the columns.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7cc7]
     * @param value
     *            the column value to be added to this entry
     * @throws IllegalArgumentException
     *             if value is null
     * @throws IllegalStateException
     *             if makeImmutable() has been called
     */
    public void addColumn(String value);

    /**
     * <p>
     * Inserts a column at a specific index.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7cbc]
     * @param index
     *            the index of the new column to be inserted
     * @param value
     *            the new column value
     * @throws IllegalArgumentException
     *             if value is null
     * @throws IndexOutOfBoundsException
     *             if the index is out of the bounds [0, column size).
     * @throws IllegalStateException
     *             if makeImmutable() has been called
     */
    public void insertColumn(int index, String value);

    /**
     * <p>
     * Gets a column value at the specified index.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7cb0]
     * @return the column value of the given index
     * @param index
     *            the index of the column to get its value
     * @throws IndexOutOfBoundsException
     *             if the index is out of the bounds [0, column size).
     */
    public String getColumn(int index);

    /**
     * <p>
     * Removes a specific column.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7ca6]
     * @param index
     *            the index of the column to be deleted
     * @throws IndexOutOfBoundsException
     *             if the index is out of the bounds [0, column size).
     * @throws IllegalStateException
     *             if makeImmutable() has been called
     */
    public void removeColumn(int index);

    /**
     * <p>
     * Returns the number of columns for the entry.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c9d]
     * @return the number of columns for the entry.
     */
    public int getColumnSize();

    /**
     * <p>
     * Returns the visibility property of the entry.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c96]
     * @return the visibility property of the entry.
     */
    public boolean isVisible();

    /**
     * <p>
     * Sets the visibility property of the entry to a new value.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c8e]
     * @param visible
     *            the new visibility value for this entry
     */
    public void setVisible(boolean visible);

    /**
     * <p>
     * Returns the associated Image instance with this entry.
     * </p>
     * <p>
     * The Image can be null.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c87]
     * @return the associated Image instance with this entry.
     */
    public Image getImage();

    /**
     * <p>
     * Sets a new Image instance to be associated with this entry.
     * </p>
     * <p>
     * The image can be null.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c7f]
     * @param image
     *            a new Image instance to be associated with this entry.
     */
    public void setImage(Image image);

    /**
     * <p>
     * Makes the columns of this entry to be immutable.
     * </p>
     * <p>
     * Note, the effect done by this method should not be withdrawn. Once this method is called, the columns of this
     * entry will become immutable forever. If any methods that will modify the columns are called after this method,
     * then it will result in IllegalStateException.
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7c78]
     */
    public void makeColumnsImmutable();
}
