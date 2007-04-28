/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.DuplicateEntityException;
import com.topcoder.timetracker.project.MockAuditManager;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.TestHelper;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbProjectEntryDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectEntryDAOTests extends TestCase {
    /**
     * <p>
     * The constant represents the table name for testing.
     * </p>
     */
    private static final String TABLE_NAME = "project_expense";

    /**
     * <p>
     * The constant represents the project column name for testing.
     * </p>
     */
    private static final String PROJECT_COLUMN_NAME = "project_id";

    /**
     * <p>
     * The constant represents the entry column name for testing.
     * </p>
     */
    private static final String ENTRY_COLUMN_NAME = "expense_entry_id";

    /**
     * <p>
     * The DbProjectEntryDAO instance for testing.
     * </p>
     */
    private DbProjectEntryDAO entryDao;

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
        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);

        entryDao = new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME,
            TABLE_NAME);
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

        dbFactory = null;
        auditManager = null;
        entryDao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbProjectEntryDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbProjectEntryDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbProjectEntryDAO instance.", entryDao);
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFact is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnFact() throws Exception {
        try {
            new DbProjectEntryDAO(null, "tt_project", auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME, TABLE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case that when connName is null and expects success.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to create a new DbProjectEntryDAO instance.", new DbProjectEntryDAO(dbFactory, null,
            auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME, TABLE_NAME));
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
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
            new DbProjectEntryDAO(dbFactory, "  ", auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME, TABLE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
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
            new DbProjectEntryDAO(dbFactory, "tt_project", null, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME, TABLE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projIdColname is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullProjIdColname() throws Exception {
        try {
            new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, null, ENTRY_COLUMN_NAME, TABLE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projIdColname is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyProjIdColname() throws Exception {
        try {
            new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, "", ENTRY_COLUMN_NAME, TABLE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when entryIdColname is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullEntryIdColname() throws Exception {
        try {
            new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, PROJECT_COLUMN_NAME, null, TABLE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when entryIdColname is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyEntryIdColname() throws Exception {
        try {
            new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, PROJECT_COLUMN_NAME, " ", TABLE_NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when tableName is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullTableName() throws Exception {
        try {
            new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectEntryDAO#DbProjectEntryDAO(DBConnectionFactory,String,AuditManager,
     * String,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when tableName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyTableName() throws Exception {
        try {
            new DbProjectEntryDAO(dbFactory, "tt_project", auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME, "  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectEntryDAO#addEntryToProject(Project,long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject() throws Exception {
        Project project = TestHelper.createProjectUtilityImpl().getProject(1);

        entryDao.addEntryToProject(project, 2, true);

        long[][] entryIds = entryDao.retrieveEntriesForProjects(new long[] {project.getId()});
        assertEquals("Failed to add the entry to the project.", 2, entryIds[0][1]);
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_NullProject() throws Exception {
        try {
            entryDao.addEntryToProject(null, 1, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the project id is zero and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_ZeroProjectId() throws Exception {
        Project project = TestHelper.createTestingProject("Id");

        try {
            entryDao.addEntryToProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the creation date of the project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_NullCreationDate() throws Exception {
        Project project = TestHelper.createTestingProject("CreationDate");

        try {
            entryDao.addEntryToProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the creation user of the project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_NullCreationUser() throws Exception {
        Project project = TestHelper.createTestingProject("CreationUser");

        try {
            entryDao.addEntryToProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the modification date of the project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_NullModificationDate() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationDate");

        try {
            entryDao.addEntryToProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the modification user of project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_NullModificationUser() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationUser");

        try {
            entryDao.addEntryToProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the entry id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_NegativeEntryId() throws Exception {
        try {
            entryDao.addEntryToProject(TestHelper.createTestingProject(null), -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the entry id is duplicate and expects DuplicateEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_DuplicateEntryId() throws Exception {
        Project project = TestHelper.createProjectUtilityImpl().getProject(1);

        try {
            entryDao.addEntryToProject(project, 1, true);
            fail("DuplicateEntityException expected.");
        } catch (DuplicateEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_InvalidConnectionName() throws Exception {
        Project project = TestHelper.createProjectUtilityImpl().getProject(1);

        entryDao = new DbProjectEntryDAO(dbFactory, "unknown", auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME,
            TABLE_NAME);
        try {
            entryDao.addEntryToProject(project, 2, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#addEntryToProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the table name is unknown and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddEntryToProject_InvalidTableName() throws Exception {
        Project project = TestHelper.createProjectUtilityImpl().getProject(1);

        entryDao = new DbProjectEntryDAO(dbFactory, null, auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME,
            "no_table");
        try {
            entryDao.addEntryToProject(project, 2, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject() throws Exception {
        Project project = TestHelper.createProjectUtilityImpl().getProject(1);

        entryDao.removeEntryFromProject(project, 1, true);

        long[][] entryIds = entryDao.retrieveEntriesForProjects(new long[] {project.getId()});
        assertEquals("Failed to remove the entry from the project.", 0, entryIds[0].length);
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_NullProject() throws Exception {
        try {
            entryDao.removeEntryFromProject(null, 1, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the entry id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_NegativeEntryId() throws Exception {
        try {
            entryDao.removeEntryFromProject(TestHelper.createTestingProject(null), -98, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the project id is zero and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_ZeroProjectId() throws Exception {
        Project project = TestHelper.createTestingProject("Id");

        try {
            entryDao.removeEntryFromProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the creation date of the project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_NullCreationDate() throws Exception {
        Project project = TestHelper.createTestingProject("CreationDate");

        try {
            entryDao.removeEntryFromProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the creation user of the project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_NullCreationUser() throws Exception {
        Project project = TestHelper.createTestingProject("CreationUser");

        try {
            entryDao.removeEntryFromProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the modification date of the project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_NullModificationDate() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationDate");

        try {
            entryDao.removeEntryFromProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the modification user of project is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_NullModificationUser() throws Exception {
        Project project = TestHelper.createTestingProject("ModificationUser");

        try {
            entryDao.removeEntryFromProject(project, -9, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_InvalidConnectionName() throws Exception {
        Project project = TestHelper.createProjectUtilityImpl().getProject(1);

        entryDao = new DbProjectEntryDAO(dbFactory, "unknown", auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME,
            TABLE_NAME);
        try {
            entryDao.removeEntryFromProject(project, 1, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#removeEntryFromProject(Project,long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the table name is unknown and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveEntryFromProject_InvalidTableName() throws Exception {
        Project project = TestHelper.createProjectUtilityImpl().getProject(1);

        entryDao = new DbProjectEntryDAO(dbFactory, null, auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME,
            "no_table");
        try {
            entryDao.removeEntryFromProject(project, 1, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#retrieveEntriesForProjects(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectEntryDAO#retrieveEntriesForProjects(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProjects() throws Exception {
        long[][] entriesIds = entryDao.retrieveEntriesForProjects(new long[] {1, 789});

        assertEquals("Failed to retrieve the entry ids.", 2, entriesIds.length);
        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds[0].length);
        assertEquals("Failed to retrieve the entry ids.", 1, entriesIds[0][0]);
        assertEquals("Failed to retrieve the entry ids.", 0, entriesIds[1].length);
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#retrieveEntriesForProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProjects_NullProjectIds() throws Exception {
        try {
            entryDao.retrieveEntriesForProjects(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#retrieveEntriesForProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProjects_EmptyProjectIds() throws Exception {
        try {
            entryDao.retrieveEntriesForProjects(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#retrieveEntriesForProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds contains negative id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProjects_NegativeId() throws Exception {
        try {
            entryDao.retrieveEntriesForProjects(new long[] {-8});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#retrieveEntriesForProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProjects_InvalidConnectionName() throws Exception {
        entryDao = new DbProjectEntryDAO(dbFactory, "unknown", auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME,
            TABLE_NAME);
        try {
            entryDao.retrieveEntriesForProjects(new long[] {1});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectEntryDAO#retrieveEntriesForProjects(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the table name is unknown and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntriesForProjects_InvalidTableName() throws Exception {
        entryDao = new DbProjectEntryDAO(dbFactory, null, auditManager, PROJECT_COLUMN_NAME, ENTRY_COLUMN_NAME,
            "no_table");
        try {
            entryDao.retrieveEntriesForProjects(new long[] {1});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}