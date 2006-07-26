/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.search;

import com.topcoder.management.deliverable.DeliverableHelper;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The SubmissionFilterBuilder class supports building filters for searching for Submissions.
 * </p>
 * <p>
 * This class has only final static fields/methods, so mutability is not an issue.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0
 */
public class SubmissionFilterBuilder {

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Submission.
     */
    private static final String SUBMISSION_ID_FIELD_NAME = "submission_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Upload that is associated with the Submission.
     */
    private static final String UPLOAD_ID_FIELD_NAME = "upload_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the project the submission is associated with.
     */
    private static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the resource the Submission is associated with.
     */
    private static final String RESOURCE_ID_FIELD_NAME = "resource_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the submission status of the Submissions.
     */
    private static final String SUBMISSION_STATUS_ID_FIELD_NAME = "submission_status_id";

    /**
     * Private constructor to prevent instantiation.
     */
    private SubmissionFilterBuilder() {
        // no operation.
    }

    /**
     * Creates a filter that selects the submission with the given id.
     *
     * @return A filter for selecting submissions with the given id
     * @param submissionId The submission id to filter on
     * @throws IllegalArgumentException If submissionId is <= 0
     */
    public static Filter createSubmissionIdFilter(long submissionId) {
        DeliverableHelper.checkGreaterThanZero(submissionId, "submissionId");
        return SearchBundle.buildEqualToFilter(SUBMISSION_ID_FIELD_NAME, new Long(submissionId));
    }

    /**
     * Creates a filter that selects submissions related to the given upload.
     *
     * @return A filter for selecting submissions related to the given upload
     * @param uploadId The upload id to filter on
     * @throws IllegalArgumentException If uploadId is <= 0
     */
    public static Filter createUploadIdFilter(long uploadId) {
        DeliverableHelper.checkGreaterThanZero(uploadId, "uploadId");
        return SearchBundle.buildEqualToFilter(UPLOAD_ID_FIELD_NAME, new Long(uploadId));
    }

    /**
     * Creates a filter that selects submissions related to the project with the given id.
     *
     * @return A filter for selecting submissions related to the given project
     * @param projectId The project id to filter on
     * @throws IllegalArgumentException If projectId is <= 0
     */
    public static Filter createProjectIdFilter(long projectId) {
        DeliverableHelper.checkGreaterThanZero(projectId, "projectId");
        return SearchBundle.buildEqualToFilter(PROJECT_ID_FIELD_NAME, new Long(projectId));
    }

    /**
     * Creates a filter that selects submissions related to the given resource id.
     *
     * @return A filter for selecting submissions related to the given resource
     * @param resourceId The resource id to filter on
     * @throws IllegalArgumentException If resourceId is <= 0
     */
    public static Filter createResourceIdFilter(long resourceId) {
        DeliverableHelper.checkGreaterThanZero(resourceId, "resourceId");
        return SearchBundle.buildEqualToFilter(RESOURCE_ID_FIELD_NAME, new Long(resourceId));
    }

    /**
     * Creates a filter that selects submissions related having the given submission status id.
     *
     * @return A filter for selecting submissions with the given status
     * @param submissionStatusId The submission status id to filter on
     * @throws IllegalArgumentException If submissionStatusId is <= 0
     */
    public static Filter createSubmissionStatusIdFilter(long submissionStatusId) {
        DeliverableHelper.checkGreaterThanZero(submissionStatusId, "submissionStatusId");
        return SearchBundle.buildEqualToFilter(SUBMISSION_STATUS_ID_FIELD_NAME, new Long(submissionStatusId));
    }
}
