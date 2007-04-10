/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.failuretests;

import org.jmock.MockObjectTestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.daoimplementation.DbCompanyDAO;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test for <code>{@link DbCompanyDAO}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class DbCompanyDAOFailureTests extends MockObjectTestCase {

    /**
     * <p>
     * Represents the DBConnectionFactory instance.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Represents the connName.
     * </p>
     */
    private String connName;

    /**
     * <p>
     * Represents the Id generator name.
     * </p>
     */
    private String idGen;

    /**
     * <p>
     * Represents the ContactManager instance.
     * </p>
     */
    private ContactManager contactManager;

    /**
     * <p>
     * Represents the namespace for contact manager.
     * </p>
     */
    private String contactManagerNamspace;

    /**
     * <p>
     * Represents the AddressManager instance.
     * </p>
     */
    private AddressManager addressManager;

    /**
     * <p>
     * Represents the namespace for address manager.
     * </p>
     */
    private String addressManagerNamspace;

    /**
     * <p>
     * Represents the AuditManager instance.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * Represents the namespace for audit manager.
     * </p>
     */
    private String auditManagerNamspace;

    /**
     * <p>
     * Represents the DbCompanyDAO instance.
     * </p>
     */
    private DbCompanyDAO dbCompanyDAO;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     */
    protected void setUp() throws Exception {
        super.setUp();

        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add("DBConnectionFactory_Config.xml");

        // TODO If real provided should load namespace.
        dbConnectionFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        connName = "informix";

        idGen = "DbCompanyDAO";

        contactManager = (ContactManager) mock(ContactManager.class).proxy();
        contactManagerNamspace = ContactManager.class.getName();
        addressManager = (AddressManager) mock(AddressManager.class).proxy();
        addressManagerNamspace = AddressManager.class.getName();
        auditManager = (AuditManager) mock(AuditManager.class).proxy();
        auditManagerNamspace = AuditManager.class.getName();

        dbCompanyDAO = new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManager, addressManager,
            auditManager);
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullDBConnectionFactory() throws Exception {
        try {
            new DbCompanyDAO(null, connName, idGen, contactManagerNamspace, addressManagerNamspace,
                auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullConnectionName() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, null, idGen, contactManagerNamspace, addressManagerNamspace,
                auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_EmptyConnectionName() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, "", idGen, contactManagerNamspace, addressManagerNamspace,
                auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_TrimmedConnectionName() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, " ", idGen, contactManagerNamspace, addressManagerNamspace,
                auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullIdGen() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, null, contactManagerNamspace, addressManagerNamspace,
                auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_EmptyIdGen() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, "", contactManagerNamspace, addressManagerNamspace,
                auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_TrimmedIdGen() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, " ", contactManagerNamspace, addressManagerNamspace,
                auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullContactManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, null, addressManagerNamspace, auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_EmptyContactManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, "", addressManagerNamspace, auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_TrimmedContactManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, " ", addressManagerNamspace, auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullAddressManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManagerNamspace, null, auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_EmptyAddressManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManagerNamspace, "", auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_TrimmedAddressManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManagerNamspace, " ", auditManagerNamspace);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullAuditManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManagerNamspace, addressManagerNamspace, null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_EmptyAuditManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManagerNamspace, addressManagerNamspace, "");
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, String, String, String)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_TrimmedAuditManagerNamspace() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManagerNamspace, addressManagerNamspace, " ");
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO2_NullDBConnectionFactory() throws Exception {
        try {
            new DbCompanyDAO(null, connName, idGen, contactManager, addressManager, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO2_NullConnectionName() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, null, idGen, contactManager, addressManager, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO2_EmptyConnectionName() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, "", idGen, contactManager, addressManager, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO2_TrimmedConnectionName() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, " ", idGen, contactManager, addressManager, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO2_NullIdGen() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, null, contactManager, addressManager, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO2_EmptyIdGen() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, "", contactManager, addressManager, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO2_TrimmedIdGen() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, " ", contactManager, addressManager, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullContactManager() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, null, addressManager, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullAddressManager() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManager, null, auditManager);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbCompanyDAO#DbCompanyDAO(DBConnectionFactory, String, String, ContactManager, AddressManager, AuditManager)}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbCompanyDAO1_NullAuditManager() throws Exception {
        try {
            new DbCompanyDAO(dbConnectionFactory, connName, idGen, contactManager, addressManager, null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompany_NullCompany() throws Exception {
        try {
            dbCompanyDAO.createCompany(null, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompany_InvalidCompany1() throws Exception {
        Company company = new Company();
        try {
            dbCompanyDAO.createCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompany_InvalidCompany2() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        try {
            dbCompanyDAO.createCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompany_InvalidCompany3() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        try {
            dbCompanyDAO.createCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompany_InvalidCompany4() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        try {
            dbCompanyDAO.createCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompany_NullUserName() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.createCompany(company, null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompany_EmptyUserName() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.createCompany(company, "", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompany_TrimmedUserName() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.createCompany(company, " ", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#retrieveCompany(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveCompany_ZeroId() throws Exception {
        try {
            dbCompanyDAO.retrieveCompany(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#retrieveCompany(long)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveCompany_NegativeId() throws Exception {
        try {
            dbCompanyDAO.retrieveCompany(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_NullCompany() throws Exception {
        try {
            dbCompanyDAO.updateCompany(null, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_InvalidCompany1() throws Exception {
        Company company = new Company();
        try {
            dbCompanyDAO.updateCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_InvalidCompany2() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        try {
            dbCompanyDAO.updateCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_InvalidCompany3() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        try {
            dbCompanyDAO.updateCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_InvalidCompany4() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        try {
            dbCompanyDAO.updateCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_InvalidCompany5() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompany(company, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_NullUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompany(company, null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_EmptyUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompany(company, "", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompany_TrimmedUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompany(company, " ", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_NullCompany() throws Exception {
        try {
            dbCompanyDAO.deleteCompany(null, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_InvalidCompany1() throws Exception {
        Company company = new Company();
        try {
            dbCompanyDAO.deleteCompany(company, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_InvalidCompany2() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        try {
            dbCompanyDAO.deleteCompany(company, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_InvalidCompany3() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        try {
            dbCompanyDAO.deleteCompany(company, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_InvalidCompany4() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        try {
            dbCompanyDAO.deleteCompany(company, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_InvalidCompany5() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompany(company, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_NullUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompany(company, true, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_EmptyUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompany(company, true, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompany(Company, String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteCompany_TrimmedUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompany(company, true, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#search(com.topcoder.search.builder.filter.Filter)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSearch_NullFilter() throws Exception {
        try {
            dbCompanyDAO.search(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_NullCompany() throws Exception {
        try {
            dbCompanyDAO.createCompanies(null, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_ContainsNullCompany() throws Exception {
        try {
            dbCompanyDAO.createCompanies(new Company[] {null}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_InvalidCompany1() throws Exception {
        Company company = new Company();
        try {
            dbCompanyDAO.createCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_InvalidCompany2() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        try {
            dbCompanyDAO.createCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_InvalidCompany3() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        try {
            dbCompanyDAO.createCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_InvalidCompany4() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        try {
            dbCompanyDAO.createCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_NullUserName() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.createCompanies(new Company[] {company}, null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_EmptyUserName() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.createCompanies(new Company[] {company}, "", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateCompanies_TrimmedUserName() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.createCompanies(new Company[] {company}, " ", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#retrieveCompanies(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveCompanies_NullIds() throws Exception {
        try {
            dbCompanyDAO.retrieveCompanies(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#retrieveCompanies(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveCompanies_ContainsZeroId() throws Exception {
        try {
            dbCompanyDAO.retrieveCompanies(new long[] {0});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#retrieveCompanies(long[])}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRetrieveCompanies_ContainsNegativeId() throws Exception {
        try {
            dbCompanyDAO.retrieveCompanies(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_NullCompany() throws Exception {
        try {
            dbCompanyDAO.updateCompanies(null, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_ContainsNullCompany() throws Exception {
        try {
            dbCompanyDAO.updateCompanies(new Company[] {null}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_InvalidCompany1() throws Exception {
        Company company = new Company();
        try {
            dbCompanyDAO.updateCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_InvalidCompany2() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        try {
            dbCompanyDAO.updateCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_InvalidCompany3() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        try {
            dbCompanyDAO.updateCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_InvalidCompany4() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        try {
            dbCompanyDAO.updateCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_InvalidCompany5() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompanies(new Company[] {company}, "username", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_NullUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompanies(new Company[] {company}, null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_EmptyUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompanies(new Company[] {company}, "", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCompanies_TrimmedUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompanies(new Company[] {company}, " ", false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_NullCompany() throws Exception {
        try {
            dbCompanyDAO.deleteCompanies(null, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_ContainsNullCompany() throws Exception {
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {null}, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_InvalidCompany1() throws Exception {
        Company company = new Company();
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {company}, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_InvalidCompany2() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {company}, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_InvalidCompany3() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {company}, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_InvalidCompany4() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {company}, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_InvalidCompany5() throws Exception {
        Company company = new Company();
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {company}, false, "username");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_NullUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {company}, true, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_EmptyUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {company}, true, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#deleteCompanies(Company[], String, boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testdeleteCompanies_TrimmedUserName() throws Exception {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompanies(new Company[] {company}, true, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#createCompaniesNonAtomically(Company[], String, boolean)}</code>
     * method.
     * </p>
     */
    public void testCreateCompaniesNonAtomicallyUnsupportedOperationException() {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.createCompaniesNonAtomically(new Company[] {company}, "username", true);
            fail("expect throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompaniesNonAtomically(Company[], String, boolean)}</code>
     * method.
     * </p>
     */
    public void testUpdateCompaniesNonAtomicallyUnsupportedOperationException() {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.updateCompaniesNonAtomically(new Company[] {company}, "username", true);
            fail("expect throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbCompanyDAO#updateCompaniesNonAtomically(Company[], String, boolean)}</code>
     * method.
     * </p>
     */
    public void testDeleteCompaniesNonAtomicallyUnsupportedOperationException() {
        Company company = new Company();
        company.setId(12);
        company.setCompanyName("TopCoder");
        company.setPassCode("PassCode");
        company.setAddress(new Address());
        company.setContact(new Contact());
        try {
            dbCompanyDAO.deleteCompaniesNonAtomically(new Company[] {company}, true, "username");
            fail("expect throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }
}
