/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.io.Serializable;
import java.util.Date;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a base <code>FilterFactory</code> interface that provides filter creation methods that
 * may be used for filters of any Time Tracker Bean.
 * </p>
 *
 * <p>
 * It encapsulates filters for the common functionality - namely, the creation and modification date and user.
 * </p>
 *
 * <p>
 * Thread Safety : Implementations are required to be thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface BaseFilterFactory extends Serializable {
    /**
     * <p>
     * Creates a <code>Filter</code> based on the Creation Date of the entity.
     * </p>
     *
     * <p>
     * A date range that may be open-ended can be specified.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be null.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is null, then the filter should search the records with creation
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with creation
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with creation date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid
     * (e.g. rangeStart &gt; rangeEnd), or if both arguments are null.
     */
    public Filter createCreationDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * Creates a <code>Filter</code> based on the Modification Date of the entity.
     * </p>
     *
     * <p>
     * A date range that may be open-ended can be specified.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be null.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is null, then the filter should search the records with modification
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with modification
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with modification date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid
     * (eg. rangeStart &gt; rangeEnd), or if both arguments are null.
     */
    public Filter createModificationDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * Creates a <code>Filter</code> based on the Creation User of the entity.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param username The username used for searching.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or username is null or an empty String.
     */
    public Filter createCreationUserFilter(StringMatchType matchType, String username);

    /**
     * <p>
     * Creates a <code>Filter</code> based on the Modification User of the entity.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param username The username used for searching.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or username is null or an empty String.
     */
    public Filter createModificationUserFilter(StringMatchType matchType, String username);
}
