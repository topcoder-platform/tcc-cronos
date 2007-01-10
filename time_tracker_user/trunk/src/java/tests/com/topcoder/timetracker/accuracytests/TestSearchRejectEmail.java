/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import java.util.Date;

import com.topcoder.timetracker.common.DbRejectEmailDAO;
import com.topcoder.timetracker.common.RejectEmail;
import com.topcoder.timetracker.common.RejectReasonSearchBuilder;
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
public class TestSearchRejectEmail extends TestCase {

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
     * Test method searchRejectEmails.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectEmails_searchCompanyId() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();
        builder.hasCompanyId(1);

        Filter filter = builder.buildFilter();

        // the email is within start to end.
        RejectEmail[] ret = test.searchRejectEmails(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchRejectEmails.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectEmails_searchCreateUser() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();
        builder.createdByUser("tc");

        Filter filter = builder.buildFilter();

        // the email is within start to end.
        RejectEmail[] ret = test.searchRejectEmails(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchRejectEmails.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectEmails_searchmodifiedByUser() throws Exception {
        RejectEmail email = new RejectEmail();
        email.setCompanyId(1);
        email.setBody("topcoder");
        email.setChanged(false);

        String user = "tc";

        test.createRejectEmail(email, user);

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();
        builder.modifiedByUser("tc");

        Filter filter = builder.buildFilter();

        // the email is within start to end.
        RejectEmail[] ret = test.searchRejectEmails(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchRejectEmails.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectEmails_searchmodifiedWithinDateRange() throws Exception {
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

        builder.modifiedWithinDateRange(start, end);

        Filter filter = builder.buildFilter();

        // the email is within start to end.
        RejectEmail[] ret = test.searchRejectEmails(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }
}
