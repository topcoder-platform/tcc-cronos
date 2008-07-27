/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

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

    public Object getBusinessObject(Class arg0) throws IllegalStateException {
        return null;
    }

    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        return null;
    }

    public EJBObject getEJBObject() throws IllegalStateException {
        return null;
    }

    public Class getInvokedBusinessInterface() throws IllegalStateException {

        return null;
    }

    public MessageContext getMessageContext() throws IllegalStateException {
        return null;
    }

    public Identity getCallerIdentity() {
        return null;
    }

    public Principal getCallerPrincipal() {
        return null;
    }

    public EJBHome getEJBHome() {
        return null;
    }

    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    public Properties getEnvironment() {
        return null;
    }

    public boolean getRollbackOnly() throws IllegalStateException {
        return false;
    }

    public TimerService getTimerService() throws IllegalStateException {
        return null;
    }

    public UserTransaction getUserTransaction() throws IllegalStateException {
        return null;
    }

    public boolean isCallerInRole(Identity arg0) {
        return false;
    }

    public boolean isCallerInRole(String arg0) {
        return false;
    }

    public Object lookup(String name) {
        if ("entityManager".equals(name)) {
            return new MockEntityManager();
        }
        if ("invlaid".equals(name)) {
            return "string";
        }
        return null;
    }

    public void setRollbackOnly() throws IllegalStateException {
    }

}
