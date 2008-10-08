/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import java.io.IOException;
import java.security.Principal;

import junit.framework.TestCase;

import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.SessionContextMock;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.facade.BaseUserProfile;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test for Util class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UtilTest extends TestCase {

    /**
     * <p>
     * Accuracy test of <code>checkNull(Object data, String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckNull_Accuracy() throws Exception {
        Util.checkNull("testObject", "testName");
        // OK
    }

    /**
     * <p>
     * Failure test of <code>checkNull(Object data, String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if passed data parameter is null
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckNull_Failure() throws Exception {
        try {
            Util.checkNull(null, "testName");
            fail("Expected IllegalArgumentException if passed data parameter is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>checkNullAndReturn(T data, String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckNullAndReturn_Accuracy() throws Exception {
        Integer n = 10;
        assertEquals("checkNullAndReturn should return passed value.", n, Util.checkNullAndReturn(n,
                "testName"));
    }

    /**
     * <p>
     * Failure test of <code>checkNullAndReturn(T data, String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if passed data parameter is null
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckNullAndReturn_Failure() throws Exception {
        try {
            Util.checkNullAndReturn(null, "testName");
            fail("Expected IllegalArgumentException if passed data parameter is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>checkNullWithLog(Log log, Object data, String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckNullWithLog_Accuracy() throws Exception {
        Util.checkNullWithLog(LogManager.getLog(), "testObject", "testName");
        // OK
    }

    /**
     * <p>
     * Failure test of <code>checkNullWithLog(Log log, Object data, String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if passed data parameter is null
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckNullWithLog_Failure() throws Exception {
        try {
            Util.checkNullWithLog(LogManager.getLog(), null, "testName");
            fail("Expected IllegalArgumentException if passed data parameter is null.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>checkNullOrEmpty(Log log, String data, String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckNullOrEmpty_Accuracy() throws Exception {
        Util.checkNullOrEmpty(LogManager.getLog(), "testObject", "testName");
        // OK
    }

    /**
     * <p>
     * Failure test of <code>checkNullOrEmpty(Log log, String data, String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if passed data parameter is empty string
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckNullOrEmpty_Failure() throws Exception {
        try {
            Util.checkNullOrEmpty(LogManager.getLog(), "   ", "testName");
            fail("Expected IllegalArgumentException if passed data parameter is empty string.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>checkPositiveWithLog(Log log, long data, String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckPositiveWithLog_Accuracy() throws Exception {
        Util.checkPositiveWithLog(LogManager.getLog(), 5, "name");
        // OK
    }

    /**
     * <p>
     * Failure test of <code>checkPositiveWithLog(Log log, long data, String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if passed number < 0
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckPositiveWithLog_Failure1() throws Exception {
        try {
            Util.checkPositiveWithLog(LogManager.getLog(), -17, "name");
            fail("Expected IllegalArgumentException if passed number < 0.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of <code>checkPositiveWithLog(Log log, long data, String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalArgumentException
     * </p>
     * <p>
     * Because of: if passed number = 0
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckPositiveWithLog_Failure2() throws Exception {
        try {
            Util.checkPositiveWithLog(LogManager.getLog(), 0, "name");
            fail("Expected IllegalArgumentException if passed number = 0.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>isValidString(String data)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testIsValidString_Accuracy() throws Exception {
        assertTrue("'XYZ' is valid string.", Util.isValidString("XYZ"));
        assertFalse("Empty string is invalid string.", Util.isValidString("  "));
        assertFalse("null is invalid string.", Util.isValidString(null));
    }

    /**
     * <p>
     * Accuracy test of
     * <code>createManagerFromConfiguration(String fileResource, String namespaceResource, String tokenName)</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateManagerFromConfiguration_Accuracy() throws Exception {
        Object manager = Util.createManagerFromConfiguration("config.properties", "ClientStatusServiceBean",
                "client_manager_token");
        assertTrue("Created object should be ClientManager.", manager instanceof ClientManager);
    }

    /**
     * <p>
     * Failure test of
     * <code>createManagerFromConfiguration(String fileResource, String namespaceResource, String tokenName)</code>
     * method.
     * </p>
     * <p>
     * Expect: IOException
     * </p>
     * <p>
     * Because of: if couldn't locate configuration file.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateManagerFromConfiguration_Failure1() throws Exception {
        try {
            Util.createManagerFromConfiguration("broken.properties", "ClientStatusServiceBean",
                    "client_manager_token");
            fail("Expected IOException if couldn't locate configuration file.");
        } catch (IOException e) {
            // expect
        }
    }

    /**
     * <p>
     * Failure test of
     * <code>createManagerFromConfiguration(String fileResource, String namespaceResource, String tokenName)</code>
     * method.
     * </p>
     * <p>
     * Expect: BaseException
     * </p>
     * <p>
     * Because of: if namespace couldn't be found.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCreateManagerFromConfiguration_Failure2() throws Exception {
        try {
            Util.createManagerFromConfiguration("config.properties", "AbsentConfig", "client_manager_token");
            fail("Expected BaseException if namespace couldn't be found.");
        } catch (BaseException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of <code>checkResource(Log log, Object resource, String name)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckResource_Accuracy() throws Exception {
        Util.checkResource(LogManager.getLog(), "testResource", "testName");
        // OK
    }

    /**
     * <p>
     * Failure test of <code>checkResource(Log log, Object resource, String name)</code> method.
     * </p>
     * <p>
     * Expect: IllegalStateException
     * </p>
     * <p>
     * Because of: if passed object is null
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testCheckResource_Failure() throws Exception {
        try {
            Util.checkResource(LogManager.getLog(), null, "testName");
            fail("Expected IllegalStateException.");
        } catch (IllegalStateException e) {
            // expect
        }
    }

    /**
     * <p>
     * Accuracy test of
     * <code>onEntityUpdate(AuditableEntity entity, SessionContext sessionContext, boolean create)</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testOnEntityUpdate_Accuracy() throws Exception {

        // Prepare SessionContext
        SessionContextMock context = new SessionContextMock();
        Principal principal = new UserProfilePrincipal(new BaseUserProfile(), 1, "testUser");
        SessionContextMock.setPrincipal(principal);

        Client client = new Client();

        Util.onEntityUpdate(client, context, false);
        assertNotNull("modifyDate should be set to current date.", client.getModifyDate());
        assertEquals("modifyUsername should be set to " + principal.getName(), principal.getName(), client
                .getModifyUsername());

        Util.onEntityUpdate(client, context, true);
        assertNotNull("createDate should be set to current date.", client.getCreateDate());
        assertEquals("createUsername should be set to " + principal.getName(), principal.getName(), client
                .getCreateUsername());
    }

    /**
     * <p>
     * Accuracy test of
     * <code>logEnter(Log logger, String className, String methodName, String argumentInfo, boolean verbose)</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testLogEnter_Accuracy() throws Exception {
        Util.logEnter(LogManager.getLog(), "class", "method", "argument", null, false);
        // OK
    }

    /**
     * <p>
     * Accuracy test of <code>logExit(Log logger, String className, String methodName, long startTime,
     * String returnValue, boolean verbose)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testLogExit_Accuracy() throws Exception {
        Util.logExit(LogManager.getLog(), "class", "method", 1000, null, false);
    }

    /**
     * <p>
     * Accuracy test of <code>logException(Log logger, T exception)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testLogException_Accuracy() throws Exception {
        Util.logException(LogManager.getLog(), new IllegalArgumentException("test"));
        // OK
    }

    /**
     * <p>
     * Accuracy test of <code>describeError(Exception e, String methodName)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testDescribeError_Accuracy() throws Exception {
        assertEquals("describeError is incorrect.",
                "Exception occured while performing [testMethod] operation: test message", Util
                        .describeError(new Exception("test message"), "testMethod"));
    }

    /**
     * <p>
     * Accuracy test of <code>formatClientStatus(ClientStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testFormatClientStatus_Accuracy() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setDescription("test description");
        assertEquals("formatClientStatus is incorrect.",
                "[ id=0 name=null description=test description isDeleted=false]", Util.formatClientStatus(
                        status, true));
    }

    /**
     * <p>
     * Accuracy test of <code>formatProjectStatus(ProjectStatus status)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit if any error occurs.
     */
    public void testFormatProjectStatus_Accuracy() throws Exception {
        ProjectStatus status = new ProjectStatus();
        status.setDescription("test description");
        assertEquals("formatProjectStatus is incorrect.",
                "[ id=0 name=null description=test description isDeleted=false]", Util.formatProjectStatus(
                        status, true));
    }
}
