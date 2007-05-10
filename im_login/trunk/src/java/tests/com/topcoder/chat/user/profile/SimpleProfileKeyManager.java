/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.user.profile;

import java.util.HashMap;
import java.util.Map;

/**
 * A Mock class used to run test.
 * 
 * @author kkkk9
 * @version 1.0
 */
public class SimpleProfileKeyManager implements ProfileKeyManager
{
    /**
     * profile keys storage.
     */
    private Map profileKeys = new HashMap();

    /**
     * <p>
     * Put the ProfileKey into the map.
     * </p>
     */
    public ProfileKey createProfileKey(ProfileKey arg0) throws DuplicateProfileKeyException,
        ProfileKeyManagerPersistenceException {
        profileKeys.put(new Long(arg0.getId()), arg0);
        return arg0;
    }

    /**
     * <p>
     * Remove the ProfileKey from the map.
     * </p>
     */
    public void deleteProfileKey(long arg0) throws ProfileKeyNotFoundException, ProfileKeyManagerPersistenceException {
        profileKeys.remove(new Long(arg0));
    }

    /**
     * <p>Use the id to find the key.</p>
     */
    public ProfileKey getProfileKey(long arg0) throws ProfileKeyManagerPersistenceException {
        return (ProfileKey)profileKeys.get(new Long(arg0));
    }

    /**
     * <p>get the key using the ids.</p>
     */
    public ProfileKey[] getProfileKeys(long[] arg0) throws ProfileKeyManagerPersistenceException {
        ProfileKey[] ret = new ProfileKey[arg0.length];
        for (int i = 0; i < arg0.length; ++i) {
            ret[i] = (ProfileKey) profileKeys.get(new Long(arg0[i]));
        }
        return ret;
    }

    /**
     * <p>Do nothing.</p>
     */
    public ProfileKey getProfileKey(String arg0, String arg1) throws ProfileKeyManagerPersistenceException {
        return null;
    }

    /**
     * <p>Do nothing.</p>
     */
    public ProfileKey[] getProfileKeys(String[] arg0, String arg1) throws ProfileKeyManagerPersistenceException {
        return null;
    }

    /**
     * <p>Do nothing.</p>
     */
    public ProfileKey[] getProfileKeys(String arg0) throws ProfileKeyNotFoundException,
        ProfileKeyManagerPersistenceException {
        return null;
    }

    /**
     * <p>Do nothing.</p>
     */
    public ProfileKey[][] getProfileKeys(String[] arg0) throws ProfileKeyNotFoundException,
        ProfileKeyManagerPersistenceException {
        return null;
    }
}
