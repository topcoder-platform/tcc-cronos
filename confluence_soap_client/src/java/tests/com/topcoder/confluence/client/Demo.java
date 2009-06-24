/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.client;

import junit.framework.TestCase;

import com.atlassian.confluence.rpc.soap.beans.RemoteUser;
import com.topcoder.confluence.client.impl.ConfluenceUserServiceImpl;

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

        String endPoint = "http://ec2-174-129-155-4.compute-1.amazonaws.com:8080/wiki/rpc/soap-axis/confluenceservice-v1";
        // String endPoint = "http://localhost:8080/confluence/rpc/soap-axis/confluenceservice-v1";

        String[] defaultGroups = new String[] {"confluence-users", "topcoder-staff"};

        String adminUserName = "admin";
        String adminPassword = "password";

        ConfluenceUserService service = new ConfluenceUserServiceImpl();

        String handle = "rem";
        String email = "email@email.com";

        if (!service.hasUser(endPoint, adminUserName, adminPassword, handle)) {
            service.createUser(endPoint, adminUserName, adminPassword, handle, email, defaultGroups);
        }

        RemoteUser user = service.getUser(endPoint, adminUserName, adminPassword, handle);
        if (user != null) {
            System.out.println("Confluence user [" + user.getName() + " / " + user.getFullname() + "]");
        } else {
            System.out.println(handle + ": is null");
        }
    }
}
