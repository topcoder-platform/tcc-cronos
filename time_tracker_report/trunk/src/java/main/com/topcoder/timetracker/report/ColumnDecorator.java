/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

/**
 * This interface basically defines the behavior of a ColumnDecorator. A ColumnDecorator will be associated with a
 * {@link Column} and will assist in formatting the {@link Column}'s Data. Additionally it also contains the information
 * as to what should be the {@link Column}'s display text and the name of the column it is decorating.
 * <p/>
 * The implementers of this class may not be thread-safe. However, the ColumnDecorator objects are created and used by
 * this component in a thread-safe manner. (The ColumnDecorator objects will not be reused and will not be passed across
 * threads).
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public interface ColumnDecorator {
    /**
     * Returns the name of the {@link Column} the ColumnDecorator is decorating.
     * <p/>
     * <p/>
     *
     * @return the name of the {@link Column} the ColumnDecorator is decorating.
     */
    public String getColumnName();

    /**
     * Returns the Display Label to be used for the {@link Column}.
     *
     * @return : the Display Label to be used for the column.
     */
    public String getColumnDisplayText();

    /**
     * Decorates the passed columnData and returns the decorated value. The parameter "columnData" can be <tt>null</tt>
     * or an empty string as, its value is the value returned from the Database and database columns may contain
     * <tt>null</tt>-values. The implementation may return <tt>null</tt> if the "columnData" is <tt>null</tt> or may
     * choose to decorate it in certain format.
     *
     * @param columnData the columnData that needs to be decorated, may be <tt>null</tt>
     *
     * @return the decorated value of the columnData, may be <tt>null</tt>
     */
    public String decorateColumn(String columnData);
}


