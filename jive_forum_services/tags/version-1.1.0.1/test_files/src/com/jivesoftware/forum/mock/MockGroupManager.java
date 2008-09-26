package com.jivesoftware.forum.mock;

import com.jivesoftware.base.Group;
import com.jivesoftware.base.GroupAlreadyExistsException;
import com.jivesoftware.base.GroupManager;
import com.jivesoftware.base.GroupNotFoundException;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MockGroupManager implements GroupManager {
    private static boolean exception1;
    private static boolean exception2;
    private MockGroup moderatorGroup;
    private MockGroup userGroup;
    private MockGroup adminGroup;

    public MockGroupManager() {
        moderatorGroup = new MockGroup();
        moderatorGroup.setID(1);
        moderatorGroup.setName("Software_Moderators_1");

        userGroup = new MockGroup();
        userGroup.setID(2);
        userGroup.setName("Software_Users_1");

        adminGroup = new MockGroup();
        adminGroup.setID(3);
        adminGroup.setName("Software Admins");
    }

    public Group createGroup(String name) throws GroupAlreadyExistsException {
        if (exception1) {
            throw new GroupAlreadyExistsException("Simulated Exception");
        }

        if (adminGroup.getName().equals(name)) {
            return adminGroup;
        }

        if (moderatorGroup.getName().equals(name)) {
            return moderatorGroup;
        }

        if (userGroup.getName().equals(name)) {
            return userGroup;
        }

        return null;
    }

    public Group getGroup(long arg0) throws GroupNotFoundException {
        return null;
    }

    public Group getGroup(String name) throws GroupNotFoundException {
        if (exception2) {
            throw new GroupNotFoundException("Simulated Exception");
        }

        if (adminGroup.getName().equals(name)) {
            return adminGroup;
        }

        if (moderatorGroup.getName().equals(name)) {
            return moderatorGroup;
        }

        if (userGroup.getName().equals(name)) {
            return userGroup;
        }

        throw new GroupNotFoundException("Simulated Exception.");
    }

    public void deleteGroup(Group arg0) throws UnauthorizedException {
    }

    public int getGroupCount() {
        return 0;
    }

    public Iterator getGroups() {
        return null;
    }

    public Iterator getGroups(int arg0, int arg1) {
        return null;
    }

    public Iterator getUserGroups(User user) {
        List<Group> list = new ArrayList<Group>();
        list.add(new MockGroup());
        list.add(new MockGroup());

        MockUser m = (MockUser) user;

        if (m.getGroup() != null) {
            list.add(m.getGroup());
        }

        return list.iterator();
    }

    public static void setException1(boolean val) {
        exception1 = val;
    }

    public static void setException2(boolean val) {
        exception2 = val;
    }
}
