/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import javax.transaction.UserTransaction;


/**
 * This is a mock implementation of UserTransaction interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTransaction implements UserTransaction {
    /**
     * Do nothing.
     */
    public void begin() {
    }

    /**
     * Do nothing.
     */
    public void commit() {
    }

    /**
     * Do nothing.
     */
    public void rollback() {
    }

    /**
     * Do nothing.
     */
    public void setRollbackOnly() {
    }

    /**
     * Returns zero.
     *
     * @return zero.
     */
    public int getStatus() {
        return 0;
    }

    /**
     * Do nothing.
     *
     * @param arg0 never used.
     */
    public void setTransactionTimeout(int arg0) {
    }
}
