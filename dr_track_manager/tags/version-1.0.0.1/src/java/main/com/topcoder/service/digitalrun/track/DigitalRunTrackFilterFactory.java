/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import com.topcoder.util.errorhandling.ExceptionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * This class provides static methods that create filters that can be used by the <code>DigitalRunTrackManager</code>
 * implementations  to retrieve track related entities, that match the filter criteria, from the persistence.
 * </p>
 *
 * <p>
 * The methods provided return filters with:
 *
 * <ul>
 * <li>
 * -equals/in operation for track id/track status id/track type id/project type id
 * </li>
 * <li>
 * -equals/like operation track description
 * </li>
 * <li>
 * -equals/greater or equal than/lower or equal than for track start date and track end date
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * This class also provides and/or/not filters so that the user does not need to interact with Search Builder component
 * to create such filters. This class is thread safe since it has no state.
 * </p>
 *
 * @author DanLazar, waits
 * @version 1.0
 */
public class DigitalRunTrackFilterFactory {
    /** Represents the track id searchable field. It is a constant. */
    public static final String TRACK_ID = "track_id";

    /** Represents the track status id searchable field. It is a constant. */
    public static final String TRACK_STATUS_ID = "track_status_id";

    /** Represents the track description field. It is a constant. */
    public static final String TRACK_DESCRIPTION = "track_desc";

    /** Represents the track type id searchable field. It is a constant. */
    public static final String TRACK_TYPE_ID = "track_type_id";

    /** Represents the track project type id searchable field. It is a constant. */
    public static final String TRACK_PROJECT_TYPE_ID = "project_type_id";

    /** Represents the track start date searchable field. It is a constant. */
    public static final String TRACK_START_DATE = "track_start_date";

    /** Represents the track end date searchable field. It is a constant. */
    public static final String TRACK_END_DATE = "track_end_date";

    /**
     * <p>
     * Empty constructor. Stop from initialization.
     * </p>
     */
    private DigitalRunTrackFilterFactory() {
    }

    /**
     * <p>
     * Creates a filter that will be used to return the row, from track table, for which track_id=trackId.
     * </p>
     *
     * @param trackId the track id used to create the filter
     *
     * @return the created Filter instance
     *
     * @throws IllegalArgumentException if argument is negative
     */
    public static Filter createTrackIdEqualsFilter(long trackId) {
        Helper.checkNegative(trackId, "trackId", null);

        return new EqualToFilter(TRACK_ID, trackId);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which track_id is in the ids
     * contained by the trackIds list.
     * </p>
     *
     * @param trackIds the track ids used to create the filter
     *
     * @return the created Filter instance
     *
     * @throws IllegalArgumentException the argument list is null or empty or if it contain null elements or if the
     *         value (Long.longValue()) of the elements is negative
     */
    public static Filter createTrackIdInFilter(List < Long > trackIds) {
        Helper.checkLongList(trackIds, "trackIds");

        return new InFilter(TRACK_ID, copyList(trackIds));
    }

    /**
     * <p>
     * Creates a filter that will be used to return the row, from track table, for which track_status_id=trackStatusId.
     * </p>
     *
     * @param trackStatusId the track status id used to create the filter
     *
     * @return the created Filter instance
     *
     * @throws IllegalArgumentException if argument is negative
     */
    public static Filter createTrackStatusIdEqualsFilter(long trackStatusId) {
        Helper.checkNegative(trackStatusId, "trackStatusId", null);

        return new EqualToFilter(TRACK_STATUS_ID, trackStatusId);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which track_status_id is in the ids
     * contained by the trackStatusIds list.
     * </p>
     *
     * @param trackStatusIds the track status ids used to create the filter
     *
     * @return the created Filter instance
     *
     * @throws IllegalArgumentException the argument list is null or empty or if it contain null elements or if the
     *         value (Long.longValue()) of the elements is negative
     */
    public static Filter createTrackStatusIdInFilter(List < Long > trackStatusIds) {
        Helper.checkLongList(trackStatusIds, "trackStatusIds");

        return new InFilter(TRACK_STATUS_ID, copyList(trackStatusIds));
    }

    /**
     * <p>
     * Creates a filter that will be used to return the row, from track table, for which track_desc=trackDescription.
     * </p>
     *
     * @param trackDescription the track description used to create the filter
     *
     * @return the created Filter instance
     *
     * @throws IllegalArgumentException if argument is null or empty string
     */
    public static Filter createTrackDescriptionEqualsFilter(String trackDescription) {
        ExceptionUtils.checkNullOrEmpty(trackDescription, null, null, "trackDescription");

        return new EqualToFilter(TRACK_DESCRIPTION, trackDescription);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which track_desc is "like" value
     * argument.
     * </p>
     *
     * @param value the value used to create the filter
     *
     * @return the created Filter instance
     *
     * @throws IllegalArgumentException if value is null or empty or value does not begin with 'SS:', 'SW:', 'EW:',
     *         'WC:' or value is zero length without the prefix
     */
    public static Filter createTrackDescriptionLikeFilter(String value) {
        return new LikeFilter(TRACK_DESCRIPTION, value);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which track_desc is "like" value
     * argument.
     * </p>
     *
     * @param value the value passed to the filter
     * @param escapeCharacter the escape character passed to the filter
     *
     * @return the created filter instance
     *
     * @throws IllegalArgumentException if value is null or empty or value does not begin with 'SS:', 'SW:', 'EW:',
     *         'WC:',or value is zero length without the prefix, or escapeCharacter is of some special invalid chars
     */
    public static Filter createTrackDescriptionLikeFilter(String value, char escapeCharacter) {
        return new LikeFilter(TRACK_DESCRIPTION, value, escapeCharacter);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the row, from track table, for which track_type_id=trackTypeId.
     * </p>
     *
     * @param trackTypeId the track type id used to create the filter
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException if argument is negative
     */
    public static Filter createTrackTypeIdEqualsFilter(long trackTypeId) {
        Helper.checkNegative(trackTypeId, "trackTypeId", null);

        return new EqualToFilter(TRACK_TYPE_ID, trackTypeId);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which track_type_id is in the ids
     * contained by the trackTypeIds list.
     * </p>
     *
     * @param trackTypeIds the track type ids used to create the filter
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException the argument list is null or empty or if it contain null elements or if the
     *         value (Long.longValue()) of the elements is negative
     */
    public static Filter createTrackTypeIdInFilter(List < Long > trackTypeIds) {
        Helper.checkLongList(trackTypeIds, "trackTypeIds");

        return new InFilter(TRACK_TYPE_ID, copyList(trackTypeIds));
    }

    /**
     * <p>
     * Creates a filter that will be used to return the row, from track_project_type_xref table, for which
     * project_type_id=projectTypeId.
     * </p>
     *
     * @param projectTypeId the project type id used to create the filter
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException if argument is negative
     */
    public static Filter createTrackProjectTypeIdEqualsFilter(long projectTypeId) {
        Helper.checkNegative(projectTypeId, "projectTypeId", null);

        return new EqualToFilter(TRACK_PROJECT_TYPE_ID, projectTypeId);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track_project_type_xref table, for which
     * project_type_id is in the ids contained by the projectTypeIds list.
     * </p>
     *
     * @param projectTypeIds the project type ids used to create the filter
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException the argument list is null or empty or if it contain null elements or if the
     *         value (Long.longValue()) of the elements is negative
     */
    public static Filter createTrackProjectTypedInFilter(List < Long > projectTypeIds) {
        Helper.checkLongList(projectTypeIds, "projectTypeIds");

        return new InFilter(TRACK_PROJECT_TYPE_ID, copyList(projectTypeIds));
    }

    /**
     * <p>
     * Creates a filter that will be used to return the row, from track table, for which
     * track_start_date=trackStartDate.
     * </p>
     *
     * @param trackStartDate the track start date
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException if argument is null
     */
    public static Filter createTrackStartDateEqualsFilter(Date trackStartDate) {
        ExceptionUtils.checkNull(trackStartDate, null, null, "Track.StartDate");

        return new EqualToFilter(TRACK_START_DATE, trackStartDate);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which
     * track_start_date&gt;=trackStartDate.
     * </p>
     *
     * @param trackStartDate the track start date
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException if argument is null
     */
    public static Filter createTrackStartDateGreaterOrEqualFilter(Date trackStartDate) {
        ExceptionUtils.checkNull(trackStartDate, null, null, "Track.StartDate");

        return new GreaterThanOrEqualToFilter(TRACK_START_DATE, trackStartDate);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which
     * track_start_date&lt;=trackStartDate.
     * </p>
     *
     * @param trackStartDate the trackStart date
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException if argument is null
     */
    public static Filter createTrackStartDateLowerOrEqualFilter(Date trackStartDate) {
        ExceptionUtils.checkNull(trackStartDate, null, null, "Track.StartDate");

        return new LessThanOrEqualToFilter(TRACK_START_DATE, trackStartDate);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the row, from track table, for which track_end_date=trackEndDate.
     * </p>
     *
     * @param trackEndDate the track end date
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException if argument is null
     */
    public static Filter createTrackEndDateEqualsFilter(Date trackEndDate) {
        ExceptionUtils.checkNull(trackEndDate, null, null, "Track.EndDate");

        return new EqualToFilter(TRACK_END_DATE, trackEndDate);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which track_end_date
     * &gt;=trackEndDate.
     * </p>
     *
     * @param trackEndDate the track end date
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException if argument is null
     */
    public static Filter createTrackEndDateGreaterOrEqualFilter(Date trackEndDate) {
        ExceptionUtils.checkNull(trackEndDate, null, null, "Track.EndDate");

        return new GreaterThanOrEqualToFilter(TRACK_END_DATE, trackEndDate);
    }

    /**
     * <p>
     * Creates a filter that will be used to return the rows, from track table, for which track_end_date &lt;=
     * trackEndDate.
     * </p>
     *
     * @param trackEndDate the track end date
     *
     * @return the created filter
     *
     * @throws IllegalArgumentException if argument is null
     */
    public static Filter createTrackEndDateLowerOrEqualFilter(Date trackEndDate) {
        ExceptionUtils.checkNull(trackEndDate, null, null, "Track.EndDate");

        return new LessThanOrEqualToFilter(TRACK_END_DATE, trackEndDate);
    }

    /**
     * <p>
     * Creates an "and" filter from the given filters.
     * </p>
     *
     * @param filters the filters
     *
     * @return an AndFilter instance constructed from the given filters
     *
     * @throws IllegalArgumentException if the argument list is null or empty or if it contain null elements
     */
    public static Filter createAndFilter(List < Filter > filters) {
        Helper.checkList(filters, "filters");

        return new AndFilter(copyList(filters));
    }

    /**
     * <p>
     * Creates an "or" filter from the given filters.
     * </p>
     *
     * @param filters the filters
     *
     * @return an OrFilter instance constructed from the given filters
     *
     * @throws IllegalArgumentException if the argument list is null or empty or if it contain null elements
     */
    public static Filter createOrFilter(List < Filter > filters) {
        Helper.checkList(filters, "filters");

        return new OrFilter(copyList(filters));
    }

    /**
     * <p>
     * Shallow copy the filters.
     * </p>
     *
     * @param filters list of filters to copy
     * @param <T> the class type in list
     * @return List of filter
     */
    private static < T > List < T > copyList(List < T > filters) {
        List < T > fs = new ArrayList<T>();
        fs.addAll(filters);

        return fs;
    }

    /**
     * <p>
     * Creates an "not" filter from the given filter.
     * </p>
     *
     * @param filter the filter
     *
     * @return a NotFilter instance constructed from the given filter
     *
     * @throws IllegalArgumentException if the argument is null
     */
    public static Filter createNotFilter(Filter filter) {
        ExceptionUtils.checkNull(filter, null, null, "Filter");

        return new NotFilter(filter);
    }
}
