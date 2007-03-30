/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.accuracytests;

import java.rmi.RemoteException;


import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;

/**
 * Sample login bean.
 *
 * @author stylecheck
 * @version 1.0
 */
public class SampleLoginBean implements SessionBean {

    /**
     * Void method.
     */
    public void ejbCreate() {

    }

    /**
     * Void method.
     *
     * @throws RemoteException never thrown.
     */
    public void ejbActivate() throws RemoteException {

    }

    /**
     * Void method.
     *
     * @throws RemoteException never thrown.
     */
    public void ejbPassivate() throws RemoteException {

    }

    /**
     * Void method.
     *
     * @throws RemoteException never thrown.
     */
    public void ejbRemove() throws RemoteException {

    }

    /**
     * Void method.
     * @param arg0 An instance of <code>SessionContext</code>.
     * @throws RemoteException never thrown.
     */
    public void setSessionContext(SessionContext arg0) throws RemoteException {

    }

    /**
     * Sample validation of login.
     *
     * @param user
     *            The user name to validate.
     * @param password
     *            The corresponding password for the user
     * @return an instance of <code>TCSubject</code> containing the user id.
     *
     * @throws RemoteException
     *             This exception is never thrown.
     * @throws GeneralSecurityException
     *             If unable to login
     */
    public TCSubject login(String user, String password) throws RemoteException, GeneralSecurityException {
        TCSubject subject = null;
        if ("administrator".equals(user) && "adminpwd".equals(password)) {
            subject = new TCSubject(1);
        } else if ("manager".equals(user) && "managerpwd".equals(password)) {
            subject = new TCSubject(2);
        } else if ("user".equals(user) && "userpwd".equals(password)) {
            subject = new TCSubject(3);
        } else {
            throw new GeneralSecurityException();
        }
        return subject;
    }

}
