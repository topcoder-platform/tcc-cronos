/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.utils;

import java.util.Arrays;
import java.util.Date;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.service.studio.contest.bean.Helper;

/**
 * <p>
 * This is a convenience class for creating filters to search for contests that match specific filter criteria.Searches
 * can be done on File Type ID, File Type Extension, Contest Channel ID, etc ...
 * </p>
 * <p>
 * The constants used as search criterion names are provided by this class for each factory method are constructed as
 * following:
 * <ol>
 * <li> each field name will be the specific column name in the specific table.</li>
 * <li> We will also pre-pend the field name with the table name using this format:"table.column" so for example the
 * contests contest_id column would be encoded as "contest.contest_id" </li>
 * </ol>
 * </p>
 * <p>
 * This factory also provides convenience methods for combining filters without needing to instantiate specific Filter
 * classes. This is done for convenience only. This way a used can associate filters by AND, OR, or NOT. Any filters,
 * including the ones created in this factory can be combined in this manner.
 * </p>
 * <p>
 * <b>Thread Safety</b> This is a static, immutable and thus thread-safe class.
 * </p>
 *
 * @author AleaActaEst, BeBetter
 * @version 1.1
 * @since 1.1
 */
public class ContestFilterFactory {
    /**
     * <p>
     * Table name constant for contest.
     * </p>
     */
    private static final String TABLE_CONTEST = "contest";

    /**
     * <p>
     * Table name constant for contest channel lookup.
     * </p>
     */
    private static final String TABLE_CONTEST_CHANNEL_LOOKUP = "contest_channel_lu";

    /**
     * <p>
     * Table name constant for file type lookup.
     * </p>
     */
    private static final String TABLE_FILE_TYPE_LOOKUP = "file_type_lu";

    /**
     * <p>
     * Table name constant for contest status lookup.
     * </p>
     */
    private static final String TABLE_CONTEST_STATUS_LOOKUP = "contest_status_lu";

    /**
     * <p>
     * Column name constant for file type id.
     * </p>
     */
    private static final String COLUMN_FILE_TYPE_ID = TABLE_CONTEST_CHANNEL_LOOKUP + ".file_type_id";

    /**
     * <p>
     * Column name constant for file extension.
     * </p>
     */
    private static final String COLUMN_FILE_EXTENSION = TABLE_FILE_TYPE_LOOKUP + ".extension";

    /**
     * <p>
     * Column name constant for contest channel id.
     * </p>
     */
    private static final String COLUMN_CONTEST_CHANNEL_ID = TABLE_CONTEST + ".contest_channel_id";

    /**
     * <p>
     * Column name constant for contest channel name.
     * </p>
     */
    private static final String COLUMN_CONTEST_CHANNEL_NAME = TABLE_CONTEST_CHANNEL_LOOKUP + ".channel_name";

    /**
     * <p>
     * Column name constant for contest status id.
     * </p>
     */
    private static final String COLUMN_CONTEST_STATUS_ID = TABLE_CONTEST + ".contest_detailed_status_id";

    /**
     * <p>
     * Column name constant for contest status name.
     * </p>
     */
    private static final String COLUMN_CONTEST_STATUS_NAME = TABLE_CONTEST_STATUS_LOOKUP + ".name";

    /**
     * <p>
     * Column name constant for contest id.
     * </p>
     */
    private static final String COLUMN_CONTEST_ID = TABLE_CONTEST + ".contest_id";

    /**
     * <p>
     * Column name constant for contest name.
     * </p>
     */
    private static final String COLUMN_CONTEST_NAME = TABLE_CONTEST + ".name";

    /**
     * <p>
     * Column name constant for contest project id.
     * </p>
     */
    private static final String COLUMN_CONTEST_PROJECT_ID = TABLE_CONTEST + ".project_id";

    /**
     * <p>
     * Column name constant for contest direct project id.
     * </p>
     */
    private static final String COLUMN_CONTEST_DIRECT_PROJECT_ID = TABLE_CONTEST + ".tc_direct_project_id";

    /**
     * <p>
     * Column name constant for contest forum id.
     * </p>
     */
    private static final String COLUMN_CONTEST_FORUM_ID = TABLE_CONTEST + ".forum_id";

    /**
     * <p>
     * Column name constant for contest event id.
     * </p>
     */
    private static final String COLUMN_CONTEST_EVENT_ID = TABLE_CONTEST + ".event_id";

    /**
     * <p>
     * Column name constant for contest start time.
     * </p>
     */
    private static final String COLUMN_CONTEST_START_TIME = TABLE_CONTEST + ".start_time";

    /**
     * <p>
     * Column name constant for contest end date.
     * </p>
     */
    private static final String COLUMN_CONTEST_END_DATE = TABLE_CONTEST + ".end_date";

    /**
     * <p>
     * Column name constant for winner announcement deadline.
     * </p>
     */
    private static final String COLUMN_CONTEST_WINNER_ANNOUNCEMENT_DEADLINE = TABLE_CONTEST
        + ".winner_annoucement_deadline";

    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private ContestFilterFactory() {
        // do nothing
    }

    /**
     * <p>
     * This should search for Contests with ContestChannels whose StudioFileType ids match the specified value.
     * </p>
     *
     * @param fileTypeId file type id value to be used for searching
     * @return file type id filter
     */
    public static Filter createStudioContestFileTypeIdFilter(long fileTypeId) {
        // http://forums.topcoder.com/?module=Thread&threadID=613081&start=0
        // we will put all table joins in FROM clause.
        return new EqualToFilter(COLUMN_FILE_TYPE_ID, fileTypeId);
    }

    /**
     * <p>
     * This should search for Contests with ContestChannels whose StudioFileType extensions match the specified value.
     * </p>
     *
     * @param extension the file extension we will use to search the contests.
     * @return the resulting filter
     *
     * @throws IllegalArgumentException if the input was null or an empty string.
     */
    public static Filter createStudioFileTypeExtensionFilter(String extension) {
        Helper.checkString(extension, "extension");
        return new EqualToFilter(COLUMN_FILE_EXTENSION, extension);
    }

    /**
     * <p>
     * This should search for Contests with ContestChannels whose ids match the specified value.
     * </p>
     *
     * @param channelId the specific channel id we will use to search the contests.
     * @return the resulting filter
     */
    public static Filter createStudioContestChannelIdFilter(long channelId) {
        // NOTE: the original design is not necessary, we only need to look at contest table directly
        return new EqualToFilter(COLUMN_CONTEST_CHANNEL_ID, channelId);
    }

    /**
     * <p>
     * This should search for Contests with ContestChannels whose names match the specified value.
     * </p>
     *
     * @param channelName the specific channel name we will use to search the contests.
     * @return the resulting filter
     *
     * @throws IllegalArgumentException if the input was null or an empty string.
     */
    public static Filter createStudioContestChannelNameFilter(String channelName) {
        Helper.checkString(channelName, "channel name");
        return new EqualToFilter(COLUMN_CONTEST_CHANNEL_NAME, channelName);
    }

    /**
     * <p>
     * This should search for Contests with ContestStatus whose ids match the specified value.
     * </p>
     *
     * @param statusId the specific status id we will use to search the contests.
     * @return the resulting filter
     */
    public static Filter createStudioContestStatusIdFilter(long statusId) {
        return new EqualToFilter(COLUMN_CONTEST_STATUS_ID, statusId);
    }

    /**
     * <p>
     * This should search for Contests with ContestStatus whose names match the specified value. Note that this is the
     * only property that needs to be supported for the Studio Contest Management component to work with Auto Pilot
     * component.
     * </p>
     *
     * @param statusName the specific status name we will use to search the contests.
     * @return the resulting filter
     *
     * @throws IllegalArgumentException if the input was null or an empty string.
     */
    public static Filter createStudioContestStatusNameFilter(String statusName) {
        Helper.checkString(statusName, "status name");
        return new EqualToFilter(COLUMN_CONTEST_STATUS_NAME, statusName);
    }

    /**
     * <p>
     * This should search for Contests with ids that match the specified value.
     * </p>
     *
     * @param contestId the specific contest id we will use to search the contests.
     * @return - the resulting filter
     */
    public static Filter createStudioContestIdFilter(long contestId) {
        return new EqualToFilter(COLUMN_CONTEST_ID, contestId);
    }

    /**
     * <p>
     * This should search for Contests with names that match the specified value.
     * </p>
     *
     * @param contestName the specific contest name we will use to search the contests.
     * @return the resulting filter
     *
     * @throws IllegalArgumentException if the input was null or an empty string.
     */
    public static Filter createStudioContestNameFilter(String contestName) {
        Helper.checkString(contestName, "contest name");
        return new EqualToFilter(COLUMN_CONTEST_NAME, contestName);
    }

    /**
     * <p>
     * This should search for Contests with projectIds that match the specified value.
     * </p>
     *
     * @param projectId the specific project id we will use to search the contests.
     * @return the resulting filter
     */
    public static Filter createStudioContestProjectIdFilter(long projectId) {
        return new EqualToFilter(COLUMN_CONTEST_PROJECT_ID, projectId);
    }

    /**
     * <p>
     * This should search for Contests with directProjectIds that match the specified value.
     * </p>
     *
     * @param directProjectId the specific direct project id we will use to search the contests.
     * @return the resulting filter
     */
    public static Filter createStudioContestDirectProjectIdFilter(long directProjectId) {
        return new EqualToFilter(COLUMN_CONTEST_DIRECT_PROJECT_ID, directProjectId);
    }

    /**
     * <p>
     * This should search for Contests with contestForumIds that match the specified value.
     * </p>
     *
     * @param forumId the specific forum id we will use to search the contests.
     * @return the resulting filter
     */
    public static Filter createStudioContestForumIdFilter(long forumId) {
        return new EqualToFilter(COLUMN_CONTEST_FORUM_ID, forumId);
    }

    /**
     * <p>
     * This should search for Contests with contestEventIds that match the specified value.
     * </p>
     *
     * @param contestEventId the specific event id we will use to search the contests.
     * @return the resulting filter
     */
    public static Filter createStudioContestEventIdFilter(long contestEventId) {
        return new EqualToFilter(COLUMN_CONTEST_EVENT_ID, contestEventId);
    }

    /**
     * <p>
     * This should search for Contests with start dates that match the specified value.
     * </p>
     *
     * @param startTime the specific contest start date we will use to search the contests.
     * @return the resulting filter
     *
     * @throws IllegalArgumentException if the input was null.
     */
    public static Filter createStudioContestStartDateFilter(Date startTime) {
        Helper.checkNull(startTime, "start time");
        return new EqualToFilter(COLUMN_CONTEST_START_TIME, startTime);
    }

    /**
     * <p>
     * This should search for Contests with end dates that match the specified value.
     * </p>
     *
     * @param endDate the specific contest end date we will use to search the contests.
     * @return the resulting filter
     *
     * @throws IllegalArgumentException if the input was null.
     */
    public static Filter createStudioContestEndDateFilter(Date endDate) {
        Helper.checkNull(endDate, "end date");
        return new EqualToFilter(COLUMN_CONTEST_END_DATE, endDate);
    }

    /**
     * <p>
     * This should search for Contests with winner announcement deadlines that match the specified value.
     * </p>
     *
     * @param deadlineDate the specific contest winner announcement date we will use to search the contests.
     * @return the resulting filter
     *
     * @throws IllegalArgumentException if the input was null.
     */
    public static Filter createStudioContestWinnerAnnouncementDeadlineDateFilter(Date deadlineDate) {
        Helper.checkNull(deadlineDate, "winner announcement deadline date");
        return new EqualToFilter(COLUMN_CONTEST_WINNER_ANNOUNCEMENT_DEADLINE, deadlineDate);
    }

    /**
     * <p>
     * Creates an AND filter with the passed filters.
     * </p>
     *
     * @param first the first filter
     * @param second the second filter
     * @return the resulting AndFilter
     *
     * @throws IllegalArgumentException if the input is null.
     */
    public static Filter createAndFilter(Filter first, Filter second) {
        Helper.checkNull(first, "first filter");
        Helper.checkNull(second, "second filter");
        return new AndFilter(first, second);
    }

    /**
     * <p>
     * Creates an AND filter with the passed filters.
     * </p>
     *
     * @param filters the list of filters to be combined
     * @return and filter
     *
     * @throws IllegalArgumentException if the input is null or empty or if the list contains null elements.
     */
    public static Filter createAndFilter(Filter[] filters) {
        checkFilterArray(filters);
        return new AndFilter(Arrays.asList(filters));
    }

    /**
     * <p>
     * Creates an OR filter with the passed filters.
     * </p>
     *
     * @param first the first filter
     * @param second the second filter
     * @return the resulting OrFilter
     *
     * @throws IllegalArgumentException if the input is null.
     */
    public static Filter createOrFilter(Filter first, Filter second) {
        Helper.checkNull(first, "first filter");
        Helper.checkNull(second, "second filter");
        return new OrFilter(first, second);
    }

    /**
     * <p>
     * Creates an OR filter with the passed filters.
     * </p>
     *
     * @param filters the list of filters to be combined
     * @return the resulting OrFilter
     *
     * @throws IllegalArgumentException if the input is null or empty or if the list contains null elements.
     */
    public static Filter createOrFilter(Filter[] filters) {
        checkFilterArray(filters);
        return new OrFilter(Arrays.asList(filters));
    }

    /**
     * <p>
     * Creates an NOT filter with the passed filters.
     * </p>
     *
     * @param filter the filter to be reversed
     * @return the resulting NotFilter
     *
     * @throws IllegalArgumentException if the input is null.
     */
    public static Filter createNotFilter(Filter filter) {
        Helper.checkNull(filter, "filter");
        return new NotFilter(filter);
    }

    /**
     * <p>
     * Checks filter array.
     * </p>
     *
     * @param filters filter array to be checked
     *
     * @throws IllegalArgumentException if the input is null or empty or if the array contains null elements.
     */
    private static void checkFilterArray(Filter[] filters) {
        if (filters == null) {
            throw new IllegalArgumentException("filter array is null.");
        }

        if (filters.length == 0) {
            throw new IllegalArgumentException("filter array is empty.");
        }

        for (Filter filter : filters) {
            if (filter == null) {
                throw new IllegalArgumentException("filter array contains null element.");
            }
        }
    }
}
