/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.failuretests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.persistence.InformixProjectPersistence;
import com.topcoder.management.project.persistence.failuretests.mock.MockConnectionProducer;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionException;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * <p>A failure test for {@link InformixProjectPersistence} class. Tests the class for proper handling of invalid input
 * data.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class InformixProjectPersistenceTest extends TestCase {

    /**
     * <p>An instance of {@link InformixProjectPersistence} which is tested. This instance is initialized in {@link
     * #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private InformixProjectPersistence testedInstance = null;

    /**
     * <p>A <code>Connection</code> used by this test case for accesing the target database.</p>
     */
    private Connection connection = null;

    /**
     * <p>Gets the test suite for {@link InformixProjectPersistence} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link InformixProjectPersistence} class.
     */
    public static Test suite() {
        return new TestSuite(InformixProjectPersistenceTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("test_files/failure/config.xml"));

        DBConnectionFactory connectionFactory
            = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        this.connection = connectionFactory.createConnection("informix");
        clearDatabaseTables();
        insertData();

        this.testedInstance = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
        MockConnectionProducer.releaseState();
        MockConnectionProducer.throwGlobalException(new DBConnectionException("SQL error imitated by failure test"));
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        this.testedInstance = null;
        this.connection.close();
        ConfigHelper.releaseNamespaces();
        MockConnectionProducer.releaseState();
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>namespace</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testConstructor_String_namespace_null() {
        try {
            new InformixProjectPersistence(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_String_namespace_ZERO_LENGTH_STRING() {
        try {
            new InformixProjectPersistence(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_String_namespace_WHITESPACE_ONLY_STRING() {
        try {
            new InformixProjectPersistence(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#createProject(Project,String)} method for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>project</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testCreateProject_Project_String_project_null() {
        try {
            this.testedInstance.createProject(null, TestDataFactory.OPERATOR);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#createProject(Project,String)} method for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getProjectWithInvalidCategory()} as <code>project</code> and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testCreateProject_Project_String_project_PROJECT_WITH_INVALID_CATEGORY() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProjectWithInvalidCategory(),
                                              TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#createProject(Project,String)} method for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getProjectWithInvalidStatus} as <code>project</code> and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testCreateProject_Project_String_project_PROJECT_WITH_INVALID_STATUS() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProjectWithInvalidStatus(),
                                              TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#createProject(Project,String)} method for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>operator</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testCreateProject_Project_String_operator_null() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProject(), null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#createProject(Project,String)} method for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>operator</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testCreateProject_Project_String_operator_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProject(), TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#createProject(Project,String)} method for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>operator</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testCreateProject_Project_String_operator_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProject(), TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project,String,String)} method for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>project</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testUpdateProject_Project_String_String_project_null() {
        try {
            this.testedInstance.updateProject(null, TestDataFactory.REASON, TestDataFactory.OPERATOR);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project,String,String)} method for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getProjectWithZeroId} as <code>project</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testUpdateProject_Project_String_String_project_PROJECT_WITH_ZERO_ID() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProjectWithZeroId(),
                                              TestDataFactory.REASON, TestDataFactory.OPERATOR);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project,String,String)} method for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getProjectWithInvalidCategory()} as <code>project</code> and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testUpdateProject_Project_String_String_project_PROJECT_WITH_INVALID_CATEGORY() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProjectWithInvalidCategory(),
                                              TestDataFactory.REASON, TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project,String,String)} method for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getProjectWithInvalidStatus()} as <code>project</code> and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testUpdateProject_Project_String_String_project_PROJECT_WITH_INVALID_STATUS() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProjectWithInvalidStatus(),
                                              TestDataFactory.REASON, TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project,String,String)} method for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>reason</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testUpdateProject_Project_String_String_reason_null() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProject(), null, TestDataFactory.OPERATOR);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project,String,String)} method for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>operator</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testUpdateProject_Project_String_String_operator_null() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProject(), TestDataFactory.REASON, null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project,String,String)} method for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>operator</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testUpdateProject_Project_String_String_operator_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProject(), TestDataFactory.REASON,
                                              TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project,String,String)} method for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>operator</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testUpdateProject_Project_String_String_operator_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProject(), TestDataFactory.REASON,
                                              TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getProject(long)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link 0} as <code>id</code> and expects the <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetProject_long_id_0() {
        try {
            this.testedInstance.getProject(0);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getProject(long)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link -10} as <code>id</code> and expects the <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetProject_long_id_NegativeId() {
        try {
            this.testedInstance.getProject(-10);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getProjects(long[])} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>ids</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testGetProjects_long_ids_null() {
        try {
            this.testedInstance.getProjects(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getProjects(long[])} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#EMPTY_ID_ARRAY} as <code>ids</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetProjects_long_ids_EMPTY_ID_ARRAY() {
        try {
            this.testedInstance.getProjects(TestDataFactory.EMPTY_ID_ARRAY);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getProjects(long[])} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NEGATIVE_ID_ARRAY} as <code>ids</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetProjects_long_ids_NEGATIVE_ID_ARRAY() {
        try {
            this.testedInstance.getProjects(TestDataFactory.NEGATIVE_ID_ARRAY);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getProjects(long[])} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_ID_ARRAY} as <code>ids</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetProjects_long_ids_ZERO_ID_ARRAY() {
        try {
            this.testedInstance.getProjects(TestDataFactory.ZERO_ID_ARRAY);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper behavior if the configuration is invalid.</p>
     *
     * <p>Removes the <code>ConnectionFactoryNS</code> configuration property from the configuration namespace and
     * expects the <code>PersistenceException</code> to be thrown.</p>
     */
    public void testConstructor_MissingRequiredProperty_ConnectionFactoryNS() {
        String[] values = ConfigHelper.removeProperty("com.topcoder.management.project.persistence.failuretests",
                                                      "ConnectionFactoryNS");
        try {
            new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ConnectionFactoryNS", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper behavior if the configuration is invalid.</p>
     *
     * <p>Replaces the <code>ConnectionFactoryNS</code> configuration property with invalid value and expects the
     * <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testConstructor_InvalidProperty_ConnectionFactoryNS() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ConnectionFactoryNS", "UnknownNamespace");
        try {
            new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ConnectionFactoryNS", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper behavior if the configuration is invalid.</p>
     *
     * <p>Replaces the <code>ProjectIdSequenceName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testConstructor_InvalidProperty_ProjectIdSequenceName() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ProjectIdSequenceName", "UnknownSequence");
        try {
            new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ProjectIdSequenceName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper behavior if the configuration is invalid.</p>
     *
     * <p>Replaces the <code>ProjectAuditIdSequenceName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testConstructor_InvalidProperty_ProjectAuditIdSequenceName() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ProjectAuditIdSequenceName", "UnknownSequence");
        try {
            new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ProjectAuditIdSequenceName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getAllProjectCategories()} method for proper
     * behavior if the SQL error occurs while communicating to DB.</p>
     *
     * <p>Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testGetAllProjectCategories_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ConnectionName", "invalid");
        try {
            this.testedInstance = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            this.testedInstance.getAllProjectCategories();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ConnectionName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getAllProjectPropertyTypes()} method for proper
     * behavior if the SQL error occurs while communicating to DB.</p>
     *
     * <p>Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testGetAllProjectPropertyTypes_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ConnectionName", "invalid");
        try {
            this.testedInstance = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            this.testedInstance.getAllProjectPropertyTypes();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ConnectionName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getAllProjectStatuses()} method for proper
     * behavior if the SQL error occurs while communicating to DB.</p>
     *
     * <p>Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testGetAllProjectStatuses_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ConnectionName", "invalid");
        try {
            this.testedInstance = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            this.testedInstance.getAllProjectStatuses();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ConnectionName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#getAllProjectTypes()} method for proper
     * behavior if the SQL error occurs while communicating to DB.</p>
     *
     * <p>Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testGetAllProjectTypes_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ConnectionName", "invalid");
        try {
            this.testedInstance = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            this.testedInstance.getAllProjectTypes();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ConnectionName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project, String, String)} method for
     * proper behavior if the SQL error occurs while communicating to DB.</p>
     *
     * <p>Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testUpdateProject_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ConnectionName", "invalid");
        try {
            this.testedInstance = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            this.testedInstance.updateProject(TestDataFactory.getProject(), TestDataFactory.OPERATOR,
                                              TestDataFactory.REASON);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ConnectionName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link InformixProjectPersistence#createProject(Project, String)} method for
     * proper behavior if the SQL error occurs while communicating to DB.</p>
     *
     * <p>Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.</p>
     */
    public void testCreateProject_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                                                   "ConnectionName", "invalid");
        try {
            this.testedInstance = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            this.testedInstance.createProject(TestDataFactory.getProject(), TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                                         "ConnectionName", values);
        }
    }

    /**
     * <p>Removes all data from the database tables which are affected by this test case.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void clearDatabaseTables() throws SQLException {
        String[] tables = new String[] {"project_audit", "project_info", "project", "project_category_lu",
            "project_type_lu", "project_info_type_lu", "project_status_lu"};

        PreparedStatement stmt = null;
        try {
            for (int i = 0; i < tables.length; i++) {
                stmt = this.connection.prepareStatement("DELETE FROM " + tables[i]);
                stmt.executeUpdate();
                stmt.close();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Populates the database with sample data.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void insertData() throws SQLException {
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            stmt.executeUpdate("INSERT INTO project_info_type_lu (project_info_type_id, name, description, "
                               + "create_user, create_date, modify_user, modify_date) "
                               + "VALUES (1, 'property_type', 'property_description', 'operator', CURRENT, "
                               + "'operator', CURRENT)");
            stmt.executeUpdate("INSERT INTO project_status_lu (project_status_id, name, description, create_user, "
                               + "create_date, modify_user, modify_date) VALUES (1, 'project_status', "
                               + "'status_description', 'operator', CURRENT, 'operator', CURRENT)");
            stmt.executeUpdate("INSERT INTO project_type_lu (project_type_id, name, description, create_user, "
                               + "create_date, modify_user, modify_date) VALUES (1, 'project_type', "
                               + "'type_description', 'operator', CURRENT, 'operator', CURRENT)");
            stmt.executeUpdate("INSERT INTO project_category_lu(project_category_id, project_type_id, name, "
                               + "description, create_user, create_date, modify_user, modify_date) "
                               + "VALUES (1, 1, 'category', 'description', 'operator', CURRENT, 'operator',"
                               + "CURRENT)");
            stmt.executeUpdate("INSERT INTO project (project_id, project_status_id, project_category_id, create_user, "
                               + "create_date, modify_user, modify_date) VALUES (1, 1, 1, 'operator', CURRENT, "
                               + "'operator', CURRENT)");
            stmt.close();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
