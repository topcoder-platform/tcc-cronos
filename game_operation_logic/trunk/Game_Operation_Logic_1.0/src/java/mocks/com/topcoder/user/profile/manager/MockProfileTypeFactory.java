package com.topcoder.user.profile.manager;

import com.topcoder.user.profile.CustomProfileType;
import com.topcoder.user.profile.ProfileType;

public class MockProfileTypeFactory extends ProfileTypeFactory {

	public MockProfileTypeFactory(String namespace) throws ProfileManagerConfigurationException {
		super(namespace);
	}

	public ProfileType getProfileType(String name)
			throws UnknownProfileTypeException {
		return new CustomProfileType(name);
	}

}
