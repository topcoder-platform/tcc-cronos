/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.client.db.InformixClientDAO;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;


/**
 * Unit test for the <code>Depth</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DepthUnitTest extends TestCase {
    /** Represents the private instance used for test. */
    private Depth depth = null;

    /** Represents the private instance used for test. */
    private InformixClientDAO dao = null;

    /** DB connection factory. */
    private DBConnectionFactory dbFactory = null;

    /** Client Utility Impl instance. */
    private ClientUtility impl = null;

    /**
     * Set up the test.
     *
     * @throws Exception to Junit
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

        depth = new ClientIDOnlyDepth();

        impl = new ClientUtilityImpl();
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Constructor with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithNull() throws Exception {
        try {
            new MockDepth(null, false, false, false, false);
            fail("Constructor with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Constructor with null element is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testConstructorWithInvlid1() throws Exception {
        try {
            new MockDepth(new String[] {null}, false, false, false, false);
            fail("Constructor with null element is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>Constructor</code>. Constructor with empty element is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorWithInvlid2() throws Exception {
        try {
            new MockDepth(new String[] {"      "}, false, false, false, false);
            fail("Constructor with null element is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
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
     * Accuracy test for method <code>getFields</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetFieldsAccuracy() throws Exception {
        depth = new ClientIDOnlyDepth();

        assertNotNull("The fields is not null.", depth.getFields());
    }

    /**
     * <p>
     * Failure test for method <code>buildResult</code>. Build with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testbuildResultWith() throws Exception {
        try {
            depth.buildResult(null);
            fail("Build with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>buildResult</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testbuildResultAccuracy() throws Exception {
        impl.addClient(UnitTestHelper.createCient(1), false);

        CustomResultSet result = dao.searchClient(ClientFilterFactory.createActiveFilter(true), depth);

        depth.buildResult(result);
    }

    /**
     * <p>
     * Accuracy test for method <code>onlyProjectIDName</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testonlyProjectIDNameAccuracy() throws Exception {
        assertFalse("The flag is false.", depth.onlyProjectsIdName());
    }

    /**
     * <p>
     * Accuracy test for method <code>useAddress</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testuseAddressAccuracy() throws Exception {
        assertFalse("The flag is false.", depth.useAddress());
    }

    /**
     * <p>
     * Accuracy test for method <code>useContact</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testuseContactAccuracy() throws Exception {
        assertFalse("the flag is false.", depth.useContact());
    }

    /**
     * <p>
     * Accuracy test for method <code>useProjects</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testuseProjectsAccuracy() throws Exception {
        assertFalse("The flag is false.", depth.useProjects());
    }

    /**
     * Mock depth class used for test.
     */
    private class MockDepth extends Depth {
        /**
         * <p>
         * Constructs the Depth.
         * </p>
         *
         * @param fields non null array containing non null, non empty(trim'd) field names
         * @param onlyProjectsIdName whether the project info only need id and name.
         * @param setProjects whether the projects property need to be set
         * @param setAddress whether the address property need to be set
         * @param setContact whether the contact property need to be set
         *
         * @throws IllegalArgumentException if the fields is null or containing null/empty field name
         */
        public MockDepth(String[] fields, boolean onlyProjectsIdName, boolean setProjects, boolean setAddress,
            boolean setContact) {
            super(fields, onlyProjectsIdName, setProjects, setAddress, setContact);
        }

        /**
         * <p>
         * Build the client according to current row of the result set.
         * </p>
         *
         * @param result non null result set used to build result
         *
         * @return non null Client build from current row of result set
         *
         * @throws ClientPersistenceException if any error occurred
         */
        public Client buildClient(CustomResultSet result)
            throws ClientPersistenceException {
            return null;
        }
    }
}
