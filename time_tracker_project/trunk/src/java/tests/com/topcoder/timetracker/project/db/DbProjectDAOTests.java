/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.common.CommonManager;
import com.topcoder.timetracker.common.SimpleCommonManager;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.MockAddressManager;
import com.topcoder.timetracker.project.MockAuditManager;
import com.topcoder.timetracker.project.MockContactManager;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.StringMatchType;
import com.topcoder.timetracker.project.TestHelper;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbProjectDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectDAOTests extends TestCase {
    /**
     * <p>
     * The constant represents the id generator namespace for testing.
     * </p>
     */
    private static final String ID_GENERATOR_NAME = "com.topcoder.timetracker.project.Project";

    /**
     * <p>
     * The DbProjectDAO instance for testing.
     * </p>
     */
    private DbProjectDAO projectDao;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * The ContactManager instance for testing.
     * </p>
     */
    private ContactManager contactManager;

    /**
     * <p>
     * The AddressManager instance for testing.
     * </p>
     */
    private AddressManager addressManager;

    /**
     * <p>
     * The CommonManager instance for testing.
     * </p>
     */
    private CommonManager commonManager;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.setUpDataBase();

        auditManager = new MockAuditManager();
        contactManager = new MockContactManager();
        addressManager = new MockAddressManager();
        commonManager = new SimpleCommonManager();
        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);

        projectDao = new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, contactManager, addressManager,
            commonManager, TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        auditManager = null;
        contactManager = null;
        addressManager = null;
        commonManager = null;
        dbFactory = null;
        projectDao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbProjectDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbProjectDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbProjectDAO instance.", projectDao);
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnFactory() throws Exception {
        try {
            new DbProjectDAO(null, "tt_project", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
                TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case that when connName is null and expects success.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to create a new DbProjectDAO instance.", new DbProjectDAO(dbFactory, null,
            ID_GENERATOR_NAME, contactManager, addressManager, commonManager, TestHelper.SEARCH_NAMESPACE,
            "ProjectSearchBundle", auditManager));
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyConnName() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "  ", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
                TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullIdGen() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "tt_project", null, contactManager, addressManager, commonManager,
                TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyIdGen() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "tt_project", "  ", contactManager, addressManager, commonManager,
                TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when contactManager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullContactManager() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, null, addressManager, commonManager,
                TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when addressManager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullAddressManager() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, contactManager, null, commonManager,
                TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when termManager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullTermManager() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, contactManager, addressManager, null,
                TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullSearchStrategyNamespace() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
                null, "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptySearchStrategyNamespace() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
                " ", "ProjectSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when auditor is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullAuditor() throws Exception {
        try {
            new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
                TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectDAO#DbProjectDAO(DBConnectionFactory,String,String,ContactManager,
     * AddressManager,CommonManager,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * Expects for ConfigurationException.
     * </p>
     */
    public void testCtor_UnknownSearchNamespace() {
        try {
            new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
                "HelloWorld", "ProjectSearchBundle", auditManager);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectDAO#addProjects(Project[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);

        projectDao.addProjects(new Project[] {testingProject}, true);

        Project actualProject = projectDao.getProjects(new long[] {testingProject.getId()})[0];

        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projects is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullProjects() throws Exception {
        try {
            projectDao.addProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projects is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_EmptyProjects() throws Exception {
        try {
            projectDao.addProjects(new Project[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null name and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullName() throws Exception {
        Project project = TestHelper.createTestingProject("Name");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project doesn't set the company id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_CompanyIdNotSet() throws Exception {
        Project project = TestHelper.createTestingProject("CompanyId");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project doesn't set the client id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_ClientIdNotSet() throws Exception {
        Project project = TestHelper.createTestingProject("ClientId");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null address and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullAddress() throws Exception {
        Project project = TestHelper.createTestingProject("Address");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null contact and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullContact() throws Exception {
        Project project = TestHelper.createTestingProject("Contact");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the contact type of some project is invalid and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_InvalidContactType() throws Exception {
        Contact contact = TestHelper.createTestingContact();
        contact.setId(2);
        contact.setContactType(ContactType.USER);
        Project project = TestHelper.createTestingProject(null);
        project.setContact(contact);

        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the address type of some project is invalid and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_InvalidAddressType() throws Exception {
        Address address = TestHelper.createTestingAddress();
        address.setId(2);
        address.setAddressType(AddressType.USER);
        Project project = TestHelper.createTestingProject(null);
        project.setAddress(address);

        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null payment terms and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullTerms() throws Exception {
        Project project = TestHelper.createTestingProject("PaymentTerm");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null start date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullStartDate() throws Exception {
        Project project = TestHelper.createTestingProject("StartDate");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null end date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullEndDate() throws Exception {
        Project project = TestHelper.createTestingProject("EndDate");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullCreationDate() throws Exception {
        Project project = TestHelper.createTestingProject("CreationDate");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullCreationUser() throws Exception {
        Project project = TestHelper.createTestingProject("CreationUser");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null modification date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullModificationDate() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationDate");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_NullModificationUser() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationUser");
        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_InvalidConnectionName() throws Exception {
        Project project = TestHelper.createTestingProject(null);

        projectDao = new DbProjectDAO(dbFactory, "unknown", ID_GENERATOR_NAME, contactManager, addressManager,
            commonManager, TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);

        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#addProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects_EmptyDataBase() throws Exception {
        Project project = TestHelper.createTestingProject(null);

        projectDao = new DbProjectDAO(dbFactory, "empty", ID_GENERATOR_NAME, contactManager, addressManager,
            commonManager, TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);

        try {
            projectDao.addProjects(new Project[] {project}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectDAO#updateProjects(Project[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject("Description");
        projectDao.addProjects(new Project[] {testingProject}, true);

        testingProject.setName("helloWorld");
        testingProject.setSalesTax(0.14);
        testingProject.setClientId(3);

        projectDao.updateProjects(new Project[] {testingProject}, true);

        Project actualProject = projectDao.getProjects(new long[] {testingProject.getId()})[0];
        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projects is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullProjects() throws Exception {
        try {
            projectDao.updateProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projects is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_EmptyProjects() throws Exception {
        try {
            projectDao.updateProjects(new Project[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null name and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullName() throws Exception {
        Project project = TestHelper.createTestingProject("Name");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project doesn't set the company id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_CompanyIdNotSet() throws Exception {
        Project project = TestHelper.createTestingProject("CompanyId");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project doesn't set the client id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_ClientIdNotSet() throws Exception {
        Project project = TestHelper.createTestingProject("ClientId");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null address and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullAddress() throws Exception {
        Project project = TestHelper.createTestingProject("Address");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null contact and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullContact() throws Exception {
        Project project = TestHelper.createTestingProject("Contact");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the contact type of some project is invalid and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_InvalidContactType() throws Exception {
        Contact contact = TestHelper.createTestingContact();
        contact.setId(2);
        contact.setContactType(ContactType.USER);
        Project project = TestHelper.createTestingProject(null);
        project.setContact(contact);

        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the address type of some project is invalid and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_InvalidAddressType() throws Exception {
        Address address = TestHelper.createTestingAddress();
        address.setId(2);
        address.setAddressType(AddressType.USER);
        Project project = TestHelper.createTestingProject(null);
        project.setAddress(address);

        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null payment terms and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullTerms() throws Exception {
        Project project = TestHelper.createTestingProject("PaymentTerm");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null start date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullStartDate() throws Exception {
        Project project = TestHelper.createTestingProject("StartDate");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null end date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullEndDate() throws Exception {
        Project project = TestHelper.createTestingProject("EndDate");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullCreationDate() throws Exception {
        Project project = TestHelper.createTestingProject("CreationDate");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullCreationUser() throws Exception {
        Project project = TestHelper.createTestingProject("CreationUser");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null modification date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullModificationDate() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationDate");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_NullModificationUser() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationUser");
        try {
            projectDao.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_InvalidConnectionName() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);
        projectDao.addProjects(new Project[] {testingProject}, true);

        projectDao = new DbProjectDAO(dbFactory, "unknown", ID_GENERATOR_NAME, contactManager, addressManager,
            commonManager, TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);

        try {
            projectDao.updateProjects(new Project[] {testingProject}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#updateProjects(Project[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects_EmptyDataBase() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);
        projectDao.addProjects(new Project[] {testingProject}, true);

        projectDao = new DbProjectDAO(dbFactory, "empty", ID_GENERATOR_NAME, contactManager, addressManager,
            commonManager, TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);

        try {
            projectDao.updateProjects(new Project[] {testingProject}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#removeProjects(long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectDAO#removeProjects(long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);
        projectDao.addProjects(new Project[] {testingProject}, true);

        projectDao.removeProjects(new long[] {1, testingProject.getId()}, true);

        Project[] projects = projectDao.enumerateProjects();
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId() == 1 && projects[i].isActive()) {
                fail("Failed to remove the user with id [1].");
            }

            if (projects[i].getId() == testingProject.getId() && projects[i].isActive()) {
                fail("Failed to remove the user with id [" + testingProject.getId() + "].");
            }
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#removeProjects(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjects_NullProjectIds() throws Exception {
        try {
            projectDao.removeProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#removeProjects(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjects_NegativeProjectId() throws Exception {
        try {
            projectDao.removeProjects(new long[] {-9}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#getProjects(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectDAO#getProjects(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects() throws Exception {
        Project project = projectDao.getProjects(new long[] {1})[0];

        assertEquals("The project names are not equals.", "time tracker", project.getName());
        assertEquals("The project descriptions are not equals.", "time tracker", project.getDescription());
        assertEquals("The sales taxes are not equals.", 0.35, project.getSalesTax(), 0.01);
        assertEquals("The payment terms ids are not equals.", 1, project.getTerms().getId());
        assertEquals("The addresses are not equals.", 1, project.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, project.getCompanyId());
        assertEquals("The contacts are not equals.", 1, project.getContact().getId());
        assertEquals("The creation users are not equals.", "", project.getCreationUser());
        assertEquals("The modification users are not equals.", "", project.getModificationUser());
    }

    /**
     * <p>
     * Tests DbProjectDAO#getProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects_NullProjectIds() throws Exception {
        try {
            projectDao.getProjects(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#getProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects_NegativeProjectId() throws Exception {
        try {
            projectDao.getProjects(new long[] {-9});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#searchProjects(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectDAO#searchProjects(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjects() throws Exception {
        Filter nameFilter = projectDao.getProjectFilterFactory().createNameFilter(StringMatchType.EXACT_MATCH,
            "time tracker");
        Project[] projects = projectDao.searchProjects(nameFilter);

        assertEquals("Only one project should be in the database.", 1, projects.length);

        assertEquals("The project names are not equals.", "time tracker", projects[0].getName());
        assertEquals("The project descriptions are not equals.", "time tracker", projects[0].getDescription());
        assertEquals("The sales taxes are not equals.", 0.35, projects[0].getSalesTax(), 0.01);
        assertEquals("The payment terms ids are not equals.", 1, projects[0].getTerms().getId());
        assertEquals("The addresses are not equals.", 1, projects[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, projects[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, projects[0].getContact().getId());
        assertEquals("The creation users are not equals.", "", projects[0].getCreationUser());
        assertEquals("The modification users are not equals.", "", projects[0].getModificationUser());
    }

    /**
     * <p>
     * Tests DbProjectDAO#searchProjects(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the search result is empty and verify the return array is empty.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectsForEmptyResult() throws Exception {
        Filter nameFilter = projectDao.getProjectFilterFactory().createNameFilter(StringMatchType.EXACT_MATCH,
            "new project");
        Project[] projects = projectDao.searchProjects(nameFilter);

        assertEquals("No project should be matched in the database.", 0, projects.length);
    }

    /**
     * <p>
     * Tests DbProjectDAO#searchProjects(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjects_NullFilter() throws Exception {
        try {
            projectDao.searchProjects(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#searchProjects(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjects_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            projectDao.searchProjects(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#getProjectFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectDAO#getProjectFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectFilterFactory() {
        assertNotNull("Failed to get the project filter factory.", projectDao.getProjectFilterFactory());
    }

    /**
     * <p>
     * Tests DbProjectDAO#enumerateProjects() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectDAO#enumerateProjects() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjects() throws Exception {
        Project[] projects = projectDao.enumerateProjects();

        assertEquals("Only one project should be in the database.", 1, projects.length);

        assertEquals("The project names are not equals.", "time tracker", projects[0].getName());
        assertEquals("The project descriptions are not equals.", "time tracker", projects[0].getDescription());
        assertEquals("The sales taxes are not equals.", 0.35, projects[0].getSalesTax(), 0.01);
        assertEquals("The payment terms ids are not equals.", 1, projects[0].getTerms().getId());
        assertEquals("The addresses are not equals.", 1, projects[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, projects[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, projects[0].getContact().getId());
        assertEquals("The creation users are not equals.", "", projects[0].getCreationUser());
        assertEquals("The modification users are not equals.", "", projects[0].getModificationUser());
    }

    /**
     * <p>
     * Tests DbProjectDAO#enumerateProjects() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjects_InvalidConnectionName() throws Exception {
        projectDao = new DbProjectDAO(dbFactory, "unknown", ID_GENERATOR_NAME, contactManager, addressManager,
            commonManager, TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);

        try {
            projectDao.enumerateProjects();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectDAO#enumerateProjects() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjects_EmptyDataBase() throws Exception {
        projectDao = new DbProjectDAO(dbFactory, "empty", ID_GENERATOR_NAME, contactManager, addressManager,
            commonManager, TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);

        try {
            projectDao.enumerateProjects();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}