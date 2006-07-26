/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The UploadManager interface provides the ability to persist, retrieve and search for persisted
 * upload and submission modeling objects. This interface provides a higher level of interaction
 * than the UploadPersistence interface. The methods in this interface break down into dealing with
 * the 5 Upload/Submission modeling classes in this component, and the methods for each modeling
 * class are fairly similar. However, searching methods are provided only for the Upload and
 * Submission objects.
 * </p>
 * <p>
 * Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0
 */
public interface UploadManager {
    /**
     * Creates a new upload in the persistence store. The id of the upload must be UNSET_ID. The
     * manager will assign an id to the upload.
     *
     * @param upload The upload to add
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If upload or operator is null
     * @throws IllegalArgumentException If the id is not UNSET_ID
     * @throws IllegalArgumentException If the upload (once an id and creation/modification
     *             user/date are assigned) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error persisting the upload
     */
    public void createUpload(Upload upload, String operator) throws UploadPersistenceException;

    /**
     * Updates the upload in the persistence store. The id of the upload can not be
     * UNSET_ID and all necessary fields must have already been assigned.
     *
     * @param upload The upload to update
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If upload or operator is null
     * @throws IllegalArgumentException If the upload (once the modification user/date is set) is
     *             not in a state to be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error persisting the upload changes
     */
    public void updateUpload(Upload upload, String operator) throws UploadPersistenceException;

    /**
     * Removes the upload (by id) from the persistence store. The id of the upload can
     * not be UNSET_ID.
     *
     * @param upload The upload to remove
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If upload or operator is null
     * @throws IllegalArgumentException If the id of the upload is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUpload(Upload upload, String operator) throws UploadPersistenceException;

    /**
     * Gets the upload in the persistence store for the given id. Returns null if there
     * is no upload for the given id.
     *
     * @return The retrieved Upload, or null
     * @param id The id of the upload to retrieve
     * @throws IllegalArgumentException If id is <= 0
     * @throws UploadPersistenceException If there is an error retrieving the Upload from
     *             persistence
     */
    public Upload getUpload(long id) throws UploadPersistenceException;

    /**
     * Searches the persistence for uploads meeting the filter. The filter can be
     * easily built using the UploadFilterBuilder.
     *
     * @return The Uploads meeting the filter
     * @param filter The filter to use
     * @throws IllegalArgumentException If filter is null
     * @throws UploadPersistenceException If there is an error reading the upload from persistence
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the SearchBundle used by the manager is not
     *             configured for proper searching.
     */
    public Upload[] searchUploads(Filter filter) throws UploadPersistenceException, SearchBuilderException;

    /**
     * Creates a new UploadType in the persistence store. The id of the upload
     * type must be UNSET_ID. The manager will assign an id before persisting the change.
     *
     * @param uploadType The upload type to add to the persistence store
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If uploadType or operator is null
     * @throws IllegalArgumentException If the id is not UNSET_ID
     * @throws IllegalArgumentException If the upload type (once an id and creation/modification
     *             user/date are assigned) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void createUploadType(UploadType uploadType, String operator) throws UploadPersistenceException;

    /**
     * Updates the info for an UploadType in the persistence store. All fields of
     * the upload type must have values assigned when this method is called.
     *
     * @param uploadType The upload type to update
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If uploadType or operator is null
     * @throws IllegalArgumentException If the upload type (once the modification user/date is set)
     *             is not in a state to be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateUploadType(UploadType uploadType, String operator) throws UploadPersistenceException;

    /**
     * Removes the given upload type (by id) from the persistence. The id of the
     * uploadType can not be UNSET_ID.
     *
     * @param uploadType The upload type to remove
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If uploadType or operator is null
     * @throws IllegalArgumentException If the id of the upload type is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUploadType(UploadType uploadType, String operator) throws UploadPersistenceException;

    /**
     * Gets all the upload types in the persistence store.
     *
     * @return All UploadTypes in the persistence store.
     * @throws UploadPersistenceException If there is an error accessing the persistence store
     */
    public UploadType[] getAllUploadTypes() throws UploadPersistenceException;

    /**
     * Creates a new UploadStatus in the persistence store. The id of the upload
     * status must be UNSET_ID. The manager will assign an id before persisting the change.
     *
     * @param uploadStatus The upload status to add to the persistence store
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If uploadStatus or operator is null
     * @throws IllegalArgumentException If the id is not UNSET_ID
     * @throws IllegalArgumentException If the upload status (once an id and creation/modification
     *             user/date are assigned) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void createUploadStatus(UploadStatus uploadStatus, String operator)
        throws UploadPersistenceException;

    /**
     * Updates the info for an UploadStatus in the persistence store. All fields
     * of the upload status must have values assigned when this method is called.
     *
     * @param uploadStatus The upload status to update
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If uploadStatus or operator is null
     * @throws IllegalArgumentException If the upload status (once the modification user/date is
     *             set) is not in a state to be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateUploadStatus(UploadStatus uploadStatus, String operator)
        throws UploadPersistenceException;

    /**
     * Removes the given upload status (by id) from the persistence. The id of
     * the uploadStatus can not be UNSET_ID.
     *
     * @param uploadStatus The upload status to remove
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If uploadStatus or operator is null
     * @throws IllegalArgumentException If the id of the upload status is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeUploadStatus(UploadStatus uploadStatus, String operator)
        throws UploadPersistenceException;

    /**
     * Gets all the upload statuses in the persistence store.
     *
     * @return All UploadStatuses in the persistence store.
     * @throws UploadPersistenceException If there is an error accessing the persistence store
     */
    public UploadStatus[] getAllUploadStatuses() throws UploadPersistenceException;

    /**
     * Creates a new Submission in the persistence store. The id of the submission
     * must be UNSET_ID. The manager will assign an id before persisting the change.
     *
     * @param submission The submission to add to the persistence store
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If submission or operator is null
     * @throws IllegalArgumentException If the id is not UNSET_ID
     * @throws IllegalArgumentException If the submission (once an id and creation/modification
     *             user/date are assigned) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void createSubmission(Submission submission, String operator) throws UploadPersistenceException;

    /**
     * Updates the info for a Submission in the persistence store. All fields of
     * the submission must have values assigned when this method is called.
     *
     * @param submission The submission to update
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If submission or operator is null
     * @throws IllegalArgumentException If the submission (once the modification user/date is set)
     *             is not in a state to be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateSubmission(Submission submission, String operator) throws UploadPersistenceException;

    /**
     * Removes the given submission (by id) from the persistence. The id of the
     * submission can not be UNSET_ID.
     *
     * @param submission The submission to remove
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If submission or operator is null
     * @throws IllegalArgumentException If the id of the submission is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeSubmission(Submission submission, String operator) throws UploadPersistenceException;

    /**
     * Gets the Submission in the persistence store for the given id. Returns null if
     * there is no submission for the given id.
     *
     * @return The retrieved Submission, or null
     * @param id The id of the submission to retrieve
     * @throws IllegalArgumentException If id is <= 0
     * @throws UploadPersistenceException If there is an error retrieving the Submission from
     *             persistence
     */
    public Submission getSubmission(long id) throws UploadPersistenceException;

    /**
     * Searches the persistence for submissions meeting the filter. The filter
     * can be easily built using the SubmissionFilterBuilder.
     *
     * @return The submissions meeting the filter
     * @param filter The filter to use
     * @throws IllegalArgumentException If filter is null
     * @throws UploadPersistenceException If there is an error reading the submissions from
     *             persistence
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the SearchBundle used by the manager is not
     *             configured for proper searching.
     */
    public Submission[] searchSubmissions(Filter filter) throws UploadPersistenceException,
            SearchBuilderException;

    /**
     * Creates a new SubmissionStatus in the persistence store. The id of
     * the submission status must be UNSET_ID. The manager will assign an id before persisting the
     * change.
     *
     * @param submissionStatus The submission status to add to the persistence store
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If submissionStatus or operator is null
     * @throws IllegalArgumentException If the id is not UNSET_ID
     * @throws IllegalArgumentException If the submission status (once an id and
     *             creation/modification user/date are assigned) is not in a state to be persisted
     *             (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void createSubmissionStatus(SubmissionStatus submissionStatus, String operator)
        throws UploadPersistenceException;

    /**
     * Updates the info for submission status in the persistence store. All
     * fields of the submission status must have values assigned when this method is called.
     *
     * @param submissionStatus The submission status to update
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If submissionStatus or operator is null
     * @throws IllegalArgumentException If the submission status (once the modification user/date is
     *             set) is not in a state to be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus, String operator)
        throws UploadPersistenceException;

    /**
     * Removes the given submission status (by id) from the persistence. The
     * id of the submissionStatus can not be UNSET_ID.
     *
     * @param submissionStatus The submission status to remove
     * @param operator The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException If submissionStatus or operator is null
     * @throws IllegalArgumentException If the id of the submission status is UNSET_ID
     * @throws UploadPersistenceException If there is an error making the change in the persistence
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus, String operator)
        throws UploadPersistenceException;

    /**
     * Gets all the submission statuses in the persistence store.
     *
     * @return All SubmissionStatuses in the persistence store.
     * @throws UploadPersistenceException If there is an error accessing the persistence store
     */
    public SubmissionStatus[] getAllSubmissionStatuses() throws UploadPersistenceException;
}
