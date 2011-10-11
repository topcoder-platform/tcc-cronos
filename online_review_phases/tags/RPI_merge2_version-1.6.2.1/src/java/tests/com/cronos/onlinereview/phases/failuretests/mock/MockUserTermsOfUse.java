/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;

import com.topcoder.web.ejb.user.UserTermsOfUse;
/**
 * <p>
 * A mock implementation of {@link UserTermsOfUse} class to be used for testing.
 * </p>
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>added unimplemented methods.</li>
 * </ol>
 * </p>
 *
 * @author TMALBONPH
 * @version 1.6.2
 * @since 1.6.2
 */
public class MockUserTermsOfUse implements UserTermsOfUse, EJBHome {

    /**
     * <p>Constructs new <code>MockUserTermsOfUse</code> instance.</p>
     */
    public MockUserTermsOfUse() {
        // does nothing.
    }

// ----------------------------------------------- UserTermsOfUse

    /* (non-Javadoc)
     * @see javax.ejb.EJBObject#getEJBHome()
     */
    @Override
    public EJBHome getEJBHome() throws RemoteException {
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ejb.EJBObject#getHandle()
     */
    @Override
    public Handle getHandle() throws RemoteException {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.ejb.EJBObject#getPrimaryKey()
     */
    @Override
    public Object getPrimaryKey() throws RemoteException {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.ejb.EJBObject#isIdentical(javax.ejb.EJBObject)
     */
    @Override
    public boolean isIdentical(EJBObject arg0) throws RemoteException {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.ejb.EJBObject#remove()
     */
    @Override
    public void remove() throws RemoteException, RemoveException {
    }

    /* (non-Javadoc)
     * @see com.topcoder.web.ejb.user.UserTermsOfUse#hasTermsOfUse(long, long, java.lang.String)
     */
    @Override
    public boolean hasTermsOfUse(long userId, long termsId,
        String commonOltpDatasourceName) throws EJBException,
        RemoteException {
        return false;
    }

// ----------------------------------------------- EJBHome

    /* (non-Javadoc)
     * @see javax.ejb.EJBHome#getEJBMetaData()
     */
    @Override
    public EJBMetaData getEJBMetaData() throws RemoteException {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.ejb.EJBHome#getHomeHandle()
     */
    @Override
    public HomeHandle getHomeHandle() throws RemoteException {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.ejb.EJBHome#remove(javax.ejb.Handle)
     */
    @Override
    public void remove(Handle arg0) throws RemoteException, RemoveException {
    }

    /* (non-Javadoc)
     * @see javax.ejb.EJBHome#remove(java.lang.Object)
     */
    @Override
    public void remove(Object arg0) throws RemoteException, RemoveException {
    }

}
