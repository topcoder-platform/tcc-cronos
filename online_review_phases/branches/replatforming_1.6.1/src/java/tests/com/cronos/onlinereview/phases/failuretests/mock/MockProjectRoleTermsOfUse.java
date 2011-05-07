/*
 * Copyright (C) 20011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import com.topcoder.web.ejb.project.ProjectRoleTermsOfUse;

import java.rmi.RemoteException;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;


/**
 * Mock implementation.
 *
 * @author gjw99
 * @version 1.0
 */
public class MockProjectRoleTermsOfUse implements ProjectRoleTermsOfUse {
    /**
     * A mock!
     *
     * @param projectId A mock!
     * @param is A mock!
     * @param commonOltpDatasourceName A mock!
     *
     * @return A mock!
     *
     * @throws EJBException A mock!
     * @throws RemoteException A mock!
     */
    public List[] getTermsOfUse(int projectId, int[] is, String commonOltpDatasourceName)
        throws EJBException, RemoteException {
        return null;
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
