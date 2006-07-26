/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import com.topcoder.management.resource.Helper;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The ResourceRoleFilterBuilder class supports building filters for searching for ResourceRoles.
 * </p>
 *
 * <p>
 * This class consists of 2 parts. The first part consists of static Strings that name the fields that are available for
 * searching. All ResourceManager implementations should use SearchBundles that are configured to use these names. The
 * second part of this class consists of convenience methods to create common filters based on these field names.
 * Development-wise, this class should prove quite simply, as all methods are a single line.
 * </p>
 *
 * <p>
 * This class has only final static fields/methods, so mutability is not an issue. This class is thread-safe.
 * </p>
 *
 *
 *
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class ResourceRoleFilterBuilder {

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the resource role id.
     * <p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createResourceRoleIdFilter method.
     * </p>
     */
    public static final String RESOURCE_ROLE_ID_FIELD_NAME = "resource_role_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the resource role
     * name.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createNameFilter method.
     * </p>
     */
    public static final String NAME_FIELD_NAME = "name";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the phase type id of
     * the resource role.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createPhaseTypeIdFilter and createNoPhaseTypeFilter
     * methods.
     * </p>
     */
    public static final String PHASE_TYPE_ID_FIELD_NAME = "phase_type_id";

    /**
     * Private constructor to prevent instantiation.
     *
     */
    private ResourceRoleFilterBuilder() {
        // empty.
    }

    /**
     * Creates a filter that selects resource roles which have the given id.
     *
     * @return A filter for selecting resource roles which have the given id.
     * @param resourceRoleId
     *            The resource role id to filter on
     * @throws IllegalArgumentException
     *             If resourceRoleId is <= 0.
     */
    public static Filter createResourceRoleIdFilter(long resourceRoleId) {
        Helper.checkPositiveValue(resourceRoleId, "resourceRoleId");

        return SearchBundle.buildEqualToFilter(RESOURCE_ROLE_ID_FIELD_NAME, new Long(resourceRoleId));
    }

    /**
     * Creates a filter that selects resource roles which have the given name.
     *
     * @return A filter for selecting resource roles which have the given name.
     * @param name
     *            The resource role name to filter on
     * @throws IllegalArgumentException
     *             If name is null
     */
    public static Filter createNameFilter(String name) {
        Helper.checkNull(name, "name");

        return SearchBundle.buildEqualToFilter(NAME_FIELD_NAME, name);
    }

    /**
     * Creates a filter that selects resource roles which are related to the given phase type.
     *
     * @return A filter for selecting resource roles associated with the given phase type id
     * @param phaseTypeId
     *            The phase type id to create the filter with.
     * @throws IllegalArgumentException
     *             If phaseTypeId is <= 0
     */
    public static Filter createPhaseTypeIdFilter(long phaseTypeId) {
        Helper.checkPositiveValue(phaseTypeId, "phaseTypeId");

        return SearchBundle.buildEqualToFilter(PHASE_TYPE_ID_FIELD_NAME, new Long(phaseTypeId));
    }

    /**
     * Creates a filter that selects resources which do not have an associated phase.
     *
     * @return A filter for selecting ResourceRoles that are not associated with a phase type
     */
    public static Filter createNoPhaseTypeFilter() {
        return null;
    }
}
