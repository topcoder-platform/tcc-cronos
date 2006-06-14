/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import java.util.Date;

import com.cronos.timetracker.common.DbRejectEmailDAO;
import com.cronos.timetracker.common.RejectEmail;
import com.cronos.timetracker.common.RejectReasonSearchBuilder;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>DbRejectEmailDAO</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestDbRejectEmailDAOAccuracy extends TestCase {

    /**
     * Represents the DbRejectEmailDAO instance for test.
     */
    private DbRejectEmailDAO test = null;

    /**
     * Represents the connectName.
     */
    private static final String connectName = "sysuser";

    /**
     * Represents the id name.
     */
    private static final String idName = "sysuser";

    /**
     * Set up the enviroment. Create DbRejectEmailDAO instance.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        Util.clearNamespace();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracytests/DBConnectionFactory.xml");
        cm.add("accuracytests/Logging.xml");

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        Util.insertRecordsIntoCompanyTable(1);

        test = new DbRejectEmailDAO(factory, connectName, idName);
    }

    /**
     * Tear down the enviroment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        Util.clearTables();

        Util.clearNamespace();

        test = null;
    }

    /**
     * Test constructor.
     *
     */
    public void testDbRejectEmailDAO() {
        assertNotNull("Should not be null.", test);
    }

    /**
     * test method <code>RejectEmail createRejectEmail(RejectEmail rejectEmail, String username) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateRejectEmail() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        RejectEmail ret = test.createRejectEmail(email, user);
        assertEquals("Equal is expected.", email, ret);
    }

    /**
     * Test method <code>RejectEmail retrieveRejectEmail(long id) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testRetrieveRejectEmail() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        RejectEmail ret = test.retrieveRejectEmail(email.getId());

        assertEquals("Equal is expected.", "topcoder", ret.getBody());
        assertEquals("Equal is expected.", email, ret);

    }

    /**
     * Test method <code>void updateRejectEmail(RejectEmail rejectEmail, String username) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateRejectEmail() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        email.setBody("review");

        test.updateRejectEmail(email, user);

        RejectEmail ret = test.retrieveRejectEmail(email.getId());

        assertEquals("Equal is expected.", "review", ret.getBody());
    }

    /**
     * Test method <code>void deleteRejectEmail(RejectEmail rejectEmail) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteRejectEmail() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        RejectEmail ret = test.retrieveRejectEmail(email.getId());

        assertNotNull("Should be exist", ret);

        test.deleteRejectEmail(email);

        ret = test.retrieveRejectEmail(email.getId());

        assertNull("Should be deleted.", ret);
    }

    /**
     * Test method <code>RejectEmail[] listRejectEmails() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testListRejectEmails() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        RejectEmail[] ret = test.listRejectEmails();
        assertEquals("Equal is expected.", 1, ret.length);
        assertEquals("Equal is expected.", email, ret[0]);
    }

    /**
     * Test method <code>RejectEmail[] searchRejectEmails(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectEmails_1() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.createdWithinDateRange(start, end);
        builder.hasCompanyId(1);

        Filter filter = builder.buildFilter();

        // the email is within start to end.
        RejectEmail[] ret = test.searchRejectEmails(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method <code>RejectEmail[] searchRejectEmails(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectEmails_2() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        Date start = new Date(System.currentTimeMillis() + 1000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.createdWithinDateRange(start, end);
        builder.hasCompanyId(1);

        Filter filter = builder.buildFilter();

        // not within the scope of start to end.
        RejectEmail[] ret = test.searchRejectEmails(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }
}
