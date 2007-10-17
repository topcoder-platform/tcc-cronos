package com.topcoder.mobile.filterlistscreen.models;

import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import java.util.Vector;
import javax.microedition.lcdui.Image;

/**
 * <p>
 * This class implements FilterListEntry interface.
 * </p>
 * <p>
 * A FilterList entry maintains columns in string representation. Though each string is stored in string representation,
 * but it can be an integer if it is. A FilterList entry has associated Image instance to be display in list screen. A
 * FilterList entry has a visibility property which is used to determine whether to render this entry in UI or not. This
 * property is very useful in the filtering operation.
 * </p>
 * <p>
 * The columns of a FilterListEntry can be made to immutable after calling makeColumnsImmutable(). In this case, if the
 * addColumn, insertColumn and removeColumn methods are used, IllegalStateException will be thrown.
 * </p>
 * <p>
 * Thread Safety : This class is mutable and so is not thread safe.
 * </p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e40]
 */
public class DefaultFilterListEntry implements FilterListEntry {

    /**
     * <p>
     * This vector stores all the columns of the entry.
     * </p>
     * <p>
     * Initial Value: The reference is immutable and set to an empty vector in constructor
     * </p>
     * <p>
     * Accessed In: getColumn, getColumnSize
     * </p>
     * <p>
     * Modified In: addColumn, removeColumn
     * </p>
     * <p>
     * Valid Values: It can be empty but not null, element in this vector is non-null string
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7a1f]
     */
    private final Vector columns;

    /**
     * <p>
     * This flag is used to identify whether this entry is visible in the UI. It is very useful in the filtering
     * operation.
     * </p>
     * <p>
     * Initial Value: true
     * </p>
     * <p>
     * Accessed In: isVisible
     * </p>
     * <p>
     * Modified In: setVisible
     * </p>
     * <p>
     * Valid Values: It can be any boolean value
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7a1a]
     */
    private boolean visible = true;

    /**
     * <p>
     * This is the associated Image instance when displaying this entry in UI.
     * </p>
     * <p>
     * Initial Value: Set by the given value in constructor
     * </p>
     * <p>
     * Accessed In: getImage
     * </p>
     * <p>
     * Modified In: setImage
     * </p>
     * <p>
     * Valid Values: any Image value, it can be null
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7a14]
     */
    private Image image;

    /**
     * <p>
     * This flag is used to identify whether the columns of this entry can be modified. It is true by default and can be
     * set to false. If this flag is set to false, then any methods will modify the columns will throw
     * IllegalStateException.
     * </p>
     * <p>
     * Initial Value: true
     * </p>
     * <p>
     * Accessed In: None
     * </p>
     * <p>
     * Modified In: makeColumnsImmutable
     * </p>
     * <p>
     * Valid Values: It can be any boolean value
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7a0f]
     */
    private boolean isColumnsMutable = true;

    /**
     * <p>
     * Constructor that creates an entry with column values.
     * </p>
     * <p>
     * This entry will have no Image instance associated. An entry will be visible and the columns are mutable by
     * default.
     * </p>
     * <p>
     * Implementation Note: this(columns, null);
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm7a06]
     * @param columns
     *            the column values to initialize this entry
     * @throws IllegalArgumentException
     *             if columns array is null or contains any null element
     */
    public DefaultFilterListEntry(String[] columns) {
        this(columns, null);
    }

    /**
     * <p>
     * Constructor that creates an entry with column values and its associated Image.
     * </p>
     * <p>
     * An entry will be visible and the columns are mutable by default.
     * </p>
     * <p>
     * The given image can be null.
     * </p>
     * <p>
     * Implementation Note: check the arguments; this.columns = new Vector(); for (int i = 0; i less than
     * columns.length; i++) { this.columns.addElement(columns[i]); } this.image = image;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79fc]
     * @param columns
     *            the column values to initialize this entry
     * @param image
     *            the associated Image of this entry
     * @throws IllegalArgumentException
     *             if columns array is null or contains any null element
     */
    public DefaultFilterListEntry(String[] columns, Image image) {
        this.columns = new Vector();
        for (int i = 0; i < columns.length; i++) {
            addColumn(columns[i]);
        }
        this.image = image;
    }

    /**
     * <p>
     * This method is used to add a new column to this entry.
     * </p>
     * <p>
     * The new column will be added to the end of the columns.
     * </p>
     * <p>
     * check the argument; if (!isColumnsMutable) { throw new IllegalStateException("..."); } columns.addElement(value);
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79f2]
     * @param value
     *            the new column value to add
     * @throws IllegalArgumentException
     *             if value is null
     * @throws IllegalStateException
     *             if makeImmutable() has been called
     */
    public void addColumn(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value null");
        }
        if (!isColumnsMutable) {
            throw new IllegalStateException("mutable");
        }
        columns.addElement(value);
    }

    /**
     * <p>
     * Inserts a column at a specific index.
     * </p>
     * <p>
     * check the argument; if (!isColumnsMutable) { throw new IllegalStateException("..."); }
     * columns.insertElementAt(value, index);
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79e7]
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
    public void insertColumn(int index, String value) {
        if (!isColumnsMutable) {
            throw new IllegalStateException("...");
        }
        if (index < 0 || index >= columns.size()) {
            throw new IndexOutOfBoundsException("index out of[0, " + columns.size() + "]");
        }
        if (!isColumnsMutable) { throw new IllegalStateException("..."); }
        columns.insertElementAt(value, index);
    }

    /**
     * <p>
     * Gets a column value at the specified index.
     * </p>
     * <p>
     * Implementation Note: return (String) columns.elementAt(index);
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79db]
     * @return the column value of the given index
     * @param index
     *            the index of the column to get its value
     * @throws IndexOutOfBoundsException
     *             if the index is out of the bounds [0, column size).
     */
    public String getColumn(int index) {
        if (index < 0 || index >= columns.size()) {
            throw new IndexOutOfBoundsException("index out of[0, " + columns.size() + "]");
        }
        return (String) columns.elementAt(index);
    }

    /**
     * <p>
     * Removes a specific column.
     * </p>
     * <p>
     * check the argument; if (!isColumnsMutable) { throw new IllegalStateException("..."); }
     * columns.removeElementAt(index);
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79d1]
     * @param index
     *            the index of the column to be deleted
     * @throws IndexOutOfBoundsException
     *             if the index is out of the bounds [0, column size).
     * @throws IllegalStateException
     *             if makeImmutable() has been called
     */
    public void removeColumn(int index) {
        if (!isColumnsMutable) {
            throw new IllegalStateException("...");
        }
        if (index < 0 || index >= columns.size()) {
            throw new IndexOutOfBoundsException("index out of[0, " + columns.size() + "]");
        }
        columns.removeElementAt(index);
    }

    /**
     * <p>
     * Returns the number of columns for the entry.
     * </p>
     * <p>
     * Implementation Note: return columns.size();
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79c8]
     * @return the number of columns for the entry.
     */
    public int getColumnSize() {
        return columns.size();
    }

    /**
     * <p>
     * Returns the visibility property of the entry.
     * </p>
     * <p>
     * Implementation Note: return visible;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79c1]
     * @return the visibility property of the entry.
     */
    public boolean isVisible() {
        // your code here
        return visible;
    }

    /**
     * <p>
     * Sets the visibility property of the entry to a new value.
     * </p>
     * <p>
     * Implementation Note: this.visible = visible;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79b9]
     * @param visible
     *            the new visibility value for this entry
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * <p>
     * This method is used to make the columns of this entry to be immutable.
     * </p>
     * <p>
     * Note, the effect done by this method can not be withdrawn. Once this method is called, the columns of this entry
     * will become immutable forever. If any methods that will modify the columns are called after this method, then it
     * will result in IllegalStateException.
     * </p>
     * <p>
     * Implementation Note: isColumnsMutable = false;
     * </p>
     * 
     * @poseidon-object-id [I6d0f7eam11394169b90mm79a3]
     */
    public void makeColumnsImmutable() {
        isColumnsMutable = false;
    }

    public Image getImage() {
        // TODO Auto-generated method stub
        return image;
    }

    public void setImage(Image image) {
        this.image = image;

    }
}
