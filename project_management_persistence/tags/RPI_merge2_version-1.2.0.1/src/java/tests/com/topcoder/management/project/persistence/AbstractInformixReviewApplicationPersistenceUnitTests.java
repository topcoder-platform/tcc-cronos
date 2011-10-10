/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.project.ReviewApplicationPersistenceException;
import com.topcoder.util.cache.CacheException;
import com.topcoder.util.cache.SimpleCache;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Class to hold tests for: AbstractInformixReviewApplicationPersistence.
 *
 * @author pvmagacho, TCSDEVELOPER
 * @version 1.2.1
 * @since 1.2 (Review Process Improvements Project)
 */
public class AbstractInformixReviewApplicationPersistenceUnitTests extends TestCase {
    /**
     * File contains sql statement to initial database.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * Class used to retrieve instance variable.
     */
    protected Class<?> useClass;

    /**
     * AbstractInformixReviewApplicationPersistence instance used in test.
     */
    protected AbstractInformixReviewApplicationPersistence persistence;

    /**
     * AbstractInformixReviewApplicationPersistence instance used in test.
     */
    protected AbstractInformixReviewApplicationPersistence persistenceException;

    /**
     * This project is used in the test.
     */
    private ReviewApplication reviewApplication;

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
        reviewApplication.setApplicationDate(new Date());

        executeSQL(CLEAR_DB_SQL);

        executeSQL(INIT_DB_SQL);
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
     * Accuracy test of <code>create(ReviewApplication reviewApplication)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewApplicationAccuracy() throws Exception {
        ReviewApplication result = persistence.create(reviewApplication);

        assertEquals(result, reviewApplication);

        SimpleCache cache = (SimpleCache) getField(persistence, "cache", useClass);
        assertEquals(result, (ReviewApplication) cache.get(AbstractInformixReviewApplicationPersistence.SINGLE_PREFIX
            + reviewApplication.getId()));
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
            persistence.create(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>create(ReviewApplication reviewApplication)</code> method.
     *
     * <p>
     * reviewApplication projectId is invalid (equal to Long.MAX_VALUE).
     * </p>
     *
     * <p>
     * Expect ReviewApplicationPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewApplicationFailure2() throws Exception {
        try {
            reviewApplication.setProjectId(Long.MAX_VALUE);
            persistence.create(reviewApplication);
            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>create(ReviewApplication reviewApplication)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewApplicationFailure3() throws Exception {
        try {
            persistenceException.create(reviewApplication);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Accuracy test of <code>update(ReviewApplication reviewApplication)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewApplicationAccuracy() throws Exception {
        ReviewApplication result = persistence.create(reviewApplication);

        long id = result.getId();
        assertEquals(1, result.getProjectId());

        // Update review application
        reviewApplication.setProjectId(2);
        result = persistence.update(reviewApplication);

        assertEquals(2, result.getProjectId());
        assertEquals(id, result.getId());
    }

    /**
     * Failure test of <code>update(ReviewApplication reviewApplication)</code> method.
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
            persistence.update(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>update(ReviewApplication reviewApplication)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsFailure2() throws Exception {
        try {
            reviewApplication.setId(1L);
            persistenceException.update(reviewApplication);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>update(ReviewApplication reviewApplication)</code> method.
     *
     * <p>
     * id doesn't exist in persistence.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewApplicationFailure3() throws Exception {
        try {
            // Update review application
            reviewApplication.setId(999);
            persistence.update(reviewApplication);
            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>update(ReviewApplication reviewApplication)</code> method.
     *
     * <p>
     * reviewApplication projectId is invalid (equal to Long.MAX_VALUE).
     * </p>
     *
     * <p>
     * Expect ReviewApplicationPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewApplicationFailure4() throws Exception {
        try {
            // Update review application
            reviewApplication.setId(1L);
            reviewApplication.setProjectId(Long.MAX_VALUE);
            persistence.update(reviewApplication);
            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewApplicationAccuracy1() throws Exception {
        // Retrieve review application
        ReviewApplication result = persistence.retrieve(1L);
        assertSame(1L, result.getId());
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewApplicationAccuracy2() throws Exception {
        // Retrieve review application
        ReviewApplication result = persistence.retrieve(6L);
        assertNull(result);
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method. Use cache.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewApplicationAccuracy3() throws Exception {
        ReviewApplication result = persistence.create(reviewApplication);

        long id = result.getId();

        // Retrieve review application
        result = persistence.retrieve(id);
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
            persistence.retrieve(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>retrieve(long id)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsFailure2() throws Exception {
        try {
            persistenceException.retrieve(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>retrieve(long id)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (PersistenceException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsFailure3() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE review_applications;");

            persistence.retrieve(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof PersistenceException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    statement.execute("CREATE TABLE review_applications(id DECIMAL(10,0) NOT NULL,"
                        + " reviewer_id DECIMAL(10,0) NOT NULL, project_id DECIMAL(10,0) NOT NULL,"
                        + " application_date  DATETIME YEAR TO FRACTION(3) NOT NULL,"
                        + " is_primary CHAR(1) NOT NULL, PRIMARY KEY(id));");
                }
            } finally {
                connection.close();
            }
        }
    }

    /**
     * Accuracy test of <code>delete(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewApplicationAccuracy1() throws Exception {
        ReviewApplication result = persistence.create(reviewApplication);
        persistence.getAllApplications(reviewApplication.getProjectId());

        long id = result.getId();

        SimpleCache cache = (SimpleCache) getField(persistence, "cache", useClass);
        assertNotNull(cache.get(AbstractInformixReviewApplicationPersistence.ALL_PREFIX
            + reviewApplication.getProjectId()));

        // Delete review application
        assertTrue(persistence.delete(id));

        assertNull(cache.get(AbstractInformixReviewApplicationPersistence.ALL_PREFIX
            + reviewApplication.getProjectId()));
    }

    /**
     * Accuracy test of <code>delete(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewApplicationAccuracy() throws Exception {
        // Delete review application
        assertFalse(persistence.delete(5L));
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
            persistence.delete(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>delete(long id)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (PersistenceException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewApplicationFailure2() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE review_applications;");

            persistence.delete(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof PersistenceException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    statement.execute("CREATE TABLE review_applications(id DECIMAL(10,0) NOT NULL,"
                        + " reviewer_id DECIMAL(10,0) NOT NULL, project_id DECIMAL(10,0) NOT NULL,"
                        + " application_date  DATETIME YEAR TO FRACTION(3) NOT NULL,"
                        + " is_primary CHAR(1) NOT NULL, PRIMARY KEY(id));");
                }
            } finally {
                connection.close();
            }
        }
    }

    /**
     * Accuracy test of <code>getPrimaryApplications(long projectId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetPrimaryApplicationsApplicationsAccuracy1() throws Exception {
        ReviewApplication[] reviewApplications = persistence.getPrimaryApplications(1);
        assertEquals(1, reviewApplications.length);
        assertEquals(1, reviewApplications[0].getReviewerId());
    }

    /**
     * Accuracy test of <code>getPrimaryApplications(long projectId)</code> method. Use cache.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetPrimaryApplicationsApplicationsAccuracy2() throws Exception {
        ReviewApplication[] reviewApplications = persistence.getPrimaryApplications(1);
        assertEquals(1, reviewApplications.length);
        assertEquals(1, reviewApplications[0].getReviewerId());

        reviewApplications = persistence.getPrimaryApplications(1);
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
            persistence.getPrimaryApplications(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getPrimaryApplications(long reviewerId)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetPrimaryApplicationsApplicationsFailure2() throws Exception {
        try {
            persistenceException.getPrimaryApplications(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>getPrimaryApplications(long reviewerId)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (PersistenceException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetPrimaryApplicationsApplicationsFailure3() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE review_applications;");

            persistence.getPrimaryApplications(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof PersistenceException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    statement.execute("CREATE TABLE review_applications(id DECIMAL(10,0) NOT NULL,"
                        + " reviewer_id DECIMAL(10,0) NOT NULL, project_id DECIMAL(10,0) NOT NULL,"
                        + " application_date  DATETIME YEAR TO FRACTION(3) NOT NULL,"
                        + " is_primary CHAR(1) NOT NULL, PRIMARY KEY(id));");
                }
            } finally {
                connection.close();
            }
        }
    }

    /**
     * Accuracy test of <code>getSecondaryApplications(long projectId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSecondaryApplicationsApplicationsAccuracy1() throws Exception {
        ReviewApplication[] reviewApplications = persistence.getSecondaryApplications(1);
        assertEquals(1, reviewApplications.length);
        assertEquals(2, reviewApplications[0].getReviewerId());
    }

    /**
     * Accuracy test of <code>getSecondaryApplications(long projectId)</code> method. Use cache.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSecondaryApplicationsApplicationsAccuracy2() throws Exception {
        ReviewApplication[] reviewApplications = persistence.getSecondaryApplications(1);
        assertEquals(1, reviewApplications.length);
        assertEquals(2, reviewApplications[0].getReviewerId());

        reviewApplications = persistence.getSecondaryApplications(1);
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
            persistence.getSecondaryApplications(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getSecondaryApplications(long reviewerId)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSecondaryApplicationsFailure2() throws Exception {
        try {
            persistenceException.getSecondaryApplications(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>getSecondaryApplications(long reviewerId)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (PersistenceException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSecondaryApplicationsFailure3() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE review_applications;");

            persistence.getSecondaryApplications(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof PersistenceException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    statement.execute("CREATE TABLE review_applications(id DECIMAL(10,0) NOT NULL,"
                        + " reviewer_id DECIMAL(10,0) NOT NULL, project_id DECIMAL(10,0) NOT NULL,"
                        + " application_date  DATETIME YEAR TO FRACTION(3) NOT NULL,"
                        + " is_primary CHAR(1) NOT NULL, PRIMARY KEY(id));");
                }
            } finally {
                connection.close();
            }
        }
    }

    /**
     * Accuracy test of <code>getAllApplications(long projectId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllApplicationsAccuracy1() throws Exception {
        ReviewApplication[] reviewApplications = persistence.getAllApplications(1);
        assertEquals(2, reviewApplications.length);
        assertEquals(1, reviewApplications[0].getReviewerId());
        assertEquals(2, reviewApplications[1].getReviewerId());
    }

    /**
     * Accuracy test of <code>getAllApplications(long projectId)</code> method. Use cache.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllApplicationsAccuracy2() throws Exception {
        ReviewApplication[] reviewApplications = persistence.getAllApplications(1);
        assertEquals(2, reviewApplications.length);
        assertEquals(1, reviewApplications[0].getReviewerId());
        assertEquals(2, reviewApplications[1].getReviewerId());

        reviewApplications = persistence.getAllApplications(1);
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
            persistence.getAllApplications(0);
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
            persistence.getAllApplications(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getAllApplications(long projectId)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllApplicationsFailure3() throws Exception {
        try {
            persistenceException.getAllApplications(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>getAllApplications(long projectId)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException. (PersistenceException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllApplicationsFailure4() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE review_applications;");

            persistence.getAllApplications(1L);

            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof PersistenceException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    statement.execute("CREATE TABLE review_applications(id DECIMAL(10,0) NOT NULL,"
                        + " reviewer_id DECIMAL(10,0) NOT NULL, project_id DECIMAL(10,0) NOT NULL,"
                        + " application_date  DATETIME YEAR TO FRACTION(3) NOT NULL,"
                        + " is_primary CHAR(1) NOT NULL, PRIMARY KEY(id));");
                }
            } finally {
                connection.close();
            }
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

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj the given object.
     * @param field the field name.
     * @param useClass the field owner.
     *
     * @throws Exception for JUnit.
     *
     * @return the field value.
     */
    private static Object getField(Object obj, String field, Class<?> useClass) throws Exception {
        Object value = null;

        Field declaredField = useClass.getDeclaredField(field);
        declaredField.setAccessible(true);

        value = declaredField.get(obj);

        return value;
    }
}
