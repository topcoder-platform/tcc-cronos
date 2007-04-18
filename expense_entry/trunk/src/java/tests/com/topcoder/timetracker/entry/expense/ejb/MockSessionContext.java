/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import java.security.Identity;
import java.security.Principal;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;


/**
 * <p>
 * A mock class which extends SessionContext for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockSessionContext implements SessionContext {
    /** Represents the RollbackOnly value. */
    private boolean rollbackOnly = false;

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getEJBLocalObject()
     */
    public EJBLocalObject getEJBLocalObject() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getEJBObject()
     */
    public EJBObject getEJBObject() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getMessageContext()
     */
    public MessageContext getMessageContext() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param arg0 arg
     *
     * @return null.
     *
     * @see SessionContext#getBusinessObject(java.lang.Class)
     */
    public Object getBusinessObject(Class arg0) {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getEJBHome()
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getEJBLocalHome()
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getEnvironment()
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getCallerIdentity()
     * @deprecated
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getCallerPrincipal()
     */
    public Principal getCallerPrincipal() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param arg0 arg
     *
     * @return null.
     *
     * @see javax.ejb.SessionContext#isCallerInRole(Identity)
     * @deprecated
     */
    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param arg0 arg
     *
     * @return null.
     *
     * @see javax.ejb.SessionContext#isCallerInRole(String)
     */
    public boolean isCallerInRole(String arg0) {
        return false;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getUserTransaction()
     */
    public UserTransaction getUserTransaction() {
        return null;
    }

    /**
     * @see SessionContext#setRollbackOnly()
     */
    public void setRollbackOnly() {
        rollbackOnly = true;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getRollbackOnly()
     */
    public boolean getRollbackOnly() {
        return rollbackOnly;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null.
     *
     * @see SessionContext#getTimerService()
     */
    public TimerService getTimerService() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param arg0 arg.
     *
     * @return null.
     *
     * @see SessionContext#lookup(String)
     */
    public Object lookup(String arg0) {
        return null;
    }
}
