/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.accuracytests;

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
 * Dummy class of SessionContext for accuracy tests.
 * 
 * @author kinfkong
 * @version 3.2
 */
public class DummySessionContext implements SessionContext {

    /**
     * Not supported operation.
     */
    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public EJBObject getEJBObject() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public MessageContext getMessageContext() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public Identity getCallerIdentity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public Principal getCallerPrincipal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public EJBHome getEJBHome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public EJBLocalHome getEJBLocalHome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public Properties getEnvironment() {
        Properties p = new Properties();
        p.setProperty("of_namespace", "objectfactory.InformixRatePersistence");
        p.setProperty("of_rate_persistence_key", "ratePersistence");
        return p;
    }

    /**
     * Not supported operation.
     */
    public boolean getRollbackOnly() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public TimerService getTimerService() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public UserTransaction getUserTransaction() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public boolean isCallerInRole(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public boolean isCallerInRole(Identity arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Not supported operation.
     */
    public void setRollbackOnly() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");

    }

}
