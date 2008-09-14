/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.security.Identity;
import java.security.Principal;
import java.util.Arrays;
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
 * Mock implementation of the SessionContext interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class SessionContextMock implements SessionContext {

    /**
     * <p>
     * Principal to be returned from getCallerPrincipal.
     * </p>
     */
    private static Principal principal;

    /**
     * <p>
     * Roles that caller have.
     * </p>
     */
    private static String[] roles;

    /**
     * <p>
     * Setter for principal field.
     * </p>
     *
     * @param callerPrincipal
     *            value to set
     */
    public static void setPrincipal(Principal callerPrincipal) {
        principal = callerPrincipal;
    }

    /**
     * <p>
     * Setter for roles field.
     * </p>
     *
     * @param callerRoles
     *            value to set
     */
    public static void setRoles(String[] callerRoles) {
        roles = callerRoles;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param arg0
     *            not used
     * @param <T>
     *            not used
     * @return null
     */
    public < T > T getBusinessObject(Class < T > arg0) {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public EJBLocalObject getEJBLocalObject() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public EJBObject getEJBObject() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    @SuppressWarnings("unchecked")
    public Class getInvokedBusinessInterface() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public MessageContext getMessageContext() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * <p>
     * Returns caller Principal.
     * </p>
     *
     * @return caller principal
     */
    public Principal getCallerPrincipal() {
        return principal;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public boolean getRollbackOnly() {
        return false;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public TimerService getTimerService() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @return null
     */
    public UserTransaction getUserTransaction() {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param arg0
     *            not used
     * @return false
     */
    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    /**
     * <p>
     * Returns if caller has passed role.
     * </p>
     *
     * @param role
     *            role to check
     * @return if caller has passed role
     */
    public boolean isCallerInRole(String role) {
        if (roles == null) {
            return false;
        }
        return Arrays.asList(roles).contains(role);
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param arg0
     *            not used
     * @return false
     */
    public Object lookup(String arg0) {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     */
    public void setRollbackOnly() {
    }

}
