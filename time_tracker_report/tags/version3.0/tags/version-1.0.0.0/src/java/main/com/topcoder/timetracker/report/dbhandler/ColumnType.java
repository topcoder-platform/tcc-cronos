/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.dbhandler;

import com.topcoder.util.collection.typesafeenum.Enum;

import java.util.List;


/**
 * This class is an enumeration and defines the different data types for the Database column supported by the component.
 * This enumeration defines the supported data types and the corresponding {@link DBHandler} implementation would
 * require to map this information to the data types that are supported by the corresponding Database.
 * <p/>
 * As an example, the DataType for "MONEY" is not as such available with most of the Database and the {@link DBHandler}
 * implementation would require to map it to the corresponding data type and handle the necessary conversion required.
 * <p/>
 * This is an enumeration class and is thread-safe.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ColumnType extends Enum {

    /**
     * Represents the type of the column for small integers. The actual bit length of this DataType is Database
     * dependent.
     */
    public static final ColumnType SMALLINT = new ColumnType("SMALLINT");

    /**
     * Represents the type of the column for integers. The actual bit length of this DataType is Database dependent.
     */
    public static final ColumnType INTEGER = new ColumnType("INTEGER");

    /**
     * Represents the type of the column for data in Money format. The actual bit length of this DataType is Database
     * dependent.
     */
    public static final ColumnType MONEY = new ColumnType("MONEY");

    /**
     * Represents the type of the column for floating point numbers. The actual bit length of this DataType is Database
     * dependent.
     */
    public static final ColumnType FLOAT = new ColumnType("FLOAT");

    /**
     * Represents the type of the column for Date values. The actual bit length of this DataType is Database dependent.
     */
    public static final ColumnType DATE = new ColumnType("DATE");

    /**
     * Represents the type of the column for textual values.
     */
    public static final ColumnType VARCHAR = new ColumnType("VARCHAR");

    /**
     * Represents a String that defines the type of the Column. This instance member will not be <tt>null</tt> in an
     * instantiated object of this class.
     */
    private final String type;

    /**
     * Constructor. Initializes the instance member "type" to the argument specified.
     *
     * @param type the type name of the column
     */
    private ColumnType(final String type) {
        this.type = type;
    }

    /**
     * Returns the type of the column.
     *
     * @return the type name of the column
     */
    public String getType() {
        return type;
    }

    /**
     * Returns String representation of the Enum. The String representation returned is the identifying value of an
     * instance of this class.
     * <p/>
     * This will ensure that all enum instances of this type can be looked up using {@link
     * Enum#getEnumByStringValue(String, Class)}.
     *
     * @return String representation of the Enum
     */
    public String toString() {
        return type;
    }

    /**
     * Returns all the enumeration constants defined in this enumeration class as a List.
     *
     * @return all the enumeration constants defined in this enumeration class as an unmodifiable List.
     */
    public static List getColumnTypes() {
        return getEnumList(ColumnType.class);
    }

}
