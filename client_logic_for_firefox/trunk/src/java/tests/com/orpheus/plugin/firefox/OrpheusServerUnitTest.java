/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of {@link OrpheusServer} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class OrpheusServerUnitTest extends TestCase {
    /** Represents the timestampParameter for testing. */
    private static final String TIEMSTAMP_PARAMETER = "timestampParameter";

    /** Represents the timestamp for testing. */
    private static final Calendar TIEMSTAMP = Calendar.getInstance();

    /** Represents the pollTime for testing. */
    private static final int POLL_TIME = 1;

    /** Represents the domainParameter for testing. */
    private static final String DOMAIN_PARAMETER = "domainParameter";

    /** Represents the domain for testing. */
    private static final String DOMAIN = "domain";

    /** Represents the url for testing. */
    private String url = null;

    /** Represents the listener for testing. */
    private FirefoxExtensionHelper listener = null;

    /** Represents the <code>OrpheusServer</code> instance used for testing. */
    private OrpheusServer server = null;

    /** Represents the JSObject for testing. */
    private MockJSObject jsObject = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        url = UnitTestHelper.getDomain() + "pollServer.html";
        listener = new FirefoxExtensionHelper();
        jsObject = new MockJSObject();
        UnitTestHelper.setPrivateField(listener.getClass(), listener, "clientWindow", jsObject);
        listener.initialize();
        server = new OrpheusServer(url, TIEMSTAMP_PARAMETER, TIEMSTAMP, POLL_TIME, listener);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * <p>
     * Tests the constructor {@link OrpheusServer#OrpheusServer(String, String, Calendar, int, FirefoxExtensionHelper)}
     * when the given url is null. IllegalArgumentException is expected.
     * </p>
     */
    public void testOrpheusServer_NullUrl() {
        try {
            new OrpheusServer(null, TIEMSTAMP_PARAMETER, TIEMSTAMP, POLL_TIME, listener);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor {@link OrpheusServer#OrpheusServer(String, String, Calendar, FirefoxExtensionHelper)} when
     * the given url is empty. IllegalArgumentException is expected.
     * </p>
     */
    public void testOrpheusServer_EmptyUrl() {
        try {
            new OrpheusServer(" ", TIEMSTAMP_PARAMETER, TIEMSTAMP, POLL_TIME, listener);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor {@link OrpheusServer#OrpheusServer(String, String, Calendar, FirefoxExtensionHelper)} when
     * the given timeParameter is null. IllegalArgumentException is expected.
     * </p>
     */
    public void testOrpheusServer_NullTimeParameter() {
        try {
            new OrpheusServer(url, null, TIEMSTAMP, POLL_TIME, listener);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor {@link OrpheusServer#OrpheusServer(String, String, Calendar, FirefoxExtensionHelper)} when
     * the given timeParameter is empty. IllegalArgumentException is expected.
     * </p>
     */
    public void testOrpheusServer_EmptyTimeParameter() {
        try {
            new OrpheusServer(url, " ", TIEMSTAMP, POLL_TIME, listener);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor {@link OrpheusServer#OrpheusServer(String, String, Calendar, FirefoxExtensionHelper)} when
     * the given timestamp is null. No exception is expected.
     * </p>
     */
    public void testOrpheusServer_NullTimestamp() {
        new OrpheusServer(url, TIEMSTAMP_PARAMETER, null, POLL_TIME, listener);
    }

    /**
     * <p>
     * Tests the constructor {@link OrpheusServer#OrpheusServer(String, String, Calendar, FirefoxExtensionHelper)} when
     * the given pollTime not positive. IllegalArgumentException is expected.
     * </p>
     */
    public void testOrpheusServer_InvalidPollTime() {
        try {
            new OrpheusServer(url, TIEMSTAMP_PARAMETER, TIEMSTAMP, 0, listener);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor {@link OrpheusServer#OrpheusServer(String, String, Calendar, FirefoxExtensionHelper)} when
     * the given listener is null. IllegalArgumentException is expected.
     * </p>
     */
    public void testOrpheusServer_NullListener() {
        try {
            new OrpheusServer(url, TIEMSTAMP_PARAMETER, TIEMSTAMP, POLL_TIME, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of constructor {@link OrpheusServer#OrpheusServer(String, String, Calendar,
     * FirefoxExtensionHelper)}.
     * </p>
     */
    public void testOrpheusServer_Accuracy() {
        assertEquals("The url value should be set.", url,
            UnitTestHelper.getPrivateField(server.getClass(), server, "url"));
        assertEquals("The listener value should be set.", listener,
            UnitTestHelper.getPrivateField(server.getClass(), server, "listener"));
        assertEquals("The pollTime value should be set.", POLL_TIME + "",
            UnitTestHelper.getPrivateField(server.getClass(), server, "pollTime").toString());
        assertEquals("The timestamp value should be set.", TIEMSTAMP,
            UnitTestHelper.getPrivateField(server.getClass(), server, "timestamp"));
        assertEquals("The timestampParameter should be set.", TIEMSTAMP_PARAMETER,
            UnitTestHelper.getPrivateField(server.getClass(), server, "timestampParameter"));
        assertEquals("The stopPolling value should be false.", false + "",
            UnitTestHelper.getPrivateField(server.getClass(), server, "stopPolling").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link OrpheusServer#pollServerNow()}.
     * </p>
     */
    public void testPollServerNow_Accuracy() {
        server.pollServerNow();
        assertEquals("The pollTime value should be set properly.", new Date(1000),
            ((Calendar) UnitTestHelper.getPrivateField(server.getClass(), server, "timestamp")).getTime());
    }

    /**
     * <p>
     * Tests the method {@link OrpheusServer#setPollTime()} when the pollTime is not positive, IllegalArgumentException
     * is expected.
     * </p>
     */
    public void testSetPollTime_InvalidPollTime() {
        try {
            server.setPollTime(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link OrpheusServer#setPollTime(int)}.
     * </p>
     */
    public void testSetPollTime_Accuracy() {
        server.setPollTime(POLL_TIME);
        assertEquals("The pollTime value should be set properly.", POLL_TIME + "",
            UnitTestHelper.getPrivateField(server.getClass(), server, "pollTime").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link OrpheusServer#getPollTime()}.
     * </p>
     */
    public void testGetPollTime_Accuracy() {
        assertEquals("The pollTime value should be got properly.", POLL_TIME, server.getPollTime());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link OrpheusServer#run()}, no exception is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRun_Accuracy() throws Exception {
        server.start();
        Thread.sleep(3000);
        server.stopThread();
    }

    /**
     * <p>
     * Tests the method {@link OrpheusServer#queryDomain(String, String)} when the domainParameter is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testQueryDomain_NullDomainParameter() throws Exception {
        try {
            server.queryDomain(null, DOMAIN);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link OrpheusServer#queryDomain(String, String)} when the domainParameter is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testQueryDomain_EmptyDomainParameter() throws Exception {
        try {
            server.queryDomain(" ", DOMAIN);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link OrpheusServer#queryDomain(String, String)} when the domain is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testQueryDomain_NullDomain() throws Exception {
        try {
            server.queryDomain(DOMAIN_PARAMETER, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link OrpheusServer#queryDomain(String, String)} when the domain is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testQueryDomain_EmptyDomain() throws Exception {
        try {
            server.queryDomain(DOMAIN_PARAMETER, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link OrpheusServer#queryDomain(String, String)}.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testQueryDomain_Accuracy() throws Exception {
        assertEquals("The number of active games in the given domain should be got properly.", 3.14,
            server.queryDomain(DOMAIN_PARAMETER, DOMAIN), 1e-8);
    }

    /**
     * <p>
     * Tests the accuracy of method {@link OrpheusServer#stopThread(boolen)}.
     * </p>
     */
    public void testStopThread_Accuracy() {
        server.stopThread();
        assertEquals("The stopPolling value should be set properly.", "" + true,
            UnitTestHelper.getPrivateField(server.getClass(), server, "stopPolling").toString());
    }
}
