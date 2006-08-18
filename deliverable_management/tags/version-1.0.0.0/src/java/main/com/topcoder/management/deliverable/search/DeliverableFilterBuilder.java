/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.search;

import com.topcoder.management.deliverable.DeliverableHelper;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The DeliverableFilterBuilder class supports building filters for searching for Deliverables.
 * </p>
 * <p>
 * This class has only final static fields/methods, so mutability is not an issue.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0
 */
public class DeliverableFilterBuilder {

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Deliverable.
     */
    private static final String DELIVERABLE_ID_FIELD_NAME = "deliverable_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the name of the Deliverable.
     */
    private static final String NAME_FIELD_NAME = "name";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the project of the Deliverable.
     */
    private static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the phase the Deliverable falls in.
     */
    private static final String PHASE_ID_FIELD_NAME = "phase_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the submission the Deliverable is associated with.
     */
    private static final String SUBMISSION_ID_FIELD_NAME = "submission_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the resource id of the Deliverable.
     */
    private static final String RESOURCE_ID_FIELD_NAME = "resource_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * whether the Deliverable is required or not.
     */
    private static final String REQUIRED_FIELD_NAME = "required";

    /**
     * Private constructor to prevent instantiation.
     */
    private DeliverableFilterBuilder() {
        // no operation.
    }

    /**
     * Creates a filter that selects deliverables with the given id.
     *
     * @return A filter for selecting deliverables by id
     * @param deliverableId The id of the deliverable
     * @throws IllegalArgumentException If deliverableId is <= 0
     */
    public static Filter createDeliverableIdFilter(long deliverableId) {
        DeliverableHelper.checkGreaterThanZero(deliverableId, "deliverableId");
        return SearchBundle.buildEqualToFilter(DELIVERABLE_ID_FIELD_NAME, new Long(deliverableId));
    }

    /**
     * Creates a filter that selects deliverables that fall have the given name.
     *
     * @return A filter for selecting deliverables with the given name
     * @param name The name to filter on
     * @throws IllegalArgumentException If name is null
     */
    public static Filter createNameFilter(String name) {
        DeliverableHelper.checkObjectNotNull(name, "name");
        return SearchBundle.buildEqualToFilter(NAME_FIELD_NAME, name);
    }

    /**
     * Creates a filter that selects deliverables related to the project with the given id.
     *
     * @return A filter for selecting deliverables related to the given project
     * @param projectId The project id to filter on
     * @throws IllegalArgumentException If projectId is <= 0
     */
    public static Filter createProjectIdFilter(long projectId) {
        DeliverableHelper.checkGreaterThanZero(projectId, "projectId");
        return SearchBundle.buildEqualToFilter(PROJECT_ID_FIELD_NAME, new Long(projectId));
    }

    /**
     * Creates a filter that selects deliverables that fall in the phase with the given id.
     *
     * @return A filter for selecting deliverables for the given phase
     * @param phaseId The phase id to filter on
     * @throws IllegalArgumentException If phaseId is <= 0
     */
    public static Filter createPhaseIdFilter(long phaseId) {
        DeliverableHelper.checkGreaterThanZero(phaseId, "phaseId");
        return SearchBundle.buildEqualToFilter(PHASE_ID_FIELD_NAME, new Long(phaseId));
    }

    /**
     * Creates a filter that selects deliverables that are related to the given submission.
     *
     * @return A filter for selecting deliverables for the given submission
     * @param submissionId The submission id to filter on
     * @throws IllegalArgumentException If submissionId is <= 0
     */
    public static Filter createSubmissionIdFilter(long submissionId) {
        DeliverableHelper.checkGreaterThanZero(submissionId, "submissionId");
        return SearchBundle.buildEqualToFilter(SUBMISSION_ID_FIELD_NAME, new Long(submissionId));
    }

    /**
     * Creates a filter that selects deliverables that are related to the given resource.
     *
     * @return A filter for selecting deliverables for the given resource
     * @param resourceId The resource id to filter on
     * @throws IllegalArgumentException If resourceId is <= 0
     */
    public static Filter createResourceIdFilter(long resourceId) {
        DeliverableHelper.checkGreaterThanZero(resourceId, "resourceId");
        return SearchBundle.buildEqualToFilter(RESOURCE_ID_FIELD_NAME, new Long(resourceId));
    }

    /**
     * Creates a filter that selects deliverables that fall on whether or not the deliverable is
     * required.
     *
     * @return A filter for selecting deliverables that are (or are not) required
     * @param isRequired Whether or not deliverable is required
     */
    public static Filter createRequiredFilter(boolean isRequired) {
        // Build the filter according to the fact that SQL Boolean false = 0 and SQL Boolean true != 0
        return (isRequired)
                ? SearchBundle.buildNotFilter(SearchBundle.buildEqualToFilter(REQUIRED_FIELD_NAME, new Integer(0)))
                : SearchBundle.buildEqualToFilter(REQUIRED_FIELD_NAME, new Integer(0));
    }
}
