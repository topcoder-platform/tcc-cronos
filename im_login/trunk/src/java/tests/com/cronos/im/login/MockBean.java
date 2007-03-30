/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import java.rmi.RemoteException;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * Mock login bean for testing purpose.
 * </p>
 *
 * @author tyrian
 * @version 1.0
 */
public class MockBean implements SessionBean {
    /**
     * No implementation.
     */
    public void ejbCreate() {
        // empty
    }

    /**
     * No implementation.
     */
    public void ejbActivate() {
        // empty
    }

    /**
     * No implementation.
     */
    public void ejbPassivate() {
        // empty
    }

    /**
     * No implementation.
     */
    public void ejbRemove() {
        // empty
    }

    /**
     * No implementation.
     *
     * @param arg0
     *            session context.
     */
    public void setSessionContext(SessionContext arg0) {
        // empty
    }

    /**
     * <p>
     * A mock login implementation for testing purpose.
     * </p>
     *
     * @param username
     *            the user name to match
     * @param password
     *            the password to match
     * @return a TCSubject containing the user id.
     * @throws RemoteException
     *             will not be thrown
     * @throws GeneralSecurityException
     *             if the login fails
     */
    public TCSubject login(String username, String password) throws RemoteException, GeneralSecurityException {
        TCSubject subject = null;
        if ("manager".equals(username) && "manager".equals(password)) {
            subject = new TCSubject(3); // actual manager
        } else if ("dummy_manager".equals(username) && "manager".equals(password)) {
            subject = new TCSubject(6); // dummy manager
        } else if ("admin".equals(username) && "admin".equals(password)) {
            subject = new TCSubject(1); // admin
        } else if ("dummy_admin".equals(username) && "admin".equals(password)) {
            subject = new TCSubject(4); // dummy admin
        } else if ("junit_user".equals(username) && "pwd".equals(password)) {
            subject = new TCSubject(2); // client
        } else {
            throw new GeneralSecurityException();
        }
        return subject;
    }

    /**
     * No implementation.
     *
     * @param loginId
     *            login id
     * @return always null
     * @throws RemoteException
     *             not implemented
     * @throws GeneralSecurityException
     *             not implemented
     */
    public TCSubject login(long loginId) throws RemoteException, GeneralSecurityException {
        return null;
    }

    /**
     * No implementation.
     *
     * @return always null.
     * @throws RemoteException
     *             not implemented
     */
    public EJBHome getEJBHome() throws RemoteException {
        return null;
    }

    /**
     * No implementation.
     *
     * @return always null.
     * @throws RemoteException
     *             not implemented
     */
    public Handle getHandle() throws RemoteException {
        return null;
    }

    /**
     * No implementation.
     *
     * @return always null.
     * @throws RemoteException
     *             not implemented
     */
    public Object getPrimaryKey() throws RemoteException {
        return null;
    }

    /**
     * No implementation.
     *
     * @param arg0
     *            ejb object
     * @return always false.
     * @throws RemoteException
     *             not implemented
     */
    public boolean isIdentical(EJBObject arg0) throws RemoteException {
        return false;
    }

    /**
     * No implementation.
     *
     * @throws RemoteException
     *             not implemented
     * @throws RemoveException
     *             not implemented
     */
    public void remove() throws RemoteException, RemoveException {
    }
}