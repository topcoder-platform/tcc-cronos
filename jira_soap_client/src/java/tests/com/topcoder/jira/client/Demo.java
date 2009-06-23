/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.client;

import junit.framework.TestCase;

import com.atlassian.jira.rpc.soap.beans.RemoteUser;
import com.topcoder.jira.client.impl.JiraUserServiceImpl;

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

        // String endPoint = "http://ec2-75-101-172-17.compute-1.amazonaws.com:8080/jira/rpc/soap/jirasoapservice-v2";
        String endPoint = "http://localhost:8080/jira/rpc/soap/jirasoapservice-v2";
        JiraUserService service = new JiraUserServiceImpl();

        String adminUserName = "romanoTC";
        String adminPassword = "password";

        String handle = "Psyho";

        RemoteUser user = service.getUser(endPoint, adminUserName, adminPassword, handle);
        if (user != null) {
            System.out.println("Jira user [" + user.getName() + " / " + user.getFullname() + "]");
        } else {
            System.out.println(handle + ": is null");
        }
    }
}
