/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.j2ee;

import javax.transaction.UserTransaction;

/**
 * <p>
 * Mock implementation of <code>UserTransaction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockUserTransaction implements UserTransaction {
    /**
     * <p>
     * Implements the begin() method.
     * </p>
     */
    public void begin() {
        // empty

    }

    /**
     * <p>
     * Implements the commit() method.
     * </p>
     */
    public void commit() {
        // empty

    }

    /**
     * <p>
     * Implements the rollback() method.
     * </p>
     */
    public void rollback() {
        // empty

    }

    /**
     * <p>
     * Implements the setRollbackOnly() method.
     * </p>
     */
    public void setRollbackOnly() {
        // empty

    }

    /**
     * <p>
     * Implements the getStatus() method.
     * </p>
     *
     * @return always return 0.
     */
    public int getStatus() {
        return 0;
    }

    /**
     * <p>
     * Implements the setTransactionTimeout(int) method.
     * </p>
     *
     * @param arg0 not used
     */
    public void setTransactionTimeout(int arg0) {
        // empty
    }
}
