/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.rmi.RemoteException;
import java.util.Arrays;
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
 * <p>
 * A simple accuracy mock of PrincipalMgrRemote.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class PrincipalMgrAccuracyMock implements PrincipalMgrRemote {

    
    public void addUserToGroup(GroupPrincipal groupprincipal, UserPrincipal userprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
    }

    
    public void addUserToGroup(GroupPrincipal groupprincipal, UserPrincipal userprincipal,
            TCSubject tcsubject, String dataSource) throws RemoteException, GeneralSecurityException {
    }

    
    public void assignRole(UserPrincipal userprincipal, RolePrincipal roleprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
    }

    
    public void assignRole(UserPrincipal userprincipal, RolePrincipal roleprincipal, TCSubject tcsubject,
            String dataSource) throws RemoteException, GeneralSecurityException {
    }

    
    public void assignRole(GroupPrincipal groupprincipal, RolePrincipal roleprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
    }

    
    public GroupPrincipal createGroup(String s, TCSubject tcsubject) throws RemoteException,
            GeneralSecurityException {
        return null;
    }

    
    public RolePrincipal createRole(String s, TCSubject tcsubject) throws RemoteException,
            GeneralSecurityException {
        return null;
    }

    
    public UserPrincipal createUser(String username, String password, TCSubject requestor)
            throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public UserPrincipal createUser(String username, String password, TCSubject requestor, String dataSource)
            throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public UserPrincipal createUser(long userId, String username, String password, TCSubject requestor)
            throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public UserPrincipal createUser(long userId, String username, String password, TCSubject requestor,
            String dataSource) throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public UserPrincipal editPassword(UserPrincipal userprincipal, String s, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public UserPrincipal editPassword(UserPrincipal userprincipal, String s, TCSubject tcsubject,
            String dataSource) throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public GroupPrincipal getGroup(long l) throws RemoteException, GeneralSecurityException,
            NoSuchGroupException {
        return null;
    }

    
    public GroupPrincipal getGroup(long l, String dataSource) throws RemoteException,
            GeneralSecurityException, NoSuchGroupException {
        return null;
    }

    
    public Collection getGroups(TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public Collection getGroups(TCSubject tcsubject, String dataSource) throws RemoteException,
            GeneralSecurityException {
        return Arrays.asList(new GroupPrincipal("Users", 200L), new GroupPrincipal("Anonymous", 100L));
    }

    
    public String getPassword(long l) throws RemoteException, GeneralSecurityException, NoSuchUserException {
        return null;
    }

    
    public String getPassword(long l, String dataSource) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        return null;
    }

    
    public RolePrincipal getRole(long l) throws RemoteException, GeneralSecurityException,
            NoSuchRoleException {
        return null;
    }

    
    public Collection getRoles(TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public UserPrincipal getUser(String s) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        return null;
    }

    
    public UserPrincipal getUser(String s, String dataSource) throws RemoteException,
            GeneralSecurityException, NoSuchUserException {
        return null;
    }

    
    public UserPrincipal getUser(long l) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        return null;
    }

    
    public UserPrincipal getUser(long l, String dataSource) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        return null;
    }

    
    public TCSubject getUserSubject(long l) throws RemoteException, GeneralSecurityException,
            NoSuchUserException {
        return null;
    }

    
    public TCSubject getUserSubject(long l, String dataSource) throws RemoteException,
            GeneralSecurityException, NoSuchUserException {
        return null;
    }

    
    public Collection getUsers(TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
        return null;
    }

    
    public void removeGroup(GroupPrincipal groupprincipal, TCSubject tcsubject) throws RemoteException,
            GeneralSecurityException {
    }

    
    public void removeRole(RolePrincipal roleprincipal, TCSubject tcsubject) throws RemoteException,
            GeneralSecurityException {
    }

    
    public void removeUser(UserPrincipal userprincipal, TCSubject tcsubject) throws RemoteException,
            GeneralSecurityException {
    }

    
    public void removeUser(UserPrincipal userprincipal, TCSubject tcsubject, String dataSource)
            throws RemoteException, GeneralSecurityException {
    }

    
    public void removeUserFromGroup(GroupPrincipal groupprincipal, UserPrincipal userprincipal,
            TCSubject tcsubject) throws RemoteException, GeneralSecurityException {
    }

    
    public void removeUserFromGroup(GroupPrincipal groupprincipal, UserPrincipal userprincipal,
            TCSubject tcsubject, String dataSource) throws RemoteException, GeneralSecurityException {
    }

    
    public void unAssignRole(UserPrincipal userprincipal, RolePrincipal roleprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
    }

    
    public void unAssignRole(GroupPrincipal groupprincipal, RolePrincipal roleprincipal, TCSubject tcsubject)
            throws RemoteException, GeneralSecurityException {
    }

    
    public EJBHome getEJBHome() throws RemoteException {
        return null;
    }

    
    public Handle getHandle() throws RemoteException {
        return null;
    }

    
    public Object getPrimaryKey() throws RemoteException {
        return null;
    }

    
    public boolean isIdentical(EJBObject arg0) throws RemoteException {
        return false;
    }

    
    public void remove() throws RemoteException, RemoveException {
    }

}
