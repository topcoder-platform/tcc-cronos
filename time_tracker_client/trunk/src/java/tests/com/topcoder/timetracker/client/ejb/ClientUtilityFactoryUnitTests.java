/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.ejb;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.timetracker.client.UnitTestHelper;


/**
 * Unit test for the <code>ClientUtilityFactory</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilityFactoryUnitTests extends TestCase {
    /**
     * Set tup the test.
     *
     * @throws Exception to Junit
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception to Junit
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * <p>
     * Accuracy test for method <code>getClientUtility</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetNotificationPersistenceAccuracy()
        throws Exception {
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "InformixClientDAO_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "ClientUtilityImpl_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "cu_factory_config.xml");
        assertNotNull("The persistence should be get.", ClientUtilityFactory.getClientUtility());
    }
}
