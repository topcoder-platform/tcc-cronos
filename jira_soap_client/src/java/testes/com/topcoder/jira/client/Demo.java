/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.client;

import junit.framework.TestCase;

import com.topcoder.jira.client.impl.JiraAddUserServiceImpl;

/**
 * Demonstration class.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * Calls the service on the localhost and creates a new user.
     * 
     * @throws Exception to JUnit.
     */
    public void testMain() throws Exception {

        String endPoint = "http://localhost:8080/jira/rpc/soap/jirasoapservice-v2";
        JiraAddUserService service = new JiraAddUserServiceImpl();

        service.createUser(endPoint, "romanoTC", "password", "tll1380", "jobi", "The Old Big", "oldbig@gmail.com",
            new String[] {"jira-users", "jira-administrators"});

        System.out.println("finished...");
    }
}
