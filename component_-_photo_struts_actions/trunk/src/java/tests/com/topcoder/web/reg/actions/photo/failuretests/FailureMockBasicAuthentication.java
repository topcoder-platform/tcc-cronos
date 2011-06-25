package com.topcoder.web.reg.actions.photo.failuretests;

import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.BasicAuthentication;


public class FailureMockBasicAuthentication extends BasicAuthentication {
    public User getActiveUser() {
        return SimpleUser.createGuest();
    }
}
