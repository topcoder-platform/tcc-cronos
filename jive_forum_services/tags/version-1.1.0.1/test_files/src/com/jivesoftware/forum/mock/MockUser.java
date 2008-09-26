package com.jivesoftware.forum.mock;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.Group;
import com.jivesoftware.base.Permissions;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;

import java.util.Date;
import java.util.Iterator;


public class MockUser implements User {
    private long userID;
    private Group group;

    public long getID() {
        return userID;
    }

    public void setID(long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return null;
    }

    public String getName() {
        return null;
    }

    public void setName(String arg0) throws UnauthorizedException {
    }

    public boolean isNameVisible() {
        return false;
    }

    public void setNameVisible(boolean arg0) throws UnauthorizedException {
    }

    public void setPassword(String arg0) throws UnauthorizedException {
    }

    public String getPasswordHash() throws UnauthorizedException {
        return null;
    }

    public void setPasswordHash(String arg0) throws UnauthorizedException {
    }

    public String getEmail() {
        return null;
    }

    public void setEmail(String arg0) throws UnauthorizedException {
    }

    public boolean isEmailVisible() {
        return false;
    }

    public void setEmailVisible(boolean arg0) throws UnauthorizedException {
    }

    public Date getCreationDate() {
        return null;
    }

    public void setCreationDate(Date arg0) throws UnauthorizedException {
    }

    public Date getModificationDate() {
        return null;
    }

    public void setModificationDate(Date arg0) throws UnauthorizedException {
    }

    public String getProperty(String arg0) {
        return null;
    }

    public void setProperty(String arg0, String arg1)
        throws UnauthorizedException {
    }

    public void deleteProperty(String arg0) throws UnauthorizedException {
    }

    public Iterator getPropertyNames() {
        return null;
    }

    public Permissions getPermissions(AuthToken arg0) {
        return null;
    }

    public boolean isAuthorized(long arg0) {
        return false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
