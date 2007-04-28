/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Arrays;
import java.util.Date;


/**
 * <p>
 * A utility class providing some common operations related to database. This class and all its methods are
 * package-private, so that they can only be used by classes in the same package.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
class DBUtil {
    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private DBUtil() {
    }

    /**
     * <p>
     * Checks if the given string is null or empty. A NullPointerException is thrown if the argument is null.
     * </p>
     *
     * @param str the string to check against.
     * @param name the name of the string argument.
     *
     * @throws NullPointerException if str is null.
     * @throws IllegalArgumentException if str is empty.
     */
    static void checkStringNPE(String str, String name) {
        if (str == null) {
            throw new NullPointerException("String argument " + name + " is null");
        }
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("String argument " + name + " is empty string");
        }
    }

    /**
     * <p>
     * Checks if the given string is null or empty. A IllegalArgumentException is thrown if the argument is null.
     * </p>
     *
     * @param str the string to check against.
     * @param name the name of the string argument.
     *
     * @throws IllegalArgumentException if str is null or empty.
     */
    static void checkStringIAE(String str, String name) {
        if (str == null) {
            throw new IllegalArgumentException("String argument " + name + " is null");
        }
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("String argument " + name + " is empty string");
        }
    }

    /**
     * <p>
     * Checks if the given Object array is null, empty or contains null element.
     * </p>
     *
     * @param array the array to check against.
     * @param name the name of the array argument.
     *
     * @throws IllegalArgumentException if array is null, empty or contains null element.
     */
    static void checkArray(Object[] array, String name) {
        if (array == null) {
            throw new IllegalArgumentException("Array argument " + name + " is null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array argument " + name + " is empty");
        }
        if (Arrays.asList(array).contains(null)) {
            throw new IllegalArgumentException("Array argument " + name + " contains null element");
        }
    }

    /**
     * <p>
     * Checks if the given int array is null, empty or contains null element.
     * </p>
     *
     * @param array the array to check against.
     * @param name the name of the array argument.
     *
     * @throws IllegalArgumentException if array is null, empty or contains null element.
     */
    static void checkArray(int[] array, String name) {
        if (array == null) {
            throw new IllegalArgumentException("Array argument " + name + " is null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array argument " + name + " is empty");
        }
    }

    /**
     * <p>
     * Checks if any of the causes is not null.
     * </p>
     *
     * @param causes the causes to check against.
     * @param message the message describing the exception.
     *
     * @throws BatchOperationException if any of the causes is not null.
     */
    static void checkAnyCauses(Throwable[] causes, String message)
        throws BatchOperationException {
        for (int i = 0; i < causes.length; i++) {
            if (causes[i] != null) {
                throw new BatchOperationException(message, causes);
            }
        }
    }

    /**
     * <p>
     * Checks if all of the causes are not null.
     * </p>
     *
     * @param causes the causes to check against.
     * @param message the message describing the exception.
     *
     * @throws BatchOperationException if all of the causes are not null.
     */
    static void checkAllCauses(Throwable[] causes, String message)
        throws BatchOperationException {
        for (int i = 0; i < causes.length; i++) {
            if (causes[i] == null) {
                return;
            }
        }
        throw new BatchOperationException(message, causes);
    }

    /**
     * <p>
     * Checks the causes for the batch version of getter methods. A BatchOperationException will be thrown if atomic is
     * true and any cause is not null, or if atomic is false and all causes are not null.
     * </p>
     *
     * @param causes the causes to check against.
     * @param message the message describing the exception.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws BatchOperationException if the batch version of getter methods fails.
     */
    static void checkGetCauses(Throwable[] causes, String message, boolean atomic)
        throws BatchOperationException {
        if (atomic) {
            checkAnyCauses(causes, message);
        } else {
            checkAllCauses(causes, message);
        }
    }

    /**
     * <p>
     * Converts the given java.util.Date to the java.sql.Timestamp for SQL operations.
     * </p>
     *
     * @param date the java.util.Date to be converted
     *
     * @return the converted java.sql.Timestamp
     */
    static Timestamp toSQLDate(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * <p>
     * Converts the given java.sql.Timestamp to the java.util.Date for setting the fields.
     * </p>
     *
     * @param date the java.sql.Timestamp to be converted
     *
     * @return the converted java.util.Date
     */
    static Date toUtilDate(Timestamp date) {
        return new Date(date.getTime());
    }

    /**
     * <p>
     * Try to close the connection object if it is not null. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param con the connection object.
     */
    static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore SQL error
            }
        }
    }

    /**
     * <p>
     * Try to close the prepared statement object if it is not null. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param pstmt the prepared statement object.
     */
    static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                // ignore SQL error
            }
        }
    }

    /**
     * <p>
     * Try to close the result set object if it is not null. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param rs the result set object.
     */
    static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore SQL error
            }
        }
    }

    /**
     * <p>
     * Commits the transaction for the connection object. If any error occurs, it will be wrapped as a
     * PersistenceException.
     * </p>
     *
     * @param con the connection object.
     *
     * @throws PersistenceException if transaction cannot be committed
     */
    static void commit(Connection con) throws PersistenceException {
        try {
            con.commit();
        } catch (SQLException e) {
            throw new PersistenceException("Fails to commit transaction", e);
        }
    }

    /**
     * <p>
     * Try to rollback the connection object. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param con the connection object.
     */
    static void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            // ignore SQL error
        }
    }

    /**
     * <p>
     * Starts transaction for the connection object. If any error occurs, it will be wrapped as a PersistenceException.
     * </p>
     *
     * @param con the connection object.
     *
     * @throws PersistenceException if transaction cannot be enabled
     */
    static void startTransaction(Connection con) throws PersistenceException {
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to enable transaction", e);
        }
    }

    /**
     * <p>
     * Try to end transaction for the connection object. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param con the connection object.
     */
    static void endTransaction(Connection con) {
        try {
            con.setAutoCommit(true);
        } catch (SQLException e) {
            // ignore SQL error
        }
    }
}
