package com.topcoder.user.profile.manager;

import java.util.Map;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.persistence.UserProfilePersistence;

public class MockUserProfileManager implements UserProfileManager {

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
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUserProfile(long profileId)
			throws UserProfileManagerException, UnknownProfileException {
		// TODO Auto-generated method stub

	}

	public UserProfile getUserProfile(long profileId)
			throws UserProfileManagerException, UnknownProfileException {
		// TODO Auto-generated method stub
		return null;
	}

	public void commit() throws UserProfileManagerException {
		// TODO Auto-generated method stub

	}

}
