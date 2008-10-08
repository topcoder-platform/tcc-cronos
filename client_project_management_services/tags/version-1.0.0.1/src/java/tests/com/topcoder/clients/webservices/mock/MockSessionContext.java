/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import java.security.Identity;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
 * <p>Mockup of <code>SessionContext</code>.</p>
 *
 * @author CaDenza
 * @version 1.0
 */
public class MockSessionContext implements SessionContext {

    /**
     * Storage of declared role.
     */
    private List<String> roles = new ArrayList<String>();

    /**
     * The Principal instance.
     */
    private Principal principal;

    /**
     * <p>Initializes <code>persistenceUnit</code>.</p>
     */
    public MockSessionContext() {
    }

    /**
     * Setter for principal.
     *
     * @param principal
     *      The custom principal.
     */
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public EJBLocalObject getEJBLocalObject() {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public EJBObject getEJBObject() {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public MessageContext getMessageContext() {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @param aClass ignored
     * @return <code>null</code> always.
     */
    @SuppressWarnings("unchecked")
    public Object getBusinessObject(Class aClass) {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    @SuppressWarnings("unchecked")
    public Class getInvokedBusinessInterface() {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    @SuppressWarnings("deprecation")
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public Principal getCallerPrincipal() {
        return principal;
    }

    /**
     * <p>The method is a stub. Always returns <code>false</code>.</p>
     *
     * @param identity ignored
     * @return <code>false</code> always.
     */
    @SuppressWarnings("deprecation")
    public boolean isCallerInRole(Identity identity) {
        return false;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @param string ignored
     * @return <code>null</code> always.
     */
    public boolean isCallerInRole(String string) {
        return roles.contains(string);
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public UserTransaction getUserTransaction() {
        return null;
    }

    /**
     * <p>The method is a stub. Does nothing.
     */
    public void setRollbackOnly() {
    }

    /**
     * <p>The method is a stub. Always returns <code>false</code>.</p>
     *
     * @return <code>false</code> always.
     */
    public boolean getRollbackOnly() {
        return false;
    }

    /**
     * <p>The method is a stub. Always returns <code>null</code>.</p>
     *
     * @return <code>null</code> always.
     */
    public TimerService getTimerService() {
        return null;
    }

    /**
     * @param string <ul> <li><code>"reportsUnitName"</code> - returns <code>"hermesReportsPersistence"</code></li>
     *               <li><code>"hermesReportsPersistence"</code> - returns <code>EntityManager</code>instance</li>
     *               <li><code>"javax.persistence.PersistenceException"</code> - returns
     *               <code>FailTestEntityManager</code>instance</li> <li><code>"java.lang.ClassCastException"</code> -
     *               returns <code>Boolean.FALSE</code> to provoke a class cast error in the <code>initialization</code>
     *               method</li> <li>any other argument - returns <code>null</code></li></ul>
     * @return corresponding object to input: <code>persistenceUnit</code>, <code>EntityManager</code> or
     *         <code>FailTestEntityManager</code>.
     */
    public Object lookup(String string) {
        return null;
    }

    /**
     * Set defined roles.
     *
     * @param roles
     *      The defined roles.
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * Add new role.
     *
     * @param role
     *      The defined role.
     */
    public void addRole(String role) {
        this.roles.add(role);
    }

    /**
     * Clear all stored role.
     */
    public void clearRoles() {
        this.roles.clear();
    }
}