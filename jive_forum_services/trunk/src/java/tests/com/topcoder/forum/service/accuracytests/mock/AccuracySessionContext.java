/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.accuracytests.mock;

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
 * Mock implementation of <code>SessionContext</code>. It's used to simulate the ejb environment.
 * </p>
 *
 * @author kaqi072821
 * @version 1.0
 */
public class AccuracySessionContext implements SessionContext {
    /**
     * The mock adminUserId config item.
     */
    private Object adminUserId;

    /**
     * The mock applicationCategoryId config item.
     */
    private Object appCatId;

    /**
     * The mock componentCategoryId config item.
     */
    private Object comCatId;

    /**
     * The mock assemblyCategoryId config item.
     */
    private Object assCatId;

    /**
     * The mock testingCompetitionCategoryId config item.
     */
    private Object testCatId;

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public MessageContext getMessageContext() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public EJBObject getEJBObject() {
        return null;
    }

    /**
     * Mock method.
     *
     * @param c unused
     * @return always returns null
     */
    public Object getBusinessObject(Class c) {
        return null;
    }

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public EJBLocalObject getEJBLocalObject() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public Class getInvokedBusinessInterface() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return always returns null
     */
    public Principal getCallerPrincipal() {
        return null;
    }

    /**
     * Mock method.
     *
     * @param arg0 unused
     * @return always return null
     */
    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    /**
     * Mock method.
     *
     * @param arg0 unused
     * @return always returns null
     */
    public boolean isCallerInRole(String arg0) {
        return false;
    }

    /**
     * Mock method.
     *
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
     *
     * @return always returns false
     */
    public boolean getRollbackOnly() {
        return false;
    }

    /**
     * Mock method.
     *
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
        if ("AdminUserId".equals(name)) {
            return adminUserId;
        } else if ("ApplicationCategoryId".equals(name)) {
            return appCatId;
        } else if ("AssemblyCompetitionCategoryId".equals(name)) {
            return assCatId;
        } else if ("ComponentCategoryId".equals(name)) {
            return comCatId;
        } else if ("TestingCompetitionCategoryId".equals(name)) {
            return testCatId;
        }

        return null;
    }

    /**
     * Mock method. Set the adminUserId to the specified object.
     *
     * @param val new config object
     */
    public void setAdminUserId(Object val) {
        this.adminUserId = val;
    }

    /**
     * Mock method. Set the applicationCategoryId to the specified object.
     *
     * @param val new config object
     */
    public void setApplicationCategoryId(Object val) {
        this.appCatId = val;
    }

    /**
     * Mock method. Set the AssemblyCompetitionCategoryId to the specified object.
     *
     * @param val new config object
     */
    public void setAssemblyCompetitionCategoryId(Object val) {
        this.assCatId = val;
    }

    /**
     * Mock method. Set the ComponentCategoryId to the specified object.
     *
     * @param val new config object
     */
    public void setComponentCategoryId(Object val) {
        this.comCatId = val;
    }

    /**
     * Mock method. Set the TestingCompetitionCategoryId to the specified object.
     *
     * @param val new config object
     */
    public void setTestingCompetitionCategoryId(Object val) {
        this.testCatId = val;
    }
}