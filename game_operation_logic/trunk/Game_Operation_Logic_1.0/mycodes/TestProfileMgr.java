import com.topcoder.user.profile.CustomProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.ProfileTypeFactory;

/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */

public class TestProfileMgr {

    /**
     *@param args
     */
    public static void main(String[] args) throws Exception{

        UserProfile up = new UserProfile(new Long(1));
        up.addProfileType(new CustomProfileType("a"));
        up.setProperty("first_name","b");
        
        ProfileTypeFactory factory = ProfileTypeFactory.getInstance("com.topcoder.user.profile.manager");
    }

}
