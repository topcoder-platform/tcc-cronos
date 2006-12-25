import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.user.profile.ProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

public class UserProfileTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		UserProfile profile = new UserProfile();

			ProfileType type = profileTypFactory
					.getProfileType(profileTypeNames[i]);

		Iterator iter = this.propertyNameMap.entrySet().iterator();
		Entry entry = null;
		String propertyName;
		String[] propertyValues;
		HttpServletRequest request = context.getRequest();
		while (iter.hasNext()) {
			entry = (Entry) iter.next();
			propertyName = (String) entry.getKey();
			propertyValues = request
					.getParameterValues((String) entry.getKey());
			if (propertyValues.length == 1) {
				profile.setProperty(propertyName, propertyValues[0]);
			} else {
				profile.setProperty(propertyName, propertyValues);
			}
		}

		profileManager.createUserProfile(profile);

	}

}
