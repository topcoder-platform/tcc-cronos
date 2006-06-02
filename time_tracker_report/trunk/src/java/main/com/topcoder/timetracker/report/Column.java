/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import com.topcoder.util.collection.typesafeenum.Enum;

import java.util.List;


/**
 * This class is an enumeration and hence is thread-safe. This enumeration class defines all the Columns used in the
 * Time Tracker Report component.
 * <p/>
 * The Column here are not the actual Database columns but rather the abstract identifiers for the columns in a {@link
 * java.sql.ResultSet} of a Query defined by Time Tracker Report configuration.
 * <p/>
 * It is possible that the name of the application column is same as the Database column, but not necessary.
 *
 * @author fastprogrammer, traugust
 * @version 2.0
 * @since 1.0
 */
public class Column extends Enum {

    /**
     * Represents the enumeration constant for Column "Date".
     */
    public static final Column DATE = new Column("DATE");

    /**
     * Represents the enumeration constant for Column "Client".
     */
    public static final Column CLIENT = new Column("CLIENT");

    /**
     * Represents the enumeration constant for Column "Project".
     */
    public static final Column PROJECT = new Column("PROJECT");

    /**
     * Represents the enumeration constant for Column "Task".
     */
    public static final Column TASK = new Column("TASK");

    /**
     * Represents the enumeration constant for Column "Hours".
     */
    public static final Column HOURS = new Column("HOURS");

    /**
     * Represents the enumeration constant for Column "Pay Rate".
     */
    public static final Column PAY_RATE = new Column("PAY_RATE");

    /**
     * Represents the enumeration constant for Column "Billable".
     */
    public static final Column BILLABLE = new Column("BILLABLE");

    /**
     * Represents the enumeration constant for Column "Employee".
     */
    public static final Column EMPLOYEE = new Column("EMPLOYEE");

    /**
     * Represents the enumeration constant for Column "Type".
     */
    public static final Column TYPE = new Column("TYPE");

    /**
     * Represents the enumeration constant for Column "Description".
     */
    public static final Column DESCRIPTION = new Column("DESCRIPTION");

    /**
     * Represents the enumeration constant for Column "Amount".
     */
    public static final Column AMOUNT = new Column("AMOUNT");

    /**
     * Represents the enumeration constant for Column "Status".
     */
    public static final Column STATUS = new Column("STATUS");

    /**
     * Represents the enumeration constant for Column "COMPANY".
     * @since 2.0
     */
    public static final Column COMPANY = new Column("COMPANY");

    /**
     * A String specifying the name of the Column. This instance member will not be <tt>null</tt> in an instantiated
     * object.
     * <p/>
     * Note that this name might not be exact name of the column in the Database.
     */
    private final String name;

    /**
     * Constructor. Initializes the instance member with the argument passed.
     *
     * @param name the name of the Column
     */
    private Column(final String name) {
        this.name = name;
    }

    /**
     * Returns a String representing the name of the Column.
     *
     * @return a String representing the name of the Column.
     */
    public String getName() {
        return name;
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
        return name;
    }

    /**
     * Returns all the enumeration constants defined in this enumeration class as a List.
     *
     * @return all the enumeration constants defined in this enumeration class as an unmodifiable List.
     */
    public static List getColumns() {
        return getEnumList(Column.class);
    }

}
