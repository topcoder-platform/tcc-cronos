/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.utils;

import java.util.HashMap;

import com.topcoder.search.builder.SearchContext;
import com.topcoder.search.builder.SearchFragmentBuilder;
import com.topcoder.search.builder.UnrecognizedFilterException;
import com.topcoder.search.builder.database.AndFragmentBuilder;
import com.topcoder.search.builder.database.EqualsFragmentBuilder;
import com.topcoder.search.builder.database.InFragmentBuilder;
import com.topcoder.search.builder.database.LikeFragmentBuilder;
import com.topcoder.search.builder.database.NotFragmentBuilder;
import com.topcoder.search.builder.database.NullFragmentBuilder;
import com.topcoder.search.builder.database.OrFragmentBuilder;
import com.topcoder.search.builder.database.RangeFragmentBuilder;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.service.studio.contest.bean.Helper;
import com.topcoder.util.classassociations.ClassAssociator;
import com.topcoder.util.classassociations.IllegalHandlerException;

/**
 * <p>
 * This is a simple converter helper which converts the composite Filter object
 * into an SQL statement.
 * </p>
 *
 * <p>
 * <b>Thread-Safety</b> This is immutable and thus thread-safe.
 * </p>
 *
 * @author AleaActaEst, BeBetter
 * @version 1.1
 * @since 1.1
 */
public class FilterToSqlConverter {
    /**
     * <p>
     * Search context string. It contains the first part of SQL string and will
     * prepend to returned conditional statement to form a detailed search SQL
     * string.
     * </p>
     * Fixed: [27074412-7]
     */
    private static final String SQL_SEARCH_CONTEXT = ""
    	+ "SELECT contest.contest_id,contest.contest_channel_id,contest.name,contest.contest_type_id, "  
        + "contest.project_id,contest.tc_direct_project_id,contest.contest_status_id,contest.contest_detailed_status_id,contest.forum_id, " 
        + "contest.event_id,contest.start_time,contest.end_time,contest.winner_announcement_time, "  
        + "contest.create_user_id, contest.launch_immediately, contest.deleted "
        + "FROM contest "
        + "INNER JOIN contest_status_lu ON contest.contest_status_id = contest_status_lu.contest_status_id "
        + "INNER JOIN contest_channel_lu ON contest.contest_channel_id = contest_channel_lu.contest_channel_id "
        + "WHERE ";


    /**
     * <p>
     * This is the classAssociator that will be mapping the Filter classes to
     * their respective SearchFragmentBuilders. The keys are non-null Filter
     * classes, and the values are non-null FragmentBuilders. It is used for
     * providing the FragmentBuilder lookup for certain SearchFragmentBuilders
     * that need to delegate to other SearchFragmentBuilders.
     * </p>
     */
    private static final ClassAssociator FRAGMENTBUILDERS = new ClassAssociator();
    static {
        try {
            // see SearchBuilderHelper#loadClassAssociator
            FRAGMENTBUILDERS.addClassAssociation(AndFilter.class, new AndFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(OrFilter.class, new OrFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(LikeFilter.class, new LikeFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(NotFilter.class, new NotFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(NullFilter.class, new NullFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(EqualToFilter.class, new EqualsFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(GreaterThanFilter.class, new RangeFragmentBuilder());
            FRAGMENTBUILDERS
                    .addClassAssociation(GreaterThanOrEqualToFilter.class, new RangeFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(BetweenFilter.class, new RangeFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(LessThanOrEqualToFilter.class, new RangeFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(LessThanFilter.class, new RangeFragmentBuilder());
            FRAGMENTBUILDERS.addClassAssociation(InFilter.class, new InFragmentBuilder());
        } catch (IllegalHandlerException e) {
            // since we initiate fragment builders manually, this exception will
            // not occur as the case through
            // configuration file.
        }
    }

    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private FilterToSqlConverter() {
        // do nothing
    }

    /**
     * <p>
     * This method will convert the provided filter into an SQL statement.
     * </p>
     *
     * @param filter to be converted
     * @return a simple 2 element array and the first element contains sql
     *         string and the second contains a <code>List</code> including
     *         all bind variable values accumulated during SQL string
     *         construction
     *
     * @throws IllegalArgumentException if the input is null or if the sql
     *         string cannot be generated from the given input due to not
     *         supported filter(s)
     */
    public static Object[] convert(Filter filter) {
        Helper.checkNull(filter, "filter");
        SearchContext searchContext = buildSearchContext(filter);
        // see
        // http://forums.topcoder.com/?module=Thread&threadID=613082&start=0&mc=6#974556

        return new Object[] {SQL_SEARCH_CONTEXT + searchContext.getSearchString().toString(),
                searchContext.getBindableParameters()};
    }

    /**
     * <p>
     * Uses database fragment builder to construct actual search SQL as well as
     * all bind parameter values.
     * </p>
     *
     * @param filter used to build search SQL
     * @return SeachContext object containing search SQL to be used for building
     *         final search statement
     *
     * @throws IllegalArgumentException if no builder can be found for given
     *         filter or if one of nested filter can not be recognized due to no
     *         corresponding builder
     */
    private static SearchContext buildSearchContext(Filter filter) {
        // PLEASE see
        // http://forums.topcoder.com/?module=Thread&threadID=613082&start=0&mc=4#974554
        // we use mature existing code base from search builder. this factory
        // could be converted to another or improved
        // database search strategy based on
        // this code easily
        SearchFragmentBuilder builder = getFragmentBuilder(filter);
        SearchContext searchContext = new SearchContext(FRAGMENTBUILDERS, new HashMap<String, String>());
        try {
            builder.buildSearch(filter, searchContext);
        } catch (UnrecognizedFilterException e) {
            throw new IllegalArgumentException("One of  nested filter is not supported in converting to sql.");
        }

        return searchContext;
    }

    /**
     * <p>
     * Retrieves the SearchFragmentBuilder that is associated for the provided
     * filter. Throws IllegalArgumentException if no fragment builder could be
     * found.
     * </p>
     *
     * @param filter The filter to retrieve a SearchFragmentBuidler form.
     * @return the SearchFragmentBuilder that is associated for the provided
     *         filter.
     *
     * @throws IllegalArgumentException if builder can not be found
     */
    private static SearchFragmentBuilder getFragmentBuilder(Filter filter) {
        SearchFragmentBuilder builder = (SearchFragmentBuilder) FRAGMENTBUILDERS.getAssociations().get(
                filter.getClass());
        if (builder == null) {
            throw new IllegalArgumentException("The filter is not supported in converting to sql.");
        } else {
            return builder;
        }
    }

}
