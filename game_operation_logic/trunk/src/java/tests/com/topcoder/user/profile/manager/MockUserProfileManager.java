package com.topcoder.user.profile.manager;

import java.util.Map;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.persistence.UserProfilePersistence;
import com.topcoder.util.config.ConfigManagerException;

public class MockUserProfileManager implements UserProfileManager {

	private UserProfile userProfile = null;
	
	public UserProfile[] searchUserProfiles(Map searchAttributes)
			throws UserProfileManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	public UserProfile[] getAllUserProfiles()
			throws UserProfileManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUserProfilePersistence(UserProfilePersistence plugin) {
		// TODO Auto-generated method stub

	}

	public UserProfilePersistence getUserProfilePersistence() {
		// TODO Auto-generated method stub
		return null;
	}

	public UserProfile createUserProfile(UserProfile profile)
			throws UserProfileManagerException, DuplicateProfileException {
		return null;
	}

	public UserProfile updateUserProfile(UserProfile profile)
			throws UserProfileManagerException, UnknownProfileException {
		userProfile = profile;
		return null;
	}

	public void deleteUserProfile(long profileId)
			throws UserProfileManagerException, UnknownProfileException {
		// TODO Auto-generated method stub

	}

	/**
	 * <p>Modified by Zulander to fix BALL-5082.</p>
	 * <p>Get certain user profile.</p>
	 * 
	 * @param profileId ID of user profiled will be return
	 * @exception UserProfileManagerException
	 *       if there is any problem in the persistence layer
	 * @exception UnknownProfileException
	 *       if there's no persistent representation of the profile in the persistent store
	 * @return user profile with ID of value <strong>profileId</strong>
	 */
	public UserProfile getUserProfile(long profileId)
			throws UserProfileManagerException, UnknownProfileException {
		try {
			if (userProfile == null) {
				return userProfile = new UserProfile(new Long(profileId));
			} else {
				return userProfile;
			}
		} catch (ConfigManagerException e) {
			throw new UserProfileManagerException("Error reading configuration", e);
		}
	}
	
	/**
	 * <p>Added by Zulander for test after fixing BALL-5082.</p>
	 * <p>Clear current saved user profile.</p>
	 */
	public void clearProfile()
	{
		userProfile = null;
	}

	public void commit() throws UserProfileManagerException {
		// TODO Auto-generated method stub

	}

}
