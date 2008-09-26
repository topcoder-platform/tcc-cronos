package com.jivesoftware.forum.mock;

import com.jivesoftware.base.Group;
import com.jivesoftware.base.PermissionType;
import com.jivesoftware.base.PermissionsManager;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;

import java.util.Iterator;


public class MockPermissionsManager implements PermissionsManager {
    public void addUserPermission(User arg0, PermissionType arg1, long arg2)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void addAnonymousUserPermission(PermissionType arg0, long arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void addRegisteredUserPermission(PermissionType arg0, long arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void removeUserPermission(User arg0, PermissionType arg1, long arg2)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void removeAnonymousUserPermission(PermissionType arg0, long arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void removeRegisteredUserPermission(PermissionType arg0, long arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void removeAllUserPermissions(PermissionType arg0)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public boolean anonymousUserHasPermission(PermissionType arg0, long arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean registeredUserHasPermission(PermissionType arg0, long arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    public Iterator usersWithPermission(PermissionType arg0, long arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public int usersWithPermissionCount(PermissionType arg0, long arg1) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void addGroupPermission(Group arg0, PermissionType arg1, long arg2)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void removeGroupPermission(Group arg0, PermissionType arg1, long arg2)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void removeAllGroupPermissions(PermissionType arg0)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public Iterator groupsWithPermission(PermissionType arg0, long arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public int groupsWithPermissionCount(PermissionType arg0, long arg1) {
        // TODO Auto-generated method stub
        return 0;
    }
}
