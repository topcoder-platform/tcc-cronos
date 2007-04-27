/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.accuracytests;

import javax.transaction.*;

/**
 * <p>This is a mock transaction implementation.</p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class MockTransaction implements UserTransaction {
    private boolean beginCalled;
    private boolean commitCalled;
    private boolean rollbackCalled;
    private boolean rollbackOnlyCalled;
    private int transactionTimeout;
    private int beginCalls;
    private int commitCalls;
    private int rollbackCalls;
    private int rollbackOnlyCalls;

    public MockTransaction() {
        reset();
    }

    public void reset() {
        beginCalled = false;
        commitCalled = false;
        rollbackCalled = false;
        rollbackOnlyCalled = false;
        transactionTimeout = 0;
        beginCalls = 0;
        commitCalls = 0;
        rollbackCalls = 0;
        rollbackOnlyCalls = 0;
    }

    public boolean wasBeginCalled() {
        return beginCalled;
    }

    public boolean wasCommitCalled() {
        return commitCalled;
    }

    public boolean wasRollbackCalled() {
        return rollbackCalled;
    }

    public boolean wasRollbackOnlyCalled() {
        return rollbackOnlyCalled;
    }

    public int getTransactionTimeout() {
        return transactionTimeout;
    }

    public int getNumberBeginCalls() {
        return beginCalls;
    }

    public int getNumberCommitCalls() {
        return commitCalls;
    }

    public int getNumberRollbackCalls() {
        return rollbackCalls;
    }

    public int getNumberRollbackOnlyCalls() {
        return rollbackOnlyCalls;
    }

    public int getStatus() throws SystemException {
        if (rollbackCalled) {
            return 4;
        }

        if (commitCalled) {
            return 3;
        }

        if (rollbackOnlyCalled) {
            return 1;
        }

        return (!beginCalled) ? 6 : 0;
    }

    public void begin() throws NotSupportedException, SystemException {
        beginCalled = true;
        commitCalled = false;
        rollbackCalled = false;
        rollbackOnlyCalled = false;
        beginCalls++;
    }

    public void commit()
        throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException,
            IllegalStateException, SystemException {
        commitCalled = true;
        commitCalls++;
    }

    public void rollback() throws IllegalStateException, SecurityException, SystemException {
        rollbackCalled = true;
        rollbackCalls++;
    }

    public void setRollbackOnly() throws IllegalStateException, SystemException {
        rollbackOnlyCalled = true;
        rollbackOnlyCalls++;
    }

    public void setTransactionTimeout(int i) throws SystemException {
        transactionTimeout = i;
    }
}
