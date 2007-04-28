/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

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
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager;
import com.topcoder.timetracker.entry.time.TimeEntryManager;
import com.topcoder.timetracker.project.db.DbProjectDAO;
import com.topcoder.timetracker.project.db.DbProjectEntryDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ProjectUtilityImpl.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectUtilityImplTests extends TestCase {
    /**
     * <p>
     * The constant represents the id generator namespace.
     * </p>
     */
    private static final String ID_GENERATOR_NAME = "com.topcoder.timetracker.project.Project";

    /**
     * <p>
     * The ProjectUtilityImpl instance for testing.
     * </p>
     */
    private ProjectUtilityImpl impl;

    /**
     * <p>
     * The DbProjectDAO instance for testing.
     * </p>
     */
    private DbProjectDAO dao;

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
     * The ProjectEntryDAO instance for testing.
     * </p>
     */
    private ProjectEntryDAO timeEntryDao;

    /**
     * <p>
     * The ProjectEntryDAO instance for testing.
     * </p>
     */
    private ProjectEntryDAO expenseEntryDao;

    /**
     * <p>
     * The ProjectEntryDAO instance for testing.
     * </p>
     */
    private ProjectEntryDAO fixedBillEntryDao;

    /**
     * <p>
     * The ProjectManager instance for testing.
     * </p>
     */
    private TimeEntryManager timeManager;

    /**
     * <p>
     * The ProjectManager instance for testing.
     * </p>
     */
    private FixedBillingEntryManager fixedBillingManager;

    /**
     * <p>
     * The ProjectManager instance for testing.
     * </p>
     */
    private ExpenseEntryManager expenseManager;

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
        dao = new DbProjectDAO(dbFactory, "tt_project", ID_GENERATOR_NAME, contactManager, addressManager,
            commonManager, TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);

        timeEntryDao = new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, "project_id", "time_entry_id",
            "project_time");
        expenseEntryDao = new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, "project_id",
            "expense_entry_id", "project_expense");
        fixedBillEntryDao = new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, "project_id",
            "fix_bill_entry_id", "project_fix_bill");
        timeManager = new MockTimeEntryManager();
        fixedBillingManager = new MockFixedBillingEntryManager();
        expenseManager = new MockExpenseEntryManager();

        impl = new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, fixedBillEntryDao, timeManager,
            fixedBillingManager, expenseManager);
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

        impl = null;
        expenseManager = null;
        fixedBillingManager = null;
        timeManager = null;
        fixedBillEntryDao = null;
        expenseEntryDao = null;
        timeEntryDao = null;
        auditManager = null;
        contactManager = null;
        addressManager = null;
        commonManager = null;
        dbFactory = null;
        dao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectUtilityImplTests.class);
    }

    /**
     * <p>
     * Tests ctor ProjectUtilityImpl#ProjectUtilityImpl(ProjectDAO,ProjectEntryDAO,ProjectEntryDAO,
     * ProjectEntryDAO,TimeEntryManager,FixedBillingEntryManager,ExpenseEntryManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectUtilityImpl instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new ProjectUtilityImpl instance.", impl);
    }

    /**
     * <p>
     * Tests ctor ProjectUtilityImpl#ProjectUtilityImpl(ProjectDAO,ProjectEntryDAO,ProjectEntryDAO,
     * ProjectEntryDAO,TimeEntryManager,FixedBillingEntryManager,ExpenseEntryManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullProjectDao() {
        try {
            new ProjectUtilityImpl(null, timeEntryDao, expenseEntryDao, fixedBillEntryDao, timeManager,
                fixedBillingManager, expenseManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectUtilityImpl#ProjectUtilityImpl(ProjectDAO,ProjectEntryDAO,ProjectEntryDAO,
     * ProjectEntryDAO,TimeEntryManager,FixedBillingEntryManager,ExpenseEntryManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullTimeEntryDao() {
        try {
            new ProjectUtilityImpl(dao, null, expenseEntryDao, fixedBillEntryDao, timeManager, fixedBillingManager,
                expenseManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectUtilityImpl#ProjectUtilityImpl(ProjectDAO,ProjectEntryDAO,ProjectEntryDAO,
     * ProjectEntryDAO,TimeEntryManager,FixedBillingEntryManager,ExpenseEntryManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when expenseEntryDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullExpenseEntryDao() {
        try {
            new ProjectUtilityImpl(dao, timeEntryDao, null, fixedBillEntryDao, timeManager, fixedBillingManager,
                expenseManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectUtilityImpl#ProjectUtilityImpl(ProjectDAO,ProjectEntryDAO,ProjectEntryDAO,
     * ProjectEntryDAO,TimeEntryManager,FixedBillingEntryManager,ExpenseEntryManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fixedBillEntryDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullFixedBillEntryDao() {
        try {
            new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, null, timeManager, fixedBillingManager,
                expenseManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectUtilityImpl#ProjectUtilityImpl(ProjectDAO,ProjectEntryDAO,ProjectEntryDAO,
     * ProjectEntryDAO,TimeEntryManager,FixedBillingEntryManager,ExpenseEntryManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullTimeManager() {
        try {
            new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, fixedBillEntryDao, null, fixedBillingManager,
                expenseManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectUtilityImpl#ProjectUtilityImpl(ProjectDAO,ProjectEntryDAO,ProjectEntryDAO,
     * ProjectEntryDAO,TimeEntryManager,FixedBillingEntryManager,ExpenseEntryManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when fixedBillingManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullFixedBillingManager() {
        try {
            new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, fixedBillEntryDao, timeManager, null,
                expenseManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectUtilityImpl#ProjectUtilityImpl(ProjectDAO,ProjectEntryDAO,ProjectEntryDAO,
     * ProjectEntryDAO,TimeEntryManager,FixedBillingEntryManager,ExpenseEntryManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when expenseManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullExpenseManager() {
        try {
            new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, fixedBillEntryDao, timeManager,
                fixedBillingManager, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProject(Project,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#addProject(Project,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProject() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);

        impl.addProject(testingProject, true);

        Project actualProject = impl.getProject(testingProject.getId());

        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProject(Project,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProject_NullProject() throws Exception {
        try {
            impl.addProject(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProject(Project,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProject_DataAccessException() throws Exception {
        dao = new DbProjectDAO(dbFactory, "empty", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
            TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
        impl = new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, fixedBillEntryDao, timeManager,
            fixedBillingManager, expenseManager);
        Project testingProject = TestHelper.createTestingProject(null);

        try {
            impl.addProject(testingProject, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProject(Project,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#updateProject(Project,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject() throws Exception {
        Project testingProject = TestHelper.createTestingProject("Description");
        impl.addProject(testingProject, true);

        testingProject.setName("helloWorld");
        testingProject.setSalesTax(0.14);
        testingProject.setClientId(3);

        impl.updateProject(testingProject, true);

        Project actualProject = impl.getProject(testingProject.getId());
        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProject(Project,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject_NullProject() throws Exception {
        try {
            impl.updateProject(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#removeProject(long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#removeProject(long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProject() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);
        impl.addProject(testingProject, true);

        impl.removeProject(testingProject.getId(), true);

        Project[] projects = impl.enumerateProjects();
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId() == testingProject.getId() && projects[i].isActive()) {
                fail("Failed to remove the user with id [" + testingProject.getId() + "].");
            }
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#getProject(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#getProject(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProject() throws Exception {
        Project project = impl.getProject(1);

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
     * Tests ProjectUtilityImpl#addEntryToProject(long,long,EntryType,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#addEntryToProject(long,long,EntryType,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject() throws Exception {
        impl.addEntryToProject(1, 2, EntryType.EXPENSE_ENTRY, true);

        long[] entryIds = impl.retrieveEntriesForProject(1, EntryType.EXPENSE_ENTRY);
        assertEquals("Failed to add the entry to the project.", 2, entryIds[1]);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addEntryToProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_NullType() throws Exception {
        try {
            impl.addEntryToProject(1, 2, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addEntryToProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the entity id is unknown and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_UnknownEntityId() throws Exception {
        try {
            impl.addEntryToProject(1, 5555, EntryType.EXPENSE_ENTRY, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addEntryToProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the entity id is unknown and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_UnknownProjectId() throws Exception {
        try {
            impl.addEntryToProject(456, 2, EntryType.EXPENSE_ENTRY, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addEntryToProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the company ids of the project and the entity are not the same
     * and expects InvalidCompanyException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_InvalidCompanyException() throws Exception {
        Project project = TestHelper.createTestingProject(null);
        project.setCompanyId(3);

        impl.addProject(project, false);

        try {
            impl.addEntryToProject(project.getId(), 1, EntryType.EXPENSE_ENTRY, true);
            fail("InvalidCompanyException expected.");
        } catch (InvalidCompanyException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#removeEntryFromProject(long,long,EntryType,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#removeEntryFromProject(long,long,EntryType,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject() throws Exception {
        impl.removeEntryFromProject(1, 1, EntryType.EXPENSE_ENTRY, false);
        impl.removeEntryFromProject(1, 1, EntryType.FIXED_BILLING_ENTRY, false);
        impl.removeEntryFromProject(1, 1, EntryType.TIME_ENTRY, false);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#removeEntryFromProject(long,long,EntryType,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_NullType() throws Exception {
        try {
            impl.removeEntryFromProject(1, 1, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#retrieveEntriesForProject(long,EntryType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#retrieveEntriesForProject(long,EntryType) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProject() throws Exception {
        long[] entriesIds = impl.retrieveEntriesForProject(1, EntryType.EXPENSE_ENTRY);

        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds.length);
        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds[0]);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#retrieveEntriesForProject(long,EntryType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProject_NullType() throws Exception {
        try {
            impl.retrieveEntriesForProject(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#retrieveEntriesForClient(long,EntryType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#retrieveEntriesForClient(long,EntryType) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForClient() throws Exception {
        long[] entriesIds = impl.retrieveEntriesForClient(1, EntryType.EXPENSE_ENTRY);

        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds.length);
        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds[0]);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#retrieveEntriesForClient(long,EntryType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForClient_NullType() throws Exception {
        try {
            impl.retrieveEntriesForClient(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#addProjects(Project[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);

        impl.addProjects(new Project[] {testingProject}, true);

        Project actualProject = impl.getProjects(new long[] {testingProject.getId()})[0];

        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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
            impl.addProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#addProjects(Project[],boolean) for failure.
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

        dao = new DbProjectDAO(dbFactory, "empty", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
            TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
        impl = new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, fixedBillEntryDao, timeManager,
            fixedBillingManager, expenseManager);

        try {
            impl.addProjects(new Project[] {project}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#updateProjects(Project[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject("Description");
        impl.addProjects(new Project[] {testingProject}, true);

        testingProject.setName("helloWorld");
        testingProject.setSalesTax(0.14);
        testingProject.setClientId(3);

        impl.updateProjects(new Project[] {testingProject}, true);

        Project actualProject = impl.getProjects(new long[] {testingProject.getId()})[0];
        TestHelper.assertProjectEquals(testingProject, actualProject);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
            impl.updateProjects(new Project[] {project}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#updateProjects(Project[],boolean) for failure.
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
        impl.addProjects(new Project[] {testingProject}, true);

        dao = new DbProjectDAO(dbFactory, "empty", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
            TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
        impl = new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, fixedBillEntryDao, timeManager,
            fixedBillingManager, expenseManager);

        try {
            impl.updateProjects(new Project[] {testingProject}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#removeProjects(long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#removeProjects(long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjects() throws Exception {
        Project testingProject = TestHelper.createTestingProject(null);
        impl.addProjects(new Project[] {testingProject}, true);

        impl.removeProjects(new long[] {1, testingProject.getId()}, true);

        Project[] projects = impl.enumerateProjects();
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
     * Tests ProjectUtilityImpl#removeProjects(long[],boolean) for failure.
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
            impl.removeProjects(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#removeProjects(long[],boolean) for failure.
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
            impl.removeProjects(new long[] {-9}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#getProjects(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#getProjects(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects() throws Exception {
        Project project = impl.getProjects(new long[] {1})[0];

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
     * Tests ProjectUtilityImpl#getProjects(long[]) for failure.
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
            impl.getProjects(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#getProjects(long[]) for failure.
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
            impl.getProjects(new long[] {-9});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#searchProjects(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#searchProjects(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjects() throws Exception {
        Filter nameFilter = impl.getProjectFilterFactory().createNameFilter(StringMatchType.EXACT_MATCH, "time tracker");
        Project[] projects = impl.searchProjects(nameFilter);

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
     * Tests ProjectUtilityImpl#searchProjects(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the search result is empty and verify the return array is empty.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectsForEmptyResult() throws Exception {
        Filter nameFilter = impl.getProjectFilterFactory().createNameFilter(StringMatchType.EXACT_MATCH, "new project");
        Project[] projects = impl.searchProjects(nameFilter);

        assertEquals("No project should be matched in the database.", 0, projects.length);
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#searchProjects(Filter) for failure.
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
            impl.searchProjects(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#searchProjects(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjects_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            impl.searchProjects(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#getProjectFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#getProjectFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectFilterFactory() {
        assertNotNull("Failed to get the project filter factory.", impl.getProjectFilterFactory());
    }

    /**
     * <p>
     * Tests ProjectUtilityImpl#enumerateProjects() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectUtilityImpl#enumerateProjects() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjects() throws Exception {
        Project[] projects = impl.enumerateProjects();

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
     * Tests ProjectUtilityImpl#enumerateProjects() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjects_EmptyDataBase() throws Exception {
        dao = new DbProjectDAO(dbFactory, "empty", ID_GENERATOR_NAME, contactManager, addressManager, commonManager,
            TestHelper.SEARCH_NAMESPACE, "ProjectSearchBundle", auditManager);
        impl = new ProjectUtilityImpl(dao, timeEntryDao, expenseEntryDao, fixedBillEntryDao, timeManager,
            fixedBillingManager, expenseManager);

        try {
            impl.enumerateProjects();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}