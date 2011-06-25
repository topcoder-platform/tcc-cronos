/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.rmi.RemoteException;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.login.LoginRemote;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockLoginRemote implements LoginRemote {

    /**
     * Mock method.
     */
    public EJBHome getEJBHome() throws RemoteException {
        return null;
    }

    /**
     * Mock method.
     */
    public Handle getHandle() throws RemoteException {
        return null;
    }

    /**
     * Mock method.
     */
    public Object getPrimaryKey() throws RemoteException {
        return null;
    }

    /**
     * Mock method.
     */
    public boolean isIdentical(EJBObject arg0) throws RemoteException {
        return false;
    }

    /**
     * Mock method.
     */
    public void remove() throws RemoteException, RemoveException {

    }

    /**
     * Mock method.
     */
    public TCSubject login(String username, String password) throws RemoteException, GeneralSecurityException {
        return null;
    }

    /**
     * Mock method.
     */
    public TCSubject login(String username, String password, String dataSource) throws RemoteException,
            GeneralSecurityException {
        TCSubject tcSubject = new TCSubject(10L);
        return tcSubject;
    }

}
