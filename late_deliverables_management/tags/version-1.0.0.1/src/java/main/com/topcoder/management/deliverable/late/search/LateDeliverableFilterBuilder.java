/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.late.search;

import com.topcoder.management.deliverable.late.Helper;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a static helper class that provides factory methods for creating filters that can be used when searching
 * for late deliverables using LateDeliverableManagerImpl and possibly other implementations of
 * LateDeliverableManager.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public final class LateDeliverableFilterBuilder {
    /**
     * <p>
     * Empty private constructor.
     * </p>
     */
    private LateDeliverableFilterBuilder() {
        // Empty
    }

    /**
     * <p>
     * Creates a filter that selects late deliverables for the project with the given ID.
     * </p>
     *
     * @param projectId
     *            the ID of the project.
     *
     * @return the filter that selects late deliverables for the project with the given ID (not null).
     *
     * @throws IllegalArgumentException
     *             if projectId &lt;= 0.
     */
    public static Filter createProjectIdFilter(long projectId) {
        Helper.checkPositive(projectId, "projectId");

        return new EqualToFilter("projectId", projectId);
    }

    /**
     * <p>
     * Creates a filter that selects late deliverables for all projects with the specified status.
     * </p>
     *
     * @param projectStatusId
     *            the ID of the project status.
     *
     * @return the filter that selects late deliverables for all projects with the specified status (not null).
     *
     * @throws IllegalArgumentException
     *             if projectStatusId &lt;= 0.
     */
    public static Filter createProjectStatusIdFilter(long projectStatusId) {
        Helper.checkPositive(projectStatusId, "projectStatusId");

        return new EqualToFilter("projectStatusId", projectStatusId);
    }

    /**
     * <p>
     * Creates a filter that selects late deliverables for all projects from the specified category.
     * </p>
     *
     * @param projectCategoryId
     *            the ID of the project category.
     *
     * @return the filter that selects late deliverables for all projects from the specified category (not null).
     *
     * @throws IllegalArgumentException
     *             if projectCategoryId &lt;= 0.
     */
    public static Filter createProjectCategoryIdFilter(long projectCategoryId) {
        Helper.checkPositive(projectCategoryId, "projectCategoryId");

        return new EqualToFilter("projectCategoryId", projectCategoryId);
    }

    /**
     * <p>
     * Creates a filter that selects late deliverables for specific deliverable types.
     * </p>
     *
     * @param deliverableId
     *            the ID of the deliverable type.
     *
     * @return the filter that selects late deliverables for specific deliverable types (not null).
     *
     * @throws IllegalArgumentException
     *             if deliverableId &lt;= 0.
     */
    public static Filter createDeliverableIdFilter(long deliverableId) {
        Helper.checkPositive(deliverableId, "deliverableId");

        return new EqualToFilter("deliverableId", deliverableId);
    }

    /**
     * <p>
     * Creates a filter that selects late deliverables with forgiven flag equal to the specified value.
     * </p>
     *
     * @param forgiven
     *            the value of the forgiven flag of late deliverables to be selected.
     *
     * @return the filter that selects late deliverables with forgiven flag equal to the specified value (not null).
     */
    public static Filter createForgivenFilter(boolean forgiven) {
        return new EqualToFilter("forgiven", forgiven ? 1 : 0);
    }
}
