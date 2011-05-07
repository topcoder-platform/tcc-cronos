/*
 * Copyright (C) 20011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import com.topcoder.web.ejb.user.UserTermsOfUse;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;


/**
 * Mock implementation of UserTermsOfUse.
 *
 * @author gjw99
 * @version 1.0
 */
public class MockUserTermsOfUse implements UserTermsOfUse {
    /**
     * A mock!
     *
     * @param userId A mock!
     * @param termsId A mock!
     * @param commonOltpDatasourceName A mock!
     *
     * @return A mock!
     *
     * @throws EJBException A mock!
     * @throws RemoteException A mock!
     */
    public boolean hasTermsOfUse(long userId, long termsId, String commonOltpDatasourceName)
        throws EJBException, RemoteException {
        return false;
    }

    /**
     * A mock!
     *
     * @return A mock!
     *
     * @throws RemoteException A mock!
     */
    public EJBHome getEJBHome() throws RemoteException {
        return null;
    }

    /**
     * A mock!
     *
     * @return A mock!
     *
     * @throws RemoteException A mock!
     */
    public Handle getHandle() throws RemoteException {
        return null;
    }

    /**
     * A mock!
     *
     * @return A mock!
     *
     * @throws RemoteException A mock!
     */
    public Object getPrimaryKey() throws RemoteException {
        return null;
    }

    /**
     * A mock!
     *
     * @param arg0 A mock!
     *
     * @return A mock!
     *
     * @throws RemoteException A mock!
     */
    public boolean isIdentical(EJBObject arg0) throws RemoteException {
        return false;
    }

    /**
     * A mock!
     *
     * @throws RemoteException A mock!
     * @throws RemoveException A mock!
     */
    public void remove() throws RemoteException, RemoveException {
    }
}
