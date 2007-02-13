/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * <p>
 * Defines utilities for persistence classes.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
final class PersistenceHelper {
    /**
     * <p>
     * Creates a new instance of <code>PersistenceHelper</code> class. This private constructor prevents the creation
     * of a new instance.
     * </p>
     */
    private PersistenceHelper() {
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>Object</code>. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws NullPointerException if <code>value</code> is <code>null</code>.
     */
    public static void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new NullPointerException(name + " cannot be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be empty string, but may be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is empty string.
     */
    public static void validateNotEmpty(String value, String name) {
        if (value != null) {
            if (value.trim().length() == 0) {
                throw new IllegalArgumentException(name + " cannot be empty string.");
            }
        }
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be <code>null</code> and empty string.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws NullPointerException if <code>value</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>value</code> is empty string.
     */
    public static void validateNotNullOrEmpty(String value, String name) {
        validateNotNull(value, name);
        validateNotEmpty(value, name);
    }

    /**
     * <p>
     * Closes the SQL statement if it is not <code>null</code>. The result set of the statement, if any is also
     * closed according to the JDBC specification.
     * </p>
     *
     * @param statement the SQL statement to close.
     *
     * @throws PersistenceException if error occurs when closing the statement.
     */
    public static void close(Statement statement) throws PersistenceException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new PersistenceException("Error occurs when closing the statement.", e);
            }
        }
    }

    /**
     * <p>
     * Closes the result set if it is not <code>null</code>.
     * </p>
     *
     * @param resultSet the result set to close.
     *
     * @throws PersistenceException if error occurs when closing the statement.
     */
    public static void close(ResultSet resultSet) throws PersistenceException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new PersistenceException("Error occurs when closing the result set.", e);
            }
        }
    }
}


