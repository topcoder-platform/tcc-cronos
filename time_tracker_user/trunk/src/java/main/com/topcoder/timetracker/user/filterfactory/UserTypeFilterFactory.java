/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.filterfactory;

import java.util.Date;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.Util;

/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * UserTypes. It offers a convenient way of specifying search criteria to use. The factory is capable of producing
 * filters that conform to a specific schema, and is associated with the UserTypeManager that supports the given
 * schema.
 * </p>
 * <p>
 * The filters that are produced by this factory should only be used by the UserTypeManager or UserTypeDAO
 * implementation that produced this UserTypeFilterFactory instance. This ensures that the Filters can be
 * recognized by the utility.
 * </p>
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this factory with any of the
 * Composite Filters in the Search Builder Component (AndFilter, OrFilter, etc.)
 * </p>
 * <p>
 * Thread Safety: This class is thread-safe.
 * </p>
 *
 * @author George1, enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserTypeFilterFactory {

    /**
     * <p>
     * This is the alias to use with search bundle to specify a search by Company ID.
     * </p>
     */
    public static final String COMPANY_ID_COLUMN_NAME = "COMPANY_ID";

    /**
     * <p>
     * This is the alias to use with search bundle to specify a search by Description.
     * </p>
     */
    public static final String DESCRIPTION_COLUMN_NAME = "DESCRIPTION";

    /**
     * <p>
     * This is the alias to use with search bundle to specify a search by column 'active'.
     * </p>
     */
    public static final String ACTIVE_COLUMN_NAME = "ACTIVE";

    /**
     * <p>
     * This is the alias to use with search bundle to specify a search by Creation Date.
     * </p>
     */
    public static final String CREATION_DATE_COLUMN_NAME = "CREATION_DATE";

    /**
     * <p>
     * This is the alias to use with search bundle to specify a search by Creation User.
     * </p>
     */
    public static final String CREATION_USER_COLUMN_NAME = "CREATION_USER";

    /**
     * <p>
     * This is the alias to use with search bundle to specify a search by Modification Date.
     * </p>
     */
    public static final String MODIFICATION_DATE_COLUMN_NAME = "MODIFICATION_DATE";

    /**
     * <p>
     * This is the alias to use with search bundle to specify a search by Modification User.
     * </p>
     */
    public static final String MODIFICATION_USER_COLUMN_NAME = "MODIFICATION_USER";

    /**
     * <p>
     * Hidden constructor of the class to prevent class instantiation.
     * </p>
     */
    private UserTypeFilterFactory() {
        // nothing to do
    }

    /**
     * <p>
     * Creates a Filter that will select UserTypes based on the UserType's companyId.
     * </p>
     *
     * @param companyId
     *            An ID of the company to filter user statuses by.
     * @return A filter that will be based off the specified criteria.
     * @throws IllegalArgumentException
     *             if parameter companyId is &lt;= 0.
     */
    public static Filter createCompanyIdFilter(long companyId) {
        return Util.createLongFilter(companyId, "companyId", COMPANY_ID_COLUMN_NAME);
    }

    /**
     * <p>
     * Creates a Filter that will select UserTypes based on the UserType's description.
     * </p>
     *
     * @param description
     *            Full description to filter user type by.
     * @return A filter that will be based off the specified criteria.
     * @throws IllegalArgumentException
     *             if parameter description is null or empty (trimmed) string.
     */
    public static Filter createDescriptionFilter(String description) {
        Util.checkString(description, "description");

        return new EqualToFilter(DESCRIPTION_COLUMN_NAME, description);
    }

    /**
     * <p>
     * Creates a Filter that will select UserTypes based on the UserType's active flag.
     * </p>
     *
     * @param active
     *            A boolean value that specifies whether only active or inactive user types should be filtered.
     * @return A filter that will be based off the specified criteria.
     */
    public static Filter createActiveFilter(boolean active) {
        return Util.createBooleanFilter(active, ACTIVE_COLUMN_NAME);
    }

    /**
     * <p>
     * Creates a Filter based on the Creation Date of the entity. A date range that may be open-ended can be
     * specified.
     * </p>
     *
     * @return A filter that will be based off the specified criteria.
     * @param rangeStart
     *            The lower bound of the date criterion.
     * @param rangeEnd
     *            The upper bound of the date criterion.
     * @throws IllegalArgumentException
     *             the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
     */
    public static Filter createCreationDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createDateFilter(rangeStart, rangeEnd, CREATION_DATE_COLUMN_NAME);
    }

    /**
     * <p>
     * Creates a Filter based on the Modification Date of the entity. A date range that may be open-ended can be
     * specified.
     * </p>
     *
     * @return A filter that will be based off the specified criteria.
     * @param rangeStart
     *            The lower bound of the date criterion.
     * @param rangeEnd
     *            The upper bound of the date criterion.
     * @throws IllegalArgumentException
     *             the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
     */
    public static Filter createModificationDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createDateFilter(rangeStart, rangeEnd, MODIFICATION_DATE_COLUMN_NAME);
    }

    /**
     * <p>
     * Creates a Filter based on the Creation User of the entity.
     * </p>
     *
     * @param username
     *            The username used for searching.
     * @return A filter that will be based off the specified criteria.
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public static Filter createCreationUserFilter(String username) {
        Util.checkString(username, "username");

        return new EqualToFilter(CREATION_USER_COLUMN_NAME, username);
    }

    /**
     * <p>
     * Creates a Filter based on the Modification User of the entity.
     * </p>
     *
     * @param username
     *            The username used for searching.
     * @return A filter that will be based off the specified criteria.
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public static Filter createModificationUserFilter(String username) {
        Util.checkString(username, "username");

        return new EqualToFilter(MODIFICATION_USER_COLUMN_NAME, username);
    }
}
