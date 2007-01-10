/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import java.util.Date;

import com.topcoder.timetracker.common.DbRejectReasonDAO;
import com.topcoder.timetracker.common.RejectReason;
import com.topcoder.timetracker.common.RejectReasonSearchBuilder;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>DbRejectReasonDAO</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestDbRejectReasonDAOAccuracy extends TestCase {

    /**
     * Represents the DbRejectReasonDAO instance for test.
     */
    private DbRejectReasonDAO test = null;

    /**
     * Represents the connectName.
     */
    private static final String connectName = "sysuser";

    /**
     * Represents the id name.
     */
    private static final String idName = "sysuser";

    /**
     * Set up the enviroment. Create DbRejectReasonDAO instance.
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

        test = new DbRejectReasonDAO(factory, connectName, idName);
    }

    /**
     * Tear down the enviroment. clear tables needed and clear all the namespace contained in the config manager
     * instance.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        // clear tables.
        Util.clearTables();
        // clear namespace.
        Util.clearNamespace();

        test = null;
    }

    /**
     * Test constructor.
     *
     */
    public void testDbRejectReasonDAO() {
        assertNotNull("Should not be null.", test);
    }

    /**
     * test method <code>RejectReason createRejectReason(RejectReason rejectReason, String username) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateRejectReason_1() throws Exception {
        RejectReason reason = this.createRejectReasonVersion_1();

        RejectReason ret = test.createRejectReason(reason, "user");

        // check if the return RejectReason is equal to the original one.
        assertEquals("Equal is expected.", reason, ret);
    }

    /**
     * test method <code>RejectReason createRejectReason(RejectReason rejectReason, String username) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateRejectReason_2() throws Exception {
        RejectReason reason = this.createRejectReasonVersion_2();

        RejectReason ret = test.createRejectReason(reason, "user");

        // check if the return RejectReason is equal to the original one.
        assertEquals("Equal is expected.", reason, ret);
    }

    /**
     * Test method <code>RejectReason retrieveRejectReason(long id) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveRejectReason_1() throws Exception {
        RejectReason reason = this.createRejectReasonVersion_1();

        test.createRejectReason(reason, "user");

        RejectReason ret = test.retrieveRejectReason(reason.getId());

        assertNotNull("The RejectReason instance should be retrieved.", ret);
        assertEquals("Equal is expected.", "topcoder", ret.getDescription());
    }

    /**
     * Test method <code>RejectReason retrieveRejectReason(long id) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveRejectReason_2() throws Exception {
        RejectReason reason = this.createRejectReasonVersion_2();

        test.createRejectReason(reason, "user");

        RejectReason ret = test.retrieveRejectReason(reason.getId());

        assertNotNull("The RejectReason instance should be retrieved.", ret);
        assertEquals("Equal is expected.", "topcoder", ret.getDescription());
    }

    /**
     * Test method <code>void updateRejectReason(RejectReason rejectReason, String username) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testUpdateRejectReason() throws Exception {
        RejectReason reason = new RejectReason();
        reason.setActive(true);
        reason.setCompanyId(1);
        reason.setDescription("topcoder");

        test.createRejectReason(reason, "user");

        reason.setDescription("review");

        test.updateRejectReason(reason, "user");

        RejectReason ret = test.retrieveRejectReason(reason.getId());
        assertNotNull("Should not be null.", ret);
        assertEquals("Equal is expected.", "review", ret.getDescription());
    }

    /**
     * Test method <Code>void deleteRejectReason(RejectReason rejectReason)</code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteRejectReason() throws Exception {
        RejectReason reason = this.createRejectReasonVersion_1();

        test.createRejectReason(reason, "user");

        assertNotNull("Should not be null.", test.retrieveRejectReason(reason.getId()));

        test.deleteRejectReason(reason);

        // now it should be deleted.
        assertNull("Should be null.", test.retrieveRejectReason(reason.getId()));
    }

    /**
     * Test method <code>RejectReason[] listRejectReasons() </code>.
     *
     */
    public void testListRejectReasons() throws Exception {
        RejectReason reason = this.createRejectReasonVersion_1();

        test.createRejectReason(reason, "user");
        RejectReason[] ret = test.listRejectReasons();
        assertEquals("Equal is expected.", 1, ret.length);

        assertEquals("Equal is expected.", reason, ret[0]);
    }

    /**
     * test method <code> RejectReason[] searchRejectReasons(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchRejectReasons_1() throws Exception {
        RejectReason reason = this.createRejectReasonVersion_1();

        test.createRejectReason(reason, "topcoder");

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        Date start = new Date(System.currentTimeMillis() - 1000000000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.createdWithinDateRange(start, end);
        builder.hasActiveStatus(1);
        builder.hasCompanyId(1);

        Filter filter = builder.buildFilter();

        RejectReason[] ret = test.searchRejectReasons(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * test method <code> RejectReason[] searchRejectReasons(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchRejectReasons_2() throws Exception {
        RejectReason reason = this.createRejectReasonVersion_1();

        test.createRejectReason(reason, "topcoder");

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        Date start = new Date(System.currentTimeMillis() + 100000);
        Date end = new Date(System.currentTimeMillis() + 1000000000);

        builder.createdWithinDateRange(start, end);
        builder.hasActiveStatus(1);
        builder.hasCompanyId(1);

        Filter filter = builder.buildFilter();

        RejectReason[] ret = test.searchRejectReasons(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Create RejectReason instance for test. In this method, the active is set to true;
     *
     * @return RejectReason instance.
     */
    private RejectReason createRejectReasonVersion_1() {
        RejectReason reason = new RejectReason();
        reason.setActive(true);
        reason.setCompanyId(1);
        reason.setDescription("topcoder");

        return reason;
    }

    /**
     * Create RejectReason instance for test. In this method, the active is set to false;
     *
     * @return RejectReason instance.
     */
    private RejectReason createRejectReasonVersion_2() {
        RejectReason reason = new RejectReason();
        reason.setActive(false);
        reason.setCompanyId(1);
        reason.setDescription("topcoder");

        return reason;
    }
}
