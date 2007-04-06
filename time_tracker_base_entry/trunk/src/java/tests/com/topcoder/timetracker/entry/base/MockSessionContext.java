/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

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
     * Ignore this.
     *
     * @return Ignore this.
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     */
    public Principal getCallerPrincipal() {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     *
     * @throws IllegalStateException Ignore this.
     */
    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     *
     * @throws IllegalStateException Ignore this.
     */
    public EJBObject getEJBObject() throws IllegalStateException {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     *
     * @throws IllegalStateException Ignore this.
     */
    public MessageContext getMessageContext() throws IllegalStateException {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     *
     * @throws IllegalStateException Ignore this.
     */
    public boolean getRollbackOnly() throws IllegalStateException {
        return false;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     *
     * @throws IllegalStateException Ignore this.
     */
    public TimerService getTimerService() throws IllegalStateException {
        return null;
    }

    /**
     * Ignore this.
     *
     * @return Ignore this.
     *
     * @throws IllegalStateException Ignore this.
     */
    public UserTransaction getUserTransaction() throws IllegalStateException {
        return null;
    }

    /**
     * Ignore this.
     *
     * @param arg0 Ignore this.
     *
     * @return Ignore this.
     */
    public boolean isCallerInRole(String arg0) {
        return false;
    }

    /**
     * Ignore this.
     *
     * @param arg0 Ignore this.
     *
     * @return Ignore this.
     */
    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    /**
     * Ignore this.
     *
     * @throws IllegalStateException Ignore this.
     */
    public void setRollbackOnly() throws IllegalStateException {
    }
}
