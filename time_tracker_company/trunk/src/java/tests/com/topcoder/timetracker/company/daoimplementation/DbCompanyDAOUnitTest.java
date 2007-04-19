/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.daoimplementation;

import java.sql.Connection;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAOException;
import com.topcoder.timetracker.company.CompanyNotFoundException;
import com.topcoder.timetracker.company.CompanySearchBuilder;
import com.topcoder.timetracker.company.MockAddressManager;
import com.topcoder.timetracker.company.MockAuditManager;
import com.topcoder.timetracker.company.MockContactManager;
import com.topcoder.timetracker.company.UnitTestHelper;


/**
 * <p>
 * Tests functionality and error cases of <code>DbCompanyDAO</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbCompanyDAOUnitTest extends TestCase {
    /** Represents the <code>ContactManager</code> instance used for testing. */
    private MockContactManager contactManager = null;

    /** Represents the <code>AddressManager</code> instance used for testing. */
    private MockAddressManager addressManager = null;

    /** Represents the <code>AuditManager</code> instance used for testing. */
    private MockAuditManager auditManager = null;

    /** Represents the <code>SearchBundle</code> instance used for testing. */
    private SearchBundle searchBundle = null;

    /** Represents the <code>DbCompanyDAO</code> instance used for testing. */
    private DbCompanyDAO dao = null;

    /** Represents the <code>DBConnectionFactory</code> instance used for testing. */
    private DBConnectionFactory connFactory = null;

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the user to do the audit for testing. */
    private String user = "user";

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        // create the testing instance
        connFactory = UnitTestHelper.getDBConnectionFactory();
        contactManager = new MockContactManager(false);
        addressManager = new MockAddressManager(false);
        auditManager = new MockAuditManager(false);
        searchBundle = UnitTestHelper.createSearchBundle();
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                addressManager, auditManager, searchBundle);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String
     * contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code> when the given
     * connFactory is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO1_NullConnFactory() throws Exception {
        try {
            new DbCompanyDAO(null, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, DbCompanyDAO.class.getName());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String
     * contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code> when the given
     * connName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO1_NullConnName() throws Exception {
        try {
            new DbCompanyDAO(connFactory, null, UnitTestHelper.IDGEN_NAME, DbCompanyDAO.class.getName());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String
     * contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code> when the given
     * connName is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO1_EmptyConnName() throws Exception {
        try {
            new DbCompanyDAO(connFactory, " ", UnitTestHelper.IDGEN_NAME, DbCompanyDAO.class.getName());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String
     * contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code> when the given
     * idGen is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO1_NullIdGen() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, null, DbCompanyDAO.class.getName());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String
     * contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code> when the given
     * idGen is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO1_EmptyIdGen() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, " ", DbCompanyDAO.class.getName());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String
     * contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code> when the given
     * idGen is invalid, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO1_InvalidIdGen() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, "invalid", DbCompanyDAO.class.getName());
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String
     * contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code> when the given
     * name is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO1_NullNamespace() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen, String
     * contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code> when the given
     * namespace is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO1_EmptyNamespace() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String
     * idGen, String contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code>. No
     * exception is expected, all the fields should be set.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_Accuracy() throws Exception {
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME,
                DbCompanyDAO.class.getName());
        assertNotNull("The DbCompanyDAO instance should be created.", dao);
        assertNotNull("The idGenerator should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "idGenerator"));
        assertNotNull("The searchBundle should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "searchBundle"));
        assertNotNull("The contactManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "contactManager"));
        assertNotNull("The addressManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "addressManager"));
        assertNotNull("The auditManager should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "auditManager"));
        assertEquals("The connectionName should be loaded properly.", UnitTestHelper.CONN_NAME,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "connectionName"));
        assertEquals("The connectionFactory should be loaded properly.", connFactory,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "connectionFactory"));
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * connFactory is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_NullConnFactory() throws Exception {
        try {
            new DbCompanyDAO(null, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager, addressManager,
                auditManager, searchBundle);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * connName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_NullConnName() throws Exception {
        try {
            new DbCompanyDAO(connFactory, null,
                    UnitTestHelper.IDGEN_NAME, contactManager, addressManager, auditManager, searchBundle);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * connName is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_EmptyConnName() throws Exception {
        try {
            new DbCompanyDAO(connFactory, " ", UnitTestHelper.IDGEN_NAME, contactManager, addressManager,
                    auditManager, searchBundle);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * idGen is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_NullIdGen() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, null, contactManager, addressManager,
                    auditManager, searchBundle);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * idGen is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_EmptyIdGen() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, " ", contactManager, addressManager,
                    auditManager, searchBundle);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * idGen is invalid, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_InvalidIdGen() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, "invalid", contactManager, addressManager,
                auditManager, searchBundle);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * contactManager is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_NullContactManager() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, null, addressManager,
                auditManager, searchBundle);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * addressManager is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_NullAddressManager() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager, null,
                auditManager, searchBundle);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String idGen,
     * ContactManager contactManager, AddressManager addressManager, AuditManager auditManager)</code> when the given
     * auditManager is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDbCompanyDAO2_NullAuditManager() throws Exception {
        try {
            new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                addressManager, null, searchBundle);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DbCompanyDAO(DBConnectionFactory connFactory, String connName, String
     * idGen, String contactManagerNamespace, String addressManagerNamespace, String auditManagerNamespace)</code>. No
     * exception is expected, all the fields should be set.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO2_Accuracy() throws Exception {
        assertNotNull("The DbCompanyDAO instance should be created.", dao);
        assertNotNull("The idGenerator should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "idGenerator"));
        assertNotNull("The searchBundle should be created.",
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "searchBundle"));
        assertEquals("The contactManager should be loaded properly.", contactManager,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "contactManager"));
        assertEquals("The addressManager should be loaded properly.", addressManager,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "addressManager"));
        assertEquals("The auditManager should be loaded properly.", auditManager,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "auditManager"));
        assertEquals("The connectionName should be loaded properly.", UnitTestHelper.CONN_NAME,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "connectionName"));
        assertEquals("The connectionFactory should be loaded properly.", connFactory,
            UnitTestHelper.getPrivateField(dao.getClass(), dao, "connectionFactory"));
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullCompany() throws Exception {
        try {
            dao.createCompany(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company's
     * companyName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullCompanyName() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "companyName", null);

        try {
            dao.createCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company's
     * passCode is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullPassCode() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "passCode", null);

        try {
            dao.createCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company's
     * contact is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullContact() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "contact", null);

        try {
            dao.createCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company's
     * address is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullAddress() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "address", null);

        try {
            dao.createCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the user is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_NullUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            dao.createCompany(company, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the user is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_EmptyUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            dao.createCompany(company, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the connName
     * is invalid, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_InvalidConnectionName() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao = new DbCompanyDAO(connFactory, "invalid", UnitTestHelper.IDGEN_NAME, contactManager, addressManager,
                auditManager, searchBundle);

        try {
            dao.createCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the company is
     * duplicated, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_DuplicateCompany() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, true);

        try {
            dao.createCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the errors
     * happen in contactManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_ContactManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME,
                new MockContactManager(true), addressManager, auditManager, searchBundle);

        try {
            dao.createCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the errors
     * happen in addressManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_AddressManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                new MockAddressManager(true), auditManager, searchBundle);

        try {
            dao.createCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompany(Company company, String user, boolean doAudit)</code> when the errors
     * happen in auditManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_AuditManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                addressManager, new MockAuditManager(true), searchBundle);

        try {
            dao.createCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompany(Company company, String user, boolean doAudit)</code>. The id
     * will be auto generated.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_Accuracy1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company = dao.createCompany(company, user, true);

        // retrieve it and check it
        Company persisted = dao.retrieveCompany(company.getId());
        UnitTestHelper.assertEquals(company, persisted);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompany(Company company, String user, boolean doAudit)</code>. The id
     * will be pre-defined.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_Accuracy2() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company.setId(1);
        company = dao.createCompany(company, user, true);

        // retrieve it and check it
        Company persisted = dao.retrieveCompany(1);
        UnitTestHelper.assertEquals(company, persisted);
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompany(Company company, String user, boolean doAudit)</code>. Check the
     * whether the xxxManagers are properly invoked.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompany_Accuracy3() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company = dao.createCompany(company, user, true);

        // check the ContactManager
        assertEquals("ContactManager should be properly invoked.", 1, this.contactManager.getContacts().length);
        assertEquals("ContactManager should be properly invoked.", company.getContact(),
            this.contactManager.getContacts()[0]);
        assertEquals("ContactManager should be properly invoked.", company.getContact(),
            this.contactManager.getContact(company.getId()));

        // check the AddressManager
        assertEquals("AddressManager should be properly invoked.", 1, this.addressManager.getAddresses().length);
        assertEquals("AddressManager should be properly invoked.", company.getAddress(),
            this.addressManager.getAddresses()[0]);
        assertEquals("AddressManager should be properly invoked.", company.getAddress(),
            this.addressManager.getAddress(company.getId()));

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 1, this.auditManager.getAuditHeaders().length);
        assertEquals("AuditManager should be properly invoked.", 7,
            this.auditManager.getAuditHeaders()[0].getDetails().length);
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when the id is not positive, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_InvalidId() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, true);

        try {
            dao.retrieveCompany(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when the connName is invalid, CompanyDAOException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_InvalidConnectionName() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, true);
        dao = new DbCompanyDAO(connFactory, "invalid", UnitTestHelper.IDGEN_NAME, contactManager, addressManager,
                auditManager, searchBundle);

        try {
            dao.retrieveCompany(company.getId());
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when the errors happen in addressManager,
     * CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_AddressManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, true);
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                new MockAddressManager(true), auditManager, searchBundle);

        try {
            dao.retrieveCompany(company.getId());
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when the count of contacts is not correct,
     * CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_InvalidContactsCount() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, true);
        this.contactManager.removeContact(company.getContact().getId(), true);

        try {
            dao.retrieveCompany(company.getId());
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when the errors happen in contactManager,
     * CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_ContactManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, true);
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME,
                new MockContactManager(true), addressManager, auditManager, searchBundle);

        try {
            dao.retrieveCompany(company.getId());
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when the count of addresses is not correct,
     * CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_InvalidAddressesCount() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, true);
        this.addressManager.removeAddress(company.getAddress().getId(), true);

        try {
            dao.retrieveCompany(company.getId());
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompany(long id)</code> when company with the given company does not exist,
     * null will be returned.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompany_NotExistCompany() throws Exception {
        assertNull("No company should be returned.", dao.retrieveCompany(1));
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullCompany() throws Exception {
        try {
            dao.updateCompany(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company's
     * companyName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullCompanyName() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "companyName", null);

        try {
            dao.updateCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company's
     * passCode is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullPassCode() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "passCode", null);

        try {
            dao.updateCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company's
     * contact is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullContact() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "contact", null);

        try {
            dao.updateCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company's
     * address is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullAddress() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        UnitTestHelper.setPrivateField(company.getClass(), company, "address", null);

        try {
            dao.updateCompany(company, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the user is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NullUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            dao.updateCompany(company, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the user is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_EmptyUser() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            dao.updateCompany(company, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the connName
     * is invalid, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_InvalidConnectionName() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company.setId(1);
        dao = new DbCompanyDAO(connFactory, "invalid", UnitTestHelper.IDGEN_NAME, contactManager, addressManager,
                auditManager, searchBundle);

        try {
            dao.updateCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the company
     * does not exist, CompanyNotFoundException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_NotExistCompany() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company.setId(1);

        try {
            dao.updateCompany(company, user, true);
            fail("CompanyNotFoundException should be thrown.");
        } catch (CompanyNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the errors
     * happen in contactManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_ContactManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME,
                new MockContactManager(true), addressManager, auditManager, searchBundle);

        try {
            dao.updateCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the errors
     * happen in addressManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_AddressManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                new MockAddressManager(true), auditManager, searchBundle);

        try {
            dao.updateCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompany(Company company, String user, boolean doAudit)</code> when the errors
     * happen in auditManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_AuditManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                addressManager, new MockAuditManager(true), searchBundle);

        try {
            dao.updateCompany(company, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompany(Company company, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_Accuracy1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);

        // change and update it
        company.setCompanyName("updatedCompanyName");
        company.setPassCode("updatedPassCode");
        dao.updateCompany(company, user, true);

        // retrieve it and check it
        Company persisted = dao.retrieveCompany(company.getId());
        UnitTestHelper.assertEquals(company, persisted);
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompany(Company company, String user, boolean doAudit)</code>. Check the
     * whether the xxxManagers are properly invoked.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_Accuracy2() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);

        // change and update it
        Thread.sleep(1000);
        company.setCompanyName("updatedCompanyName");
        company.setPassCode("updatedPassCode");
        dao.updateCompany(company, "ModifyUser", true);

        // check the ContactManager
        assertEquals("ContactManager should be properly invoked.", 1, this.contactManager.getContacts().length);
        assertEquals("ContactManager should be properly invoked.", company.getContact(),
            this.contactManager.getContacts()[0]);
        assertEquals("ContactManager should be properly invoked.", company.getContact(),
            this.contactManager.getContact(company.getId()));

        // check the AddressManager
        assertEquals("AddressManager should be properly invoked.", 1, this.addressManager.getAddresses().length);
        assertEquals("AddressManager should be properly invoked.", company.getAddress(),
            this.addressManager.getAddresses()[0]);
        assertEquals("AddressManager should be properly invoked.", company.getAddress(),
            this.addressManager.getAddress(company.getId()));

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 1, this.auditManager.getAuditHeaders().length);
        assertEquals("AuditManager should be properly invoked.", 4,
            this.auditManager.getAuditHeaders()[0].getDetails().length);
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompany(Company company, String user, boolean doAudit)</code> when the
     * company is not changed, but the address and contact is changed.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_Accuracy3() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);

        // update it
        company.setChanged(false);
        dao.updateCompany(company, user, true);

        // retrieve it and check it
        Company persisted = dao.retrieveCompany(company.getId());
        UnitTestHelper.assertEquals(company, persisted);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 1, this.auditManager.getAuditHeaders().length);
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompany(Company company, String user, boolean doAudit)</code> when the
     * company is not changed, and the address and contact is not changed.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompany_Accuracy4() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);

        // update it
        company.setChanged(false);
        company.getAddress().setChanged(false);
        company.getContact().setChanged(false);
        dao.updateCompany(company, user, true);

        // retrieve it and check it
        Company persisted = dao.retrieveCompany(company.getId());
        UnitTestHelper.assertEquals(company, persisted);

        // check the AuditManager
        assertEquals("AuditManager should not be invoked.", 0, this.auditManager.getAuditHeaders().length);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the company is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NullCompany() throws Exception {
        try {
            dao.deleteCompany(null, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the company's
     * id is not positive, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_InvalidCompanyId() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            dao.deleteCompany(company, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the company's
     * id does not exist, CompanyNotFoundException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NotExistCompanyId() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        company.setId(1);

        try {
            dao.deleteCompany(company, true, user);
            fail("CompanyNotFoundException should be thrown.");
        } catch (CompanyNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * null and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NullUser1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);

        try {
            dao.deleteCompany(company, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * empty string and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_EmptyUser1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);

        try {
            dao.deleteCompany(company, true, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * null and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_NullUser2() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);
        dao.deleteCompany(company, false, null);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the user is
     * empty string and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_EmptyUser2() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);
        dao.deleteCompany(company, false, " ");
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the errors
     * happen in contactManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_ContactManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME,
                new MockContactManager(true, true), addressManager, auditManager, searchBundle);
        dao.createCompany(company, user, false);

        try {
            dao.deleteCompany(company, true, user);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the errors
     * happen in addressManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_AddressManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                new MockAddressManager(true, true), auditManager, searchBundle);
        dao.createCompany(company, user, false);

        try {
            dao.deleteCompany(company, true, user);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompany(Company company, boolean doAudit, String user)</code> when the errors
     * happen in auditManager, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_AuditManagerError() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME, UnitTestHelper.IDGEN_NAME, contactManager,
                addressManager, new MockAuditManager(true), searchBundle);

        try {
            dao.deleteCompany(company, true, user);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompany(Company company, boolean doAudit, String user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_Accuracy1() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);

        // delete it and check it
        dao.deleteCompany(company, true, user);

        Company persisted = dao.retrieveCompany(company.getId());
        assertNull("The company should be removed.", persisted);
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompany(Company company, boolean doAudit, String user)</code>. Check the
     * whether the xxxManagers are properly invoked.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompany_Accuracy2() throws Exception {
        Company company = UnitTestHelper.buildCompany();
        dao.createCompany(company, user, false);
        dao.deleteCompany(company, true, user);

        // check the ContactManager
        assertEquals("ContactManager should be properly invoked.", 0, this.contactManager.getContacts().length);

        // check the AddressManager
        assertEquals("AddressManager should be properly invoked.", 0, this.addressManager.getAddresses().length);

        // check the AuditManager
        assertEquals("AuditManager should be properly invoked.", 1, this.auditManager.getAuditHeaders().length);
        assertEquals("AuditManager should be properly invoked.", 7,
            this.auditManager.getAuditHeaders()[0].getDetails().length);
    }

    /**
     * <p>
     * Test the accuracy of method <code>listCompanies()</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testListCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            dao.createCompany(companies[i], user, false);
        }

        // list it and check it
        Company[] persist = dao.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>search(Filter filter)</code> when the filter is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearch_NullFilter() throws Exception {
        try {
            dao.search(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>search(Filter filter)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearch_Accuracy1() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            companies[i].setId(i + 1);
            dao.createCompany(companies[i], user, false);
        }

        // search it and check it
        CompanySearchBuilder builder = new CompanySearchBuilder();
        builder.createdByUser(user);

        Company[] persist = dao.search(builder.buildFilter());

        UnitTestHelper.assertEquals(companies, persist);
    }
    /**
     * <p>
     * Test the accuracy of method <code>search(Filter filter)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearch_Accuracy2() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            companies[i].setId(i + 1);
            dao.createCompany(companies[i], user + i, false);
        }

        // search it and check it
        CompanySearchBuilder builder = new CompanySearchBuilder();
        builder.hasCompanyName("companyName1");
        Company[] persist = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[] {companies[1]}, persist);

        builder.reset();
        builder.createdByUser("user0");
        persist = dao.search(builder.buildFilter());
        UnitTestHelper.assertEquals(new Company[] {companies[0]}, persist);

        // mix the two filter
        builder.reset();
        builder.hasCompanyName("companyName1");
        builder.createdByUser("user0");
        persist = dao.search(builder.buildFilter());

        UnitTestHelper.assertEquals(new Company[0], persist);
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullCompanies() throws Exception {
        try {
            dao.createCompanies(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullCompany() throws Exception {
        try {
            dao.createCompanies(new Company[1], user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's companiesName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullCompanyName() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "companyName", null);

        try {
            dao.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's passCode is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullPassCode() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "passCode", null);

        try {
            dao.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's contact is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullContact() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "contact", null);

        try {
            dao.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's address is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullAddress() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "address", null);

        try {
            dao.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company is duplicated, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_DuplicateCompany() throws Exception {
        Company companies1 = UnitTestHelper.buildCompany();
        Company companies2 = UnitTestHelper.buildCompany();
        companies1.setId(1);
        companies2.setId(1);

        try {
            dao.createCompanies(new Company[] {companies1, companies2}, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the user
     * is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_NullUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            dao.createCompanies(companies, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createCompanies(Company[] companies, String user, boolean doAudit)</code> when the user
     * is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_EmptyUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            dao.createCompanies(companies, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompanies(Company[] companies, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
        }

        dao.createCompanies(companies, user, false);

        // list it and check it
        Company[] persist = dao.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompanies(long[] ids)</code> when the ids is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_NullIds() throws Exception {
        try {
            dao.retrieveCompanies(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>retrieveCompanies(long[] ids)</code> when the ids contains non-positive id,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_InvalidId() throws Exception {
        try {
            dao.retrieveCompanies(new long[1]);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>retrieveCompanies(long[] ids)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[5];
        long[] ids = new long[companies.length];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            dao.createCompany(companies[i], user, false);
            ids[i] = companies[i].getId();
        }

        // list it and check it
        Company[] persist = dao.retrieveCompanies(ids);
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the accuracy of method <code>retrieveCompanies(long[] ids)</code>, when the records do not exist.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRetrieveCompanies_NotExistAccuracy() throws Exception {
        long[] ids = new long[5];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = i + 1;
        }

        // list it and check it
        Company[] persist = dao.retrieveCompanies(ids);
        UnitTestHelper.assertEquals(new Company[ids.length], persist);
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullCompanies() throws Exception {
        try {
            dao.updateCompanies(null, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullCompany() throws Exception {
        try {
            dao.updateCompanies(new Company[1], user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's companiesName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullCompanyName() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "companyName", null);

        try {
            dao.updateCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's passCode is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullPassCode() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "passCode", null);

        try {
            dao.updateCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's contact is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullContact() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "contact", null);

        try {
            dao.updateCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the
     * company's address is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullAddress() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        UnitTestHelper.setPrivateField(companies[0].getClass(), companies[0], "address", null);

        try {
            dao.updateCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the user
     * is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_NullUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            dao.updateCompanies(companies, null, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when the user
     * is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_EmptyUser() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};

        try {
            dao.updateCompanies(companies, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code> when
     * the some of the company does not exist, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_CompanyNotExist() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        companies[0].setId(1);

        try {
            dao.updateCompanies(companies, user, true);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompanies(Company[] companies, String user, boolean doAudit)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            dao.createCompany(companies[i], user, false);
        }

        // change and update it
        for (int i = 0; i < companies.length; i++) {
            companies[i].setCompanyName("updatedCompanyName" + i);
            companies[i].setPassCode("updatedCassCode" + i);
        }

        dao.updateCompanies(companies, user, false);

        // list it and check it
        Company[] persist = dao.listCompanies();
        UnitTestHelper.assertEquals(companies, persist);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the
     * companies is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NullCompanies() throws Exception {
        try {
            dao.deleteCompanies(null, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the
     * companies contains null element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NullCompany() throws Exception {
        try {
            dao.deleteCompanies(new Company[1], true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the
     * company's id is not positive, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_InvalidCompanyId() throws Exception {
        Company company = UnitTestHelper.buildCompany();

        try {
            dao.deleteCompanies(new Company[] {company}, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the
     * company's id is duplicated, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_DuplicateCompanyId() throws Exception {
        Company companies1 = UnitTestHelper.buildCompany();
        Company companies2 = UnitTestHelper.buildCompany();
        companies1.setId(1);
        companies2.setId(1);

        try {
            dao.deleteCompanies(new Company[] {companies1, companies2}, true, user);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the user
     * is null and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NullUser1() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        dao.createCompanies(companies, user, false);

        try {
            dao.deleteCompanies(companies, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the user
     * is empty string and doAudit is true, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_EmptyUser1() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        dao.createCompanies(companies, user, false);

        try {
            dao.deleteCompanies(companies, true, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the user
     * is null and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_NullUser2() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        dao.createCompanies(companies, user, false);
        dao.deleteCompanies(companies, false, null);
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when the user
     * is empty string and doAudit is false, no IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_EmptyUser2() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        dao.createCompanies(companies, user, false);
        dao.deleteCompanies(companies, false, " ");
    }

    /**
     * <p>
     * Test the method of <code>deleteCompanies(Company companies, boolean doAudit, String user)</code> when
     * the some of the company does not exist, CompanyDAOException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_CompanyNotExist() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        companies[0].setId(1);

        try {
            dao.deleteCompanies(companies, true, user);
            fail("CompanyDAOException should be thrown.");
        } catch (CompanyDAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompanies(Company companies, boolean doAudit, String user)</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompanies_Accuracy() throws Exception {
        Company[] companies = new Company[] {UnitTestHelper.buildCompany()};
        dao.createCompanies(companies, user, false);

        // delete it and check it
        dao.deleteCompanies(companies, true, user);

        Company[] persisted = dao.listCompanies();
        assertEquals("The companies should be removed.", 0, persisted.length);
    }

    /**
     * <p>
     * Test the accuracy of method <code>getAuditManager()</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetAuditManager_Accuracy() throws Exception {
        assertEquals("The auditManager should be got properly.", auditManager, dao.getAuditManager());
    }

    /**
     * <p>
     * Test the accuracy of method <code>getAddressManager()</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetAddressManager_Accuracy() throws Exception {
        assertEquals("The addressManager should be got properly.", addressManager, dao.getAddressManager());
    }

    /**
     * <p>
     * Test the accuracy of method <code>getContactManager()</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetContactManager_Accuracy() throws Exception {
        assertEquals("The contactManager should be got properly.", contactManager, dao.getContactManager());
    }

    /**
     * <p>
     * Test the accuracy of method <code>createCompaniesNonAtomically(Company[] companies, String user, boolean
     * doAudit)</code>, UnsupportedOperationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateCompaniesNonAtomically_Accuracy() throws Exception {
        try {
            dao.createCompaniesNonAtomically(null, user, false);
            fail("UnsupportedOperationException should be thrown.");
        } catch (UnsupportedOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>updateCompaniesNonAtomically(Company[] companies, String user, boolean
     * doAudit)</code>, UnsupportedOperationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateCompaniesNonAtomically_Accuracy() throws Exception {
        try {
            dao.updateCompaniesNonAtomically(null, user, false);
            fail("UnsupportedOperationException should be thrown.");
        } catch (UnsupportedOperationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the accuracy of method <code>deleteCompaniesNonAtomically(Company[] companies, boolean doAudit, String
     * user)</code>, UnsupportedOperationException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteCompaniesNonAtomically_Accuracy() throws Exception {
        try {
            dao.deleteCompaniesNonAtomically(null, false, user);
            fail("UnsupportedOperationException should be thrown.");
        } catch (UnsupportedOperationException e) {
            // good
        }
    }
}
