/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import java.security.Identity;
import java.security.Principal;

import java.util.HashMap;
import java.util.Map;
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
 * Mock implementation of <code>SessionContext</code>. It's used to
 * simulate the ejb environment.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockSessionContext implements SessionContext {
    /**
     * <p>Mock session context entries.</p>
     */
    private Map<String, Object> entries = new HashMap<String, Object>();

    /**
     * Mock method.
     * @return always returns null
     */
    public MessageContext getMessageContext() {
        return null;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public EJBObject getEJBObject() {
        return null;
    }

    /**
     * Mock method.
     * @param c unused
     * @return always returns null
     */
    public Object getBusinessObject(Class c) {
        return null;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public EJBLocalObject getEJBLocalObject() {
        return null;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public Class getInvokedBusinessInterface() {
        return null;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public Principal getCallerPrincipal() {
        return null;
    }

    /**
     * Mock method.
     * @param arg0 unused
     * @return always return null
     */
    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    /**
     * Mock method.
     * @param arg0 unused
     * @return always returns null
     */
    public boolean isCallerInRole(String arg0) {
        return false;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public UserTransaction getUserTransaction() {
        return null;
    }

    /**
     * Mock method.
     */
    public void setRollbackOnly() {
    }

    /**
     * Mock method.
     * @return always returns false
     */
    public boolean getRollbackOnly() {
        return false;
    }

    /**
     * Mock method.
     * @return always returns null
     */
    public TimerService getTimerService() {
        return null;
    }

    /**
     * <p>
     * Returns the corresponding config item.
     * </p>
     *
     * @param name the config item name
     * @return the corresponding config item object
     */
    public Object lookup(String name) {
        return entries.get(name);
    }

    /**
     * <p>
     * Add an entry to the session context.
     * </p>
     *
     * @param key the key
     * @param value the value
     */
    public void addEntry(String key, Object value) {
        entries.put(key, value);
    }
}
