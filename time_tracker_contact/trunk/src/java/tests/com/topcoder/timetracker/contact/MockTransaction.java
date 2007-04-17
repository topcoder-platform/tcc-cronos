/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import java.sql.SQLException;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.mockrunner.mock.ejb.MockUserTransaction;

/**
 * <p>
 * This is mock implementation of <code>UserTransaction</code>.
 * It will manage a connection and commit/roll back it at the end of transaction.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockTransaction extends MockUserTransaction {

    /**
     * <p>
     * Indicate whether transaction has been commited or rolled back.
     * </p>
     */
    private boolean active = false;

    /**
     * <p>
     * The connection managed in the transaction.
     * </p>
     */
    private WrappedConnection connection = null;

    /**
     * <p>
     * Set connection managed in the transaction.
     * </p>
     *
     * @param connection The connection to be managed in the transaction.
     */
    public void setConnection(WrappedConnection connection) {
        this.connection = connection;
    }

    /**
     * <p>
     * Get connection managed in the transaction.
     * </p>
     *
     * @return <code>WrappedConnection</code>
     */
    public WrappedConnection getConnection() {
        return connection;
    }

    /**
     * <p>
     * Start the transaction.
     * </p>
     *
     * @throws SystemException to JUnit
     * @throws NotSupportedException to JUnit
     */
    public void begin() throws SystemException, NotSupportedException {
        super.begin();
        this.active = true;
    }

    /**
    * <p>
    * Commits the transaction.
    * </p>
    *
    * @throws RollbackException to JUnit
    * @throws HeuristicMixedException to JUnit
    * @throws HeuristicRollbackException to JUnit
    * @throws SecurityException to JUnit
    * @throws IllegalStateException to JUnit
    * @throws SystemException to JUnit
    */
    public void commit()
        throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
            SystemException {
        super.commit();
        if (this.connection != null) {
            try {
                this.connection.getUnderlineConnection().commit();
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            } finally {
                try {
                    this.connection.getUnderlineConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace(System.err);
                }
            }
            this.connection = null;
        }
        this.active = false;
    }

    /**
     * <p>
     * Rolls back the transaction.
     * </p>
     *
     * @throws SystemException to JUnit
     */
    public void rollback() throws SystemException {
        super.rollback();
        if (this.connection != null) {
            try {
                this.connection.getUnderlineConnection().rollback();
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            } finally {
                try {
                    this.connection.getUnderlineConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace(System.err);
                }
            }
            this.connection = null;
        }
        this.active = false;
    }

    /**
     * <p>
     * Check whether the transaction is active(has began, but not commited and not rolled back).
     * </p>
     *
     * @return true if this transaction is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
}
