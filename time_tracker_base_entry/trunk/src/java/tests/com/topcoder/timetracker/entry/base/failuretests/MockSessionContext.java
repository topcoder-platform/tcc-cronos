/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

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
 * A mock class of SessionContext.
 * </p>
 * 
 * @author Hacker_QC
 * @version 3.2
 */
public class MockSessionContext implements SessionContext {

    /**
     * A mock method.
     */
    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        return null;
    }

    /**
     * A mock method.
     */
    public EJBObject getEJBObject() throws IllegalStateException {
        return null;
    }

    /**
     * A mock method.
     */
    public MessageContext getMessageContext() throws IllegalStateException {
        return null;
    }

    /**
     * A mock method.
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * A mock method.
     */
    public Principal getCallerPrincipal() {
        return null;
    }

    /**
     * A mock method.
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * A mock method.
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * A mock method.
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * A mock method.
     */
    public boolean getRollbackOnly() throws IllegalStateException {
        return false;
    }

    /**
     * A mock method.
     */
    public TimerService getTimerService() throws IllegalStateException {
        return null;
    }

    /**
     * A mock method.
     */
    public UserTransaction getUserTransaction() throws IllegalStateException {
        return null;
    }

    /**
     * A mock method.
     */
    public boolean isCallerInRole(String arg0) {
        return false;
    }

    /**
     * A mock method.
     */
    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    /**
     * A mock method.
     */
    public void setRollbackOnly() throws IllegalStateException {
    }
}
