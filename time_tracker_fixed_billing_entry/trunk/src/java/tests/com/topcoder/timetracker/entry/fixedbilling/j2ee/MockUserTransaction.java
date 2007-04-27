/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;


/**
 * <p>
 * The mock MockUserTransaction.
 * </p>
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class MockUserTransaction implements UserTransaction {
    /**
     * The mock constructor.
     */
    public MockUserTransaction() {
    }

    /**
     * The mock instance.
     */
    public void begin() throws NotSupportedException, SystemException {
    }

    /**
     * The mock instance.
     */
    public void commit()
        throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, 
            IllegalStateException, SystemException {
    }

    /**
     * The mock instance.
     */
    public void rollback() throws IllegalStateException, SecurityException, SystemException {
    }

    /**
     * The mock instance.
     */
    public void setRollbackOnly() throws IllegalStateException, SystemException {
    }

    /**
     * The mock instance.
     *
     * @return mock instance.
     */
    public int getStatus() throws SystemException {
        return 0;
    }

    /**
     * The mock instance.
     *
     * @param arg0 mock instance.
     */
    public void setTransactionTimeout(int arg0) throws SystemException {
    }
}
