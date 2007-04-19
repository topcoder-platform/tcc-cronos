/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import java.sql.Connection;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.company.daoimplementation.DbCompanyDAO;

/**
 * <p>
 * Tests functionality and error cases of <code>CompanyDAOSynchronizedWrapper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CompanyDAOSynchronizedWrapperUnitTest extends TestCase {
    /** Represents the <code>ContactManager</code> instance used for testing. */
    private MockContactManager contactManager = null;

    /** Represents the <code>AddressManager</code> instance used for testing. */
    private MockAddressManager addressManager = null;

    /** Represents the <code>AuditManager</code> instance used for testing. */
    private MockAuditManager auditManager = null;

    /** Represents the <code>CompanyDAO</code> instance used for testing. */
    private CompanyDAO dao = null;

    /** Represents the <code>CompanyDAOSynchronizedWrapper</code> instance used for testing. */
    private CompanyDAOSynchronizedWrapper wrapper = null;

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
        DBConnectionFactory connFactory = UnitTestHelper.getDBConnectionFactory();
        contactManager = new MockContactManager(false);
        addressManager = new MockAddressManager(false);
        auditManager = new MockAuditManager(false);
        dao = new DbCompanyDAO(connFactory, UnitTestHelper.CONN_NAME,
                UnitTestHelper.IDGEN_NAME, contactManager, addressManager, auditManager,
                UnitTestHelper.createSearchBundle());
        wrapper = new CompanyDAOSynchronizedWrapper(dao);
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
     * Test the constructor <code>CompanyDAOSynchronizedWrapper(CompanyDAO companyDAO)</code> when the given
     * companyDAO is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCompanyDAOSynchronizedWrapper_NullCompanyDAO() throws Exception {
        try {
            new CompanyDAOSynchronizedWrapper(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CompanyDAOSynchronizedWrapper(CompanyDAO companyDAO)</code>. No
     * exception is expected, all the fields should be set.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCompanyDAOSynchronizedWrapper_Accuracy() throws Exception {
        assertNotNull("The CompanyDAOSynchronizedWrapper instance should be created.", wrapper);
        assertEquals("The companyDAO should be assigned.", dao,
            UnitTestHelper.getPrivateField(wrapper.getClass(), wrapper, "companyDAO"));
    }

    /**
     * <p>
     * Test the accuracy of method <code>getCompanyDAO()</code>.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetCompanyDAO_Accuracy() throws Exception {
        assertEquals("The companyDAO should be got properly.", dao, wrapper.getCompanyDAO());
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
            wrapper.createCompany(null, user, true);
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
            wrapper.createCompany(company, user, true);
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
            wrapper.createCompany(company, user, true);
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
            wrapper.createCompany(company, user, true);
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
            wrapper.createCompany(company, user, true);
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
            wrapper.createCompany(company, null, true);
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
            wrapper.createCompany(company, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
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
        wrapper.createCompany(company, user, true);

        try {
            wrapper.createCompany(company, user, true);
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
        company = wrapper.createCompany(company, user, true);

        // retrieve it and check it
        Company persisted = wrapper.retrieveCompany(company.getId());
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
        company = wrapper.createCompany(company, user, true);

        // retrieve it and check it
        Company persisted = wrapper.retrieveCompany(1);
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
        company = wrapper.createCompany(company, user, true);

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
        wrapper.createCompany(company, user, true);

        try {
            wrapper.retrieveCompany(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
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
        wrapper.createCompany(company, user, true);
        this.contactManager.removeContact(company.getContact().getId(), true);

        try {
            wrapper.retrieveCompany(company.getId());
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
        wrapper.createCompany(company, user, true);
        this.addressManager.removeAddress(company.getAddress().getId(), true);

        try {
            wrapper.retrieveCompany(company.getId());
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
        assertNull("No company should be returned.", wrapper.retrieveCompany(1));
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
            wrapper.updateCompany(null, user, true);
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
            wrapper.updateCompany(company, user, true);
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
            wrapper.updateCompany(company, user, true);
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
            wrapper.updateCompany(company, user, true);
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
            wrapper.updateCompany(company, user, true);
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
            wrapper.updateCompany(company, null, true);
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
            wrapper.updateCompany(company, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
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
            wrapper.updateCompany(company, user, true);
            fail("CompanyNotFoundException should be thrown.");
        } catch (CompanyNotFoundException e) {
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
        wrapper.createCompany(company, user, false);

        // change and update it
        company.setCompanyName("updatedCompanyName");
        company.setPassCode("updatedPassCode");
        wrapper.updateCompany(company, user, true);

        // retrieve it and check it
        Company persisted = wrapper.retrieveCompany(company.getId());
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
        wrapper.createCompany(company, user, false);

        // change and update it
        Thread.sleep(1000);
        company.setCompanyName("updatedCompanyName");
        company.setPassCode("updatedPassCode");
        wrapper.updateCompany(company, "ModifyUser", true);

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
        wrapper.createCompany(company, user, false);

        // update it
        company.setChanged(false);
        wrapper.updateCompany(company, user, true);

        // retrieve it and check it
        Company persisted = wrapper.retrieveCompany(company.getId());
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
        wrapper.createCompany(company, user, false);

        // update it
        company.setChanged(false);
        company.getAddress().setChanged(false);
        company.getContact().setChanged(false);
        wrapper.updateCompany(company, user, true);

        // retrieve it and check it
        Company persisted = wrapper.retrieveCompany(company.getId());
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
            wrapper.deleteCompany(null, true, user);
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
            wrapper.deleteCompany(company, true, user);
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
            wrapper.deleteCompany(company, true, user);
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
        wrapper.createCompany(company, user, false);

        try {
            wrapper.deleteCompany(company, true, null);
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
        wrapper.createCompany(company, user, false);

        try {
            wrapper.deleteCompany(company, true, " ");
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
        wrapper.createCompany(company, user, false);
        wrapper.deleteCompany(company, false, null);
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
        wrapper.createCompany(company, user, false);
        wrapper.deleteCompany(company, false, " ");
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
        wrapper.createCompany(company, user, false);

        // delete it and check it
        wrapper.deleteCompany(company, true, user);

        Company persisted = wrapper.retrieveCompany(company.getId());
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
        wrapper.createCompany(company, user, false);
        wrapper.deleteCompany(company, true, user);

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
            wrapper.createCompany(companies[i], user, false);
        }

        // list it and check it
        Company[] persist = wrapper.listCompanies();
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
            wrapper.search(null);
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
    public void testSearch_Accuracy() throws Exception {
        Company[] companies = new Company[5];

        for (int i = 0; i < companies.length; i++) {
            companies[i] = UnitTestHelper.buildCompany();
            companies[i].setCompanyName("companyName" + i);
            companies[i].setPassCode("passCode" + i);
            companies[i].setId(i + 1);
            wrapper.createCompany(companies[i], user, false);
        }

        // search it and check it
        CompanySearchBuilder build = new CompanySearchBuilder();
        build.createdByUser(user);

        Company[] persist = wrapper.search(build.buildFilter());

        UnitTestHelper.assertEquals(companies, persist);
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
            wrapper.createCompanies(null, user, true);
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
            wrapper.createCompanies(new Company[1], user, true);
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
            wrapper.createCompanies(companies, user, true);
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
            wrapper.createCompanies(companies, user, true);
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
            wrapper.createCompanies(companies, user, true);
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
            wrapper.createCompanies(companies, user, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
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
            wrapper.createCompanies(companies, null, true);
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
            wrapper.createCompanies(companies, " ", true);
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

        wrapper.createCompanies(companies, user, false);

        // list it and check it
        Company[] persist = wrapper.listCompanies();
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
            wrapper.retrieveCompanies(null);
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
            wrapper.retrieveCompanies(new long[1]);
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
            wrapper.createCompany(companies[i], user, false);
            ids[i] = companies[i].getId();
        }

        // list it and check it
        Company[] persist = wrapper.retrieveCompanies(ids);
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
        Company[] persist = wrapper.retrieveCompanies(ids);
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
            wrapper.updateCompanies(null, user, true);
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
            wrapper.updateCompanies(new Company[1], user, true);
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
            wrapper.updateCompanies(companies, user, true);
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
            wrapper.updateCompanies(companies, user, true);
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
            wrapper.updateCompanies(companies, user, true);
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
            wrapper.updateCompanies(companies, user, true);
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
            wrapper.updateCompanies(companies, null, true);
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
            wrapper.updateCompanies(companies, " ", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
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
            wrapper.createCompany(companies[i], user, false);
        }

        // change and update it
        for (int i = 0; i < companies.length; i++) {
            companies[i].setCompanyName("updatedCompanyName" + i);
            companies[i].setPassCode("updatedCassCode" + i);
        }

        wrapper.updateCompanies(companies, user, false);

        // list it and check it
        Company[] persist = wrapper.listCompanies();
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
            wrapper.deleteCompanies(null, true, user);
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
            wrapper.deleteCompanies(new Company[1], true, user);
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
            wrapper.deleteCompanies(new Company[] {company}, true, user);
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
            wrapper.deleteCompanies(new Company[] {companies1, companies2}, true, user);
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
        wrapper.createCompanies(companies, user, false);

        try {
            wrapper.deleteCompanies(companies, true, null);
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
        wrapper.createCompanies(companies, user, false);

        try {
            wrapper.deleteCompanies(companies, true, " ");
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
        wrapper.createCompanies(companies, user, false);
        wrapper.deleteCompanies(companies, false, null);
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
        wrapper.createCompanies(companies, user, false);
        wrapper.deleteCompanies(companies, false, " ");
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
        wrapper.createCompanies(companies, user, false);

        // delete it and check it
        wrapper.deleteCompanies(companies, true, user);

        Company[] persisted = wrapper.listCompanies();
        assertEquals("The companies should be removed.", 0, persisted.length);
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
            wrapper.createCompaniesNonAtomically(null, user, false);
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
            wrapper.updateCompaniesNonAtomically(null, user, false);
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
            wrapper.deleteCompaniesNonAtomically(null, false, user);
            fail("UnsupportedOperationException should be thrown.");
        } catch (UnsupportedOperationException e) {
            // good
        }
    }
}
