/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for ApplicationsManagerImpl class.
 * </p>
 *
 * @author pvmagacho
 * @version 1.1
 * @since 1.1
 */
public class ApplicationsManagerImplUnitTests extends TestCase {
    /**
     * File contains sql statement to initial database.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * This project is used in the test.
     */
    private ReviewApplication reviewApplication;

    /**
     * this ApplicationsManagerImpl is used in the test.
     */
    private ApplicationsManagerImpl applicationsManagerImpl;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ApplicationsManagerImplUnitTests.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config.xml
        ConfigManager.getInstance().add("config.xml");

        // initialize the instance that is used in the test.
        reviewApplication = new ReviewApplication();
        reviewApplication.setProjectId(1);
        reviewApplication.setReviewerId(1);
        reviewApplication.setAcceptPrimary(false);
        reviewApplication.setApplicationDate(new Date(0));

        executeSQL(CLEAR_DB_SQL);

        executeSQL(INIT_DB_SQL);

        applicationsManagerImpl = new ApplicationsManagerImpl();
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        executeSQL(CLEAR_DB_SQL);

        // remove all namespace.
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator<String> iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace(iter.next());
        }
    }

    /**
     * Accuracy test of <code>ApplicationsManagerImpl()</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplAccuracy1() throws Exception {
        new ApplicationsManagerImpl();
    }

    /**
     * Accuracy test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * use 'custom.ApplicationsManagerImpl' namespace.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplAccuracy2() throws Exception {
        new ApplicationsManagerImpl(ApplicationsManagerImpl.DEFAULT_NAMESPACE);
    }

    /**
     * Accuracy test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * use 'custom.ApplicationsManagerImpl' namespace.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplAccuracy3() throws Exception {
        new ApplicationsManagerImpl("custom.ApplicationsManagerImpl");
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * namespace is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure1() throws Exception {
        try {
            new ApplicationsManagerImpl(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * namespace is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure2() throws Exception {
        try {
            new ApplicationsManagerImpl("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' property is missed.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure3() throws Exception {
        try {
            new ApplicationsManagerImpl("invalid1.ApplicationsManagerImpl");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' is String.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure4() throws Exception {
        try {
            new ApplicationsManagerImpl("invalid2.ApplicationsManagerImpl");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * 'PersistenceClassName' is empty.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure5() throws Exception {
        try {
            new ApplicationsManagerImpl("invalid3.ApplicationsManagerImpl");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * 'PersistenceClassName' is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure6() throws Exception {
        try {
            new ApplicationsManagerImpl("invalid4.ApplicationsManagerImpl");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * 'PersistenceClassName' is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure7() throws Exception {
        try {
            new ApplicationsManagerImpl("invalid5.ApplicationsManagerImpl");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * 'PersistenceClassName' is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure8() throws Exception {
        try {
            new ApplicationsManagerImpl("invalid6.ApplicationsManagerImpl");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ApplicationsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * 'PersistenceClassName' is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationsManagerImplFailure9() throws Exception {
        try {
            new ApplicationsManagerImpl("unkown_namespace");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>create(ReviewApplication reviewApplication)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewApplicationAccuracy() throws Exception {
        ReviewApplication result = applicationsManagerImpl.create(reviewApplication);
    }

    /**
     * Failure test of <code>create(ReviewApplication reviewApplication)</code> method.
     *
     * <p>
     * reviewApplication is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewApplicationFailure1() throws Exception {
        try {
            applicationsManagerImpl.create(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>updateReviewApplication(ReviewApplication reviewApplication)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewApplicationAccuracy() throws Exception {
        ReviewApplication result = applicationsManagerImpl.create(reviewApplication);

        long id = result.getId();
        assertEquals(1, result.getProjectId());

        // Update review application
        reviewApplication.setProjectId(2);
        result = applicationsManagerImpl.update(reviewApplication);

        assertEquals(2, result.getProjectId());
        assertEquals(id, result.getId());
    }

    /**
     * Failure test of <code>updateReviewApplication(ReviewApplication reviewApplication)</code> method.
     *
     * <p>
     * project is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewApplicationFailure1() throws Exception {
        try {
            applicationsManagerImpl.update(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewApplicationAccuracy() throws Exception {
        ReviewApplication result = applicationsManagerImpl.create(reviewApplication);

        long id = result.getId();

        // Delete review application
        result = applicationsManagerImpl.retrieve(id);
        assertSame(result, reviewApplication);
    }

    /**
     * Failure test of <code>retrieve(long id)</code> method.
     *
     * <p>
     * id <= 0.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewApplicationFailure1() throws Exception {
        try {
            applicationsManagerImpl.retrieve(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>delete(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewApplicationAccuracy() throws Exception {
        ReviewApplication result = applicationsManagerImpl.create(reviewApplication);

        long id = result.getId();

        // Delete review application
        assertTrue(applicationsManagerImpl.delete(id));
    }

    /**
     * Failure test of <code>delete(long id)</code> method.
     *
     * <p>
     * id <= 0.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewApplicationFailure1() throws Exception {
        try {
            applicationsManagerImpl.delete(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getPrimaryApplications(long projectId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetPrimaryApplicationsApplicationsAccuracy() throws Exception {
        ReviewApplication[] reviewApplications = applicationsManagerImpl.getPrimaryApplications(1);
        assertEquals(1, reviewApplications.length);
        assertEquals(1, reviewApplications[0].getReviewerId());
    }

    /**
     * Failure test of <code>getPrimaryApplications(long projectId)</code> method.
     *
     * <p>
     * id <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetPrimaryApplicationsApplicationsFailure1() throws Exception {
        try {
            applicationsManagerImpl.getPrimaryApplications(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getSecondaryApplications(long projectId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSecondaryApplicationsApplicationsAccuracy() throws Exception {
        ReviewApplication[] reviewApplications = applicationsManagerImpl.getSecondaryApplications(1);
        assertEquals(1, reviewApplications.length);
        assertEquals(2, reviewApplications[0].getReviewerId());
    }

    /**
     * Failure test of <code>getSecondaryApplications(long projectId)</code> method.
     *
     * <p>
     * id <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSecondaryApplicationsFailure1() throws Exception {
        try {
            applicationsManagerImpl.getPrimaryApplications(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getAllApplications(long projectId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllApplicationsAccuracy() throws Exception {
        ReviewApplication[] reviewApplications = applicationsManagerImpl.getAllApplications(1);
        assertEquals(2, reviewApplications.length);
        assertEquals(1, reviewApplications[0].getReviewerId());
        assertEquals(2, reviewApplications[1].getReviewerId());
    }

    /**
     * Failure test of <code>getAllApplications(long projectId)</code> method.
     *
     * <p>
     * id <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllApplicationsFailure1() throws Exception {
        try {
            applicationsManagerImpl.getAllApplications(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getAllApplications(long projectId)</code> method.
     *
     * <p>
     * id <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllApplicationsFailure2() throws Exception {
        try {
            applicationsManagerImpl.getAllApplications(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file the file contains the sql statements.
     *
     * @throws Exception pass to JUnit.
     */
    private void executeSQL(String file) throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").createConnection();
        Statement statement = connection.createStatement();

        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.addBatch(line);
            }
        }

        statement.executeBatch();
    }
}
