/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.AuditedDeliverableStructure;
import com.topcoder.management.deliverable.NamedDeliverableStructure;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.PersistenceException;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.persistence.sql.Helper.DataType;
import com.topcoder.management.deliverable.persistence.sql.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * The SqlUploadPersistence class implements the UploadPersistence interface, in
 * order to persist to the database structure given in the
 * deliverable_management.sql script.
 * </p>
 * <p>
 * This class does not cache a Connection to the database. Instead a new
 * Connection is used on every method call. All methods in this class will just
 * create and execute a single PreparedStatement.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread-safe in the sense that
 * multiple threads can not corrupt its internal data structures. However, the
 * results if used from multiple threads can be unpredictable as the database is
 * changed from different threads. This can equally well occur when the
 * component is used on multiple machines or multiple instances are used, so
 * this is not a thread-safety concern.
 * </p>
 *
 * <p>
 * Changes in v1.0.4 (Cockpit Spec Review Backend Service Update v1.0):
 * - replaced LogFactory with LogManager
 * </p>
 *
 * @author aubergineanode, urtks, George1, pulky
 * @version 1.0.4
 */
public class SqlUploadPersistence implements UploadPersistence {
     /** Logger instance using the class name as category */
    private static final Log logger = LogManager.getLog(SqlUploadPersistence.class.getName());
    /**
     * Represents a place holder for id column in the sql statement which will
     * be replaced by the actual id column name.
     */
    private static final String ID_NAME_PLACEHOLDER = "@id";

    /**
     * Represents a place holder for table name in the sql statement which will
     * be replaced by the actual table name.
     */
    private static final String TABLE_NAME_PLACEHOLDER = "@table";

    /**
     * Represents the sql statement to add named deliverable structure.
     */
    private static final String ADD_NAMED_ENTITY_SQL = "INSERT INTO @table "
        + "(@id, create_user, create_date, modify_user, modify_date, name, description) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the argument types for the sql statement to add named
     * deliverable structure.
     */
    private static final DataType[] ADD_NAMED_ENTITY_ARGUMENT_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to remove audited deliverable structure.
     */
    private static final String REMOVE_ENTITY_SQL = "DELETE FROM @table WHERE @id=?";

    /**
     * Represents the argument types for the sql statement to remove audited
     * deliverable structure.
     */
    private static final DataType[] REMOVE_ENTITY_ARGUMENT_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * Represents the sql statement to update named deliverable structure.
     */
    private static final String UPDATE_NAMED_ENTITY_SQL = "UPDATE @table "
        + "SET modify_user=?, modify_date=?, name=?, description=? WHERE @id=?";

    /**
     * Represents the argument types for the sql statement to named deliverable
     * structure.
     */
    private static final DataType[] UPDATE_NAMED_ENTITY_ARGUMENT_TYPES = new DataType[] {
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE};

    /**
     * Represents the sql statement to load named deliverable structure.
     */
    private static final String LOAD_NAMED_ENTITIES_SQL = "SELECT "
        + "@id, create_user, create_date, modify_user, modify_date, name, description "
        + "FROM @table WHERE @id IN ";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to load named deliverable structure.
     */
    private static final DataType[] LOAD_NAMED_ENTITIES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to load all audited deliverable structure
     * ids.
     */
    private static final String GET_ALL_ENTITY_IDS_SQL = "SELECT @id FROM @table";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to all audited deliverable structure ids.
     */
    private static final DataType[] GET_ALL_ENTITY_IDS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * Represents the sql statement to add upload.
     */
    private static final String ADD_UPLOAD_SQL = "INSERT INTO upload "
        + "(upload_id, create_user, create_date, modify_user, modify_date, "
        + "project_id, resource_id, upload_type_id, upload_status_id, parameter) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the argument types for the sql statement to add upload.
     */
    private static final DataType[] ADD_UPLOAD_ARGUMENT_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to update upload.
     */
    private static final String UPDATE_UPLOAD_SQL = "UPDATE upload "
        + "SET modify_user=?, modify_date=?, "
        + "project_id=?, resource_id=?, upload_type_id=?, upload_status_id=?, parameter=? "
        + "WHERE upload_id=?";

    /**
     * Represents the argument types for the sql statement to update upload.
     */
    private static final DataType[] UPDATE_UPLOAD_ARGUMENT_TYPES = new DataType[] {
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE};

    // OrChange : Modifications for the or integration
    /**
     * Represents the sql statement to add submission.
     */
    private static final String ADD_SUBMISSION_SQL = "INSERT INTO submission "
        + "(submission_id, create_user, create_date, modify_user, modify_date, "
            + "upload_id, submission_status_id, screening_score, initial_score, final_score, placement)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the argument types for the sql statement to add submission.
     */
    private static final DataType[] ADD_SUBMISSION_ARGUMENT_TYPES = new DataType[] { Helper.LONG_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE };

    /**
     * Represents the sql statement to update submission.
     */
    private static final String UPDATE_SUBMISSION_SQL = "UPDATE submission "
        + "SET modify_user=?, modify_date=?, upload_id=?, submission_status_id=?, screening_score=?, initial_score=?, final_score=?, placement=? "
        + "WHERE submission_id=?";

    /**
     * Represents the argument types for the sql statement to update submission.
     */
    private static final DataType[] UPDATE_SUBMISSION_ARGUMENT_TYPES = new DataType[] { Helper.STRING_TYPE,
            Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,  Helper.DOUBLE_TYPE,
            Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE };

    /**
     * Represents the sql statement to load uploads.
     */
    private static final String LOAD_UPLOADS_SQL = "SELECT "
        + "upload.upload_id, upload.create_user, upload.create_date, "
        + "upload.modify_user, upload.modify_date, "
        + "upload.project_id, upload.resource_id, upload.parameter, "
        + "upload_type_lu.upload_type_id, upload_type_lu.create_user, upload_type_lu.create_date, "
        + "upload_type_lu.modify_user, upload_type_lu.modify_date, "
        + "upload_type_lu.name, upload_type_lu.description, "
        + "upload_status_lu.upload_status_id, upload_status_lu.create_user, upload_status_lu.create_date, "
        + "upload_status_lu.modify_user, upload_status_lu.modify_date, "
        + "upload_status_lu.name, upload_status_lu.description "
        + "FROM upload INNER JOIN upload_type_lu "
        + "ON upload.upload_type_id=upload_type_lu.upload_type_id "
        + "INNER JOIN upload_status_lu "
        + "ON upload.upload_status_id=upload_status_lu.upload_status_id " + "WHERE upload_id IN ";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to load uploads.
     */
    private static final DataType[] LOAD_UPLOADS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE};

    /**
     * Represents the sql statement to load submissions.
     */
    private static final String LOAD_SUBMISSIONS_SQL = "SELECT "
        + "submission.submission_id, submission.create_user, submission.create_date, "
        + "submission.modify_user, submission.modify_date, "
        + "submission_status_lu.submission_status_id, "
        + "submission_status_lu.create_user, submission_status_lu.create_date, "
        + "submission_status_lu.modify_user, submission_status_lu.modify_date, "
        + "submission_status_lu.name, submission_status_lu.description, "
        + "upload.upload_id, upload.create_user, upload.create_date, "
        + "upload.modify_user, upload.modify_date, "
        + "upload.project_id, upload.resource_id, upload.parameter, "
        + "upload_type_lu.upload_type_id, upload_type_lu.create_user, upload_type_lu.create_date, "
        + "upload_type_lu.modify_user, upload_type_lu.modify_date, "
        + "upload_type_lu.name, upload_type_lu.description, "
        + "upload_status_lu.upload_status_id, upload_status_lu.create_user, upload_status_lu.create_date, "
        + "upload_status_lu.modify_user, upload_status_lu.modify_date, "
        + "upload_status_lu.name, upload_status_lu.description, submission.screening_score, "
        + "submission.initial_score, submission.final_score, submission.placement "
        + "FROM submission INNER JOIN submission_status_lu "
        + "ON submission.submission_status_id=submission_status_lu.submission_status_id "
        + "INNER JOIN upload ON submission.upload_id=upload.upload_id "
        + "INNER JOIN upload_type_lu " + "ON upload.upload_type_id=upload_type_lu.upload_type_id "
        + "INNER JOIN upload_status_lu "
        + "ON upload.upload_status_id=upload_status_lu.upload_status_id "
        + "WHERE submission.submission_id IN ";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to load submissions.
     */
    private static final DataType[] LOAD_SUBMISSIONS_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE };

    /**
     * The name of the connection producer to use when a
     * connection to the database is retrieved from the DBConnectionFactory.
     * This field is immutable and can be null or non-null. When non-null, no
     * restrictions are applied to the field. When this field is null, the
     * createConnection() method is used to get a connection. When it is
     * non-null, the createConnection(String) method is used to get a
     * connection. This field is not exposed by this class, and is used whenever
     * a connection to the database is needed (i.e. in every method).
     */
    private final String connectionName;

    /**
     * The connection factory to use when a connection to the
     * database is needed. This field is immutable and must be non-null. This
     * field is not exposed by this class and is used whenever a connection to
     * the database is needed (i.e. in every method).
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * Creates a new instance of the SqlUploadPersistence class. Default
     * connection name of the connectionFactory will be used.
     * @param connectionFactory
     *            The connection factory to use for getting connections to the
     *            database.
     * @throws IllegalArgumentException
     *             If connectionFactory is null.
     */
    public SqlUploadPersistence(DBConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    /**
     * Creates a new instance of the SqlUploadPersistence class, with the given
     * connectionFactory and connectionName.
     * @param connectionFactory
     *            The connection factory to use for getting connections to the
     *            database.
     * @param connectionName
     *            The name of the connection to use. Can be null.
     * @throws IllegalArgumentException
     *             If connectionFactory is null, or connectionName is empty (trimmed).
     */
    public SqlUploadPersistence(DBConnectionFactory connectionFactory, String connectionName) {
        Helper.assertObjectNotNull(connectionFactory, "connectionFactory");
        Helper.assertStringNotEmpty(connectionName, "connectionName");

        logger.log(Level.INFO,
                "Instantiate SqlUploadPersistence with connectionFactory and connectioName:" + connectionName);

        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * Adds the given NamedDeliverableStructure instance to the persistence.
     * @param namedEntity
     *            the NamedDeliverableStructure instance to add
     * @param tableName
     *            the table name to persist the instance into
     * @param idName
     *            the id column name of the table that corresponds to the id
     *            field of the instance
     * @throws UploadPersistenceException
     *             if there is an error during the persistence process
     */
    private void addNameEntity(NamedDeliverableStructure namedEntity, String tableName,
        String idName) throws UploadPersistenceException {

        logger.log(Level.INFO, "add record into table:" + tableName + " with id:" + namedEntity.getId());

        try {
            // build arguments
            Object[] queryArgs = new Object[] {new Long(namedEntity.getId()),
                namedEntity.getCreationUser(), namedEntity.getCreationTimestamp(),
                namedEntity.getModificationUser(), namedEntity.getModificationTimestamp(),
                namedEntity.getName(), namedEntity.getDescription()};

            // add named entity to database
            Helper.doDMLQuery(connectionFactory, connectionName, ADD_NAMED_ENTITY_SQL.replaceAll(
                TABLE_NAME_PLACEHOLDER, tableName).replaceAll(ID_NAME_PLACEHOLDER, idName),
                ADD_NAMED_ENTITY_ARGUMENT_TYPES, queryArgs);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR, "Failed to add record into table:" + tableName
                    + " with id:" + + namedEntity.getId() + ".\n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to add "
                + namedEntity.getClass().getName() + " to the database.", e);
        }
    }

    /**
     * <p>
     * Adds the given uploadType to the persistence. The id of the upload type
     * must already be assigned, as must all the other fields needed for
     * persistence.
     * </p>
     * @param uploadType
     *            The upload type to add
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void addUploadType(UploadType uploadType) throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(uploadType, "uploadType");

        logger.log(Level.INFO, new LogMessage("UploadType", new Long(uploadType.getId()), null, "add new UploadType"));
        addNameEntity(uploadType, "upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Adds the given uploadStatus to the persistence. The id of the upload
     * status must already be assigned, as must all the other fields needed for
     * persistence.
     * </p>
     * @param uploadStatus
     *            The upload status to add
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void addUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(uploadStatus, "uploadStatus");
        logger.log(Level.INFO,
                new LogMessage("UploadStatus", new Long(uploadStatus.getId()), null, "add new uploadStatus"));
        addNameEntity(uploadStatus, "upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * Adds the given submission status to the persistence. The id of the
     * submission status must already be assigned, as must all the other fields
     * needed for persistence.
     * </p>
     * @param submissionStatus
     *            The submission status to add
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void addSubmissionStatus(SubmissionStatus submissionStatus)
        throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(submissionStatus, "submissionStatus");
        logger.log(Level.INFO,
                new LogMessage("SubmissionStatus", new Long(submissionStatus.getId()), null, "add new SubmissionStatus."));
        addNameEntity(submissionStatus, "submission_status_lu", "submission_status_id");
    }

    /**
     * Removes the given AuditedDeliverableStructure instance (by id) from the
     * persistence.
     * @param entity
     *            the given AuditedDeliverableStructure instance to remove
     * @param tableName
     *            the table name to delete the instance from
     * @param idName
     *            the id column name of the table that corresponds to the id
     *            field of the instance
     * @throws UploadPersistenceException
     *             if there is an error when during the persistence process
     */
    private void removeEntity(AuditedDeliverableStructure entity, String tableName, String idName)
        throws UploadPersistenceException {

        logger.log(Level.INFO, "delete record from table: " + tableName + " with id:" + entity.getId());
        try {
            // build arguments
            Object[] queryArgs = new Object[] {new Long(entity.getId())};

            // remove entity from the database
            Helper.doDMLQuery(connectionFactory, connectionName, REMOVE_ENTITY_SQL.replaceAll(
                TABLE_NAME_PLACEHOLDER, tableName).replaceAll(ID_NAME_PLACEHOLDER, idName),
                REMOVE_ENTITY_ARGUMENT_TYPES, queryArgs);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR, "Failed to delete record from table:" + tableName
                    + " with id:" + + entity.getId() + ".\n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to remove " + entity.getClass().getName()
                + " from the database.", e);
        }
    }

    /**
     * <p>
     * Removes the given upload type (by id) from the persistence. The id of the
     * upload type can not be UNSET_ID, but the other fields do not matter.
     * </p>
     * @param uploadType
     *            The upload type to remove
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeUploadType(UploadType uploadType) throws UploadPersistenceException {
        Helper.assertObjectNotNull(uploadType, "uploadType");
        Helper.assertIdNotUnset(uploadType.getId(), "uploadType id");
        logger.log(Level.INFO,
                new LogMessage("UploadType", new Long(uploadType.getId()), null, "Remove uploadType."));
        removeEntity(uploadType, "upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Removes the given upload status (by id) from the persistence. The id of
     * the upload status can not be UNSET_ID, but the other fields do not
     * matter.
     * </p>
     * @param uploadStatus
     *            The upload status to remove
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        Helper.assertObjectNotNull(uploadStatus, "uploadStatus");
        Helper.assertIdNotUnset(uploadStatus.getId(), "uploadStatus id");
        logger.log(Level.INFO,
                new LogMessage("uploadStatus", new Long(uploadStatus.getId()), null, "Remove uploadStatus."));
        removeEntity(uploadStatus, "upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * Removes the given submission status (by id) from the persistence. The id
     * of the submission status can not be UNSET_ID, but the other fields do not
     * matter.
     * </p>
     * @param submissionStatus
     *            The submission status to remove
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus)
        throws UploadPersistenceException {
        Helper.assertObjectNotNull(submissionStatus, "submissionStatus");
        Helper.assertIdNotUnset(submissionStatus.getId(), "submissionStatus id");
        logger.log(Level.INFO,
                new LogMessage("SubmissionStatus", new Long(submissionStatus.getId()), null, "Remove SubmissionStatus."));
        removeEntity(submissionStatus, "submission_status_lu", "submission_status_id");
    }

    /**
     * <p>
     * Removes the given upload (by id) from the persistence. The id of the
     * upload can not be UNSET_ID, but the other fields do not matter.
     * </p>
     * @param upload
     *            The upload to remove
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeUpload(Upload upload) throws UploadPersistenceException {
        Helper.assertObjectNotNull(upload, "upload");
        Helper.assertIdNotUnset(upload.getId(), "upload id");
        logger.log(Level.INFO,
                new LogMessage("Upload", new Long(upload.getId()), null, "Remove upload."));
        removeEntity(upload, "upload", "upload_id");
    }

    /**
     * <p>
     * Removes the given submission (by id) from the persistence. The id of the
     * submission can not be UNSET_ID, but the other fields do not matter.
     * </p>
     * @param submission
     *            The submission to remove
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeSubmission(Submission submission) throws UploadPersistenceException {
        Helper.assertObjectNotNull(submission, "submission");
        Helper.assertIdNotUnset(submission.getId(), "submission id");
        logger.log(Level.INFO,
                new LogMessage("Submission", new Long(submission.getId()), null, "Remove Submission."));
        removeEntity(submission, "submission", "submission_id");
    }

    /**
     * Updates the given NamedDeliverableStructure instance in the persistence.
     * @param namedEntity
     *            the given NamedDeliverableStructure instance to update
     * @param tableName
     *            the table name to update the instance to
     * @param idName
     *            the id column name of the table that corresponds to the id
     *            field of the instance
     * @throws UploadPersistenceException
     *             if there is an error during the persistence process
     */
    private void updateNamedEntity(NamedDeliverableStructure namedEntity, String tableName,
        String idName) throws UploadPersistenceException {

        logger.log(Level.INFO, "update record in table: " + tableName + " with id:" + namedEntity.getId());

        try {
            // build arguments
            Object[] queryArgs = new Object[] {namedEntity.getModificationUser(),
                namedEntity.getModificationTimestamp(), namedEntity.getName(),
                namedEntity.getDescription(), new Long(namedEntity.getId())};

            // update named entity
            Helper.doDMLQuery(connectionFactory, connectionName, UPDATE_NAMED_ENTITY_SQL
                .replaceAll(TABLE_NAME_PLACEHOLDER, tableName).replaceAll(ID_NAME_PLACEHOLDER,
                    idName), UPDATE_NAMED_ENTITY_ARGUMENT_TYPES, queryArgs);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR, "Failed to update record in table:" + tableName
                    + " with id:" + + namedEntity.getId() + ".\n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to update "
                + namedEntity.getClass().getName() + " to the database.", e);
        }
    }

    /**
     * <p>
     * Updates the given upload type in the persistence. The id of the
     * uploadType can not be UNSET_ID, and all other fields needed for
     * persistence must also be assigned.
     * </p>
     * @param uploadType
     *            The upload type to update
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void updateUploadType(UploadType uploadType) throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(uploadType, "uploadType");
        logger.log(Level.INFO,
                new LogMessage("UploadType", new Long(uploadType.getId()), null, "Update UploadType."));
        updateNamedEntity(uploadType, "upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Updates the given upload status in the persistence. The id of the
     * uploadStatus can not be UNSET_ID, and all other fields needed for
     * persistence must also be assigned.
     * </p>
     * @param uploadStatus
     *            The upload status to update
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void updateUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(uploadStatus, "uploadStatus");
        logger.log(Level.INFO,
                new LogMessage("UploadStatus", new Long(uploadStatus.getId()), null, "Update uploadStatus."));
        updateNamedEntity(uploadStatus, "upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * updateSubmissionStatus: Updates the given submission status in the
     * persistence. The id of the submissionStats can not be UNSET_ID, and all
     * other fields needed for persistence must also be assigned.
     * </p>
     * @param submissionStatus
     *            The submissionStatus to update
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus)
        throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(submissionStatus, "submissionStatus");
        logger.log(Level.INFO,
                new LogMessage("SubmissionStatus", new Long(submissionStatus.getId()), null, "Update submissionStatus."));
        updateNamedEntity(submissionStatus, "submission_status_lu", "submission_status_id");
    }

    /**
     * Gets the ids from the given table in the persistence.
     * @param tableName
     *            the table name to load the ids from
     * @param idName
     *            the id column name of the table
     * @return all the ids in the table
     * @throws UploadPersistenceException
     *             if there is an error during the persistence process
     */
    private long[] getAllEntityIds(String tableName, String idName)
        throws UploadPersistenceException {

        Object[][] rows;
        try {
            // load all upload type ids
            rows = Helper.doQuery(connectionFactory, connectionName, GET_ALL_ENTITY_IDS_SQL
                .replaceAll(TABLE_NAME_PLACEHOLDER, tableName).replaceAll(ID_NAME_PLACEHOLDER,
                    idName), new DataType[0], new Object[0], GET_ALL_ENTITY_IDS_COLUMN_TYPES);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR, "Failed to load record in table:"
                    + tableName + ".\n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to get all ids from the table ["
                + tableName + "].", e);
        }

        // create a long array and set values
        long[] ids = new long[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            ids[i] = ((Long) rows[i][0]).longValue();
        }
        return ids;
    }

    /**
     * <p>
     * Gets the ids of all upload types in the persistence. The individual
     * upload types can then be loaded with the loadUploadType method.
     * </p>
     * @return The ids of all upload types
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllUploadTypeIds() throws UploadPersistenceException {
        return getAllEntityIds("upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Gets the ids of all upload statuses in the persistence. The individual
     * upload statuses can then be loaded with the loadUploadStatus method.
     * </p>
     * @return The ids of all upload statuses
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllUploadStatusIds() throws UploadPersistenceException {
        return getAllEntityIds("upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * Gets the ids of all submission statuses in the persistence. The
     * individual submission statuses can then be loaded with the
     * loadSubmissionStatus method.
     * </p>
     * @return The ids of all submission statuses
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
        return getAllEntityIds("submission_status_lu", "submission_status_id");
    }

    /**
     * Loads all NamedDeliverableStructure with the given ids from persistence.
     * May return a 0-length array.
     * @param namedEntityIds
     *            The ids of the objects to load
     * @param type
     *            the concrete class type of the NamedDeliverableStructure
     * @param tableName
     *            the name of the table to load from
     * @param idName
     *            the id column name of the table that corresponds to the id
     *            field of the instance
     * @return an array of all the instances of 'type' type
     * @throws UploadPersistenceException
     *             if there is an error during the persistence process
     */
    private NamedDeliverableStructure[] loadNamedEntities(long[] namedEntityIds, Class type,
        String tableName, String idName) throws UploadPersistenceException {

        // check if the given type is of NamedDeliverableStructure kind.
        if (!NamedDeliverableStructure.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("type [" + type.getName()
                + "] should be a NamedDeliverableStructure type or its sub-type.");
        }

        // simply return an empty 'type'[] array if
        // namedEntityIds is empty
        if (namedEntityIds.length == 0) {
            return (NamedDeliverableStructure[]) Array.newInstance(type, 0);
        }

        Object[][] rows;
        try {
            // load named entities
            rows = Helper.doQuery(connectionFactory, connectionName, LOAD_NAMED_ENTITIES_SQL
                .replaceAll(TABLE_NAME_PLACEHOLDER, tableName).replaceAll(ID_NAME_PLACEHOLDER,
                    idName)
                + Helper.makeIdListString(namedEntityIds), new DataType[0], new Object[0],
                LOAD_NAMED_ENTITIES_COLUMN_TYPES);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR,
                    "Unable to load " + type.getName() + " from the database." + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to load " + type.getName()
                + " from the database.", e);
        }

        try {
            // create a new array of 'type'[] type
            NamedDeliverableStructure[] namedEntities = (NamedDeliverableStructure[]) Array
                .newInstance(type, rows.length);

            // enumerate each data row
            for (int i = 0; i < rows.length; ++i) {
                // reference the current data row
                Object[] row = rows[i];

                // create a new 'type' object
                NamedDeliverableStructure namedEntity = (NamedDeliverableStructure) type
                    .newInstance();
                Helper.loadNamedEntityFieldsSequentially(namedEntity, row, 0);

                // assign it to the array
                namedEntities[i] = namedEntity;
            }
            return namedEntities;
        } catch (InstantiationException e) {
            logger.log(Level.ERROR,
                    "Unable to create an instance of " + type.getName() + ". \n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to create an instance of "
                + type.getName() + ".", e);
        } catch (IllegalAccessException e) {
            logger.log(Level.ERROR,
                    "Unable to create an instance of " + type.getName() + ". \n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to create an instance of "
                + type.getName() + ".", e);
        }
    }

    /**
     * <p>
     * Loads the UploadType with the given id from persistence. Returns null if
     * there is no UploadType with the given id.
     * </p>
     * @return The loaded UploadType or null
     * @param uploadTypeId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadTypeId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadType loadUploadType(long uploadTypeId) throws UploadPersistenceException {
        Helper.assertIdNotUnset(uploadTypeId, "uploadTypeId");
        logger.log(Level.INFO, new LogMessage("UploadType", new Long(uploadTypeId), null,
                "Load UploadType. Delegate to loadUploadTypes(long[] uploadTypeIds)."));
        UploadType[] uploadTypes = loadUploadTypes(new long[] {uploadTypeId});
        return uploadTypes.length == 0 ? null : uploadTypes[0];
    }

    /**
     * <p>
     * Loads the UploadStatus with the given id from persistence. Returns null
     * if there is no UploadStatus with the given id.
     * </p>
     * @return The loaded UploadStatus or null
     * @param uploadStatusId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadStatusId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadStatus loadUploadStatus(long uploadStatusId) throws UploadPersistenceException {
        Helper.assertIdNotUnset(uploadStatusId, "uploadStatusId");
        logger.log(Level.INFO, new LogMessage("UploadStatus", new Long(uploadStatusId), null,
                "Load UploadStatus. Delegate to loadUploadStatuses(long[] uploadStatusIds)."));
        UploadStatus[] uploadStatuses = loadUploadStatuses(new long[] {uploadStatusId});
        return uploadStatuses.length == 0 ? null : uploadStatuses[0];
    }

    /**
     * <p>
     * Loads the SubmissionStatus with the given id from persistence. Returns
     * null if there is no SubmissionStatus with the given id.
     * </p>
     * @return The loaded SubmissionStatus or null
     * @param submissionStatusId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if submissionStatusId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public SubmissionStatus loadSubmissionStatus(long submissionStatusId)
        throws UploadPersistenceException {
        Helper.assertIdNotUnset(submissionStatusId, "submissionStatusId");
        logger.log(Level.INFO, new LogMessage("SubmissionStatus", new Long(submissionStatusId), null,
                "Load SubmissionStatus. Delegate to loadSubmissionStatuses(long[] submissionStatusId)."));
        SubmissionStatus[] submissionStatuses = loadSubmissionStatuses(new long[] {submissionStatusId});
        return submissionStatuses.length == 0 ? null : submissionStatuses[0];
    }

    /**
     * <p>
     * Loads all UploadTypes with the given ids from persistence. May return a
     * 0-length array.
     * </p>
     * @param uploadTypeIds
     *            The ids of the objects to load
     * @return the loaded UploadTypes
     * @throws IllegalArgumentException
     *             if uploadTypeIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadType[] loadUploadTypes(long[] uploadTypeIds) throws UploadPersistenceException {
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(uploadTypeIds, "uploadTypeIds");
        logger.log(Level.INFO, new LogMessage("UploadType", null, null,
                "Load UploadTypes with ids:" + Helper.getIdString(uploadTypeIds)));
        return (UploadType[]) loadNamedEntities(uploadTypeIds, UploadType.class, "upload_type_lu",
            "upload_type_id");
    }

    /**
     * <p>
     * Loads all UploadStatuses with the given ids from persistence. May return
     * a 0-length array.
     * </p>
     * @param uploadStatusIds
     *            The ids of the objects to load
     * @return the loaded UploadStatuses
     * @throws IllegalArgumentException
     *             if uploadStatusIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadStatus[] loadUploadStatuses(long[] uploadStatusIds)
        throws UploadPersistenceException {
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(uploadStatusIds, "uploadStatusIds");
        logger.log(Level.INFO, new LogMessage("UploadStatus", null, null,
                "Load UploadStatuses with ids:" + Helper.getIdString(uploadStatusIds)));
        return (UploadStatus[]) loadNamedEntities(uploadStatusIds, UploadStatus.class,
            "upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * Loads all SubmissionStatuses with the given ids from persistence. May
     * return a 0-length array.
     * </p>
     * @param submissionStatusIds
     *            The ids of the objects to load
     * @return the loaded SubmissionStatuses
     * @throws IllegalArgumentException
     *             if submissionStatusIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds)
        throws UploadPersistenceException {
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(submissionStatusIds, "submissionStatusIds");
        logger.log(Level.INFO, new LogMessage("SubmissionStatus", null, null,
                "Load SubmissionStatuses with ids:" + Helper.getIdString(submissionStatusIds)));
        return (SubmissionStatus[]) loadNamedEntities(submissionStatusIds, SubmissionStatus.class,
            "submission_status_lu", "submission_status_id");

    }

    /**
     * <p>
     * Adds the given upload to the persistence. The id of the upload must
     * already be assigned, as must all the other fields needed for persistence.
     * </p>
     * @param upload
     *            The upload to add
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void addUpload(Upload upload) throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(upload, "upload");

        logger.log(Level.INFO, new LogMessage("Upload", new Long(upload.getId()), null,
                "Add new Upload, it will insert record into upload table."));
        // build arguments
        Object[] queryArgs = new Object[] {new Long(upload.getId()), upload.getCreationUser(),
            upload.getCreationTimestamp(), upload.getModificationUser(),
            upload.getModificationTimestamp(), new Long(upload.getProject()),
            new Long(upload.getOwner()), new Long(upload.getUploadType().getId()),
            new Long(upload.getUploadStatus().getId()), upload.getParameter()};

        try {
            // add upload to database
            Helper.doDMLQuery(connectionFactory, connectionName, ADD_UPLOAD_SQL,
                ADD_UPLOAD_ARGUMENT_TYPES, queryArgs);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR,
                    new LogMessage("Upload", new Long(upload.getId()), null,"Failed to add new Upload", e));
            throw new UploadPersistenceException("Unable to add upload to the database.", e);
        }
    }

    /**
     * <p>
     * Updates the given upload in the persistence. The id of the upload can not
     * be UNSET_ID, and all other fields needed for persistence must also be
     * assigned.
     * </p>
     * @param upload
     *            The upload to update
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void updateUpload(Upload upload) throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(upload, "upload");

        logger.log(Level.INFO, new LogMessage("Upload", new Long(upload.getId()), null,
                "Update Upload, it will update record in upload table."));

        // build arguments
        Object[] queryArgs = new Object[] {upload.getModificationUser(),
            upload.getModificationTimestamp(), new Long(upload.getProject()),
            new Long(upload.getOwner()), new Long(upload.getUploadType().getId()),
            new Long(upload.getUploadStatus().getId()), upload.getParameter(),
            new Long(upload.getId())};

        try {
            // update upload to database
            Helper.doDMLQuery(connectionFactory, connectionName, UPDATE_UPLOAD_SQL,
                UPDATE_UPLOAD_ARGUMENT_TYPES, queryArgs);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR,
                    new LogMessage("Upload", new Long(upload.getId()), null,"Failed to update Upload", e));
            throw new UploadPersistenceException("Unable to update upload to the database.", e);
        }
    }

    /**
     * <p>
     * Adds the given submission to the persistence. The id of the submission
     * must already be assigned, as must all the other fields needed for
     * persistence.
     * </p>
     * @param submission
     *            The submission to add
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void addSubmission(Submission submission) throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(submission, "submission");

        logger.log(Level.INFO, new LogMessage("Submission", new Long(submission.getId()), null,
                "Add new Submission, it will insert record into submission table."));

        // build arguments
        Object[] queryArgs = new Object[] {new Long(submission.getId()),
            submission.getCreationUser(), submission.getCreationTimestamp(),
            submission.getModificationUser(), submission.getModificationTimestamp(),
            new Long(submission.getUpload().getId()),
            new Long(submission.getSubmissionStatus().getId()),
            submission.getScreeningScore(),
            submission.getInitialScore(),
            submission.getFinalScore(),
            submission.getPlacement()};

        try {
            // add submission to database
            Helper.doDMLQuery(connectionFactory, connectionName, ADD_SUBMISSION_SQL,
                ADD_SUBMISSION_ARGUMENT_TYPES, queryArgs);

        } catch (PersistenceException e) {
            logger.log(Level.ERROR, new LogMessage("Submission", new Long(submission.getId()), null,
                    "Failed to add new submission", e));
            throw new UploadPersistenceException("Unable to add submission to the database.", e);
        }
    }

    /**
     * <p>
     * Updates the given submission in the persistence. The id of the submission
     * can not be UNSET_ID, and all other fields needed for persistence must
     * also be assigned.
     * </p>
     * @param submission
     *            The submission to add
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void updateSubmission(Submission submission) throws UploadPersistenceException {
        Helper.assertEntityNotNullAndValidToPersist(submission, "submission");

        logger.log(Level.INFO, new LogMessage("Submission", new Long(submission.getId()), null,
                "Update Submission, it will update record in submission table."));

        // build arguments
        Object[] queryArgs = new Object[] {submission.getModificationUser(),
            submission.getModificationTimestamp(),
                new Long(submission.getUpload().getId()), new Long(submission.getSubmissionStatus().getId()),
                submission.getScreeningScore(), submission.getInitialScore(),
                submission.getFinalScore(), submission.getPlacement(),
                new Long(submission.getId()) };

        try {
            // update submission to database
            Helper.doDMLQuery(connectionFactory, connectionName, UPDATE_SUBMISSION_SQL,
                UPDATE_SUBMISSION_ARGUMENT_TYPES, queryArgs);

        } catch (PersistenceException e) {
            logger.log(Level.ERROR, new LogMessage("Submission", new Long(submission.getId()), null,
                    "Failed to update submission", e));
            throw new UploadPersistenceException("Unable to update submission to the database.", e);
        }
    }

    /**
     * <p>
     * Loads the Upload with the given id from persistence. Returns null if
     * there is no Upload with the given id.
     * </p>
     * @return The loaded Upload or null
     * @param uploadId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Upload loadUpload(long uploadId) throws UploadPersistenceException {
        Helper.assertIdNotUnset(uploadId, "uploadId");
        logger.log(Level.INFO, new LogMessage("Upload", new Long(uploadId), null,
                "Load Upload. Delegate to loadUploads(long[] uploadId)."));
        Upload[] uploads = loadUploads(new long[] {uploadId});
        return uploads.length == 0 ? null : uploads[0];
    }

    /**
     * <p>
     * Loads all Uploads with the given ids from persistence. May return a
     * 0-length array.
     * </p>
     * @param uploadIds
     *            The ids of uploads to load
     * @return The loaded uploads
     * @throws IllegalArgumentException
     *             if uploadIds is null or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Upload[] loadUploads(long[] uploadIds) throws UploadPersistenceException {
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(uploadIds, "uploadIds");

        logger.log(Level.INFO, new LogMessage("Upload", null, null,
                "Load Uploads with ids:" + Helper.getIdString(uploadIds)));

        // simply return an empty Upload array if uploadIds is empty
        if (uploadIds.length == 0) {
            return new Upload[0];
        }

        Object[][] rows;
        try {
            // load upload
            rows = Helper.doQuery(connectionFactory, connectionName, LOAD_UPLOADS_SQL
                + Helper.makeIdListString(uploadIds), new DataType[0], new Object[0],
                LOAD_UPLOADS_COLUMN_TYPES);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR,
                    "Unable to load uploads to the database." + ". \n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to load uploads to the database.", e);
        }

        // create a new Upload array
        Upload[] uploads = new Upload[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // create a new Upload object
            Upload upload = new Upload();
            loadUploadFieldsSequentially(upload, row, 0);

            // assign it to the array
            uploads[i] = upload;
        }
        return uploads;
    }

    /**
     * Loads uploads from the result of the SELECT operation.
     *
     * @return an array of loaded uploads.
     * @param resultSet
     *            The result of the SELECT operation.
     * @throws UploadPersistenceException
     *             if any error occurs while loading uploads.
     */
    public Upload[] loadUploads(CustomResultSet resultSet) throws UploadPersistenceException {
        Helper.assertObjectNotNull(resultSet, "resultSet");

        if (resultSet.getRecordCount() == 0) {
            return new Upload[0];
        }

        try {
            List uploads = new ArrayList();

            while (resultSet.next()) {
                uploads.add(loadUpload(resultSet));
            }

            return (Upload[]) uploads.toArray(new Upload[uploads.size()]);
        } catch (InvalidCursorStateException icse) {
            throw new UploadPersistenceException("Error loading uploads.", icse);
        }
    }

    /**
     * <p>
     * Loads the Submission with the given id from persistence. Returns null if
     * there is no Submission with the given id.
     * </p>
     * @return The loaded Submission or null
     * @param submissionId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if submissionId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Submission loadSubmission(long submissionId) throws UploadPersistenceException {
        Helper.assertIdNotUnset(submissionId, "submissionId");
        logger.log(Level.INFO, new LogMessage("Submission", new Long(submissionId), null,
                "Load Submission. Delegate to loadSubmissions(long[] submissionId)."));
        Submission[] submissions = loadSubmissions(new long[] {submissionId});
        return submissions.length == 0 ? null : submissions[0];
    }

    /**
     * <p>
     * Loads all Submissions with the given ids from persistence. May return a
     * 0-length array.
     * </p>
     * @param submissionIds
     *            The ids of submissions to load
     * @return The loaded submissions
     * @throws IllegalArgumentException
     *             if submissionIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Submission[] loadSubmissions(long[] submissionIds) throws UploadPersistenceException {
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(submissionIds, "submissionIds");

        logger.log(Level.INFO, new LogMessage("Submission", null, null,
                "Load Submissions with ids:" + Helper.getIdString(submissionIds)));

        // simply return an empty Submission array if submissionIds is empty
        if (submissionIds.length == 0) {
            return new Submission[0];
        }

        Object[][] rows;
        try {
            // load submission
            rows = Helper.doQuery(connectionFactory, connectionName, LOAD_SUBMISSIONS_SQL
                + Helper.makeIdListString(submissionIds), new DataType[0], new Object[0],
                LOAD_SUBMISSIONS_COLUMN_TYPES);
        } catch (PersistenceException e) {
            logger.log(Level.ERROR,
                    "Unable to load submissions to the database." + ". \n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to load submissions to the database.", e);
        }

        // create a new Submission array
        Submission[] submissions = new Submission[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // create a new Submission object
            Submission submission = new Submission();

            int index = loadSubmissionFieldsSequentially(submission, row, 0);

            // load the submission values.
            Object aux = row[index++];
            if (aux != null) {
                submission.setScreeningScore(((Double) aux));
            }
            aux = row[index++];
            if (aux != null) {
                submission.setIntialScore(((Double) aux));
            }
            aux = row[index++];
            if (aux != null) {
                submission.setFinalScore(((Double) aux));
            }
            aux = row[index++];
            if (aux != null) {
                submission.setPlacement(((Long) aux));
            }

            // assign it to the array
            submissions[i] = submission;
        }
        return submissions;
    }

    /**
     * Loads submissions from the result of the SELECT operation.
     *
     * @return an array of loaded submissions.
     * @param resultSet
     *            The result of the SELECT operation.
     * @throws UploadPersistenceException
     *             if any error occurs while loading submissions.
     */
    public Submission[] loadSubmissions(CustomResultSet resultSet) throws UploadPersistenceException {
        Helper.assertObjectNotNull(resultSet, "resultSet");

        if (resultSet.getRecordCount() == 0) {
            return new Submission[0];
        }

        try {
            List submissions = new ArrayList();

            while (resultSet.next()) {
                submissions.add(loadSubmission(resultSet));
            }

            return (Submission[]) submissions.toArray(new Submission[submissions.size()]);
        } catch (InvalidCursorStateException icse) {
            throw new UploadPersistenceException("Error loading submission.", icse);
        }
    }

    /**
     * Load data items from the data row and fill the fields of an Upload
     * instance.
     * @param upload
     *            the Upload instance whose fields will be filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     */
    private int loadUploadFieldsSequentially(Upload upload, Object[] row, int startIndex) {

        startIndex = Helper.loadEntityFieldsSequentially(upload, row, startIndex);

        upload.setProject(((Long) row[startIndex++]).longValue());
        upload.setOwner(((Long) row[startIndex++]).longValue());
        upload.setParameter((String) row[startIndex++]);

        // create a new UploadType object
        UploadType uploadType = new UploadType();
        startIndex = Helper.loadNamedEntityFieldsSequentially(uploadType, row, startIndex);
        upload.setUploadType(uploadType);

        // create a new UploadStatus object
        UploadStatus uploadStatus = new UploadStatus();
        startIndex = Helper.loadNamedEntityFieldsSequentially(uploadStatus, row, startIndex);
        upload.setUploadStatus(uploadStatus);

        return startIndex;
    }

    /**
     * Load data items from the data row and fill the fields of an Submission
     * instance.
     * @param submission
     *            the Submission instance whose fields will be filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     */
    private int loadSubmissionFieldsSequentially(Submission submission, Object[] row, int startIndex) {

        startIndex = Helper.loadEntityFieldsSequentially(submission, row, startIndex);

        // create a new SubmissionStatus object
        SubmissionStatus submissionStatus = new SubmissionStatus();
        startIndex = Helper.loadNamedEntityFieldsSequentially(submissionStatus, row, startIndex);
        submission.setSubmissionStatus(submissionStatus);

        // create a new Upload object
        Upload upload = new Upload();
        startIndex = loadUploadFieldsSequentially(upload, row, startIndex);
        submission.setUpload(upload);

        return startIndex;
    }

    /**
     * Loads submission from the result of the SELECT operation.
     *
     * @return loaded submission.
     * @param resultSet
     *            Result of the SELECT operation.
     * @throws InvalidCursorStateException
     *             if any error occurs while reading the result.
     */
    private Submission loadSubmission(CustomResultSet resultSet) throws InvalidCursorStateException {
        Submission submission = new Submission();

        if (resultSet.getObject("screening_score") != null) {
            submission.setScreeningScore(new Double(resultSet.getDouble("screening_score")));
        }
        if (resultSet.getObject("initial_score") != null) {
            submission.setIntialScore(new Double(resultSet.getDouble("initial_score")));
        }
        if (resultSet.getObject("final_score") != null) {
            submission.setFinalScore(new Double(resultSet.getDouble("final_score")));
        }
        if (resultSet.getObject("placement") != null) {
            submission.setPlacement(new Long(resultSet.getLong("placement")));
        }

        submission.setId(resultSet.getLong("submission_id"));
        submission.setCreationUser(resultSet.getString("submission_create_user"));
        submission.setCreationTimestamp(resultSet.getDate("submission_create_date"));
        submission.setModificationUser(resultSet.getString("submission_modify_user"));
        submission.setModificationTimestamp(resultSet.getDate("submission_modify_date"));

        // create a new SubmissionStatus object
        SubmissionStatus submissionStatus = new SubmissionStatus();

        submissionStatus.setId(resultSet.getLong("submission_status_id"));
        submissionStatus.setCreationUser(resultSet.getString("submission_status_create_user"));
        submissionStatus.setCreationTimestamp(resultSet.getDate("submission_status_create_date"));
        submissionStatus.setModificationUser(resultSet.getString("submission_status_modify_user"));
        submissionStatus.setModificationTimestamp(resultSet.getDate("submission_status_modify_date"));
        submissionStatus.setName(resultSet.getString("submission_status_name"));
        submissionStatus.setDescription(resultSet.getString("submission_status_description"));

        submission.setSubmissionStatus(submissionStatus);

        // create a new Upload object
        Upload upload = loadUpload(resultSet);
        submission.setUpload(upload);

        return submission;
    }

    /**
     * Loads upload from the result of the SELECT operation.
     *
     * @return loaded upload.
     * @param resultSet
     *            Result of the SELECT operation.
     * @throws InvalidCursorStateException
     *             if any error occurs while reading the result.
     */
    private Upload loadUpload(CustomResultSet resultSet) throws InvalidCursorStateException {
        Upload upload = new Upload();

        upload.setId(resultSet.getLong("upload_id"));
        upload.setCreationUser(resultSet.getString("upload_create_user"));
        upload.setCreationTimestamp(resultSet.getDate("upload_create_date"));
        upload.setModificationUser(resultSet.getString("upload_modify_user"));
        upload.setModificationTimestamp(resultSet.getDate("upload_modify_date"));

        upload.setProject(resultSet.getLong("project_id"));
        upload.setOwner(resultSet.getLong("resource_id"));
        upload.setParameter(resultSet.getString("upload_parameter"));

        // create a new UploadType object
        UploadType uploadType = new UploadType();

        uploadType.setId(resultSet.getLong("upload_type_id"));
        uploadType.setCreationUser(resultSet.getString("upload_type_create_user"));
        uploadType.setCreationTimestamp(resultSet.getDate("upload_type_create_date"));
        uploadType.setModificationUser(resultSet.getString("upload_type_modify_user"));
        uploadType.setModificationTimestamp(resultSet.getDate("upload_type_modify_date"));
        uploadType.setName(resultSet.getString("upload_type_name"));
        uploadType.setDescription(resultSet.getString("upload_type_description"));

        upload.setUploadType(uploadType);

        // create a new UploadStatus object
        UploadStatus uploadStatus = new UploadStatus();

        uploadStatus.setId(resultSet.getLong("upload_status_id"));
        uploadStatus.setCreationUser(resultSet.getString("upload_status_create_user"));
        uploadStatus.setCreationTimestamp(resultSet.getDate("upload_status_create_date"));
        uploadStatus.setModificationUser(resultSet.getString("upload_status_modify_user"));
        uploadStatus.setModificationTimestamp(resultSet.getDate("upload_status_modify_date"));
        uploadStatus.setName(resultSet.getString("upload_status_name"));
        uploadStatus.setDescription(resultSet.getString("upload_status_description"));

        upload.setUploadStatus(uploadStatus);

        return upload;
    }
}
