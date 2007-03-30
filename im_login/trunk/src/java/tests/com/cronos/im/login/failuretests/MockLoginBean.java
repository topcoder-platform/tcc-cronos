/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import java.rmi.RemoteException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;

/**
 * Mock implementation of login bean.
 *
 * @author mittu
 * @version 1.0
 */
public class MockLoginBean implements SessionBean {

    /**
     * Empty method does nothing.
     */
    public void ejbCreate() {
        // empty
    }

    /**
     * Empty method does nothing.
     *
     * @throws RemoteException
     *             not thrown.
     */
    public void ejbActivate() throws RemoteException {
        // empty
    }

    /**
     * Empty method does nothing.
     *
     * @throws RemoteException
     *             not thrown.
     */
    public void ejbPassivate() throws RemoteException {
        // empty
    }

    /**
     * Empty method does nothing.
     *
     * @throws RemoteException
     *             not thrown.
     */
    public void ejbRemove() throws RemoteException {
        // empty
    }

    /**
     * Empty method does nothing.
     *
     * @param arg0
     *            not used.
     * @throws RemoteException
     *             not thrown.
     */
    public void setSessionContext(SessionContext arg0) throws RemoteException {
        // empty
    }

    /**
     * Mock implementation of ejb login.
     *
     * @param username
     *            The user name for login.
     * @param password
     *            The password for login.
     *
     * @return <code>TCSubject</code> containing the user id.
     *
     * @throws RemoteException
     *             If the login fails and the user name is <i>RemoteExceptionUser</i>.
     * @throws GeneralSecurityException
     *             If the login fails and if the user name is not invalid.
     */
    public TCSubject login(String username, String password) throws RemoteException, GeneralSecurityException {

        // checks the credentials of the login
        if ("Administrator".equals(username) && "failure_admin".equals(password)) {
            return new TCSubject(1);
        } else if ("Manager".equals(username) && "failure_manager".equals(password)) {
            return new TCSubject(2);
        } else if ("Client".equals(username) && "failure_client".equals(password)) {
            return new TCSubject(3);
        } else if ("UnAuthorized_Administrator".equals(username) && "unauth_admin".equals(password)) {
            return new TCSubject(4);
        } else if ("UnAuthorized_Manager".equals(username) && "unauth_manager".equals(password)) {
            return new TCSubject(5);
        } else if ("RemoteExceptionUser".equals(username)) {
            throw new RemoteException();
        }
        throw new GeneralSecurityException();
    }

}
