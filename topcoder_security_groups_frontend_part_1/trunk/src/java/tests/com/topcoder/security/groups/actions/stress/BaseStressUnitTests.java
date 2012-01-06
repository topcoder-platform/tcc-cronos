/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;

/**
 * <p>
 * The base class for stress unit tests.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class BaseStressUnitTests {
    /**
     * <p>
     * The application context used in testing.
     * </p>
     */
    protected static final ApplicationContext CONTEXT;

    /**
     * <p>
     * The session.
     * </p>
     */
    protected static final Map<String, Object> SESSION;

    static {
        CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
        SESSION = new HashMap<String, Object>();
        String groupSessionKey = "groupSessionKey";
        Group group = new Group();
        group.setGroupMembers(new ArrayList<GroupMember>());
        SESSION.put(groupSessionKey, group);
    }
}
