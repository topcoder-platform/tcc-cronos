/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.util.errorhandling.ExceptionUtils;


/**
 * <p>
 * This is a factory for the creation of Filters which based on some specific criteria will return
 * instances of <code>TrackContest</code> (i.e. the Filter would return such instances)
 * </p>
 *
 * <p>
 * We currently support the following filters:
 * <pre>
 *    ------------------------------|---------------------------------
 *         Search By                |    Supported Operation
 *    ----------------------------------------------------------------
 *         Contest ID               |    Equals/In
 *         Contest Type ID          |    Equals/In
 *         Contest Desc             |    Equals/In/Like
 *         Track Id                 |    Equals/In
 *    ----------------------------------------------------------------
 * </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This is an utility class with no state. This is thread safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class ContestFilterFactory {

    /**
     * <p>
     * Represents the field name of the search criterion for a <code>TrackContest</code> using a contest ID.
     * </p>
     *
     * <p>
     * Used in the <code>createContestIdEqualsFilter()</code> and <code>createContestIdInFilter()</code> factory
     * methods.
     * </p>
     */
    public static final String CONTEST_ID = "track_contest_id";

    /**
     * <p>
     * Represents the field name of the search criterion for a <code>TrackContest</code> using a contest type ID.
     * </p>
     *
     * <p>
     * Used in the <code>createContestTypeIdEqualsFilter()</code> and <code>createContestTypeIdInFilter()</code>
     * factory methods.
     * </p>
     */
    public static final String CONTEST_TYPE_ID = "track_contest_type_id";

    /**
     * <p>
     * Represents the field name of the search criterion for a <code>TrackContest</code> using a track ID.
     * </p>
     *
     * <p>
     * Used in the <code>createTrackIdEqualsFilter()</code> and <code>createTrackIdInFilter()</code>
     * factory methods.
     * </p>
     */
    public static final String TRACK_ID = "track_id";

    /**
     * <p>
     * Represents the field name of the search criterion for a <code>TrackContest</code> using the description
     * of the track contest.
     * </p>
     *
     * <p>
     * Used in the <code>createContestDescriptionEqualsFilter()</code> and <code>createContestDescriptionLikeFilter()
     * </code> factory methods.
     * </p>
     */
    public static final String CONTEST_DESCRIPTION = "track_content_description";

    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private ContestFilterFactory() {
        // empty
    }

    /**
     * <p>
     * Check negative.
     * </p>
     *
     * @param id To be checked.
     * @param usage The usage of the id.
     *
     * @throws IllegalArgumentException if given id is negative.
     */
    private static void checkNegative(long id, String usage) {
        if (id < 0) {
            throw new IllegalArgumentException(usage + " should not be negative.");
        }
    }

    /**
     * <p>
     * Create a filter that returns the <code>TrackContest</code> with the given contest ID.
     * Note that this <code>Filter</code> will return at most one result.
     * </p>
     *
     * @param contestId The id of the <code>TrackContest</code> that we would like to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest</code> with the
     *         given contest ID.
     *
     * @throws IllegalArgumentException if the provided id is negative.
     */
    public static Filter createContestIdEqualsFilter(long contestId) {
        checkNegative(contestId, "Contest id");
        return new EqualToFilter(CONTEST_ID, contestId);
    }

    /**
     * <p>
     * Create a filter that returns all the matching <code>TrackContest(s)</code> with the given contest IDs.
     * Note that this <code>Filter</code> will return 0..N <code>TrackContest(s)</code>.
     * </p>
     *
     * @param contestIds The array of ids of the <code>TrackContest(s)</code> that we would like to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest(s)</code> with the
     *         given contest IDs.
     *
     * @throws IllegalArgumentException if the provided array is null/empty or any of the ids in the array is negative.
     */
    @SuppressWarnings("unchecked")
    public static Filter createContestIdsInFilter(long[] contestIds) {
        return new InFilter(CONTEST_ID, new ArrayList(checkLongArray(contestIds, "Contest ID")));
    }

    /**
     * <p>
     * Check an array of ids.
     * </p>
     *
     * @param ids to check.
     * @param usage represents the usage.
     *
     * @return The set of ids.
     *
     * @throws IllegalArgumentException if the provided array is null/empty or any of the ids in the array is negative.
     */
    @SuppressWarnings("unchecked")
    private static Set < Long > checkLongArray(long[] ids, String usage) {
        ExceptionUtils.checkNull(ids, null, null, "The array of " + usage + " should not be null.");
        if (ids.length == 0) {
            throw new IllegalArgumentException("The array of " + usage + " should not be empty.");
        }

        Set < Long > result = new HashSet();

        for (long id : ids) {
            checkNegative(id, usage);
            result.add(id);
        }

        return result;
    }

    /**
     * <p>
     * Create a filter that returns the <code>TrackContest</code> with the given contest type ID.
     * Note that this <code>Filter</code> will return at most one result.
     * </p>
     *
     * @param contestTypeId The track contest type id of the <code>TrackContest</code> that we would like to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest</code> with the
     *         given track contest type ID.
     *
     * @throws IllegalArgumentException if the provided id is negative.
     */
    public static Filter createContestTypeIdEqualsFilter(long contestTypeId) {
        checkNegative(contestTypeId, "Contest type id");
        return new EqualToFilter(CONTEST_TYPE_ID, contestTypeId);
    }

    /**
     * <p>
     * Create a filter that returns all the matching <code>TrackContest(s)</code> with the given contest type IDs.
     * Note that this <code>Filter</code> will return 0..N <code>TrackContest(s)</code>.
     * </p>
     *
     * @param contestTypeIds The array of track contest type ids of the <code>TrackContest(s)</code> that we would like
     *        to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest(s)</code> with the
     *         given track contest type IDs.
     *
     * @throws IllegalArgumentException if the provided array is null/empty or any of the ids in the array is negative.
     */
    @SuppressWarnings("unchecked")
    public static Filter createContestTypeIdsInFilter(long[] contestTypeIds) {
        return new InFilter(CONTEST_TYPE_ID, new ArrayList(checkLongArray(contestTypeIds, "Contest type ID")));
    }

    /**
     * <p>
     * Create a filter that returns the <code>TrackContest</code> with the given track ID.
     * Note that this <code>Filter</code> will return at most one result.
     * </p>
     *
     * @param trackId The track id of the <code>TrackContest</code> that we would like to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest</code> with the
     *         given track ID.
     *
     * @throws IllegalArgumentException if the provided id is negative.
     */
    public static Filter createTrackIdEqualsFilter(long trackId) {
        checkNegative(trackId, "Track id");
        return new EqualToFilter(TRACK_ID, trackId);
    }

    /**
     * <p>
     * Create a filter that returns all the matching <code>TrackContest(s)</code> with the given track IDs.
     * Note that this <code>Filter</code> will return 0..N <code>TrackContest(s)</code>.
     * </p>
     *
     * @param trackIds The array of track ids of the <code>TrackContest(s)</code> that we would like
     *        to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest(s)</code> with the
     *         given track IDs.
     *
     * @throws IllegalArgumentException if the provided array is null/empty or any of the ids in the array is negative.
     */
    @SuppressWarnings("unchecked")
    public static Filter createTrackIdsInFilter(long[] trackIds) {
        return new InFilter(TRACK_ID, new ArrayList(checkLongArray(trackIds, "Track ID")));
    }

    /**
     * <p>
     * Creates an AND filter with the passed filters.
     * </p>
     *
     * @param first The left side filter to be combined with an AND.
     * @param second The right side filter to be combined with an AND.
     *
     * @return The resulting filter.
     *
     * @throws IllegalArgumentException if any of the input filters is null.
     */
    public static Filter createAndFilter(Filter first, Filter second) {
        ExceptionUtils.checkNull(first, null, null, "First filter should not be null.");
        ExceptionUtils.checkNull(second, null, null, "Second filter should not be null.");
        return new AndFilter(first, second);
    }

    /**
     * <p>
     * Creates an AND filter with the passed filters.
     * </p>
     *
     * @param filters The filters to combine with a boolean AND filter.
     *
     * @return The resulting filter.
     *
     * @throws IllegalArgumentException if the input array is null, or if any filter in the array is null.
     */
    @SuppressWarnings("unchecked")
    public static Filter createAndFilter(Filter[] filters) {
        return new AndFilter(new ArrayList(checkFilterArray(filters)));
    }

    /**
     * <p>
     * Creates an OR filter with the passed filters.
     * </p>
     *
     * @param first The left side filter to be combined with an OR.
     * @param second The right side filter to be combined with an OR.
     *
     * @return The resulting filter.
     *
     * @throws IllegalArgumentException if any of the input filters is null.
     */
    public static Filter createOrFilter(Filter first, Filter second) {
        ExceptionUtils.checkNull(first, null, null, "First filter should not be null.");
        ExceptionUtils.checkNull(second, null, null, "Second filter should not be null.");
        return new OrFilter(first, second);
    }

    /**
     * <p>
     * Check an array of filters.
     * </p>
     *
     * @param filters to check.
     *
     * @return The set of filters.
     *
     * @throws IllegalArgumentException if the input array is null, or if any filter in the array is null.
     */
    @SuppressWarnings("unchecked")
    private static Set < Filter > checkFilterArray(Filter[] filters) {
        ExceptionUtils.checkNull(filters, null, null, "Array of filters should not be null.");
        Set < Filter > set = new HashSet();
        for (Filter filter : filters) {
            ExceptionUtils.checkNull(filter, null, null, "Filter should not be null.");
            set.add(filter);
        }
        return set;
    }

    /**
     * <p>
     * Creates an OR filter with the passed filters.
     * </p>
     *
     * @param filters The filters to combine with a boolean OR filter.
     *
     * @return The resulting filter.
     *
     * @throws IllegalArgumentException if the input array is null, or if any filter in the array is null.
     */
    @SuppressWarnings("unchecked")
    public static Filter createOrFilter(Filter[] filters) {
        return new OrFilter(new ArrayList(checkFilterArray(filters)));
    }

    /**
     * <p>
     * Creates a NOT filter of given filter.
     * </p>
     *
     * @param filter The filter to be reversed.
     *
     * @return The resulting filter.
     *
     * @throws IllegalArgumentException if the input filter is null.
     */
    public static Filter createNotFilter(Filter filter) {
        ExceptionUtils.checkNull(filter, null, null, "The filter to be reversed should not be null.");
        return new NotFilter(filter);
    }

    /**
     * <p>
     * Create a filter that returns the <code>TrackContest</code> with the given description.
     * Note that this <code>Filter</code> will return at most one result. This is a case sensitive matching.
     * </p>
     *
     * @param description The description of the <code>TrackContest</code> that we would like to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest</code> with
     *         the given description.
     *
     * @throws IllegalArgumentException if the input string is null or empty.
     */
    public static Filter createContestDescriptionEqualsFilter(String description) {
        ExceptionUtils.checkNullOrEmpty(description, null, null,
            "Contest description should not be null or empty.");
        return new EqualToFilter(CONTEST_DESCRIPTION, description);
    }

    /**
     * <p>
     * Create a filter that returns the <code>TrackContest</code> with the given description.
     * Note that this <code>Filter</code> will return 0..N results. This is a simple SQL LIKE matching.
     * </p>
     *
     * @param descriptionLikeCriteria The description SQL LIKE string of theTrackContest that we would like to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest</code> with the given
     *         description SQL LIKE string.
     *
     * @throws IllegalArgumentException if the input string is null or empty. Or if the string does not begin with
     *         'SS', 'SW',  'EW', 'WC'.
     */
    public static Filter createContestDescriptionLikeFilter(String descriptionLikeCriteria) {
        ExceptionUtils.checkNullOrEmpty(descriptionLikeCriteria, null, null,
            "Contest description should not be null or empty.");
        return new LikeFilter(CONTEST_DESCRIPTION, descriptionLikeCriteria);
    }

    /**
     * <p>
     * Create a filter that returns all the matching <code>TrackContest(s)</code> with the given contest descriptions.
     * Note that this <code>Filter</code> will return 0..N <code>TrackContest(s)</code>.
     * </p>
     *
     * @param descriptions The array of contest descriptions of the <code>TrackContest(s)</code> that we would like
     *        to fetch.
     *
     * @return Filter which encapsulates the specific query to fetch the <code>TrackContest(s)</code> with the
     *         given contest descriptions.
     *
     * @throws IllegalArgumentException if the provided array is null/empty or any of the description strings in the
     *         array is null or empty.
     */
    @SuppressWarnings("unchecked")
    public static Filter createContestDescriptionInFilter(String[] descriptions) {
        ExceptionUtils.checkNull(descriptions, null, null, "The array of descriptions should not be null.");
        if (descriptions.length == 0) {
            throw new IllegalArgumentException("The array of descriptions should not be empty.");
        }
        Set < String > set = new HashSet();
        for (String description : descriptions) {
            ExceptionUtils.checkNullOrEmpty(description, null, null,
                "Contest description should not be null or empty.");
            set.add(description);
        }
        return new InFilter(CONTEST_DESCRIPTION, new ArrayList(set));
    }
}
