/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.GroupPrincipal;
import com.topcoder.security.NoSuchGroupException;
import com.topcoder.security.NoSuchRoleException;
import com.topcoder.security.NoSuchUserException;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockPrincipalMgrRemote implements PrincipalMgrRemote {

    /**
     * Mock method.
     */
    public EJBHome getEJBHome() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Mock method.
     */
    public Handle getHandle() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Mock method.
     */
    public Object getPrimaryKey() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Mock method.
     */
    public boolean isIdentical(EJBObject arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Mock method.
     */
    public void remove() throws RemoteException, RemoveException {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection getUsers(TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal getUser(String s) throws RemoteException, GeneralSecurityException, NoSuchUserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal getUser(String s, String dataSource) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal getUser(long l) throws RemoteException, GeneralSecurityException, NoSuchUserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal getUser(long l, String dataSource) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TCSubject getUserSubject(long l) throws RemoteException, GeneralSecurityException, NoSuchUserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TCSubject getUserSubject(long l, String dataSource) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword(long l) throws RemoteException, GeneralSecurityException, NoSuchUserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword(long l, String dataSource) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal createUser(String username, String password, TCSubject requestor) throws RemoteException,
            GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal createUser(String username, String password, TCSubject requestor, String dataSource)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal createUser(long userId, String username, String password, TCSubject requestor)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal createUser(long userId, String username, String password, TCSubject requestor,
            String dataSource) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeUser(UserPrincipal userprincipal, TCSubject tcsubject) throws RemoteException,
            GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeUser(UserPrincipal userprincipal, TCSubject tcsubject, String dataSource) throws RemoteException,
            GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public UserPrincipal editPassword(UserPrincipal userprincipal, String s, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserPrincipal editPassword(UserPrincipal userprincipal, String s, TCSubject tcsubject, String dataSource)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection getGroups(TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection getGroups(TCSubject tcsubject, String dataSource) throws RemoteException,
            GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GroupPrincipal getGroup(long l) throws RemoteException, GeneralSecurityException, NoSuchGroupException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GroupPrincipal getGroup(long l, String dataSource) throws RemoteException, GeneralSecurityException,
            NoSuchGroupException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GroupPrincipal createGroup(String s, TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeGroup(GroupPrincipal groupprincipal, TCSubject tcsubject) throws RemoteException,
            GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void addUserToGroup(GroupPrincipal groupprincipal, UserPrincipal userprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void addUserToGroup(GroupPrincipal groupprincipal, UserPrincipal userprincipal, TCSubject tcsubject,
            String dataSource) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeUserFromGroup(GroupPrincipal groupprincipal, UserPrincipal userprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeUserFromGroup(GroupPrincipal groupprincipal, UserPrincipal userprincipal, TCSubject tcsubject,
            String dataSource) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection getRoles(TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RolePrincipal getRole(long l) throws RemoteException, GeneralSecurityException, NoSuchRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RolePrincipal createRole(String s, TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeRole(RolePrincipal roleprincipal, TCSubject tcsubject) throws RemoteException,
            GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void assignRole(UserPrincipal userprincipal, RolePrincipal roleprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void assignRole(UserPrincipal userprincipal, RolePrincipal roleprincipal, TCSubject tcsubject,
            String dataSource) throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void unAssignRole(UserPrincipal userprincipal, RolePrincipal roleprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void assignRole(GroupPrincipal groupprincipal, RolePrincipal roleprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

    @Override
    public void unAssignRole(GroupPrincipal groupprincipal, RolePrincipal roleprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
        // TODO Auto-generated method stub

    }

}
