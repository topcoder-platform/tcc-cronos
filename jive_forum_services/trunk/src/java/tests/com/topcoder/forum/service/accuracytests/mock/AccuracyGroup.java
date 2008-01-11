package com.topcoder.forum.service.accuracytests.mock;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.Group;
import com.jivesoftware.base.Permissions;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;

import java.util.Date;
import java.util.Iterator;

/**
 * Mock class.
 *
 * @author kaqi072821
 * @version 1.0
 */
public class AccuracyGroup implements Group {
    private static boolean exception = false;

    private long id;

    private String name;

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return null;
    }

    public void setDescription(String arg0) throws UnauthorizedException {
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

    public void setProperty(String arg0, String arg1) throws UnauthorizedException {
    }

    public void deleteProperty(String arg0) throws UnauthorizedException {
    }

    public Iterator getPropertyNames() {
        return null;
    }

    public void addAdministrator(User arg0) throws UnauthorizedException {
    }

    public void removeAdministrator(User arg0) throws UnauthorizedException {
    }

    public void addMember(User user) throws UnauthorizedException {
        if (exception) {
            throw new UnauthorizedException("Simulated Exception.");
        }

        ((AccuracyUser) user).setGroup(this);
    }

    public void removeMember(User arg0) throws UnauthorizedException {
    }

    public boolean isAdministrator(User arg0) {
        return false;
    }

    public boolean isMember(User arg0) {
        return false;
    }

    public int getAdministratorCount() {
        return 0;
    }

    public int getMemberCount() {
        return 0;
    }

    public Iterator getMembers() {
        return null;
    }

    public Iterator getAdministrators() {
        return null;
    }

    public Permissions getPermissions(AuthToken arg0) {
        return null;
    }

    public boolean isAuthorized(long arg0) {
        return false;
    }

    public static void setException(boolean val) {
        exception = val;
    }
}