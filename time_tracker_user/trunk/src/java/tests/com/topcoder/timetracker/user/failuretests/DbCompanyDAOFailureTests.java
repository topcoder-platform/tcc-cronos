/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.DbCompanyDAO;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * Failure unit test cases for the DbCompanyDAO class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class DbCompanyDAOFailureTests extends TestCase {
    /**
     * <p>
     * Connection name.
     * </p>
     */
    private static final String CONN_NAME = "informix";

    /**
     * <p>
     * Id generator name.
     * </p>
     */
    private static final String ID_GENERATOR_NAME = "ttu2";

    /**
     * <p>
     * Algorithm name.
     * </p>
     */
    private static final String ALGORITHM_NAME = "ttu2";

    /**
     * <p>
     * DBConnection Factory namespace.
     * </p>
     */
    private static final String DB_CONN_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /**
     * <p>
     * The name of the file containing configuration data.
     * </p>
     */
    private static final String CONFIG_XML = "failuretests" + File.separatorChar + "dbconfig.xml";

    /**
     * <p>
     * DBConnectionFactory instance used for testing
     * </p>
     */
    private DBConnectionFactory connectionFactory = null;

    /**
     * <p>
     * The DbCompanyDAO instance used for testing.
     * </p>
     */
    private DbCompanyDAO dbCompanyDAO = null;

    /**
     * <p>
     * Creates DbCompanyDAO instance.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        FailureTestsHelper.loadConfig(CONFIG_XML);

        connectionFactory = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE);
        dbCompanyDAO = new DbCompanyDAO(connectionFactory, CONN_NAME, ALGORITHM_NAME, ID_GENERATOR_NAME);
    }

    /**
     * <p>
     * Removes the configuration if it exists.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureTestsHelper.cleanConfig();
    }

    /**
     * <p>
     * Tests DbCompanyDAO(DBConnectionFactory, String, String, String) for failure. Passes null as first
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1() throws Exception {
        try {
            new DbCompanyDAO(null, CONN_NAME, ALGORITHM_NAME, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbCompanyDAO(DBConnectionFactory, String, String, String) for failure. Passes null as second
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2() throws Exception {
        try {
            new DbCompanyDAO(connectionFactory, null, ALGORITHM_NAME, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbCompanyDAO(DBConnectionFactory, String, String, String) for failure. Passes null as third
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor3() throws Exception {
        try {
            new DbCompanyDAO(connectionFactory, CONN_NAME, null, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbCompanyDAO(DBConnectionFactory, String, String, String) for failure. Passes null as fourth
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor4() throws Exception {
        try {
            new DbCompanyDAO(connectionFactory, CONN_NAME, ALGORITHM_NAME, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbCompanyDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string as
     * second argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor5() throws Exception {
        try {
            new DbCompanyDAO(connectionFactory, " ", ALGORITHM_NAME, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbCompanyDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string as
     * third argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor6() throws Exception {
        try {
            new DbCompanyDAO(connectionFactory, CONN_NAME, " ", ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbCompanyDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string as
     * fourth argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor7() throws Exception {
        try {
            new DbCompanyDAO(connectionFactory, CONN_NAME, ALGORITHM_NAME, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createCompanies(Company[], String, boolean) for failure. Passes null array,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateCompanies1() throws Exception {
        try {
            dbCompanyDAO.createCompanies(null, "A", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createCompanies(Company[], String, boolean) for failure. Passes array with null elements,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateCompanies2() throws Exception {
        try {
            dbCompanyDAO.createCompanies(new Company[] {new Company(), null}, "A", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createCompanies(Company[], String, boolean) for failure. Passes empty user name,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateCompanies3() throws Exception {
        try {
            dbCompanyDAO.createCompanies(new Company[] {new Company()}, " ", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createCompany(Company, String) for failure. Passes null company, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateCompany1() throws Exception {
        try {
            dbCompanyDAO.createCompany(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteCompanies(Company[], boolean) for failure. Passes null array, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteCompanies1() throws Exception {
        try {
            dbCompanyDAO.deleteCompanies(null, false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteCompanies(Company[], boolean) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteCompanies2() throws Exception {
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {new Company(), null}, false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteCompany(Company) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteCompany1() throws Exception {
        try {
            dbCompanyDAO.deleteCompany(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveCompanies(long[]) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRetrieveCompanies1() throws Exception {
        try {
            dbCompanyDAO.retrieveCompanies(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests searchCompanies(Filter) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSearchCompanies1() throws Exception {
        try {
            dbCompanyDAO.searchCompanies(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateCompanies(Company[], String, boolean) for failure. Passes null array,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateCompanies1() throws Exception {
        try {
            dbCompanyDAO.updateCompanies(null, "A", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateCompanies(Company[], String, boolean) for failure. Passes array with null elements,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateCompanies2() throws Exception {
        try {
            dbCompanyDAO.updateCompanies(new Company[] {new Company(), null}, "A", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateCompanies(Company[], String, boolean) for failure. Passes empty user name,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateCompanies3() throws Exception {
        try {
            dbCompanyDAO.updateCompanies(new Company[] {new Company()}, " ", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateCompany(Company, String) for failure. Passes null company, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateCompany1() throws Exception {
        try {
            dbCompanyDAO.updateCompany(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
