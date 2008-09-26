package com.topcoder.forum.service.accuracytests.mock;

import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;
import com.jivesoftware.base.UserAlreadyExistsException;
import com.jivesoftware.base.UserManager;
import com.jivesoftware.base.UserNotFoundException;

import java.util.Iterator;
import java.util.Map;

/**
 * Mock class.
 *
 * @author kaqi072821
 * @version 1.0
 */
public class AccuracyUserManager implements UserManager {
    /**
     * <p>
     * The only one <code>User</code> instance in the manager.
     * </p>
     */
    private AccuracyUser user = new AccuracyUser();

    public AccuracyUserManager() {
        user.setID(100);
    }

    public User createUser(String arg0, String arg1, String arg2) throws UserAlreadyExistsException {
        return null;
    }

    public User createUser(String arg0, String arg1, String arg2, String arg3, boolean arg4, boolean arg5, Map arg6)
        throws UserAlreadyExistsException {
        return null;
    }

    public User getUser(long userID) throws UserNotFoundException {
        if (userID != 100) {
            throw new UserNotFoundException(
                "Simulated Exception: In mock impl, there is only one user, and its id is 100.");
        }

        return user;
    }

    public User getUser(String arg0) throws UserNotFoundException {
        return null;
    }

    public long getUserID(String arg0) throws UserNotFoundException {
        return 0;
    }

    public void deleteUser(User arg0) throws UnauthorizedException {
    }

    public int getUserCount() {
        return 0;
    }

    public Iterator users() {
        return null;
    }

    public Iterator users(int arg0, int arg1) {
        return null;
    }
}