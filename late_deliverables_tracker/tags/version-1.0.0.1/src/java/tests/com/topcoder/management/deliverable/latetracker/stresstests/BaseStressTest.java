/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.deliverable.latetracker.stresstests;

import java.sql.Connection;
import java.util.Date;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import junit.framework.TestCase;

/**
 * <p>
 * BaseUnitTest class for the stress tests.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class BaseStressTest extends TestCase {
    /** The test count. */
    static final int testCount = 1;

    /**
     * The connection to use.
     */
    static Connection con;

    /** time started to test */
    long start = 0;

    /**
     * Initialize variables.
     *
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        start = new Date().getTime();

        con = StressTestUtil.createConnection(StressTestUtil.loadProperties(StressTestUtil.DB_PROPERTIES_FILE));

        StressTestUtil.clearDataBase(con);
        StressTestUtil.setUpDataBase(con);

        StressTestUtil.addStressTestConfigurations();
    }

    /**
     * Tears down.
     *
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        StressTestUtil.clearDataBase(con);
        StressTestUtil.closeConnection(con);

        StressTestUtil.clearConfigurations();
    }

    /**
     * <p>
     * Asserts the email sent.
     * </p>
     *
     * @param server
     *            the smtp server to retrieve the email
     */
    void assertEmail(SimpleSmtpServer server) {
        SmtpMessage email = (SmtpMessage) server.getReceivedEmail().next();

        assertTrue("Should contain the specified message", email.getHeaderValue("Subject").indexOf(
            "You are late for Project 0") > 0);

        String body = email.getBody();
        assertTrue("Should contain the specified message", body.indexOf("You have delayed for: 2 days") != -1);
        assertTrue("Should contain the specified message", body
            .indexOf("The deadline for the phase: Registration of project: Project 0") != -1);

        assertEquals("The sender should be 'service@topcoder.com'", "service@topcoder.com", email
            .getHeaderValue("From"));
        assertEquals("The receiver should be 'service@topcoder.com'", "zhijie_liu@topcoder.com", email
            .getHeaderValue("To"));
    }
}
