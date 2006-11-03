/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit tests for <code>SqlDeliverablePersistenceTest</code>.
 * @author urtks
 * @version 1.0
 */
public class SqlDeliverablePersistenceTest extends TestCase {
    /**
     * Represents an instance of connection factory used in test methods.
     */
    private DBConnectionFactory connectionFactory = null;

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(SqlDeliverablePersistenceTest.class);
    }

    /**
     * Sets up the test environment. The configuration will be loaded.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        tearDown();

        ConfigManager cm = ConfigManager.getInstance();

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");

        // create a connection factory
        connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // create the connection
        Connection conn = connectionFactory.createConnection();

        Statement statement = conn.createStatement();

        statement.addBatch("INSERT INTO project (project_id) VALUES (1)");
        statement.addBatch("INSERT INTO project (project_id) VALUES (2)");
        statement.addBatch("INSERT INTO project (project_id) VALUES (3)");

        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (1)");
        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (2)");
        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (3)");

        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (1)");
        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (2)");
        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (3)");

        statement.addBatch("INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (1, 1, 1)");
        statement.addBatch("INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (2, 2, 2)");
        statement.addBatch("INSERT INTO project_phase (project_phase_id, project_id, phase_type_id) VALUES (3, 3, 3)");

        statement.addBatch("INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id)"
                + " VALUES (1, 2, 1, 1)");
        statement.addBatch("INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id)"
                + " VALUES (2, 2, 2, 2)");
        statement.addBatch("INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id)"
                + " VALUES (3, 3, 3, 3)");

        // add upload_type
        statement.addBatch("INSERT INTO upload_type_lu(upload_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Submission', 'Submission', 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO upload_type_lu(upload_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Test Case', 'Test Case', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO upload_type_lu(upload_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 'Final Fix', 'Final Fix', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO upload_type_lu(upload_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 'Review Document', 'Review Document', "
            + "'System', CURRENT, 'System', CURRENT)");

        // add upload_status
        statement.addBatch("INSERT INTO upload_status_lu(upload_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO upload_status_lu(upload_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT)");

        // add submission status
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Failed Screening', 'Failed Manual Screening', "
            + "'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 'Failed Review', 'Failed Review', "
            + "'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 'Completed Without Win', 'Completed Without Win', "
            + "'System', CURRENT, 'System', CURRENT)");
        statement.addBatch("INSERT INTO submission_status_lu"
            + "(submission_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (5, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT)");



        statement.addBatch("INSERT INTO upload"
            + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 2, 2, 2, 2, 'parameter 1', 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO upload"
            + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 3, 3, 2, 2, 'parameter 2', 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO submission"
            + "(submission_id, upload_id, submission_status_id, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 2, 3, 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO submission"
            + "(submission_id, upload_id, submission_status_id, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 1, 1, 'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO deliverable_lu"
            + "(deliverable_id, phase_type_id, resource_role_id, per_submission, required, "
            + "name, description, create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 2, 2, 1, 1, 'deliverable 1', 'per submission deliverable', "
            + "'System', CURRENT, 'System', CURRENT)");

        statement.addBatch("INSERT INTO deliverable_lu"
            + "(deliverable_id, phase_type_id, resource_role_id, per_submission, required, "
            + "name, description, create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 3, 3, 0, 0, 'deliverable 2', 'non per submission deliverable', "
            + "'System', CURRENT, 'System', CURRENT)");


        statement.executeBatch();
        statement.close();
        conn.close();
    }

    /**
     * Clean up the test environment. The configuration will be unloaded.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");

        // create a connection factory
        connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // create the connection
        Connection conn = connectionFactory.createConnection();

        Statement statement = conn.createStatement();

        // clear the tables
        statement.addBatch("DELETE FROM deliverable_lu");
        statement.addBatch("DELETE FROM submission");
        statement.addBatch("DELETE FROM upload");
        statement.addBatch("DELETE FROM submission_status_lu");
        statement.addBatch("DELETE FROM resource");
        statement.addBatch("DELETE FROM project_phase");

        statement.addBatch("DELETE FROM upload_type_lu");
        statement.addBatch("DELETE FROM upload_status_lu");

        statement.addBatch("DELETE FROM phase_type_lu");
        statement.addBatch("DELETE FROM resource_role_lu");
        statement.addBatch("DELETE FROM project");

        statement.executeBatch();
        statement.clearBatch();
        conn.close();

        it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory)</code>.
     * </p>
     * <p>
     * An instance of SqlDeliverablePersistence can be created.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructorA1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory);

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorA1() throws Exception {
        try {
            new SqlDeliverablePersistence(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("connectionFactory should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * An instance of SqlDeliverablePersistence can be created.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructorB1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * An instance of SqlDeliverablePersistence can be created. connectionName
     * is null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructorB2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory, null);

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructorB1() throws Exception {
        try {
            new SqlDeliverablePersistence(null, "informix_connection");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("connectionFactory should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>loadDeliverables(long deliverableId)</code>.
     * </p>
     * <p>
     * Check if the "per submission" deliverables are loaded correctly from the
     * database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverablesA1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(1, 2, 1);

        assertEquals("1 results", 1, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase type id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable
            .getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>loadDeliverables(long deliverableId)</code>.
     * </p>
     * <p>
     * Check if the non-"per submission" deliverables are loaded correctly from
     * the database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverablesA2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(2, 3, 1);

        assertEquals("1 results", 1, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 2, deliverable.getId());
        assertEquals("check project id", 3, deliverable.getProject());
        assertEquals("check phase type id", 3, deliverable.getPhase());
        assertEquals("check resource id", 3, deliverable.getResource());
        assertNull("check submission id", deliverable.getSubmission());
        assertEquals("check required", false, deliverable.isRequired());
        assertEquals("check name", "deliverable 2", deliverable.getName());
        assertEquals("check description", "non per submission deliverable", deliverable
            .getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());

    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>loadDeliverables(long deliverableId)</code>.
     * </p>
     * <p>
     * Check if the deliverable is loaded correctly from the database. empty
     * array is returned.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverablesA3() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(10, 1, 1);

        assertEquals("0 results", 0, deliverables.length);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>loadDeliverables(long deliverableId)</code>.
     * </p>
     * <p>
     * deliverableId is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverablesA1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverables(-1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * Check if the "per submission" deliverables are loaded correctly from the
     * database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverablesB1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[] {1, 3}, new long[] {2, 2}, new long[] {1, 1});

        assertEquals("1 results", 1, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase type id", 2, deliverable.getPhase());
        assertEquals("check resourceid", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable
            .getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * Check if the non-"per submission" deliverables are loaded correctly from
     * the database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverablesB2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[] {1, 2}, new long[] {2, 3}, new long[] {1, 1});

        assertEquals("2 results", 2, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 2, deliverable.getId());
        assertEquals("check project id", 3, deliverable.getProject());
        assertEquals("check phase type id", 3, deliverable.getPhase());
        assertEquals("check resource role id", 3, deliverable.getResource());
        assertNull("check submission id", deliverable.getSubmission());
        assertEquals("check required", false, deliverable.isRequired());
        assertEquals("check name", "deliverable 2", deliverable.getName());
        assertEquals("check description", "non per submission deliverable", deliverable
            .getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());

        deliverable = deliverables[1];
        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase type id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable
            .getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());

    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * Check if the deliverables are loaded correctly from the database.
     * deliverableIds is empty and empty array is returned.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverablesB3() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[0], new long[0], new long[0]);

        assertEquals("0 results", 0, deliverables.length);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * deliverableIds is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverablesB1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverables(null, null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>loadDeliverables(long[] deliverableIds)</code>.
     * </p>
     * <p>
     * deliverableIds contains -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverablesB2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1, -1}, new long[] {1, 1}, new long[] {1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>Deliverable loadDeliverable(long deliverableId, long submissionId)</code>.
     * </p>
     * <p>
     * Check if the per submission deliverable is loaded correctly from the
     * database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverable1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable deliverable = persistence.loadDeliverable(1, 2, 2, 2);

        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable
            .getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>Deliverable loadDeliverable(long deliverableId, long submissionId)</code>.
     * </p>
     * <p>
     * Check if the per submission deliverable is loaded correctly from the
     * database. null should be returned.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverable2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable deliverable = persistence.loadDeliverable(2, 1, 1, 1);

        assertNull("deliverable should be null", deliverable);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>Deliverable loadDeliverable(long deliverableId, long submissionId)</code>.
     * </p>
     * <p>
     * deliverableId is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverable1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverable(-1, 2, 2, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>Deliverable loadDeliverable(long deliverableId, long submissionId)</code>.
     * </p>
     * <p>
     * submissionId is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverable2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverable(1, -1, 2, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("resourceId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>loadDeliverables(long[] deliverableIds, long[] submissionIds)</code>.
     * </p>
     * <p>
     * Check if the deliverables are loaded correctly from the database.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverablesC1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[] {1, 1, 2, 2},
            new long[] {1, 2, 1, 2}, new long[] {1, 1, 1, 1});

        assertEquals("1 result", 1, deliverables.length);

        Deliverable deliverable = deliverables[0];
        assertEquals("check id", 1, deliverable.getId());
        assertEquals("check project id", 2, deliverable.getProject());
        assertEquals("check phase type id", 2, deliverable.getPhase());
        assertEquals("check resource id", 2, deliverable.getResource());
        assertEquals("check submission id", new Long(2), deliverable.getSubmission());
        assertEquals("check required", true, deliverable.isRequired());
        assertEquals("check name", "deliverable 1", deliverable.getName());
        assertEquals("check description", "per submission deliverable", deliverable
            .getDescription());
        assertEquals("check create user", "System", deliverable.getCreationUser());
        assertEquals("check modify user", "System", deliverable.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>loadDeliverables(long[] deliverableIds, long[] submissionIds)</code>.
     * </p>
     * <p>
     * Check if the deliverable is loaded correctly from the database.
     * deliverableIds and submissionIds is empty and empty array is returned.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyLoadDeliverablesC2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        Deliverable[] deliverables = persistence.loadDeliverables(new long[0], new long[0], new long[0]);

        assertEquals("0 results", 0, deliverables.length);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>loadDeliverables(long[] deliverableIds, long[] submissionIds)</code>.
     * </p>
     * <p>
     * deliverableIds is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverablesC1() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverables(null, new long[] {1}, new long[] {1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>loadDeliverables(long[] deliverableIds, long[] submissionIds)</code>.
     * </p>
     * <p>
     * submissionIds is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverablesC2() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1}, null, new long[] {1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("resourceIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>loadDeliverables(long[] deliverableIds, long[] submissionIds)</code>.
     * </p>
     * <p>
     * deliverableIds contains -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverablesC3() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1, -1}, new long[] {1, 2}, new long[] {1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("deliverableIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>loadDeliverables(long[] deliverableIds, long[] submissionIds)</code>.
     * </p>
     * <p>
     * submissionIds contains -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverablesC4() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1, 2}, new long[] {1, -1}, new long[] {1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("resourceIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>loadDeliverables(long[] deliverableIds, long[] submissionIds)</code>.
     * </p>
     * <p>
     * deliverableIds and submissionIds contain different number of items.
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureLoadDeliverablesC5() throws Exception {
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");

        try {
            persistence.loadDeliverables(new long[] {1, 2, 3}, new long[] {1, 2}, new long[] {1, 1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals(
                "deliverableIds and resourceIds should have the same number of elements.", e
                    .getMessage());
        }
    }
}
