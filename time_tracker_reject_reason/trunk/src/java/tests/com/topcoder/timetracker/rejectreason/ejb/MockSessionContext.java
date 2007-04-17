/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

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
 * A mock implementation of SessionContext, for test purpose.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockSessionContext implements SessionContext {
    /**
     * Returns null.
     *
     * @return null.
     */
    public EJBLocalObject getEJBLocalObject() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public EJBObject getEJBObject() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public MessageContext getMessageContext() {
        return null;
    }

    /**
     * Returns null.
     *
     * @param arg0 not used.
     *
     * @return null.
     */
    public Object getBusinessObject(Class arg0) {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public Class getInvokedBusinessInterface() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public Principal getCallerPrincipal() {
        return null;
    }

    /**
     * Returns false.
     *
     * @param arg0 not used.
     *
     * @return false.
     */
    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    /**
     * Returns false.
     *
     * @param arg0 not used.
     *
     * @return false.
     */
    public boolean isCallerInRole(String arg0) {
        return false;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public UserTransaction getUserTransaction() {
        return null;
    }

    /**
     * Do nothing.
     */
    public void setRollbackOnly() {
    }

    /**
     * Returns false.
     *
     * @return false.
     */
    public boolean getRollbackOnly() {
        return false;
    }

    /**
     * Returns null.
     *
     * @return null.
     */
    public TimerService getTimerService() {
        return null;
    }

    /**
     * Returns null.
     *
     * @param arg0 not used.
     *
     * @return null.
     */
    public Object lookup(String arg0) {
        return null;
    }
}
