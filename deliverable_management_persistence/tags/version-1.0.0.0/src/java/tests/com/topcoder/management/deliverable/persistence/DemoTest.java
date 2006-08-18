/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistence;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence;
import com.topcoder.util.config.ConfigManager;

/**
 * This TestCase demonstrates the usage of this component.
 * @author urtks
 * @version 1.0
 */
public class DemoTest extends TestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
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
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
            DBConnectionFactoryImpl.class.getName());

        // create the connection
        Connection conn = connectionFactory.createConnection();

        Statement statement = conn.createStatement();

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

        statement.addBatch("INSERT INTO project (project_id) VALUES (1)");
        statement.addBatch("INSERT INTO project (project_id) VALUES (2)");
        statement.addBatch("INSERT INTO project (project_id) VALUES (3)");

        statement.addBatch("INSERT INTO resource (resource_id) VALUES (1)");
        statement.addBatch("INSERT INTO resource (resource_id) VALUES (2)");
        statement.addBatch("INSERT INTO resource (resource_id) VALUES (3)");

        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (1)");
        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (2)");
        statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (3)");

        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (1)");
        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (2)");
        statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (3)");

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
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
            DBConnectionFactoryImpl.class.getName());

        // create the connection
        Connection conn = connectionFactory.createConnection();

        Statement statement = conn.createStatement();

        // clear the tables
        statement.addBatch("DELETE FROM submission");
        statement.addBatch("DELETE FROM submission_status_lu");

        statement.addBatch("DELETE FROM upload");
        statement.addBatch("DELETE FROM upload_type_lu");
        statement.addBatch("DELETE FROM upload_status_lu");

        statement.addBatch("DELETE FROM upload_status_lu");

        statement.addBatch("DELETE FROM deliverable_lu");
        statement.addBatch("DELETE FROM phase_type_lu");
        statement.addBatch("DELETE FROM resource_role_lu");
        statement.addBatch("DELETE FROM resource");
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
     * This method demonstrates the method to create a SqlUploadPersistence.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testCreateSqlUploadPersistence() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
            DBConnectionFactoryImpl.class.getName());

        // create the instance of SqlUploadPersistence class, using the default
        // connection name
        UploadPersistence persistence1 = new SqlUploadPersistence(connectionFactory);

        // or create the instance of SqlUploadPersistence class, using the given
        // connection name
        UploadPersistence persistence2 = new SqlUploadPersistence(connectionFactory,
            "informix_connection");
    }

    /**
     * This method demonstrates the method to create a
     * SqlDeliverablePersistence.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testCreateSqlDeliverablePersistence() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
            DBConnectionFactoryImpl.class.getName());

        // create the instance of SqlDeliverablePersistence class, using the
        // default connection name
        DeliverablePersistence persistence1 = new SqlDeliverablePersistence(connectionFactory);

        // or create the instance of SqlDeliverablePersistence class, using the
        // given connection name
        DeliverablePersistence persistence2 = new SqlDeliverablePersistence(connectionFactory,
            "informix_connection");
    }

    /**
     * This method demonstrates the usage of the UploadPersistence to manage all
     * the AuditedDeliverableStructure entities except Deliverable in this
     * component.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testManageEntity() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
            DBConnectionFactoryImpl.class.getName());

        // then create the instance of SqlUploadPersistence class
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory);

        // ////////
        // load the Upload object from the persistence
        Upload upload = persistence.loadUpload(1);
        // the above loading can be batched.
        Upload[] uploads = persistence.loadUploads(new long[] {1, 2, 3});

        // add a new Upload object to the persistence
        Upload upload2 = new Upload();
        upload2.setId(4);
        upload2.setProject(upload.getProject());
        upload2.setOwner(upload.getOwner());
        upload2.setUploadType(upload.getUploadType());
        upload2.setUploadStatus(upload.getUploadStatus());
        upload2.setParameter(upload.getParameter());
        upload2.setCreationUser(upload.getCreationUser());
        upload2.setCreationTimestamp(upload.getCreationTimestamp());
        upload2.setModificationUser(upload.getModificationUser());
        upload2.setModificationTimestamp(upload.getModificationTimestamp());
        persistence.addUpload(upload2);

        // update the Upload object to the persistence
        upload2.setParameter("new param");
        persistence.updateUpload(upload2);

        // finally the Upload object can be removed from the persistence
        persistence.removeUpload(upload2);

        // ////////
        // load the UploadType object from the persistence
        UploadType uploadType = persistence.loadUploadType(1);
        // the above loading can be batched.
        UploadType[] uploadTypes = persistence.loadUploadTypes(new long[] {1, 2, 3});

        // add a new UploadType object to the persistence
        UploadType uploadType2 = new UploadType();
        uploadType2.setId(10);
        uploadType2.setName(uploadType.getName());
        uploadType2.setDescription(uploadType.getDescription());
        uploadType2.setCreationUser(uploadType.getCreationUser());
        uploadType2.setCreationTimestamp(uploadType.getCreationTimestamp());
        uploadType2.setModificationUser(uploadType.getModificationUser());
        uploadType2.setModificationTimestamp(uploadType.getModificationTimestamp());
        persistence.addUploadType(uploadType2);

        // update the UploadType object to the persistence
        uploadType2.setDescription("new description");
        persistence.updateUploadType(uploadType2);

        // finally the UploadType object can be removed from the persistence
        persistence.removeUploadType(uploadType2);

        // ////////
        // load the UploadStatus object from the persistence
        UploadStatus uploadStatus = persistence.loadUploadStatus(1);
        // the above loading can be batched.
        UploadStatus[] uploadStatuses = persistence.loadUploadStatuses(new long[] {1, 2, 3});

        // add a new UploadStatus object to the persistence
        UploadStatus uploadStatus2 = new UploadStatus();
        uploadStatus2.setId(10);
        uploadStatus2.setName(uploadStatus.getName());
        uploadStatus2.setDescription(uploadStatus.getDescription());
        uploadStatus2.setCreationUser(uploadStatus.getCreationUser());
        uploadStatus2.setCreationTimestamp(uploadStatus.getCreationTimestamp());
        uploadStatus2.setModificationUser(uploadStatus.getModificationUser());
        uploadStatus2.setModificationTimestamp(uploadStatus.getModificationTimestamp());
        persistence.addUploadStatus(uploadStatus2);

        // update the UploadStatus object to the persistence
        uploadStatus2.setDescription("new description");
        persistence.updateUploadStatus(uploadStatus2);

        // finally the UploadStatus object can be removed from the persistence
        persistence.removeUploadStatus(uploadStatus2);

        // ////////
        // load the SubmissionStatus object from the persistence
        SubmissionStatus submissionStatus = persistence.loadSubmissionStatus(1);
        // the above loading can be batched.
        SubmissionStatus[] submissionStatuses = persistence.loadSubmissionStatuses(new long[] {1,
            2, 3});

        // add a new SubmissionStatus object to the persistence
        SubmissionStatus submissionStatus2 = new SubmissionStatus();
        submissionStatus2.setId(10);
        submissionStatus2.setName(submissionStatus.getName());
        submissionStatus2.setDescription(submissionStatus.getDescription());
        submissionStatus2.setCreationUser(submissionStatus.getCreationUser());
        submissionStatus2.setCreationTimestamp(submissionStatus.getCreationTimestamp());
        submissionStatus2.setModificationUser(submissionStatus.getModificationUser());
        submissionStatus2.setModificationTimestamp(submissionStatus.getModificationTimestamp());
        persistence.addSubmissionStatus(submissionStatus2);

        // update the SubmissionStatus object to the persistence
        submissionStatus2.setDescription("new description");
        persistence.updateSubmissionStatus(submissionStatus2);

        // finally the SubmissionStatus object can be removed from the
        // persistence
        persistence.removeSubmissionStatus(submissionStatus2);

        // for UploadType, UploadStatus SubmissionStatus, we can get all their
        // ids in the database
        long[] ids1 = persistence.getAllUploadTypeIds();
        long[] ids2 = persistence.getAllUploadStatusIds();
        long[] ids3 = persistence.getAllSubmissionStatusIds();
    }

    /**
     * This method demonstrates the usage of DeliverablePersistence to manage
     * Deliverable in this component.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testManageDeliverable() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
            DBConnectionFactoryImpl.class.getName());

        // then create the instance of SqlDeliverablePersistence class
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory);

        // load a "per submission" deliverable from the persistence
        Deliverable deliverable = persistence.loadDeliverable(2, 1);
        // the above loading can be batched.
        Deliverable[] deliverables1 = persistence.loadDeliverables(new long[] {2, 1}, new long[] {
            1, 2});

        // load a general deliverable from the persistence
        Deliverable[] deliverables2 = persistence.loadDeliverables(2);
        // the above loading can be batched.
        Deliverable[] deliverables3 = persistence.loadDeliverables(new long[] {2, 1});
    }
}
