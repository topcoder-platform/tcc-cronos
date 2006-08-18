/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.DeliverableHelper;
import com.topcoder.management.deliverable.DeliverableTestHelper;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;

/**
 * NOTE: THIS IS A MOCK CLASS FOR TEST.
 * <p>
 * The SqlUploadPersistence class implements the UploadPersistence interface, in order to persist to
 * the database structure given in the deliverable_management.sql script. This class does not cache
 * a Connection to the database. Instead a new Connection is used on every method call. Most methods
 * in this class will just create and execute a single PreparedStatement. However, some of the
 * methods will need to execute several PreparedStatements in order to accomplish their tasks.
 * </p>
 * <p>
 * This class is immutable and thread-safe in the sense that multiple threads can not corrupt its
 * internal data structures. However, the results if used from multiple threads can be unpredictable
 * as the database is changed from different threads. This can equally well occur when the component
 * is used on multiple machines or multiple instances are used, so this is not a thread-safety
 * concern.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0
 */
public class SqlUploadPersistence implements UploadPersistence {
    /** Represents the name of implemented method last called. */
    private static String lastCalled = "not set";

    /**
     * connectionName: The name of the connection producer to use when a connection to the database
     * is retrieved from the DBConnectionFactory. This field is immutable and can be null or
     * non-null. When non-null, no restrictions are applied to the field. When this field is null,
     * the createConnection() method is used to get a connection. When it is non-null, the
     * createConnection(String) method is used to get a connection. This field is not exposed by
     * this class, and is used whenever a connection to the database is needed (i.e. in every
     * method).
     */
    private final String connectionName;

    /**
     * connectionFactory: The connection factory to use when a connection to the database is needed.
     * This field is immutable and must be non-null. This field is not exposed by this class and is
     * used whenever a connection to the database is needed (i.e. in every method).
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * Upload instance for test.
     */
    private Upload upload = null;

    /**
     * UploadStatus instance for test.
     */
    private UploadStatus uploadStatus = null;

    /**
     * Submission instance for test.
     */
    private Submission submission = null;

    /**
     * SubmissionStatus instance for test.
     */
    private SubmissionStatus submissionStatus = null;

    /**
     * UploadType instance for test.
     */
    private UploadType uploadType = null;

    /**
     * SqlUploadPersistence constructor: Creates a new SqlUploadPersistence. The connectionName
     * field is set to null.
     *
     * @param connectionFactory The connection factory to use for getting connections to the
     *            database.
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public SqlUploadPersistence(DBConnectionFactory connectionFactory) {
        this.connectionFactory = null;
        this.connectionName = null;
    }

    /**
     * SqlUploadPersistence constructor: Creates a new SqlUploadPersistence. All fields are set to
     * the given values.
     *
     * @param connectionFactory The connection factory to use for getting connections to the
     *            database.
     * @param connectionName The name of the connection to use. Can be null.
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public SqlUploadPersistence(DBConnectionFactory connectionFactory, String connectionName) {
        this.connectionFactory = null;
        this.connectionName = null;
    }

    /**
     * <p>
     * addUploadType: Adds the given uploadType to the persistence. The id of the upload type must
     * already be assigned, as must all the other fields needed for persistence.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_type_lu table INSERT statement
     * in CS section 1.3.1.1 Close the database connection
     * </p>
     *
     * @param uploadType The upload type to add
     * @throws IllegalArgumentException If uploadType is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addUploadType(UploadType uploadType) throws UploadPersistenceException {
        updateUploadType(uploadType);
    }

    /**
     * <p>
     * removeUploadType: Removes the given upload type (by id) from the persistence. The id of the
     * upload type can not be UNSET_ID, but the other fields do not matter.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_type_lu table DELETE statement
     * in CS section 1.3.1.2 Close the database connection
     * </p>
     *
     * @param uploadType The upload type to remove
     * @throws IllegalArgumentException If uploadType is null
     * @throws IllegalArgumentException If the id is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUploadType(UploadType uploadType) throws UploadPersistenceException {
        updateUploadType(uploadType);
    }

    /**
     * <p>
     * updateUploadType: Updates the given upload type in the persistence. The id of the uploadType
     * can not be UNSET_ID, and all other fields needed for persistence must also be assigned.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_type_lu table UPDATE statement
     * in CS section 1.3.1.3 Close the database connection
     * </p>
     *
     * @param uploadType The upload type to update
     * @throws IllegalArgumentException If uploadType is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateUploadType(UploadType uploadType) throws UploadPersistenceException {
        DeliverableHelper.checkObjectNotNull(uploadType, "uploadType");
        if (!uploadType.isValidToPersist()) {
            throw new IllegalArgumentException("uploadType is not valid to persist");
        }
        this.uploadType = uploadType;
    }

    /**
     * <p>
     * loadUploadType: Loads the UploadType with the given id from persistence. Returns null if
     * there is no UploadType with the given id.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_type_lu table SELECT statement
     * in CS section 1.3.1.4 If no rows are selected, return null Create a new UploadType and copy
     * the data from the row of the result set into its fields Close the database connection return
     * the created UploadType
     * </p>
     *
     * @return The loaded UploadType or null
     * @param uploadTypeId The id of the item to retrieve
     * @throws IllegalArgumentException if uploadTypeId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public UploadType loadUploadType(long uploadTypeId) throws UploadPersistenceException {
        DeliverableHelper.checkGreaterThanZero(uploadTypeId, "uploadId");
        return new UploadType(uploadTypeId);
    }

    /**
     * <p>
     * getAllUploadTypeIds: Gets the ids of all upload types in the persistence. The individual
     * upload types can then be loaded with the loadUploadType method.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_type_lu table SELECT statment
     * in CS section 1.3.1.21 Create return array with number of items = number of rows in result
     * set For each row in the result set, get the id from the result set and put it in the array
     * Close the database connection return the array of ids
     * </p>
     *
     * @return The ids of all upload types
     * @throws UploadPersistenceException If there is an error reading the persistence store
     */
    public long[] getAllUploadTypeIds() throws UploadPersistenceException {
        return new long[] {1, 1};
    }

    /**
     * <p>
     * addUploadStatus: Adds the given uploadStatus to the persistence. The id of the upload status
     * must already be assigned, as must all the other fields needed for persistence.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_status_lu table INSERT
     * statement in CS section 1.3.1.5 Close the database connection
     * </p>
     *
     * @param uploadStatus The upload status to add
     * @throws IllegalArgumentException If uploadStatus is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        updateUploadStatus(uploadStatus);
    }

    /**
     * <p>
     * removeUploadStatus: Removes the given upload status (by id) from the persistence. The id of
     * the upload status can not be UNSET_ID, but the other fields do not matter.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_status_lu table DELETE
     * statement in CS section 1.3.1.6 Close the database connection
     * </p>
     *
     * @param uploadStatus The upload status to remove
     * @throws IllegalArgumentException If uploadStatus is null
     * @throws IllegalArgumentException If the id is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        updateUploadStatus(uploadStatus);
    }

    /**
     * <p>
     * updateUploadStatus: Updates the given upload status in the persistence. The id of the
     * uploadStatus can not be UNSET_ID, and all other fields needed for persistence must also be
     * assigned.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_status_lu table UPDATE
     * statement in CS section 1.3.1.7 Close the database connection
     * </p>
     *
     * @param uploadStatus The upload status to update
     * @throws IllegalArgumentException If uploadStatus is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        DeliverableHelper.checkObjectNotNull(uploadStatus, "uploadStatus");
        if (!uploadStatus.isValidToPersist()) {
            throw new IllegalArgumentException("uploadType is not valid to persist");
        }
        this.uploadStatus = uploadStatus;
    }

    /**
     * <p>
     * loadUploadStatus: Loads the UploadStatus with the given id from persistence. Returns null if
     * there is no UploadStatus with the given id.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_status_lu table SELECT
     * statement in CS section 1.3.1.8 If no rows are selected, return null Create a new
     * UploadStatus and copy the data from the row of the result set into its fields Close the
     * database connection return the created UploadStatus
     * </p>
     *
     * @return The loaded UploadStatus or null
     * @param uploadStatusId The id of the item to retrieve
     * @throws IllegalArgumentException if uploadStatusId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public UploadStatus loadUploadStatus(long uploadStatusId) throws UploadPersistenceException {
        DeliverableHelper.checkGreaterThanZero(uploadStatusId, "uploadStatusId");
        return new UploadStatus(uploadStatusId);
    }

    /**
     * <p>
     * getAllUploadStatusIds: Gets the ids of all upload statuses in the persistence. The individual
     * upload statuses can then be loaded with the loadUploadStatus method.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload_status_lu table SELECT
     * statment in CS section 1.3.1.22 Create return array with number of items = number of rows in
     * result set For each row in the result set, get the id from the result set and put it in the
     * array Close the database connection return the array of ids
     * </p>
     *
     * @return The ids of all upload statuses
     * @throws UploadPersistenceException If there is an error reading the persistence store
     */
    public long[] getAllUploadStatusIds() throws UploadPersistenceException {
        return new long[] {1, 1};
    }

    /**
     * <p>
     * addSubmissionStatus: Adds the given submission status to the persistence. The id of the
     * submission status must already be assigned, as must all the other fields needed for
     * persistence.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission_status_lu table INSERT
     * statement in CS section 1.3.1.9 Close the database connection
     * </p>
     *
     * @param submissionStatus The submission status to add
     * @throws IllegalArgumentException If submissionStatus is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        updateSubmissionStatus(submissionStatus);
    }

    /**
     * <p>
     * removeSubmissionStatus: Removes the given submission status (by id) from the persistence. The
     * id of the submission status can not be UNSET_ID, but the other fields do not matter.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission_status_lu table DELETE
     * statement in CS section 1.3.1.10 Close the database connection
     * </p>
     *
     * @param submissionStatus The submission status to remove
     * @throws IllegalArgumentException If submissionStatus is null
     * @throws IllegalArgumentException If the id is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        updateSubmissionStatus(submissionStatus);
    }

    /**
     * <p>
     * updateSubmissionStatus: Updates the given submission status in the persistence. The id of the
     * submissionStats can not be UNSET_ID, and all other fields needed for persistence must also be
     * assigned.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission_status_lu table UPDATE
     * statement in CS section 1.3.1.11 Close the database connection
     * </p>
     *
     * @param submissionStatus The submissionStatus to update
     * @throws IllegalArgumentException If submissionStatus is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        DeliverableHelper.checkObjectNotNull(submissionStatus, "submissionStatus");
        if (!submissionStatus.isValidToPersist()) {
            throw new IllegalArgumentException("submissionStatus is not valid to persist");
        }
        this.submissionStatus = submissionStatus;
    }

    /**
     * <p>
     * loadSubmissionStatus: Loads the SubmissionStatus with the given id from persistence. Returns
     * null if there is no SubmissionStatus with the given id.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission_status_lu table SELECT
     * statement in CS section 1.3.1.12 If no rows are selected, return null Create a new
     * SubmissionStatus and copy the data from the row of the result set into its fields Close the
     * database connection return the created SubmissionStatus
     * </p>
     *
     * @return The loaded SubmissionStatus or null
     * @param submissionStatusId The id of the item to retrieve
     * @throws IllegalArgumentException if submissionStatusId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public SubmissionStatus loadSubmissionStatus(long submissionStatusId) throws UploadPersistenceException {
        return null;
    }

    /**
     * <p>
     * getAllSubmissionStatusIds: Gets the ids of all submission statuses in the persistence. The
     * individual submission statuses can then be loaded with the loadSubmissionStatus method.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission_status table SELECT
     * statment in CS section 1.3.1.23 Create return array with number of items = number of rows in
     * result set For each row in the result set, get the id from the result set and put it in the
     * array Close the database connection return the array of ids
     * </p>
     *
     * @return The ids of all submission statuses
     * @throws UploadPersistenceException If there is an error reading the persistence store
     */
    public long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
        return null;
    }

    /**
     * <p>
     * addUpload: Adds the given upload to the persistence. The id of the upload must already be
     * assigned, as must all the other fields needed for persistence.
     * </p>
     * <p>
     *
     * @param upload The upload to add
     * @throws IllegalArgumentException If upload is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addUpload(Upload upload) throws UploadPersistenceException {
        updateUpload(upload);
    }

    /**
     * <p>
     * removeUpload: Removes the given upload (by id) from the persistence. The id of the upload can
     * not be UNSET_ID, but the other fields do not matter.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload table DELETE statement in CS
     * section 1.3.1.14 Close the database connection
     * </p>
     *
     * @param upload The upload to remove
     * @throws IllegalArgumentException If upload is null
     * @throws IllegalArgumentException If the id is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUpload(Upload upload) throws UploadPersistenceException {
        updateUpload(upload);
    }

    /**
     * <p>
     * updateUpload: Updates the given upload in the persistence. The id of the upload can not be
     * UNSET_ID, and all other fields needed for persistence must also be assigned.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the upload table UPDATE statement in CS
     * section 1.3.1.13 Close the database connection
     * </p>
     *
     * @param upload The upload to update
     * @throws IllegalArgumentException If upload is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateUpload(Upload upload) throws UploadPersistenceException {
        DeliverableHelper.checkObjectNotNull(upload, "upload");
        if (!upload.isValidToPersist()) {
            throw new IllegalArgumentException("upload is not valid to persist");
        }
        this.upload = upload;
    }

    /**
     * <p>
     * loadUpload: Loads the Upload with the given id from persistence. Returns null if there is no
     * Upload with the given id.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the update table SELECT statement in CS
     * section 1.3.1.16 If no rows are selected, return null Create a new Upload and copy the data
     * from the row of the result set into its fields Close the database connection return the
     * created Upload
     * </p>
     *
     * @return The loaded Upload or null
     * @param uploadId The id of the item to retrieve
     * @throws IllegalArgumentException if uploadId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Upload loadUpload(long uploadId) throws UploadPersistenceException {
        DeliverableHelper.checkGreaterThanZero(uploadId, "uploadId");
        return new Upload(uploadId);
    }

    /**
     * <p>
     * addSubmission: Adds the given submission to the persistence. The id of the submission must
     * already be assigned, as must all the other fields needed for persistence.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission table INSERT statement in
     * CS section 1.3.1.17 Close the database connection
     * </p>
     *
     * @param submission The submission to add
     * @throws IllegalArgumentException If submission is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void addSubmission(Submission submission) throws UploadPersistenceException {
        updateSubmission(submission);
    }

    /**
     * <p>
     * removeSubmission: Removes the given submission (by id) from the persistence. The id of the
     * submission can not be UNSET_ID, but the other fields do not matter.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission table DELETE statement in
     * CS section 1.3.1.18 Close the database connection
     * </p>
     *
     * @param submission The submission to remove
     * @throws IllegalArgumentException If submission is null
     * @throws IllegalArgumentException If the id is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeSubmission(Submission submission) throws UploadPersistenceException {
        updateSubmission(submission);
    }

    /**
     * <p>
     * updateSubmission: Updates the given submission in the persistence. The id of the submission
     * can not be UNSET_ID, and all other fields needed for persistence must also be assigned.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission table UPDATE statement in
     * CS section 1.3.1.19 Close the database connection
     * </p>
     *
     * @param submission The submission to add
     * @throws IllegalArgumentException If submission is null
     * @throws IllegalArgumentException If isValidToPersist returns false
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateSubmission(Submission submission) throws UploadPersistenceException {
        DeliverableHelper.checkObjectNotNull(submission, "submission");
        if (!submission.isValidToPersist()) {
            throw new IllegalArgumentException("submission is not valid to persist");
        }
        this.submission = submission;
    }

    /**
     * <p>
     * loadSubmission: Loads the Submission with the given id from persistence. Returns null if
     * there is no Submission with the given id.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission table SELECT statement in
     * CS section 1.3.1.20 If no rows are selected, return null Create a new Submission and copy the
     * data from the row of the result set into its fields Close the database connection return the
     * created Submission
     * </p>
     *
     * @return The loaded Submission or null
     * @param submissionId The id of the item to retrieve
     * @throws IllegalArgumentException if submissionId is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Submission loadSubmission(long submissionId) throws UploadPersistenceException {
        DeliverableHelper.checkGreaterThanZero(submissionId, "submissionId");
        lastCalled = "loadSubmission" + submissionId;
        return new Submission(submissionId);
    }

    /**
     * <p>
     * loadSubmissions: Loads all Submissions with the given ids from persistence. May return a
     * 0-length array.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the submission table SELECT statement in
     * CS section 1.3.1.20.1 For each row in the ResultSet Create a new Submission and copy the data
     * from the row of the result set into its fields Close the database connection return the
     * created Submissions
     * </p>
     *
     * @param submissionIds The ids of submissions to load
     * @return The loaded submissions
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Submission[] loadSubmissions(long[] submissionIds)
        throws UploadPersistenceException {
        lastCalled = "loadSubmissions" + submissionIds;

        Submission[] submissions = new Submission[submissionIds.length];

        for (int i = 0; i < submissions.length; ++i) {
            submissions[i] = new Submission(submissionIds[i]);
        }

        return submissions;
    }

    /**
     * <p>
     * loadUploads: Loads all Uploads with the given ids from persistence. May return a 0-length
     * array.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Open a database connection. Execute the update table SELECT statement in CS
     * section 1.3.1.16.1 For each row in the ResultSet Create a new Upload and copy the data from
     * the row of the result set into its fields Close the database connection return the created
     * Upload
     * </p>
     *
     * @param uploadIds The ids of uploads to load
     * @return The loaded uploads
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public Upload[] loadUploads(long[] uploadIds)
        throws UploadPersistenceException {
        lastCalled = "loadUploads" + uploadIds;

        Upload[] uploads = new Upload[uploadIds.length];
        for (int i = 0; i < uploads.length; ++i) {
            uploads[i] = new Upload(uploadIds[i]);
        }

        return uploads;
    }

    /**
     * <p>
     * loadUploadTypes: Loads all UploadTypes with the given ids from persistence. May return a
     * 0-length array.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Like the loadUploadTypes method, but multiple rows are selected by the SQL
     * query, and an upload type is loaded for each row.
     * </p>
     *
     * @param uploadTypeIds The ids of the objects to load
     * @return the loaded UploadTypes
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public UploadType[] loadUploadTypes(long[] uploadTypeIds) throws UploadPersistenceException {
        UploadType uploadType1 = DeliverableTestHelper.getValidToPersitUploadType();
        uploadType1.setId(2);
        return new UploadType[] {uploadType1};
    }

    /**
     * <p>
     * loadUploadStatuses: Loads all UploadStatuses with the given ids from persistence. May return
     * a 0-length array.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Like the loadUploadStatus method, but multiple rows are selected by the SQL
     * query, and an upload status is loaded for each row.
     * </p>
     *
     * @param uploadStatusIds The ids of the objects to load
     * @return the loaded UploadStatuses
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public com.topcoder.management.deliverable.UploadStatus[] loadUploadStatuses(long[] uploadStatusIds)
        throws UploadPersistenceException {
        lastCalled = "loadUploadStatuses" + uploadStatusIds;

        UploadStatus uploadStatus1 = DeliverableTestHelper.getValidToPersitUploadStatus();
        uploadStatus1.setId(2);
        return new UploadStatus[] {uploadStatus1};
    }

    /**
     * <p>
     * loadSubmissionStatuses: Loads all SubmissionStatuses with the given ids from persistence. May
     * return a 0-length array.
     * </p>
     * <p>
     * Exception Handling: Any SqlException or DBConnectionException should be wrapped in a
     * UploadPersistenceException.
     * </p>
     * <p>
     * Implementation: Like the loadSubmissionStatus method, but multiple rows are selected by the
     * SQL query, and a submission status is loaded for each row.
     * </p>
     *
     * @param submissionStatusIds The ids of the objects to load
     * @return the loaded SubmissionStatuses
     * @throws IllegalArgumentException if any id is <= 0
     * @throws UploadPersistenceException if there is an error reading the persistence data
     */
    public com.topcoder.management.deliverable.SubmissionStatus[] loadSubmissionStatuses(
            long[] submissionStatusIds) throws UploadPersistenceException {
        SubmissionStatus submissionStatus1 = DeliverableTestHelper.getValidToPersitSubmissionStatus();
        submissionStatus1.setId(2);
        return new SubmissionStatus[] {submissionStatus1};
    }

    /**
     * @return Returns the upload.
     */
    public Upload getUpload() {
        return upload;
    }

    /**
     * @param upload The upload to set.
     */
    public void setUpload(Upload upload) {
        this.upload = upload;
    }

    /**
     * @return Returns the submission.
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * @param submission The submission to set.
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    /**
     * @return Returns the submissionStatus.
     */
    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    /**
     * @param submissionStatus The submissionStatus to set.
     */
    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    /**
     * @return Returns the uploadStatus.
     */
    public UploadStatus getUploadStatus() {
        return uploadStatus;
    }

    /**
     * @param uploadStatus The uploadStatus to set.
     */
    public void setUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    /**
     * @return Returns the uploadType.
     */
    public UploadType getUploadType() {
        return uploadType;
    }

    /**
     * @param uploadType The uploadType to set.
     */
    public void setUploadType(UploadType uploadType) {
        this.uploadType = uploadType;
    }
    /**
     * Gets the name of implemented method last called.
     *
     * @return the name of the method last called.
     */
    public static String getLastCalled() {
        return lastCalled;
    }

}
