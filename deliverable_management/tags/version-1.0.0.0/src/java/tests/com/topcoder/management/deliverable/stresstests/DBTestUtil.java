/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.stresstests;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper for database testing.
 *
 * @author Chenhong
 * @version 1.0
 */
public final class DBTestUtil {

    /**
     * Private constructor.
     *
     */
    private DBTestUtil() {
        // empty.
    }

    /**
     * Set up the database. Some records should be first inserted for testing.
     *
     * @throws Exception
     *             to junit.
     */
    static void setUpTest() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add("stresstests/DBConnectionFactory.xml");

        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();

            statement = connection.createStatement();

            statement.addBatch("INSERT INTO project (project_id) VALUES (1)");
            statement.addBatch("INSERT INTO project (project_id) VALUES (2)");
            statement.addBatch("INSERT INTO project (project_id) VALUES (3)");

            statement.addBatch("INSERT INTO resource (resource_id) VALUES (1)");
            statement.addBatch("INSERT INTO resource (resource_id) VALUES (2)");
            statement.addBatch("INSERT INTO resource (resource_id) VALUES (3)");

            statement.addBatch("INSERT INTO upload"
                    + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, "
                    + "create_user, create_date, modify_user, modify_date) "
                    // upload_id =1 , project_id = 2, resource_i = 1, uploadType_id = 1;
                    + "VALUES (1, 2, 1, 2, 2, 'p_1', 'System', CURRENT, 'System', CURRENT)");

            statement.addBatch("INSERT INTO upload"
                    + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, "
                    + "create_user, create_date, modify_user, modify_date) "
                    // upload_id = 2, project_id = 1, resource_id = 3, uploadType_id = 2;
                    + "VALUES (2, 1, 3, 2, 2, 'p_2', 'System', CURRENT, 'System', CURRENT)");

            statement.addBatch("INSERT INTO upload"
                    + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, "
                    + "create_user, create_date, modify_user, modify_date) "
                    // upload_id = 3, project_id = 3, resource_id = 2, uploadType_id = 2
                    + "VALUES (3, 3, 2, 2, 2, 'p_3', 'System', CURRENT, 'System', CURRENT)");

            statement.addBatch("INSERT INTO upload"
                    + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter, "
                    + "create_user, create_date, modify_user, modify_date) "
                    // upload_id = 4, project_id = 1, resource_id = 3, uploadType_id = 2
                    + "VALUES (4, 1, 3, 2, 2, 'p_3', 'System', CURRENT, 'System', CURRENT)");

            statement.addBatch("INSERT INTO submission" + "(submission_id, upload_id, submission_status_id, "
                    + "create_user, create_date, modify_user, modify_date) "
                    + "VALUES (1, 1, 3, 'System', CURRENT, 'System', CURRENT)");

            statement.addBatch("INSERT INTO submission" + "(submission_id, upload_id, submission_status_id, "
                    + "create_user, create_date, modify_user, modify_date) "
                    + "VALUES (2, 2, 1, 'System', CURRENT, 'System', CURRENT)");

            statement.executeBatch();
            statement.clearBatch();
        } catch (SQLException e) {
            // ignore.
        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

    }

    /**
     * Clear the tables in use for testing.
     *
     * @throws Exception
     *             to junit.
     */
    static void tearDownTest() throws Exception {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();

            statement = connection.createStatement();
            statement.addBatch("DELETE FROM submission");
            statement.addBatch("DELETE FROM upload");

            statement.addBatch("DELETE FROM deliverable_lu");
            statement.addBatch("DELETE FROM phase_type_lu");
            statement.addBatch("DELETE FROM resource_role_lu");
            statement.addBatch("DELETE FROM resource");
            statement.addBatch("DELETE FROM project");

            statement.executeBatch();
            statement.clearBatch();

        } catch (SQLException e) {
            // ignore
        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Remove all the namespaces in the config manager instance.
     *
     * @throws Exception
     *             to junit.
     */
    static void removeAllNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Insert one Deliverable into the database.
     *
     * @throws Exception
     *             to junit.
     */
    static void insertDeliverable() throws Exception {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (1)");
            statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (1)");

            statement.addBatch("INSERT INTO deliverable_lu"
                    + "(deliverable_id, phase_type_id, resource_role_id, per_submission, required, "
                    + "name, description, create_user, create_date, modify_user, modify_date) "
                    + "VALUES (1, 1, 1, 1, 1, 'stress', 'submission deliverable', "
                    + "'System', CURRENT, 'System', CURRENT)");

            statement.executeBatch();

            statement.clearBatch();
        } catch (SQLException e) {
            // ingore.
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Create Connection.
     *
     * @return a Connection
     * @throws Exception
     *             to junit.
     */
    private static Connection getConnection() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        return factory.createConnection();
    }

    /**
     * Create a upload with no id setting.
     *
     * @param project_id
     *            the project id
     * @param resource_id
     *            the resource id
     * @return Upload instance
     */
    static Upload createUploadWithIdNoSet(long project_id, long resource_id) {
        Upload upload = new Upload();

        upload.setProject(project_id);

        UploadStatus uploadStatus = createUploadStatus();
        uploadStatus.setId(1);

        UploadType uploadType = createUploadType();
        uploadType.setId(1);

        upload.setUploadStatus(uploadStatus);
        upload.setUploadType(uploadType);
        upload.setOwner(1);
        upload.setParameter("parameter");

        upload.setCreationTimestamp(new Date());
        upload.setCreationUser("stress reviewer");

        upload.setModificationTimestamp(new Date());
        upload.setModificationUser("stress");

        return upload;
    }

    /**
     * Creates a Submission without id.
     *
     * @return a Submission
     */
    static Submission createSubmission() {

        Submission submission = new Submission();
        submission.setCreationTimestamp(new Date());
        submission.setCreationUser("stress reviewer");
        submission.setModificationTimestamp(new Date());
        submission.setModificationUser("stress");

        SubmissionStatus submissionStatus = createSubmissionStatus();
        submissionStatus.setId(1);

        Upload upload = createUploadWithIdNoSet(1, 1);
        upload.setId(1);

        submission.setSubmissionStatus(submissionStatus);
        submission.setUpload(upload);

        return submission;
    }

    /**
     * Creates a valid SubmissionStatus without an id.
     *
     * @return a SubmissionStatus
     */
    static SubmissionStatus createSubmissionStatus() {

        SubmissionStatus status = new SubmissionStatus();

        status.setName("upload status");

        status.setDescription("This is upload.");

        status.setCreationTimestamp(new Date());
        status.setCreationUser("stress reviewer");

        status.setModificationTimestamp(new Date());
        status.setModificationUser("stress reviewer");

        return status;
    }

    /**
     * Creates an UploadStatus without id
     *
     * @return a UploadStatus
     */
    static UploadStatus createUploadStatus() {

        UploadStatus status = new UploadStatus();

        status.setName("upload status");

        status.setDescription("This is upload.");

        status.setCreationTimestamp(new Date());
        status.setCreationUser("stress reviewer");

        status.setModificationTimestamp(new Date());
        status.setModificationUser("stress reviewer");

        return status;
    }

    /**
     * Creates a UploadType without id.
     *
     * @return a UploadType
     */
    static UploadType createUploadType() {

        UploadType type = new UploadType();

        type.setName("uploadType");

        type.setDescription("This is upload type");

        type.setCreationTimestamp(new Date());
        type.setCreationUser("stress reviewer");
        type.setModificationTimestamp(new Date());

        type.setModificationUser("stress reviewer");

        return type;
    }

}
