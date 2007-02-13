/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;


import com.cronos.timetracker.common.DbRejectReasonDAO;
import com.cronos.timetracker.common.RejectReason;
import com.cronos.timetracker.common.RejectReasonSearchBuilder;
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
public class TestSearchRejectReason extends TestCase {

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
     * Test method searchRejectReason.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectReasons_companyId() throws Exception {
        RejectReason reason = createRejectReasonVersion_1();

        test.createRejectReason(reason, "topcoder");

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        builder.hasCompanyId(1);

        Filter filter = builder.buildFilter();

        RejectReason[] ret = test.searchRejectReasons(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchRejectReason.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectReasons_2_createUser() throws Exception {
        RejectReason reason = createRejectReasonVersion_1();

        test.createRejectReason(reason, "topcoder");

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        builder.createdByUser("topcoder");

        Filter filter = builder.buildFilter();

        RejectReason[] ret = test.searchRejectReasons(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchRejectReason.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectReasons_3_Description() throws Exception {
        RejectReason reason = createRejectReasonVersion_1();

        test.createRejectReason(reason, "topcoder");

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        builder.hasDescription("topcoder");

        Filter filter = builder.buildFilter();

        RejectReason[] ret = test.searchRejectReasons(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchRejectReason.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectReasons_4_hasActiveStatus() throws Exception {
        RejectReason reason = createRejectReasonVersion_1();

        test.createRejectReason(reason, "topcoder");

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        builder.hasActiveStatus(1);

        Filter filter = builder.buildFilter();

        RejectReason[] ret = test.searchRejectReasons(filter);

        assertEquals("Equal is expected.", 1, ret.length);
    }

    /**
     * Test method searchRejectReason.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectReasons_5() throws Exception {
        RejectReason reason = createRejectReasonVersion_1();

        test.createRejectReason(reason, "topcoder");

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        builder.modifiedByUser("tc");

        Filter filter = builder.buildFilter();

        RejectReason[] ret = test.searchRejectReasons(filter);

        assertEquals("Equal is expected.", 0, ret.length);
    }

    /**
     * Test method searchRejectReason.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchRejectReasons_6() throws Exception {
        RejectReason reason = createRejectReasonVersion_1();

        test.createRejectReason(reason, "topcoder");

        // create the builder
        RejectReasonSearchBuilder builder = new RejectReasonSearchBuilder();

        builder.modifiedByUser("topcoder");

        Filter filter = builder.buildFilter();

        RejectReason[] ret = test.searchRejectReasons(filter);

        assertEquals("Equal is expected.", 1, ret.length);
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
}
