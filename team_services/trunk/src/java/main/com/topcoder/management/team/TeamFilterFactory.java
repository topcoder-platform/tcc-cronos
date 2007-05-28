package com.topcoder.management.team;

import com.topcoder.search.builder.filter.*;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>
 * This is a convenience class for creating filters to search for teams that match specific
 * properties and values. Searches can be done on a name, description, projectId, finalized, and
 * captainResourceId value, or to test for the presence of a specific custom property key. The
 * constants used as search criterion names are provided by this class for each factory method.
 * </p>
 * <p>
 * To combine filters, the UtilityFilterFactory can be used.
 * </p>
 * <p>
 * Thread Safety: This is a static, immutable thread-safe class.
 * </p>
 */
public class TeamFilterFactory {

    /**
     * Represents the field name of the search criterion for a team name. Used in the
     * createNameFilter factory method.
     */
    public static final String NAME = "name";

    /**
     * Represents the field name of the search criterion for a team description. Used in the
     * createDescriptionFilter factory method.
     */
    public static final String DESCRIPTION = "description";

    /**
     * Represents the field name of the search criterion for a team's project ID. Used in the
     * createProjectIdFilter factory method.
     */
    public static final String PROJECT_ID = "projectId";

    /**
     * Represents the field name of the search criterion for a team flag of either being finalized
     * or not. Used in the createFinalizedFilter factory method.
     */
    public static final String FINALIZED = "finalized";

    /**
     * Represents the field name of the search criterion for a team captain's resource ID. Used in
     * the createCaptainResourceIdFilter factory method.
     */
    public static final String CAPTAIN_RESOURCE_ID = "captainResourceId";

    /**
     * Represents the field name of the search criterion for a team custom property name. Used in
     * the createCustomPropertyNameFilter factory method.
     */
    public static final String CUSTOM_PROPERTY_NAME = "key";

    /**
     * Empty private constructor.
     */
    private TeamFilterFactory() {
        /** lock-end */
        // your code here
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Teams with the given name.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(NAME, name)</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param name
     *            The name to match Teams with
     * @throws IllegalArgumentException
     *             if name is null/empty
     */
    public static Filter createNameFilter(String name) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Teams with the given project ID.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(PROJECT_ID, new Long(projectId))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param description
     *            The description to match Teams with
     */
    public static Filter createDescriptionFilter(String description) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Teams with the given project ID.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(PROJECT_ID, new Long(projectId))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param projectId
     *            The project ID to match teams with
     * @throws IllegalArgumentException
     *             if projectId is negative
     */
    public static Filter createProjectIdFilter(long projectId) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Teams with the given finalized flag value.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>If finalized == true, return new EqualToFilter(FINALIZED,
     * UtilityFilterFactory.BOOLEAN_TRUE)</li>
     * <li>Else return new EqualToFilter(FINALIZED, UtilityFilterFactory.BOOLEAN_FALSE)</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param finalized
     *            The finalized flag to match teams with
     */
    public static Filter createFinalizedFilter(boolean finalized) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Teams with the given captain resource ID.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(PROJECT_ID, new Long(captainResourceId))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param captainResourceId
     *            The captain resource ID to match teams with
     * @throws IllegalArgumentException
     *             if projectId is negative
     */
    public static Filter createCaptainResourceIdFilter(long captainResourceId) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Teams that have a custom property with the given name.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(CUSTOM_PROPERTY_NAME, name)</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param name
     *            The name of a custom property to match Teams with
     * @throws IllegalArgumentException
     *             if name is null/empty
     */
    public static Filter createCustomPropertyNameFilter(String name) {
        /** lock-end */
        return null;
    }
    /** lock-begin */
}
