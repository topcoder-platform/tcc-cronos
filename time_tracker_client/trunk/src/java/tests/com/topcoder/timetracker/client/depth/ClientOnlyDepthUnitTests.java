/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.depth;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.ClientUtility;
import com.topcoder.timetracker.client.ClientUtilityImpl;
import com.topcoder.timetracker.client.UnitTestHelper;
import com.topcoder.timetracker.client.db.InformixClientDAO;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;


/**
 * Unit test for the <code>ClientOnlyDepth</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientOnlyDepthUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private ClientOnlyDepth depth = null;

    /** Represents the private instance used for test. */
    private InformixClientDAO dao = null;

    /** DB connection factory. */
    private DBConnectionFactory dbFactory = null;

    /** Client Utility Impl instance. */
    private ClientUtility impl = null;

    /**
     * Set up the test.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();

        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "InformixClientDAO_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "ClientUtilityImpl_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");

        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        UnitTestHelper.setUpDatabase(dbFactory, "informix_connect");

        dao = new InformixClientDAO();

        depth = new ClientOnlyDepth();

        impl = new ClientUtilityImpl();
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception to Junit
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.clearDatabase(dbFactory, "informix_connect");
    }
    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Instance should be created.", depth);
    }

    /**
     * <p>
     * Failure test for method <code>buildClient</code>. Build client with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testbuildClientWith() throws Exception {
        try {
            depth.buildClient(null);
            fail("Build client with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>buildClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testbuildClientAccuracy() throws Exception {
        impl.addClient(UnitTestHelper.createCient(1), false);

        CustomResultSet result = dao.searchClient(ClientFilterFactory.createActiveFilter(true), depth);

        while (result.next()) {
            depth.buildClient(result);
        }
    }
}
