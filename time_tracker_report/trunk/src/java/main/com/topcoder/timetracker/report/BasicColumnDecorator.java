/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

/**
 * This class implements the ColumnDecorator interface. It provides a basic column decoration feature, wherein the
 * columns data can be prefixed with a specified constant prefix and/or be suffixed with a specified constant string.
 * <p/>
 * This class is not as such thread-safe, but will be used by the component in a thread-safe manner. (The objects
 * created will not be used across threads and not reusing the objects.)
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class BasicColumnDecorator implements ColumnDecorator {

    /**
     * A string representing the name of the column decorated by this decorator. This instance member will not be
     * <tt>null</tt> in an instantiated object.
     */
    private final String columnName;

    /**
     * A String specifying the Display Label to be used for the column. This instance member will not be <tt>null</tt>
     * in an instantiated object.
     */
    private final String columnDisplayText;

    /**
     * The prefix if any for the columnData. This instance member can be <tt>null</tt>, indicating that the column data
     * should not be prefixed with a value.
     */
    private String prefix = null;

    /**
     * The suffix if any for the columnData. This instance member can be <tt>null</tt>, indicating that the column data
     * should not be suffixed with a value.
     */
    private String suffix = null;

    /**
     * Constructor. Initializes the corresponding instance member from the argument values passed.
     *
     * @param colName      the name of the Column being decorated.
     * @param displayLabel the Display Label to be used for the column.
     *
     * @throws NullPointerException     if any argument is <tt>null</tt>
     * @throws IllegalArgumentException if any argument is an empty (trim'd) String
     */
    public BasicColumnDecorator(final String colName, final String displayLabel) {
        if (colName == null) {
            throw new NullPointerException("The argument named [colName] was null.");
        }
        if (displayLabel == null) {
            throw new NullPointerException("The argument named [displayLabel] was null.");
        }
        if (colName.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [colName] was an empty String.");
        }
        if (displayLabel.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [displayLabel] was an empty String.");
        }

        columnName = colName;
        columnDisplayText = displayLabel;
    }

    /**
     * Returns the name of the Column the ColumnDecorator is decorating.
     *
     * @return the name of the Column the ColumnDecorator is decorating.
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Returns the display label to be used for the column.
     *
     * @return the display label to be used for the column.
     */
    public String getColumnDisplayText() {
        return columnDisplayText;
    }

    /**
     * Sets the instance member prefix with the value of the parameter being passed.
     *
     * @param prefix the prefix to be used for decorating the column data
     *
     * @throws NullPointerException     if prefix is <tt>null</tt>
     * @throws IllegalArgumentException if prefix is an empty (trim'd) String
     */
    public void setPrefix(final String prefix) {
        if (prefix == null) {
            throw new NullPointerException("The argument named [prefix] was null.");
        }
        if (prefix.trim().length() == 0) {
            throw new IllegalArgumentException("The argument named [prefix] was an empty String.");
        }

        this.prefix = prefix;
    }

    /**
     * Sets the instance member suffix with the value of the parameter being passed.
     * <p/>
     * <p/>
     *
     * @param suffix the suffix to be used for decorating the column data.
     *
     * @throws NullPointerException     if suffix is <tt>null</tt>.
     * @throws IllegalArgumentException if suffix is an empty (trim'd) String
     */
    public void setSuffix(final String suffix) {
        if (suffix == null) {
            throw new NullPointerException("The argument named [suffix] was null.");
        }
        if (suffix.trim().length() == 0) {
            throw new IllegalArgumentException("The argument named [suffix] was an empty String.");
        }

        this.suffix = suffix;
    }

    /**
     * Decorates the passed columnData and returns the decorated value. For decoration it prefixes the column data with
     * the set prefix value if it is not <tt>null</tt> and suffixes the column data with the set suffix value if it is
     * not <tt>null</tt>.
     * <p/>
     * If the "columnData" argument passed is <tt>null</tt>, it simply returns <tt>null</tt>
     * <p/>
     * <p/>
     *
     * @param columnData the columnData, that needs to be decorated.
     *
     * @return the decorated value of the columnData
     */
    public String decorateColumn(final String columnData) {

        if (columnData == null) {
            return null;
        } else {
            final StringBuffer ret = new StringBuffer();
            //we don't want to have the string literal "null", but no prefix
            if (prefix != null) {
                ret.append(prefix);
            }
            ret.append(columnData);
            //we don't want to have the string literal "null", but no suffix
            if (suffix != null) {
                ret.append(suffix);
            }
            return ret.toString();
        }
    }
}
