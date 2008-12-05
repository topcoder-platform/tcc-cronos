/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;

/**
 * <p>
 * UnitTest cases of the <code>Helper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTests extends TestCase {

    /**
     * <p>
     * BasicLog instance used for testing.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * ByteArrayOutputStream instance used for testing.
     * </p>
     */
    private ByteArrayOutputStream byteStream;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    protected void setUp() {
        byteStream = new ByteArrayOutputStream();
        LogManager.setLogFactory(new BasicLogFactory(new PrintStream(byteStream)));
        log = LogManager.getLog();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    protected void tearDown() {
        byteStream = null;
        log = null;
    }

    /**
     * <p>
     * Accuracy test case for {@link Helper#checkNull(Object, String)}.The argument is not null so
     * <code>IllegalArgumentException</code> should not be thrown.
     * </p>
     */
    public void testCheckNull_Accuracy() {
        try {
            Helper.checkNull("non-null", "non-null name");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * <p>
     * Failure test case for {@link Helper#checkNull(Object, String)}.The argument is null so
     * <code>IllegalArgumentException</code> should be thrown.
     * </p>
     */
    public void testCheckNull_Failure() {
        try {
            Helper.checkNull(null, "null-name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmpty(String,String) method. It test the case when a not null and not empty
     * string is passed in and expects success.
     * </p>
     */
    public void testCheckStringNullOrEmpty_NotNullNotEmptyString() {
        try {
            Helper.checkStringNullOrEmpty("test", "test");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmpty(String,String) method. It test the case when a null string is passed in
     * and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckStringNullOrEmpty_NullString() {
        try {
            Helper.checkStringNullOrEmpty(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmpty(String,String) method. It test the case when an empty string is passed
     * in and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckStringNullOrEmpty_EmptyString1() {
        try {
            Helper.checkStringNullOrEmpty(" ", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmpty(String,String) method. It test the case when an empty string is passed
     * in and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckStringNullOrEmpty_EmptyString2() {
        try {
            Helper.checkStringNullOrEmpty("", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test case for {@link Helper#checkNullAndLog(Object, String, String, Log)}.
     * </p>
     */
    public void test_checkNullAndLog_Accuracy() {
        try {
            Helper.checkNullAndLog("non-null", "non-null name", "methodName", log);
            assertTrue("Should not contain content in byteStream.", byteStream.toString().length() == 0);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * <p>
     * Failure test case for {@link Helper#checkNullAndLog(Object, String, String, Log)}.The argument is null so
     * <code>IllegalArgumentException</code> should be thrown.
     * </p>
     */
    public void test_checkNullAndLog_Failure() {
        try {
            Helper.checkNullAndLog(null, "null-name", "methodName", log);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertTrue(
                "Should contain'[Error in method {methodName}:Details {null-name should not be null.}]'in byteStream.",
                byteStream.toString().contains(
                    "[Error in method {methodName}:Details {null-name should not be null.}]"));
            // pass
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmptyAndLog method. It test the case when a not null and not empty string is
     * passed in and expects success.
     * </p>
     */
    public void test_checkStringNullOrEmptyAndLog_NotNullNotEmptyString() {
        try {
            Helper.checkStringNullOrEmptyAndLog("test", "test", "methodName", log);
            assertTrue("Should not contain content in byteStream.", byteStream.toString().length() == 0);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmptyAndLog method. It test the case when a null string is passed in and
     * expects IllegalArgumentException.
     * </p>
     */
    public void test_checkStringNullOrEmptyAndLog_NullString() {
        try {
            Helper.checkStringNullOrEmptyAndLog(null, "test", "methodName", log);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            assertTrue(
                "Should contain'[Error in method {methodName}:Details {test should not be null.}]'in byteStream.",
                byteStream.toString()
                    .contains("[Error in method {methodName}:Details {test should not be null.}]"));
            // good
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmptyAndLog method. It test the case when an empty string is passed in and
     * expects IllegalArgumentException.
     * </p>
     */
    public void test_checkStringNullOrEmptyAndLog_EmptyString1() {
        try {
            Helper.checkStringNullOrEmptyAndLog("  ", "test", "methodName", log);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            assertTrue(
                "Should contain'[Error in method {methodName}:Details {test should not be empty.}]'in byteStream.",
                byteStream.toString().contains(
                    "[Error in method {methodName}:Details {test should not be empty.}]"));
            // good
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmptyAndLog method. It test the case when an empty string is passed in and
     * expects IllegalArgumentException.
     * </p>
     */
    public void test_checkStringNullOrEmptyAndLog_EmptyString2() {
        try {
            Helper.checkStringNullOrEmptyAndLog("", "test", "methodName", log);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            assertTrue(
                "Should contain'[Error in method {methodName}:Details {test should not be empty.}]'in byteStream.",
                byteStream.toString().contains(
                    "[Error in method {methodName}:Details {test should not be empty.}]"));
            // good
        }
    }

    /**
     * <p>
     * Verify that the first message and second message should be contained in logMessage and first message should
     * be prior to second message.
     * </p>
     *
     * @param firstMessage
     *            the first message
     * @param secondMessage
     *            the second message
     * @param logMessage
     *            the log message
     */
    private void assertLogMessage(String firstMessage, String secondMessage, String logMessage) {
        assertTrue(firstMessage + " should be contained in" + " logMessage", logMessage.contains(firstMessage));
        assertTrue(secondMessage + " should be contained in" + " logMessage", logMessage.contains(secondMessage));
        assertTrue(firstMessage + " should be prior to " + secondMessage,
            logMessage.indexOf(firstMessage) < logMessage.indexOf(secondMessage));
    }

    /**
     * <p>
     * Accuracy test case for
     * {@link Helper#logConfluencePageCreationResultOutputAndMethodExit(String, ConfluencePageCreationResult, Log)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logConfluencePageCreationResultOutputAndMethodExit_Accuracy() throws Exception {
        Page page = new Page();
        page.setApplicationCode("applicationCode");
        page.setAssetType(ConfluenceAssetType.COMPONENT_DESIGN);
        ConfluencePageCreationResult result =
            new ConfluencePageCreationResult(page, ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED);
        Helper.logConfluencePageCreationResultOutputAndMethodExit("method name", result, log);

        assertLogMessage("[Exiting method {method name}]",
            "[Output parameter {page:[basePageUrl:,versionUrl:,assetName:,version:,applicationCode:"
                + "applicationCode,content:,assetType:COMPONENT_DESIGN,catalog:,componentType:], "
                + "actionTaken:BASE_PAGE_AND_VERSION_CREATED}]", byteStream.toString());
    }

    /**
     * <p>
     * Accuracy test case for Helper#logCreatePageOrRetrievePageWithApplicationCodeMethodEntryAndInput(String,
     * String, String, String, ConfluenceAssetType, ConfluenceCatalog, String, Log).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logCreatePageOrRetrievePageWithApplicationCodeMethodEntryAndInput_Accuracy() throws Exception {
        Helper.logCreatePageOrRetrievePageWithApplicationCodeMethodEntryAndInput("method name", "token",
            "page name", "version", ConfluenceAssetType.APPLICATION_ASSEMBLY, null, "application code", log);
        assertLogMessage("[Entering method {method name}]",
            "[Input parameters[{token}:{token},{pageName}:{page name},{version}:{version},{assetType}:"
                + "{APPLICATION_ASSEMBLY},{catalog}:{},{applicationCode}:{application code}]]", byteStream
                .toString());
    }

    /**
     * <p>
     * Accuracy test case for Helper#logCreatePageOrRetrievePageWithComponentTypeMethodEntryAndInput(String,
     * String, String, String, ConfluenceAssetType, ConfluenceCatalog,ComponentType, Log).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logCreatePageOrRetrievePageWithComponentTypeMethodEntryAndInput_Accuracy() throws Exception {
        Helper.logCreatePageOrRetrievePageWithComponentTypeMethodEntryAndInput("method name", "token",
            "page name", "version", ConfluenceAssetType.APPLICATION_ASSEMBLY, null, ComponentType.CUSTOM, log);
        assertLogMessage("[Entering method {method name}]",
            "[Input parameters[{token}:{token},{pageName}:{page name},{version}:{version},{assetType}:"
                + "{APPLICATION_ASSEMBLY},{catalog}:{},{componentType}:{CUSTOM}]]", byteStream.toString());
    }

    /**
     * <p>
     * Accuracy test case for {@link Helper#logCreatePageWithPageMethodEntryAndInput(String, String, Page, Log)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logCreatePageWithPageMethodEntryAndInput_Accuracy() throws Exception {
        Page page = new Page();
        page.setContent("content");
        page.setAssetType(ConfluenceAssetType.COMPONENT_DESIGN);
        Helper.logCreatePageWithPageMethodEntryAndInput("class name", "token", page, log);
        assertLogMessage("[Entering method {class name.createPage}]",
            "[Input parameters[{token}:{token},{page}:{basePageUrl:,versionUrl:,assetName:,version:,"
                + "applicationCode:,content:content,assetType:COMPONENT_DESIGN,catalog:,componentType:}]]",
            byteStream.toString());
    }

    /**
     * <p>
     * Accuracy test case for {@link Helper#logException(String, String, Throwable, Log)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logException_Accuracy() throws Exception {
        Helper.logException("error message", "method 1", new NullPointerException("null parameter"), log);

        assertTrue("Should contain'[Error in method {method 1}:Details {error message}]'in byteStream.",
            byteStream.toString().contains("[Error in method {method 1}:Details {error message}]"));

        assertTrue("Should contain'java.lang.NullPointerException: null parameter'in byteStream.", byteStream
            .toString().contains("java.lang.NullPointerException: null parameter"));
    }

    /**
     * <p>
     * Accuracy test case for {@link Helper#logLoginMethodEntryAndInput(String, String, String, Log)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logLoginMethodEntryAndInput_Accuracy() throws Exception {
        Helper.logLoginMethodEntryAndInput("className", "user name", "password", log);
        assertLogMessage("[Entering method {className.login}]",
            "[Input parameters[{userName}:{user name},{password}:{password}]]", byteStream.toString());
    }

    /**
     * <p>
     * Accuracy test case for {@link Helper#logLoginOutputAndMethodExit(String, String, Log)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logLoginOutputAndMethodExit_Accuracy() throws Exception {
        Helper.logLoginOutputAndMethodExit("method name", "token", log);
        assertLogMessage("[Exiting method {method name}]", "[Output parameter {token}]", byteStream.toString());
    }

    /**
     * <p>
     * Accuracy test case for {@link Helper#logLogoutMethodEntryAndInput(String, String, Log)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logLogoutMethodEntryAndInput_Accuracy() throws Exception {
        Helper.logLogoutMethodEntryAndInput("class name", "token", log);
        assertLogMessage("[Entering method {class name.logout}]", "[Input parameters[{token}:{token}]]",
            byteStream.toString());
    }

    /**
     * <p>
     * Accuracy test case for {@link Helper#logPageOutputAndMethodExit(String, Page, Log)}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_logPageOutputAndMethodExit_Accuracy() throws Exception {
        Helper.logPageOutputAndMethodExit("method name", new Page(), log);
        assertLogMessage("[Exiting method {method name}]",
            "[Output parameter {basePageUrl:,versionUrl:,assetName:,version:,applicationCode:,content:,"
                + "assetType:,catalog:,componentType:}]", byteStream.toString());
    }
}