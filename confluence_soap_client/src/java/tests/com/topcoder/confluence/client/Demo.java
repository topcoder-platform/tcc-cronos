/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.client;

import junit.framework.TestCase;

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

        String endPoint = "http://localhost:8080/confluence/rpc/soap-axis/confluenceservice-v1";
        ConfluenceUserService service = new ConfluenceUserServiceImpl();

        service.createUser(endPoint, "admin", "tegangi", "tll1380", "The Old Big", "oldbig@gmail.com");

        System.out.println("foi");
    }
}
