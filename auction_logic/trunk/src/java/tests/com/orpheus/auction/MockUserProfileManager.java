/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.DuplicateProfileException;
import com.topcoder.user.profile.manager.UnknownProfileException;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.user.profile.manager.UserProfileManagerException;
import com.topcoder.user.profile.manager.persistence.UserProfilePersistence;

/**
 * <p>
 * A mock implementation of the UserProfileManager interface. Uses map to store user profiles.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockUserProfileManager implements UserProfileManager {

    /**
     * <p>
     * Maps ids to the user profiles.
     * </p>
     */
    private Map userProfiles = new HashMap();

    /**
     * <p>
     * Method stub. Searches for the profiles that have the given values for the specified
     * properties.
     * </p>
     */
    public UserProfile[] searchUserProfiles(Map arg0) throws UserProfileManagerException {
        List res = new LinkedList();

        for (Iterator i = userProfiles.values().iterator(); i.hasNext();) {
            UserProfile profile = (UserProfile) i.next();

            boolean ok = true;
            for (Iterator j = arg0.keySet().iterator(); j.hasNext();) {
                String key = (String) j.next();
                if (!arg0.get(key).equals(profile.getProperty(key))) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                res.add(profile);
            }
        }

        return (UserProfile[]) res.toArray(new UserProfile[res.size()]);
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public UserProfile[] getAllUserProfiles() throws UserProfileManagerException {
        return null;
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     */
    public void setUserProfilePersistence(UserProfilePersistence arg0) {
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public UserProfilePersistence getUserProfilePersistence() {
        return null;
    }

    /**
     * <p>
     * Method stub. Adds given user profile to the mock manager.
     * </p>
     */
    public UserProfile createUserProfile(UserProfile arg0) throws UserProfileManagerException,
        DuplicateProfileException {
        userProfiles.put(arg0.getIdentifier(), arg0);
        return arg0;
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public UserProfile updateUserProfile(UserProfile arg0) throws UserProfileManagerException,
        UnknownProfileException {
        return null;
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     */
    public void deleteUserProfile(long arg0) throws UserProfileManagerException,
        UnknownProfileException {
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     */
    public UserProfile getUserProfile(long arg0) throws UserProfileManagerException,
        UnknownProfileException {
        return null;
    }

    /**
     * <p>
     * Method stub. Does nothing.
     * </p>
     */
    public void commit() throws UserProfileManagerException {
    }
}
