/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.client;

import junit.framework.TestCase;

import com.topcoder.confluence.client.impl.ConfluenceAddUserServiceImpl;

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

        String endPoint = "http://localhost:8080/confluence/rpc/soap-axis/confluenceservice-v1";
        ConfluenceAddUserService service = new ConfluenceAddUserServiceImpl();

        service.createUser(endPoint, "admin", "tegangi", "tll1380", "jobi", "The Old Big", "oldbig@gmail.com",
            new String[] {"confluence-users", "confluence-administrators"});

        System.out.println("foi");
    }
}
