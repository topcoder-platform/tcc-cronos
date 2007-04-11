/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

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
 * Just mock implementation of SessionContext.
 *
 * @author TCSDEVELOPER
 */
public class MockSessionContext implements SessionContext {
    /**
     * ignore this.
     *
     * @return ignore this.
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     */
    public Principal getCallerPrincipal() {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     *
     * @throws IllegalStateException ignore this.
     */
    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     *
     * @throws IllegalStateException ignore this.
     */
    public EJBObject getEJBObject() throws IllegalStateException {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     *
     * @throws IllegalStateException ignore this.
     */
    public MessageContext getMessageContext() throws IllegalStateException {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     *
     * @throws IllegalStateException ignore this.
     */
    public boolean getRollbackOnly() throws IllegalStateException {
        return false;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     *
     * @throws IllegalStateException ignore this.
     */
    public TimerService getTimerService() throws IllegalStateException {
        return null;
    }

    /**
     * ignore this.
     *
     * @return ignore this.
     *
     * @throws IllegalStateException ignore this.
     */
    public UserTransaction getUserTransaction() throws IllegalStateException {
        return null;
    }

    /**
     * ignore this.
     *
     * @param arg0 ignore this.
     *
     * @return ignore this.
     */
    public boolean isCallerInRole(String arg0) {
        return false;
    }

    /**
     * ignore this.
     *
     * @param arg0 ignore this.
     *
     * @return ignore this.
     */
    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    /**
     * ignore this.
     *
     * @throws IllegalStateException ignore this.
     */
    public void setRollbackOnly() throws IllegalStateException {
    }
}
