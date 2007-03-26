/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.result;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.ParameterCheck;
import com.orpheus.game.XMLHelper;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.rssgenerator.DataStore;
import com.topcoder.util.rssgenerator.RSSEntity;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.SearchCriteria;
import com.topcoder.util.rssgenerator.datastore.SearchCriteriaImpl;
import com.topcoder.util.rssgenerator.impl.RSSEntityImpl;
import com.topcoder.util.rssgenerator.impl.RSSObjectImpl;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Content;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Feed;
import com.topcoder.util.rssgenerator.io.RSSWriteException;
import com.topcoder.util.rssgenerator.io.atom10.Atom10Writer;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Result;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A Result for responding to message polling requests. It will obtain a DataStore from an application context
 * attribute of configurable name and use it to construct an Atom 1.0 feed document, which it returns (in XML format)
 * as the HTTP response entity. It will select items for the feed as follows:  The Result will be configured with a
 * request parameter name with which it will accept an item update date and time. If the request specifies a parameter
 * of that name then this Result will parse it as a java.util.Date in ISO 8601:2004 format
 * (http://en.wikipedia.org/wiki/ISO_8601), and only items with a creation or update date strictly later than the
 * parsed date will be included in the resulting feed. The Result will determine the games for which the requesting
 * player (the one currently logged-in) is registered; the name of each will be used as an item category, and items in
 * any of those categories will be included in the feed, subject to the date restriction already described. The Result
 * will be configured with zero or more category names for categories whose items are always included in the feed,
 * subject to the date restriction criterion. The response will be assigned the content type registered for Atom,
 * "application/atom+xml". This class is thread safe since it does not contain any mutable state.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class MessagePollResult implements Result {

    /** Content type while output the Atom1.0 to response */
    private static final String RESPONSE_CONTENT_TYPE = "application/atom+xml";

    /**
     * A Pattern matching that subset of ISO8601:2004 date/time strings recognized by this class.  In
     * practice, the strings recognized are those described by the "date-time" production of RFC 3339,
     * plus versions of all those strings in which a comma (,) is used as a decimal separator instead of a
     * full stop (.).  As permitted by RFC 3339, the time separator (T) and UTC indicator (Z) may be given
     * in either upper- or lower-case.
     */
    private static final Pattern ISO8601_DATE_PATTERN = Pattern.compile(
            "(\\d{4})-(\\d{2})-(\\d{2})[Tt](\\d{2}):(\\d{2}):(\\d{2})(?:[,.](\\d{1,}))?(?:([Zz])|([-+]\\d{2}:\\d{2}))?");

    /**
     * The group index in ISO8601_DATE_PATTERN representing the four-digit year
     */
    private static final int YEAR_GROUP = 1;

    /**
     * The group index in ISO8601_DATE_PATTERN representing the two-digit month number
     */
    private static final int MONTH_GROUP = 2;

    /**
     * The group index in ISO8601_DATE_PATTERN representing the two-digit day of the month
     */
    private static final int DAY_GROUP = 3;

    /**
     * The group index in ISO8601_DATE_PATTERN representing the two-digit hour (0-23)
     */
    private static final int HOUR_GROUP = 4;

    /**
     * The group index in ISO8601_DATE_PATTERN representing the two-digit minute (0-59)
     */
    private static final int MINUTE_GROUP = 5;

    /**
     * The group index in ISO8601_DATE_PATTERN representing the two-digit second (0-59)
     */
    private static final int SECOND_GROUP = 6;

    /**
     * The group index in ISO8601_DATE_PATTERN representing the millisecond portion, which is optional for
     * the purposes of this class
     */
    private static final int MILLISECOND_OPTIONAL_GROUP = 7;

    /**
     * The group index in ISO8601_DATE_PATTERN representing the millisecond portion, which is optional for
     * the purposes of this class
     */
    private static final int ZULU_OPTIONAL_GROUP = 8;

    /**
     * The group index in ISO8601_DATE_PATTERN representing the millisecond portion, which is optional for
     * the purposes of this class
     */
    private static final int GMT_OFFSET_OPTIONAL_GROUP = 9;

    /**
     * The TimeZone object for GMT
     */
    private static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT+0");

    /** A key to get the date string from the request parameters. */
    private final String dateParamKey;

    /**
     * An array contains the category names that will be used to search for the feeds in the execute method It's set in
     * the constructor.
     */
    private final String[] categoryNames;

    /**
     * Creates a MessagePollResult instance with dateParamKey and categoryNames.
     *
     * @param dateParamKey the date param key
     * @param categoryNames the category names
     *
     * @throws IllegalArgumentException if categoryNames is null or contains null or empty element.
     */
    public MessagePollResult(String dateParamKey, String[] categoryNames) {
        this.dateParamKey = dateParamKey;
        ParameterCheck.checkEmptyArray("categoryNames", categoryNames);

        String[] copy = new String[categoryNames.length];
        System.arraycopy(categoryNames, 0, copy, 0, categoryNames.length);
        this.categoryNames = copy;
    }

    /**
     * <p>Create the instance from the element. The structure of the element should be:</p>
     * <pre>&lt;result name=&quot;x&quot;&gt;
     *  &lt;date_param_key&gt;date&lt;/date_param_key&gt;
     *  &lt;catetory_names&gt;
     *      &lt;value&gt;some category name&lt;/value&gt;
     *      &lt;value&gt;some category name &lt;/value&gt;
     *      &lt;value&gt;some category name &lt;/value&gt;
     *  &lt;/category_names&gt;
     *&lt;/result&gt;</pre>
     * <p>
     * Following is simple explanation of the above XML structure.<br>
     * The handler's type attribute is required by Front Controller component, it won't be used in this design. <br>
     * The date node's value represents the http request parameter name to get the date string<br>
     * The category_names node contains an array of category names that will be used to construct the SearchCriteria
     * </p>
     *
     * @param element the xml element
     *
     * @throws IllegalArgumentException if element is null or invalid.
     */
    public MessagePollResult(Element element) {
        ParameterCheck.checkNull("element", element);

        this.dateParamKey = XMLHelper.getNodeValue(element, "date_param_key", false);
        this.categoryNames = XMLHelper.getNodeValues(element, "catetory_names");
    }

    /**
     * Executes this result, obtains data to form a Atom1.0 feed and output to response.
     *
     * @param context the action context
     *
     * @throws ResultExecutionException if and error occurred while executing this result
     * @throws IllegalArgumentException if the context is null
     */
    public void execute(ActionContext context) throws ResultExecutionException {
        ParameterCheck.checkNull("context", context);

        HttpServletRequest request = context.getRequest();

        Date date = getDateParameter(request); //obtain date from parameter
        long userId = getUserId(request);

        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();
        DataStore dataStore = (DataStore) request.getSession().getServletContext().getAttribute(golu.getDataStoreKey());

        try {
            //a list of categories that should be involved in search
            List categories = new ArrayList(Arrays.asList(this.categoryNames));
            
            if (golu.isUseLocalInterface()) {
                GameDataLocal gameData = golu.getGameDataLocalHome().create();
                long[] gameIds = gameData.findGameRegistrations(userId);

                for (int i = 0; i < gameIds.length; i++) {
                    //add the use's registration game to the categories list
                    categories.add(gameData.getGame(gameIds[i]).getName());
                }
            } else {
                GameData gameData = golu.getGameDataRemoteHome().create();
                long[] gameIds = gameData.findGameRegistrations(userId);

                for (int i = 0; i < gameIds.length; i++) {
                    //add the use's registration game to the categories list
                    categories.add(gameData.getGame(gameIds[i]).getName());
                }
            }

            //creates searchCriteria along with the categories and date
            SearchCriteria searchCriteria = getSearchCriteria(categories, date);

            //search RSSItem with the searchCriteria
            RSSItem[] items = dataStore.findItems(searchCriteria);

            //write to response the items
            writeAtom10Feed(context.getResponse(), items, date);
        } catch (Exception e) {
            throw new ResultExecutionException("error occurs while recording user registation", e);
        }
    }

    /**
     * Parses the given ISO 8601:2004 format date string into Date
     *
     * @param str String representaion of the date
     *
     * @return date Date parsed from the str
     *
     * @throws ResultExecutionException if the date is invalid, or not ISO 8601 format
     */
    private Date getDate(String str) throws ResultExecutionException {

        //pattern to recognize the ISO 8601 date format
        Matcher matcher = ISO8601_DATE_PATTERN.matcher(str);

        //the str is valid ISO 8601 format
        if (matcher.matches()) {
            Calendar cal = Calendar.getInstance();

            //matches UTC identification
            if (matcher.group(ZULU_OPTIONAL_GROUP) != null) {
                cal.setTimeZone(GMT_ZONE);

            //matches time zone
            } else if (matcher.group(GMT_OFFSET_OPTIONAL_GROUP) != null) {
                cal.setTimeZone(TimeZone.getTimeZone("GMT" + matcher.group(GMT_OFFSET_OPTIONAL_GROUP)));

            // no time zone specified
            } else {
                cal.setTimeZone(TimeZone.getDefault());
            }

            cal.set(Integer.parseInt(matcher.group(YEAR_GROUP)),
                    Integer.parseInt(matcher.group(MONTH_GROUP)) - 1,  // Java Calendar months start from 0
                    Integer.parseInt(matcher.group(DAY_GROUP)),
                    Integer.parseInt(matcher.group(HOUR_GROUP)),
                    Integer.parseInt(matcher.group(MINUTE_GROUP)),
                    Integer.parseInt(matcher.group(SECOND_GROUP)));

            //matches millisecond
            if (matcher.group(MILLISECOND_OPTIONAL_GROUP) != null) {
                String millis = matcher.group(MILLISECOND_OPTIONAL_GROUP);

                // convert to an integer number of milliseconds
                int scale = millis.length() - 3;
                double d = Double.parseDouble(millis);

                while (scale < 3) {
                    d *= 10;
                    scale++;
                }
                while (scale > 3) {
                    d /= 10;
                    scale--;
                }

                // set the number in the calendar
                cal.set(Calendar.MILLISECOND, (int) Math.round(d));
            }

            return cal.getTime();
        } else {
            throw new ResultExecutionException(str + " is not valid ISO 8601:2004 date format");
        }
    }

    /**
     * Obtains Date from request parameter, if dateParamKey is set.
     *
     * @param request HttpServletRequest
     *
     * @return Date date parsed from request.
     *
     * @throws ResultExecutionException if parsing failed
     */
    private Date getDateParameter(HttpServletRequest request)
        throws ResultExecutionException {
        if (this.dateParamKey == null) {
            return null;
        }

        String dateStr = request.getParameter(this.dateParamKey);

        if (dateStr == null) {
            throw new ResultExecutionException("parameter:" + this.dateParamKey + " does not exist");
        } else {
            return getDate(dateStr);
        }
    }

    /**
     * Creates SearchCriteria from given categories and date conditions.
     *
     * @param categories categories of the SearchCriteria
     * @param date date condition of the SearchCriteria
     *
     * @return SearchCriteria created by the given categories and date
     */
    private SearchCriteria getSearchCriteria(List categories, Date date) {
        //published date or updated date should later than the given date, if it's not null
        Filter dateFilter = null;

        if (date != null) {
            dateFilter = new GreaterThanFilter(SearchCriteria.PUBLISHED_DATE, date);
            dateFilter = new OrFilter(dateFilter, new GreaterThanFilter(SearchCriteria.UPDATED_DATE, date));
        }

        //the category that equals to any one in the list
        Filter catFilter = null;

        if (categories.size() > 0) {
            Iterator iter = categories.iterator();

            String catName = (String) iter.next();
            catFilter = new EqualToFilter(SearchCriteria.CATEGORY, catName);

            while (iter.hasNext()) {
                catName = (String) iter.next();
                catFilter = new OrFilter(catFilter, new EqualToFilter(SearchCriteria.CATEGORY, catName));
            }
        }

        Filter filter = null;

        if ((dateFilter != null) && (catFilter != null)) {
            filter = new AndFilter(dateFilter, catFilter); //restricted by dateFilter and catFilter with and relation
        } else if (dateFilter == null) {
            filter = catFilter;
        } else {
            filter = dateFilter;
        }

        if (filter == null) {
            return null;
        }

        return new SearchCriteriaImpl(filter);
    }

    /**
     * Obtains current userId from request.
     *
     * @param request HttpServletRequest
     *
     * @return userId
     *
     * @throws ResultExecutionException if user does not login in
     */
    private long getUserId(HttpServletRequest request)
        throws ResultExecutionException {
        long userId;
        UserProfile user = LoginHandler.getAuthenticatedUser(request.getSession());

        if (user == null) {
            throw new ResultExecutionException("user does not login yet");
        }

        userId = ((Long) user.getIdentifier()).longValue();

        return userId;
    }

    /**
     * Writes the array of RSSItem to response.
     *
     * @param response HttpServletResponse
     * @param items RSSItem[] to be written
     * @param updateLowerBound a Date representing a lower bound on the feed update date to be
     *        reported.  If any of the specified items are more recent then the most recent of those
     *        dates will be used, but otherwise (and in particular if there are no items) this lower
     *        bound will be used
     *
     * @throws ResultExecutionException if any error occurred
     */
    private void writeAtom10Feed(HttpServletResponse response, RSSItem[] items, Date updateLowerBound)
        throws ResultExecutionException {

        // Set the response headers to tell the client that no caching should be used for such sorts of requests
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setIntHeader("Expires", -1);

        response.setContentType(RESPONSE_CONTENT_TYPE);

        try {
            PrintWriter responseWriter = response.getWriter();

            try {
                Atom10Writer writer = new Atom10Writer();
                Atom10Feed rssFeed = new Atom10Feed(new RSSObjectImpl());
                Atom10Content title = new Atom10Content(new RSSObjectImpl());
                RSSEntity author = new RSSEntityImpl(new RSSObjectImpl());
                RSSEntity name = new RSSEntityImpl(new RSSObjectImpl());
                Date updateDate = updateLowerBound;

                // Feed Title
                title.setElementText("The Ball periodic update");
                rssFeed.setTitle(title);

                // Feed Author
                name.setElementText("The Ball Game Server");
                author.addChildElement("name", name);
                rssFeed.addAuthor(author);

                // Feed ID
                rssFeed.setId("there-can-be-only-one");

                // Feed Update Date
                for (int i = 0; i < items.length; i++) {
                    Date itemUpdate = items[i].getUpdatedDate();

                    if (itemUpdate == null) {
                        itemUpdate = items[i].getPublishedDate();
                    }
                    if (itemUpdate != null
                            && itemUpdate.compareTo(updateDate) > 0) {
                         updateDate = itemUpdate;
                    }
                    rssFeed.addItem(items[i]);
                }
                rssFeed.setUpdatedDate(updateDate);

                // Write the feed
                writer.writeFeed(rssFeed, responseWriter);
            } finally {
                responseWriter.close();
            }
        } catch (IOException e) {
            throw new ResultExecutionException("failed to write content to response", e);
        } catch (RSSWriteException e) {
            throw new ResultExecutionException("failed to write RSSItme to response", e);
        }
    }
}

