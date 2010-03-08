/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.stresstests.interceptors;

import junit.framework.TestCase;
import com.topcoder.service.interceptors.AuthenticationInterceptor;
import java.util.Date;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * Stress tests for class <code> AuthenticationInterceptor</code>.
 *
 * @author iKnown
 * @version 1.0
 */
public class AuthenticationInterceptorStressTest extends TestCase {
    /**
     * Represents the count of run times.
     */
    private static final int RUN_TIMES = 100;

    /**
     * Represents the instance used in tests.
     */
    private  AuthenticationInterceptor instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        instance = new AuthenticationInterceptor();
    }

    /**
     * Stress test of the method <code>getLoginPageName</code>.
     */
    public void testGetLoginPageName() {
        instance.setLoginPageName("topcoder");

        // Remember the start time
        long start = new Date().getTime();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Failed to get the login page name.", "topcoder", instance.getLoginPageName());
        }
        endCall("getLoginPageName", start);
    }

    /**
     * Stress test of the method <code>setLoginPageName</code>.
     */
    public void testSetLoginPageName() {
        // Remember the start time
        long start = new Date().getTime();
        for (int i = 0; i < RUN_TIMES; i++) {
            instance.setLoginPageName("topcoder");
            assertEquals("Failed to set the login page name.", "topcoder", instance.getLoginPageName());
        }
        endCall("setLoginPageName", start);
    }

    /**
     * Stress test of the method <code>setUserSessionIdentityKey</code>.
     */
    public void testsetLoginPageName() {
        // Remember the start time
        long start = new Date().getTime();
        for (int i = 0; i < RUN_TIMES; i++) {
            instance.setUserSessionIdentityKey("key");
            assertEquals("Failed to set the user session identity key.",
                "key", instance.getUserSessionIdentityKey());
        }
        endCall("setUserSessionIdentityKey", start);
    }

    /**
     * Stress test of the method <code>getUserSessionIdentityKey</code>.
     */
    public void testgetLoginPageName() {
        instance.setUserSessionIdentityKey("key");
        // Remember the start time
        long start = new Date().getTime();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Failed to get the user session identity key.",
                "key", instance.getUserSessionIdentityKey());
        }
        endCall("getUserSessionIdentityKey", start);
    }

    /**
     * Stress test of the method <code>intercept</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testIntercept_1() throws Exception {
        ActionInvocation action = new StressActionInvocation();
        instance.setLoginPageName("tc");
        instance.setUserSessionIdentityKey("key");
        // Remember the start time
        long start = new Date().getTime();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Failed to call the intercept method.",
                "tc", instance.intercept(action));
        }
        endCall("intercept", start);
    }

    /**
     * Stress test of the method <code>intercept</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testIntercept_2() throws Exception {
        ActionInvocation action = new StressActionInvocation();
        instance.setLoginPageName("tc");
        instance.setUserSessionIdentityKey("tc");
        // Remember the start time
        long start = new Date().getTime();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Failed to call the intercept method.",
                "stress_result", instance.intercept(action));
        }
        endCall("intercept", start);
    }

    /**
     * Remember the time calling the method.
     *
     * @param methodName name of the method.
     * @param start start time before calling the method.
     */
    private static void endCall(String methodName, long start) {
        System.out.println("Calling the method '" + methodName
            + "' for '" + new Integer(RUN_TIMES).toString() + "' times cost '"
            + new Long(new Date().getTime() - start).toString() + "' milliseconds.");
    }
}