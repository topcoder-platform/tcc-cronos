/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;

/**
 * <p>
 * The UploadPersistence interface defines the methods for persisting and
 * retrieving the object model in this component.
 * </p>
 * <p>
 * This interface handles the persistence of the upload related classes that
 * make up the object model ¨C Uploads, Submissions, UploadTypes, UploadStatuses,
 * SubmissionStatuses. This interface is not responsible for searching the
 * persistence for the various entities. This is instead handled by an
 * UploadManager implementation.
 * </p>
 * <p>
 * Thread Safety: Implementations of this interface are not required to be
 * thread-safe or immutable.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public interface UploadPersistence {
    /**
     * <p>
     * Adds the given uploadType to the persistence.
     * </p>
     * <p>
     * The id of the upload type must already be assigned, as must all the other
     * fields needed for persistence.
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
    public void addUploadType(UploadType uploadType) throws UploadPersistenceException;

    /**
     * <p>
     * Removes the given upload type (by id) from the persistence.
     * </p>
     * <p>
     * The id of the upload type can not be UNSET_ID, but the other fields do
     * not matter.
     * </p>
     * @param uploadType
     *            The upload type to remove
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this
     *             last part)
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeUploadType(UploadType uploadType) throws UploadPersistenceException;

    /**
     * <p>
     * Updates the given upload type in the persistence.
     * </p>
     * <p>
     * The id of the uploadType can not be UNSET_ID, and all other fields needed
     * for persistence must also be assigned.
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
    public void updateUploadType(UploadType uploadType) throws UploadPersistenceException;

    /**
     * <p>
     * Loads the UploadType with the given id from persistence.
     * </p>
     * <p>
     * Returns null if there is no UploadType with the given id.
     * </p>
     * @return The loaded UploadType or null
     * @param uploadTypeId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadTypeId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadType loadUploadType(long uploadTypeId) throws UploadPersistenceException;

    /**
     * <p>
     * Loads all UploadTypes with the given ids from persistence.
     * </p>
     * <p>
     * May return a 0-length array.
     * </p>
     * @param uploadTypeIds
     *            The ids of the objects to load
     * @return the loaded UploadTypes
     * @throws IllegalArgumentException
     *             if uploadTypeIds is null or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadType[] loadUploadTypes(long[] uploadTypeIds) throws UploadPersistenceException;

    /**
     * <p>
     * Gets the ids of all upload types in the persistence.
     * </p>
     * <p>
     * The individual upload types can then be loaded with the loadUploadType
     * method.
     * </p>
     * @return The ids of all upload types
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllUploadTypeIds() throws UploadPersistenceException;

    /**
     * <p>
     * Adds the given uploadStatus to the persistence.
     * </p>
     * <p>
     * The id of the upload status must already be assigned, as must all the
     * other fields needed for persistence.
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
    public void addUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException;

    /**
     * <p>
     * Removes the given upload status (by id) from the persistence.
     * </p>
     * <p>
     * The id of the upload status can not be UNSET_ID, but the other fields do
     * not matter.
     * </p>
     * @param uploadStatus
     *            The upload status to remove
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this
     *             last part)
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException;

    /**
     * <p>
     * Updates the given upload status in the persistence.
     * </p>
     * <p>
     * The id of the uploadStatus can not be UNSET_ID, and all other fields
     * needed for persistence must also be assigned.
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
    public void updateUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException;

    /**
     * <p>
     * Loads the UploadStatus with the given id from persistence.
     * </p>
     * <p>
     * Returns null if there is no UploadStatus with the given id.
     * </p>
     * @return The loaded UploadStatus or null
     * @param uploadStatusId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadStatusId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadStatus loadUploadStatus(long uploadStatusId) throws UploadPersistenceException;

    /**
     * <p>
     * Loads all UploadStatuses with the given ids from persistence.
     * </p>
     * <p>
     * May return a 0-length array.
     * </p>
     * @param uploadStatusIds
     *            The ids of the objects to load
     * @return the loaded UploadStatuses
     * @throws IllegalArgumentException
     *             if uploadStatusId is null, any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadStatus[] loadUploadStatuses(long[] uploadStatusIds)
        throws UploadPersistenceException;

    /**
     * <p>
     * Gets the ids of all upload statuses in the persistence.
     * </p>
     * <p>
     * The individual upload statuses can then be loaded with the
     * loadUploadStatus method.
     * </p>
     * @return The ids of all upload statuses
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllUploadStatusIds() throws UploadPersistenceException;

    /**
     * <p>
     * Adds the given submission status to the persistence.
     * </p>
     * <p>
     * The id of the submission status must already be assigned, as must all the
     * other fields needed for persistence.
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
        throws UploadPersistenceException;

    /**
     * <p>
     * Removes the given submission status (by id) from the persistence.
     * </p>
     * <p>
     * The id of the submission status can not be UNSET_ID, but the other fields
     * do not matter.
     * </p>
     * @param submissionStatus
     *            The submission status to remove
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this
     *             last part)
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus)
        throws UploadPersistenceException;

    /**
     * <p>
     * Updates the given submission status in the persistence.
     * </p>
     * <p>
     * The id of the submissionStats can not be UNSET_ID, and all other fields
     * needed for persistence must also be assigned.
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
        throws UploadPersistenceException;

    /**
     * <p>
     * Loads the SubmissionStatus with the given id from persistence.
     * </p>
     * <p>
     * Returns null if there is no SubmissionStatus with the given id.
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
        throws UploadPersistenceException;

    /**
     * <p>
     * Loads all SubmissionStatuses with the given ids from persistence.
     * </p>
     * <p>
     * May return a 0-length array.
     * </p>
     * @param submissionStatusIds
     *            The ids of the objects to load
     * @return the loaded SubmissionStatuses
     * @throws IllegalArgumentException
     *             if submissionStatusIds is null or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds)
        throws UploadPersistenceException;

    /**
     * <p>
     * Gets the ids of all submission statuses in the persistence.
     * </p>
     * <p>
     * The individual submission statuses can then be loaded with the
     * loadSubmissionStatus method.
     * </p>
     * @return The ids of all submission statuses
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllSubmissionStatusIds() throws UploadPersistenceException;

    /**
     * <p>
     * Adds the given upload to the persistence.
     * </p>
     * <p>
     * The id of the upload must already be assigned, as must all the other
     * fields needed for persistence.
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
    public void addUpload(Upload upload) throws UploadPersistenceException;

    /**
     * <p>
     * Removes the given upload (by id) from the persistence.
     * </p>
     * <p>
     * The id of the upload can not be UNSET_ID, but the other fields do not
     * matter.
     * </p>
     * @param upload
     *            The upload to remove
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this
     *             last part)
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeUpload(Upload upload) throws UploadPersistenceException;

    /**
     * <p>
     * Updates the given upload in the persistence.
     * </p>
     * <p>
     * The id of the upload can not be UNSET_ID, and all other fields needed for
     * persistence must also be assigned.
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
    public void updateUpload(Upload upload) throws UploadPersistenceException;

    /**
     * <p>
     * Loads the Upload with the given id from persistence.
     * </p>
     * <p>
     * Returns null if there is no Upload with the given id.
     * </p>
     * @return The loaded Upload or null
     * @param uploadId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Upload loadUpload(long uploadId) throws UploadPersistenceException;

    /**
     * <p>
     * Loads all Uploads with the given ids from persistence.
     * </p>
     * <p>
     * May return a 0-length array.
     * </p>
     * @param uploadIds
     *            The ids of uploads to load
     * @return The loaded uploads
     * @throws IllegalArgumentException
     *             if uploadIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Upload[] loadUploads(long[] uploadIds) throws UploadPersistenceException;

    /**
     * <p>
     * Adds the given submission to the persistence.
     * </p>
     * <p>
     * The id of the submission must already be assigned, as must all the other
     * fields needed for persistence.
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
    public void addSubmission(Submission submission) throws UploadPersistenceException;

    /**
     * <p>
     * Removes the given submission (by id) from the persistence.
     * </p>
     * <p>
     * The id of the submission can not be UNSET_ID, but the other fields do not
     * matter.
     * </p>
     * @param submission
     *            The submission to remove
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID or the modification user/date is not
     *             set (not all implementations are required to enforce this
     *             last part)
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the
     *             persistence
     */
    public void removeSubmission(Submission submission) throws UploadPersistenceException;

    /**
     * <p>
     * Updates the given submission in the persistence.
     * </p>
     * <p>
     * The id of the submission can not be UNSET_ID, and all other fields needed
     * for persistence must also be assigned.
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
    public void updateSubmission(Submission submission) throws UploadPersistenceException;

    /**
     * <p>
     * Loads the Submission with the given id from persistence.
     * </p>
     * <p>
     * Returns null if there is no Submission with the given id.
     * </p>
     * @return The loaded Submission or null
     * @param submissionId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if submissionId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Submission loadSubmission(long submissionId) throws UploadPersistenceException;

    /**
     * <p>
     * Loads all Submissions with the given ids from persistence.
     * </p>
     * <p>
     * May return a 0-length array.
     * </p>
     * @param submissionIds
     *            The ids of submissions to load
     * @return The loaded submissions
     * @throws IllegalArgumentException
     *             if submissionIds is null or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Submission[] loadSubmissions(long[] submissionIds) throws UploadPersistenceException;
}
