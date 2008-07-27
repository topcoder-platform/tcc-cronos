/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

/**
 * <p>
 * This class provides static methods that create filters that can be used by the
 * DigitalRunPointsManager implementations to retrieve DigitalRunPoints entities, that match the
 * filter criteria, from the persistence.<br>
 * The methods provided return filters with:<br>
 * <br>
 * equals/in operation for points id/track id/points status id/points type id/user id <br>
 * equals/like operation points description <br>
 * equals operation for is potential <br>
 * equals+equals/equals+in operations for reference type id+reference id <br>
 * equals/greater or equal than/lower or equal than for amount,points application date and points
 * award date <br>
 * This class also provides and/or/not filters so that the user does not need to interact with
 * Search Builder component to create such filters.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe since it has no state.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsFilterFactory {
    /**
     * Empty constructor.
     */
    private DigitalRunPointsFilterFactory() {
        // Empty
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * dr_points_id=pointsId.
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @param pointsId
     *            the points id used to create the filter
     * @return the created Filter instance
     */
    public static Filter createPointsIdEqualsFilter(long pointsId) {
        Helper.checkNegative(pointsId, "pointsId");
        return new EqualToFilter("dr_points_id", new Long(pointsId));
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * dr_points_id is in the ids contained by the pointsIds list.
     *
     * @throws IllegalArgumentException
     *             the argument list is null or empty or if it contain null elements or if the value
     *             (Long.longValue()) of the elements is <0
     * @param pointsIds
     *            the points ids used to create the filter
     * @return the created Filter instance
     */
    public static Filter createPointsIdInFilter(List<Long> pointsIds) {
        checkListLong(pointsIds, "pointsIds");
        return new InFilter("dr_points_id", new ArrayList<Long>(pointsIds));
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * track_id=trackId.
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @param trackId
     *            the track id used to create the filter
     * @return the created Filter instance
     */
    public static Filter createTrackIdEqualsFilter(long trackId) {
        Helper.checkNegative(trackId, "trackId");
        return new EqualToFilter("dr_points_id", new Long(trackId));
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * dr_points_id is in the ids contained by the trackIds list.
     *
     * @throws IllegalArgumentException
     *             the argument list is null or empty or if it contain null elements or if the value
     *             (Long.longValue()) of the elements is <0
     * @param trackIds
     *            the contest type ids used to create the filter
     * @return the created Filter instance
     */
    public static Filter createTrackIdInFilter(List<Long> trackIds) {
        checkListLong(trackIds, "trackIds");
        return new InFilter("dr_points_id", new ArrayList<Long>(trackIds));
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * dr_points_desc=pointsDescription.
     *
     * @throws IllegalArgumentException
     *             if argument is null or empty string
     * @param pointsDescription
     *            the points description used to create the filter
     * @return the created Filter instance
     */
    public static Filter createPointsDescriptionEqualsFilter(String pointsDescription) {
        Helper.checkNullAndEmpty(pointsDescription, "pointsDescription");
        return new EqualToFilter("dr_points_desc", pointsDescription);
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * dr_points_desc is "like" value argument. Implementation details: return new
     * LikeFilter("dr_points_desc",value);
     *
     * @throws IllegalArgumentException
     *             the argument list is null or empty or if it contain null or empty string elements
     * @throws IllegalArgumentException
     *             if value is null or empty or value does not begin with 'SS', 'SW', 'EW', 'WC'
     * @param value
     *            the value used to create the filter
     * @return the created Filter instance
     */
    public static Filter createPointsDescriptionLikeFilter(String value) {
        Helper.checkNullAndEmpty(value, "value");
        checkStringBeginWith(value);
        return new LikeFilter("dr_points_desc", value);
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * dr_points_desc is "like" value argument.
     *
     * @throws IllegalArgumentException
     *             if value is null or empty or value does not begin with 'SS', 'SW', 'EW', 'WC'
     * @param value
     *            the value passed to the filter
     * @param escapeCharacter
     *            the escape character passed to the filter
     * @return the created filter instance
     */
    public static Filter createPointsDescriptionLikeFilter(String value, char escapeCharacter) {
        Helper.checkNullAndEmpty(value, "value");
        checkStringBeginWith(value);
        return new LikeFilter("dr_points_desc", value, escapeCharacter);
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * is_potential equals isPotential argument.
     *
     * @param isPotential
     *            the isPotential value used to create the filter
     * @return the created Filter instance
     */
    public static Filter createIsPotentialEqualFilter(boolean isPotential) {
        return new EqualToFilter("is_potential", new Boolean(isPotential));
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * dr_points_status_id=pointsStatusId.
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @param pointsStatusId
     *            the status id used to create the filter
     * @return the created filter
     */
    public static Filter createPointsStatusIdEqualsFilter(long pointsStatusId) {
        Helper.checkNegative(pointsStatusId, "pointsStatusId");
        return new EqualToFilter("dr_points_status_id", new Long(pointsStatusId));
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * dr_points_status_id is in the ids contained by the pointsStatusIds list.
     *
     * @throws IllegalArgumentException
     *             the argument list is null or empty or if it contain null elements or if the value
     *             (Long.longValue()) of the elements is <0
     * @param pointsStatusIds
     *            the statuses ids used to create the filter
     * @return the created filter
     */
    public static Filter createPointsStatusIdInFilter(List<Long> pointsStatusIds) {
        checkListLong(pointsStatusIds, "pointsStatusIds");
        return new InFilter("dr_points_status_id", new ArrayList<Long>(pointsStatusIds));
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * dr_points_type_id=pointsTypeId.
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @param pointsTypeId
     *            the points type id used to create the filter
     * @return the created filter
     */
    public static Filter createPointsTypeIdEqualsFilter(long pointsTypeId) {
        Helper.checkNegative(pointsTypeId, "pointsTypeId");
        return new EqualToFilter("dr_points_type_id", new Long(pointsTypeId));
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * dr_points_type_id is in the ids contained by the pointsTypeIds list.
     *
     * @throws IllegalArgumentException
     *             the argument list is null or empty or if it contain null elements or if the value
     *             (Long.longValue()) of the elements is <0
     * @param pointsTypeIds
     *            the type ids used to create the filter
     * @return the created filter
     */
    public static Filter createPointsTypedInFilter(List<Long> pointsTypeIds) {
        checkListLong(pointsTypeIds, "pointsTypeIds");
        return new InFilter("dr_points_type_id", new ArrayList<Long>(pointsTypeIds));
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * dr_points_reference_type_id=pointsReferenceTypeId and reference_id = referenceId.
     *
     * @throws IllegalArgumentException
     *             if arguments<0
     * @param pointsReferenceTypeId
     *            the points reference type id
     * @param referenceId
     *            the reference id
     * @return an AndFilter instance
     */
    public static Filter createReferenceIdEqualsFilter(long pointsReferenceTypeId, long referenceId) {
        Helper.checkNegative(pointsReferenceTypeId, "pointsReferenceTypeId");
        Helper.checkNegative(referenceId, "referenceId");

        // create two equals filters
        EqualToFilter filter1 = new EqualToFilter("dr_points_reference_type_id", new Long(
                pointsReferenceTypeId));
        EqualToFilter filter2 = new EqualToFilter("reference_id", new Long(referenceId));
        // create and return and filter
        return new AndFilter(filter1, filter2);
    }

    /**
     * Creates a filter that will be used to return the rows , from dr_points table, for which
     * dr_points_reference_type_id=pointsReferenceTypeId and reference_id is in the ids contained by
     * the referenceIds list.
     *
     * @throws IllegalArgumentException
     *             if pointsReferenceTypeId<0 or the argument list is null or empty or if it
     *             contain null elements or if the value (Long.longValue()) of the elements is <0
     * @param pointsReferenceTypeId
     *            the points reference type id
     * @param referenceIds
     *            the reference ids
     * @return an AndFilter instance
     */
    public static Filter createReferenceIdEqualsInFilter(long pointsReferenceTypeId, List<Long> referenceIds) {
        Helper.checkNegative(pointsReferenceTypeId, "pointsReferenceTypeId");
        checkListLong(referenceIds, "referenceIds");

        // create an equal and an in filter
        EqualToFilter filter1 = new EqualToFilter("dr_points_reference_type_id", new Long(
                pointsReferenceTypeId));
        // create an ArrayList and add all the elements, from referenceIds list, into this list
        InFilter filter2 = new InFilter("reference_id", new ArrayList<Long>(referenceIds));
        return new AndFilter(filter1, filter2);
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * user_id=userId.
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @param userId
     *            the user id used to create the filter
     * @return the created filter
     */
    public static Filter createUserIdEqualsFilter(long userId) {
        Helper.checkNegative(userId, "userId");

        return new EqualToFilter("user_id", new Long(userId));
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * user_id is in the ids contained by the userIds list.
     *
     * @throws IllegalArgumentException
     *             the argument list is null or empty or if it contain null elements or if the value
     *             (Long.longValue()) of the elements is <0
     * @param userIds
     *            the user ids used to create the filter
     * @return the created filter
     */
    public static Filter createUserIdInFilter(List<Long> userIds) {
        checkListLong(userIds, "userIds");

        return new InFilter("user", new ArrayList<Long>(userIds));
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which column
     * amount=amount.
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @param amount
     *            the amount
     * @return the created filter
     */
    public static Filter createAmountEqualsFilter(double amount) {
        Helper.checkNegative((long) amount, "amount");

        return new EqualToFilter("amount", new Double(amount));
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which column
     * amount>=amount.
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @param amount
     *            the amount
     * @return the created filter
     */
    public static Filter createAmountGreaterOrEqualFilter(double amount) {
        Helper.checkNegative((long) amount, "amount");

        return new GreaterThanOrEqualToFilter("amount", new Double(amount));
    }

    /**
     * Creates a filter that will be used to return the rows,from dr_points table, for which column
     * amount<=amount.
     *
     * @throws IllegalArgumentException
     *             if argument<0
     * @param amount
     *            the amount
     * @return the created filter
     */
    public static Filter createAmountLowerOrEqualFilter(double amount) {
        Helper.checkNegative((long) amount, "amount");

        return new LessThanOrEqualToFilter("amount", new Double(amount));
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * application_date=pointsApplicationDate.
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @param pointsApplicationDate
     *            the application date
     * @return the created filter
     */
    public static Filter createPointsApplicationDateEqualsFilter(Date pointsApplicationDate) {
        Helper.checkNull(pointsApplicationDate, "pointsApplicationDate");

        return new EqualToFilter("application_date", pointsApplicationDate);
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * application_date>=pointsApplicationDate.
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @param pointsApplicationDate
     *            the application date
     * @return the created filter
     */
    public static Filter createPointsApplicationDateGreaterOrEqualFilter(Date pointsApplicationDate) {
        Helper.checkNull(pointsApplicationDate, "pointsApplicationDate");

        return new GreaterThanOrEqualToFilter("application_date", pointsApplicationDate);
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * application_date<=pointsApplicationDate.
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @param pointsApplicationDate
     *            the application date
     * @return the created filter
     */
    public static Filter createPointsApplicationDateLowerOrEqualFilter(Date pointsApplicationDate) {
        Helper.checkNull(pointsApplicationDate, "pointsApplicationDate");

        return new LessThanOrEqualToFilter("application_date", pointsApplicationDate);
    }

    /**
     * Creates a filter that will be used to return the row, from dr_points table, for which
     * award_date=pointsAwardDate.
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @param pointsAwardDate
     *            the points award date
     * @return the created filter
     */
    public static Filter createPointsAwardDateEqualsFilter(Date pointsAwardDate) {
        Helper.checkNull(pointsAwardDate, "pointsAwardDate");

        return new EqualToFilter("award_date", pointsAwardDate);
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * award_date>=pointsAwardDate.
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @param pointsAwardDate
     *            the points award date
     * @return the created filter
     */
    public static Filter createPointsAwardDateGreaterOrEqualFilter(Date pointsAwardDate) {
        Helper.checkNull(pointsAwardDate, "pointsAwardDate");

        return new GreaterThanOrEqualToFilter("award_date", pointsAwardDate);
    }

    /**
     * Creates a filter that will be used to return the rows, from dr_points table, for which
     * award_date<=pointsAwardDate.
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @param pointsAwardDate
     *            the points award date
     * @return the created filter
     */
    public static Filter createPointsAwardDateLowerOrEqualFilter(Date pointsAwardDate) {
        Helper.checkNull(pointsAwardDate, "pointsAwardDate");

        return new LessThanOrEqualToFilter("award_date", pointsAwardDate);
    }

    /**
     * Creates an "and" filter from the given filters.
     *
     * @throws IllegalArgumentException
     *             if the argument list is null or empty or if it contain null elements
     * @param filters
     *            the filters
     * @return an AndFilter instance constructed from the given filters
     */
    public static Filter createAndFilter(List<Filter> filters) {
        checkListFilter(filters, "filters");

        return new AndFilter(new ArrayList<Filter>(filters));
    }

    /**
     * Creates an "or" filter from the given filters.
     *
     * @throws IllegalArgumentException
     *             if the argument list is null or empty or if it contain null elements
     * @param filters
     *            the filters
     * @return an OrFilter instance constructed from the given filters
     */
    public static Filter createOrFilter(List<Filter> filters) {
        checkListFilter(filters, "filters");

        return new OrFilter(new ArrayList<Filter>(filters));
    }

    /**
     * Creates an "not" filter from the given filter.
     *
     * @throws IllegalArgumentException
     *             if the argument is null
     * @param filter
     *            the filter
     * @return a NotFilter instance constructed from the given filter
     */
    public static Filter createNotFilter(Filter filter) {
        Helper.checkNull(filter, "filter");

        return new NotFilter(filter);
    }

    /**
     * Check the List.
     *
     * @param ids
     *            the list ids
     * @param name
     *            the list name
     * @throws IllegalArgumentException
     *             if the list is invalid
     */
    private static void checkListLong(List<Long> ids, String name) {
        Helper.checkNull(ids, name);
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("The " + name + " can not be empty.");
        }
        for (Long id : ids) {
            Helper.checkNull(id, "id in " + name);
            Helper.checkNegative(id.longValue(), "id in " + name);
        }
    }

    /**
     * Check the List filter
     *
     * @param filters
     *            the list filters
     * @param name
     *            the list name
     * @throws IllegalArgumentException
     *             if the list is invalid
     */
    private static void checkListFilter(List<Filter> filters, String name) {
        Helper.checkNull(filters, name);
        if (filters.isEmpty()) {
            throw new IllegalArgumentException("The " + name + " can not be empty.");
        }
        for (Filter filter : filters) {
            Helper.checkNull(filter, "filter");
        }
    }

    /**
     * Check the string begin.
     *
     * @param str
     *            the string
     * @throws IllegalArgumentException
     *             if the string is invalid
     */
    private static void checkStringBeginWith(String str) {
        if (!str.startsWith("SS") && !str.startsWith("SW") && !str.startsWith("EW") && !str.startsWith("WC")) {
            throw new IllegalArgumentException("The value string should begin with  'SS', 'SW', 'EW', 'WC'.");
        }
    }
}
