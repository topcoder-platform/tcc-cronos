/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.failuretests;

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
 * Mock class implements SessionContext for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockSessionContext implements SessionContext {

    /**
     * Represents unitName config value.
     */
    private Object unitName;

    /**
     * Represents logger config value.
     */
    private Object logger;

    /**
     * Represents entityManager config value.
     */
    private Object entityManager;

    /**
     * Do nothing.
     *
     * @param unitName
     *            the unitName value.
     * @param logger
     *            the logger value.
     * @param entityManager
     *            the entityManager value.
     */
    public MockSessionContext(Object unitName, Object logger, Object entityManager) {
        this.unitName = unitName;
        this.logger = logger;
        this.entityManager = entityManager;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @throws IllegalStateException
     *             not thrown.
     * @return null
     */
    public Object getBusinessObject(Class arg0) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @throws IllegalStateException
     *             not thrown.
     * @return null
     */
    public EJBLocalObject getEJBLocalObject() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @throws IllegalStateException
     *             not thrown.
     * @return null
     */
    public EJBObject getEJBObject() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @throws IllegalStateException
     *             not thrown.
     * @return null
     */
    public Class getInvokedBusinessInterface() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @throws IllegalStateException
     *             not thrown.
     * @return null
     */
    public MessageContext getMessageContext() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @return null
     */
    public Identity getCallerIdentity() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @return null
     */
    public Principal getCallerPrincipal() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @return null
     */
    public EJBHome getEJBHome() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @return null
     */
    public EJBLocalHome getEJBLocalHome() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @return null
     */
    public Properties getEnvironment() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @throws IllegalStateException
     *             not thrown.
     * @return false
     */
    public boolean getRollbackOnly() {
        // DO NOTHING
        return false;
    }

    /**
     * Do nothing.
     *
     * @throws IllegalStateException
     *             not thrown.
     * @return null
     */
    public TimerService getTimerService() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @throws IllegalStateException
     *             not thrown.
     * @return null
     */
    public UserTransaction getUserTransaction() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @return false
     */
    public boolean isCallerInRole(Identity arg0) {
        // DO NOTHING
        return false;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @return false
     */
    public boolean isCallerInRole(String arg0) {
        // DO NOTHING
        return false;
    }

    /**
     * Return the given value from the constructor.
     *
     * @param name
     *            the given name
     * @return the
     *            value
     */
    public Object lookup(String name) {
        if ("java:comp/env/unitName".equals(name)) {
            return this.unitName;
        }
        if ("java:comp/env/logger".equals(name)) {
            return this.logger;
        }
        if (this.unitName != null && this.unitName.equals(name)) {
            return this.entityManager;
        }
        return null;
    }

    /**
     * Do nothing.
     *
     * @throws IllegalStateException
     *             not thrown.
     */
    public void setRollbackOnly() {
        // DO NOTHING

    }

}
