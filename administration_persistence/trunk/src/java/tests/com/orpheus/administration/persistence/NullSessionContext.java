/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

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
 * A trivial implementation of the <code>SessionContext</code> interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

class NullSessionContext implements SessionContext {
    /**
     * Obtain a reference to the EJB local object that is associated with the instance.
     *
     * @return a reference to the EJB local object that is associated with the instance
     */
    public EJBLocalObject getEJBLocalObject() {
        return null;
    }

    /**
     * Obtain a reference to the EJB object that is currently associated with the instance.
     *
     * @return a reference to the EJB object that is currently associated with the instance
     */
    public EJBObject getEJBObject() {
        return null;
    }

    /**
     * Obtain a reference to the EJB object that is currently associated with the instance.
     *
     * @return a reference to the EJB object that is currently associated with the instance
     */
    public MessageContext getMessageContext() {
        return null;
    }

    /**
     * Deprecated. Use Principal getCallerPrincipal() instead.
     *
     * @return the identity of the caller
     */
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * Obtain the <code>Principal</code> that identifies the caller.
     *
     * @return the <code>Principal</code> that identifies the caller
     */
    public Principal getCallerPrincipal() {
        return null;
    }


    /**
     * Obtain the enterprise bean's remote home interface.
     *
     * @return the enterprise bean's remote home interface
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * Obtain the enterprise bean's local home interface.
     *
     * @return the enterprise bean's local home interface
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * Deprecated. Use the JNDI naming context java:comp/env to access enterprise bean's environment.
     *
     * @return the enterprise bean's environment
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * Test if the transaction has been marked for rollback only.
     *
     * @return an indication of the transaction has been marked for rollback only
     */
    public boolean getRollbackOnly() {
        return false;
    }

    /**
     * Get access to the EJB Timer Service.
     *
     * @return the EJB Timer Service
     */
    public TimerService getTimerService() {
        return null;
    }

    /**
     * Mark the current transaction for rollback.
     */
    public void setRollbackOnly() {
    }

    /**
     * Obtain the transaction demarcation interface.
     *
     * @return the transaction demarcation interface
     */
    public UserTransaction getUserTransaction() {
        return null;
    }

    /**
     * Deprecated. Use boolean isCallerInRole(String roleName) instead.
     *
     * @param role the security role
     * @return whether the caller is in the specified role
     */
    public boolean isCallerInRole(Identity role) {
        return true;
    }

    /**
     * Test if the caller has a given security role.
     *
     * @param roleName the role name
     * @return whether the caller is in the specified role
     */
    public boolean isCallerInRole(String roleName) {
        return true;
    }
}
